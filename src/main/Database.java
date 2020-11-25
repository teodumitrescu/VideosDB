package main;

import actor.Actor;
import actor.ActorsAwards;
import comparators.*;
import entertainment.Film;
import entertainment.Series;
import fileio.Writer;
import org.json.simple.JSONObject;
import user.User;

import java.io.IOException;
import java.util.*;

public final class Database {

    private final Map<String, User> usersMap;
    private final Map<String, Actor> actorsMap;
    private final Map<String, Film> filmsMap;
    private final Map<String, Series> seriesMap;

    private static Database instance = null;

    private Database() {

        this.usersMap = new HashMap<>();
        this.actorsMap = new HashMap<>();
        this.filmsMap = new HashMap<>();
        this.seriesMap = new HashMap<>();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void emptyDatabase() {
        this.usersMap.clear();
        this.actorsMap.clear();
        this.filmsMap.clear();
        this.seriesMap.clear();
    }

    public Map<String, Actor> getActorsMap() {
        return actorsMap;
    }

    public Map<String, User> getUsersMap() {
        return usersMap;
    }

    public Map<String, Film> getFilmsMap() {
        return filmsMap;
    }

    public Map<String, Series> getSeriesMap() {
        return seriesMap;
    }

    public JSONObject sortForQuery(final String objType, final String sortType, final int number, final List<List<String>> filters,
                                   final String criteria, final int id, final Writer fileWriter) throws IOException {
        JSONObject object = null;
        String message = "Query result: [";
        int finalNum = number;
        switch (objType) {
            case "users":

                Collection<User> usersCollection = Database.getInstance().getUsersMap().values();
                List<User> usersList = new ArrayList<>(usersCollection);
                List<User> usersFinalList = new ArrayList<>(usersCollection);

                for (User crtUser : usersList) {
                    if (crtUser.getRatings().size() == 0) {
                        usersFinalList.remove(crtUser);
                    }
                }
                Collections.sort(usersFinalList, new UsersActiveComparator());


                if (sortType.equals("desc")) {
                    Collections.reverse((usersFinalList));
                }
                if (usersFinalList.size() < number) {
                    finalNum = usersFinalList.size();
                }
                for (int i = 0; i < finalNum; i++) {
                    message = message + usersFinalList.get(i).getUsername();
                    if (i != (finalNum - 1)) {
                        message += ", ";
                    }
                }

                break;
            case "actors":

                Collection<Actor> actorsCollection = Database.getInstance().getActorsMap().values();
                List<Actor> actorsList = new ArrayList<>(actorsCollection);
                List<Actor> actorsFinalList = new ArrayList<>(actorsCollection);

                if (criteria.equals("average")) {

                    for (Actor crtActor : actorsList) {
                        if (crtActor.computeAverage() == 0) {
                            actorsFinalList.remove(crtActor);

                        }
                    }

                    Collections.sort(actorsFinalList, new ActorsRatingComparator());

                } else if (criteria.equals("awards")) {
                    for (Actor crtActor : actorsList) {
                        for (String awardToCheck : filters.get(3)) {
                            if (!crtActor.getAwards().containsKey(ActorsAwards.valueOf(awardToCheck))) {
                                actorsFinalList.remove(crtActor);
                                break;
                            }
                        }
                    }

                    Collections.sort(actorsFinalList, new ActorsAwardsComparator());

                } else if (criteria.equals("filter_description")) {
                    for (Actor crtActor : actorsList) {
                        for (String wordToCheck : filters.get(2)) {
                            if (!crtActor.getKeywords().contains(wordToCheck)) {
                                actorsFinalList.remove(crtActor);
                                break;
                            }
                        }
                    }

                    Collections.sort(actorsFinalList, new ActorsAlphabeticComparator());
                }
                if (sortType.equals("desc")) {
                    Collections.reverse(actorsFinalList);
                }

                if (actorsFinalList.size() < number) {
                    finalNum = actorsFinalList.size();
                }
                for (int i = 0; i < finalNum; i++) {
                    message = message + actorsFinalList.get(i).getName();
                    if (i != (finalNum - 1)) {
                        message += ", ";
                    }
                }
                break;
            case "shows":

                Collection<Series> seriesCollection = Database.getInstance().getSeriesMap().values();
                List<Series> seriesList = new ArrayList<>(seriesCollection);
                List<Series> seriesFinalList = new ArrayList<>(seriesCollection);

                for (Series crtSerial : seriesList) {

                    String yearString = Integer.toString(crtSerial.getYear());

                    if (!filters.get(0).contains(yearString)) {
                        seriesFinalList.remove(crtSerial);
                    }
                    for (String genreToCheck : filters.get(1)) {
                        if (!crtSerial.getGenres().contains(genreToCheck)) {
                            seriesFinalList.remove(crtSerial);
                        }
                    }
                }

                if (criteria.equals("ratings")) {
                    List<Series> toRemove = new ArrayList<>();
                    for (Series serial : seriesFinalList) {
                        if (serial.computeRating() == 0) {
                            toRemove.add(serial);
                        }
                    }
                    for (Series serial : toRemove) {
                        seriesFinalList.remove(serial);
                    }

                    Collections.sort(seriesFinalList, new VideosRatingComparator());
                } else if (criteria.equals("favorite")) {

                    List<Series> toRemove = new ArrayList<>();
                    for (Series serial : seriesFinalList) {
                        if (serial.getMarkedFavourite() == 0) {
                            toRemove.add(serial);
                        }
                    }
                    for (Series serial : toRemove) {
                        seriesFinalList.remove(serial);
                    }
                    Collections.sort(seriesFinalList, new VideosFavoriteComparator());
                } else if (criteria.equals("longest")) {
                    Collections.sort(seriesFinalList, new VideosLongestComparator());
                } else if (criteria.equals("most_viewed")) {

                    List<Series> toRemove = new ArrayList<>();
                    for (Series serial : seriesFinalList) {
                        if (serial.getTotalViews() == 0) {
                            toRemove.add(serial);
                        }
                    }
                    for (Series serial : toRemove) {
                        seriesFinalList.remove(serial);
                    }
                    Collections.sort(seriesFinalList, new VideosViewsComparator());
                }

                if (sortType.equals("desc")) {
                    Collections.reverse(seriesFinalList);
                }

                if (seriesFinalList.size() < number) {
                    finalNum = seriesFinalList.size();
                }
                for (int i = 0; i < finalNum; i++) {
                    message = message + seriesFinalList.get(i).getName();
                    if (i != (finalNum - 1)) {
                        message += ", ";
                    }
                }
                break;
            case "movies":

                Collection<Film> filmsCollection = Database.getInstance().getFilmsMap().values();
                List<Film> filmsList = new ArrayList<>(filmsCollection);
                List<Film> filmsFinalList = new ArrayList<>(filmsCollection);

                for (Film crtFilm : filmsList) {

                    String yearString = Integer.toString(crtFilm.getYear());

                    if (!filters.get(0).contains(yearString)) {
                        filmsFinalList.remove(crtFilm);
                    }
                    for (String genreToCheck : filters.get(1)) {
                        if (!crtFilm.getGenres().contains(genreToCheck)) {
                            filmsFinalList.remove(crtFilm);
                        }
                    }
                }

                if (criteria.equals("rating")) {
                    Collections.sort(filmsFinalList, new VideosRatingComparator());
                } else if (criteria.equals("favorite")) {
                    List<Film> toRemove = new ArrayList<>();
                    for (Film film : filmsFinalList) {
                        if (film.getMarkedFavourite() == 0) {
                            toRemove.add(film);
                        }
                    }
                    for (Film film : toRemove) {
                        filmsFinalList.remove(film);
                    }
                    Collections.sort(filmsFinalList, new VideosFavoriteComparator());
                } else if (criteria.equals("longest")) {
                    Collections.sort(filmsFinalList, new VideosLongestComparator());
                } else if (criteria.equals("most_viewed")) {
                    List<Film> toRemove = new ArrayList<>();
                    for (Film film : filmsFinalList) {
                        if (film.getTotalViews() == 0) {
                            toRemove.add(film);
                        }
                    }
                    for (Film film : toRemove) {
                        filmsFinalList.remove(film);
                    }
                    Collections.sort(filmsFinalList, new VideosViewsComparator());

                }

                if (sortType.equals("desc")) {
                    Collections.reverse(filmsFinalList);
                }

                if (filmsFinalList.size() < number) {
                    finalNum = filmsFinalList.size();
                }
                for (int i = 0; i < finalNum; i++) {
                    message = message + filmsFinalList.get(i).getName();
                    if (i != (finalNum - 1)) {
                        message += ", ";
                    }
                }
                break;
            default:
        }
        message += "]";
        object = fileWriter.writeFile(id, "", message);
        return object;
    }

}
