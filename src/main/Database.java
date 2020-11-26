package main;

import actor.Actor;
import actor.ActorsAwards;
import comparators.*;
import entertainment.Film;
import entertainment.Series;
import entertainment.Video;
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

    private final List<Film> filmsList;
    private final List<Series> seriesList;
    private final List<Video> videosList;

    private static Database instance = null;

    private Database() {

        this.usersMap = new HashMap<>();
        this.actorsMap = new HashMap<>();
        this.filmsMap = new HashMap<>();
        this.seriesMap = new HashMap<>();
        this.filmsList = new ArrayList<>();
        this.seriesList = new ArrayList<>();
        this.videosList = new ArrayList<>();
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
        this.filmsList.clear();
        this.seriesList.clear();
        this.videosList.clear();
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

    public List<Film> getFilmsList() {
        return filmsList;
    }

    public List<Series> getSeriesList() {
        return seriesList;
    }

    public List<Video> getVideosList() {
        return videosList;
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

                List<Series> seriesListCopy = new ArrayList<Series>(Database.getInstance().seriesList);

                for (Series crtSerial : seriesList) {

                    String yearString = Integer.toString(crtSerial.getYear());

                    if (filters.get(0).get(0) != null && !filters.get(0).contains(yearString)) {
                        seriesListCopy.remove(crtSerial);
                    }
                    if (filters.get(1).get(0) != null) {
                        for (String genreToCheck : filters.get(1)) {
                            if (!crtSerial.getGenres().contains(genreToCheck)) {
                                seriesListCopy.remove(crtSerial);
                            }
                        }
                    }
                }

                if (criteria.equals("ratings")) {
                    List<Series> toRemove = new ArrayList<>();
                    for (Series serial : seriesListCopy) {
                        if (serial.computeRating() == 0) {
                            toRemove.add(serial);
                        }
                    }
                    for (Series serial : toRemove) {
                        seriesListCopy.remove(serial);
                    }

                    Collections.sort(seriesListCopy, new VideosRatingComparator());
                } else if (criteria.equals("favorite")) {

                    List<Series> toRemove = new ArrayList<>();
                    for (Series serial : seriesListCopy) {
                        if (serial.getMarkedFavourite() == 0) {
                            toRemove.add(serial);
                        }
                    }
                    for (Series serial : toRemove) {
                        seriesListCopy.remove(serial);
                    }
                    Collections.sort(seriesListCopy, new VideosFavoriteComparator());
                } else if (criteria.equals("longest")) {
                    Collections.sort(seriesListCopy, new VideosLongestComparator());
                } else if (criteria.equals("most_viewed")) {

                    List<Series> toRemove = new ArrayList<>();
                    for (Series serial : seriesListCopy) {
                        if (serial.getTotalViews() == 0) {
                            toRemove.add(serial);
                        }
                    }
                    for (Series serial : toRemove) {
                        seriesListCopy.remove(serial);
                    }
                    Collections.sort(seriesListCopy, new VideosViewsComparator());
                }

                if (sortType.equals("desc")) {
                    Collections.reverse(seriesListCopy);
                }

                if (seriesListCopy.size() < number) {
                    finalNum = seriesListCopy.size();
                }
                for (int i = 0; i < finalNum; i++) {
                    message = message + seriesListCopy.get(i).getName();
                    if (i != (finalNum - 1)) {
                        message += ", ";
                    }
                }
                break;
            case "movies":

                List<Film> filmsListCopy = new ArrayList<Film>(Database.getInstance().filmsList);


                for (Film crtFilm : filmsList) {

                    String yearString = Integer.toString(crtFilm.getYear());

                    if (filters.get(0).get(0) != null && !filters.get(0).contains(yearString)) {
                        filmsListCopy.remove(crtFilm);
                    }
                    if (filters.get(1).get(0) != null) {
                        for (String genreToCheck : filters.get(1)) {
                            if (!crtFilm.getGenres().contains(genreToCheck)) {
                                filmsListCopy.remove(crtFilm);
                            }
                        }
                    }
                }

                if (criteria.equals("ratings")) {
                    List<Film> toRemove = new ArrayList<>();
                    for (Film film : filmsListCopy) {
                        if (film.computeRating() == 0) {
                            toRemove.add(film);
                        }
                    }
                    for (Film film : toRemove) {
                        filmsListCopy.remove(film);
                    }
                    Collections.sort(filmsListCopy, new VideosRatingComparator());
                } else if (criteria.equals("favorite")) {

                    List<Film> toRemove = new ArrayList<>();
                    for (Film film : filmsListCopy) {
                        if (film.getMarkedFavourite() == 0) {
                            toRemove.add(film);
                        }
                    }
                    for (Film film : toRemove) {
                        filmsListCopy.remove(film);
                    }
                    Collections.sort(filmsListCopy, new VideosFavoriteComparator());
                } else if (criteria.equals("longest")) {
                    Collections.sort(filmsListCopy, new VideosLongestComparator());
                } else if (criteria.equals("most_viewed")) {

                    List<Film> toRemove = new ArrayList<>();
                    for (Film film : filmsListCopy) {
                        if (film.getTotalViews() == 0) {
                            toRemove.add(film);
                        }
                    }
                    for (Film film : toRemove) {
                        filmsListCopy.remove(film);
                    }
                    Collections.sort(filmsListCopy, new VideosViewsComparator());

                }

                if (sortType.equals("desc")) {
                    Collections.reverse(filmsListCopy);
                }

                if (filmsListCopy.size() < number) {
                    finalNum = filmsListCopy.size();
                }
                for (int i = 0; i < finalNum; i++) {
                    message = message + filmsListCopy.get(i).getName();
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

    public JSONObject makeRecommendation(final String type, final String username, final int id, final Writer fileWriter) throws IOException {
        JSONObject object = null;
        String message = "";
        User user = Database.getInstance().usersMap.get(username);
        switch (type) {
            case "standard":
                int found = 0;
                for(Video video : Database.getInstance().videosList) {
                    if (!user.getHistory().containsKey(video.getName())) {
                        message = "StandardRecommendation result: " + video.getName();
                        found = 1;
                        break;
                    }
                }
                if (found == 0) {
                    message = "StandardRecommendation cannot be applied!";
                }
                break;
            case "best_unseen":

                List<Video> allUnseenVids = new ArrayList<>();
                for (Video video : Database.getInstance().getVideosList()) {
                    if (!user.getHistory().containsKey(video.getName())) {
                        allUnseenVids.add(video);
                    }
                }

                Collections.sort(allUnseenVids, new BestUnseenComparator(allUnseenVids));
                if (allUnseenVids.size() > 0) {
                    message = "BestRatedUnseenRecommendation result: ";
                         message += allUnseenVids.get(0).getName();
                } else {
                    message = "BestRatedUnseenRecommendation cannot be applied!";
                }
                break;
            case "popular":

                if (user.getSubscription().equals("BASIC")) {
                    message = "PopularRecommendation cannot be applied!";
                } else {
                    Map<String, Integer> genrePopularity = new HashMap<>();
                    for (Video video : Database.getInstance().getVideosList()) {
                        for (String genre : video.getGenres()) {
                            int prev = 0;
                            if (genrePopularity.containsKey(genre)) {
                                prev = genrePopularity.get(genre);
                            }
                            genrePopularity.put(genre, prev + video.getTotalViews());
                        }
                    }

                    Collection<String> genreCollection = genrePopularity.keySet();
                    List<String> genreList = new ArrayList<>(genreCollection);

                    Collections.sort(genreList, new GenreComparator(genrePopularity));
                    int isFound = 0;
                    for (String genre : genreList) {
                        for(Video video : Database.getInstance().videosList) {
                            if (!user.getHistory().containsKey(video.getName())) {
                                if (video.getGenres().contains(genre)) {
                                    message = "PopularRecommendation result: " + video.getName();
                                    isFound = 1;
                                    break;
                                }
                            }
                        }
                    }
                    if (isFound == 0) {
                        message = "PopularRecommendation cannot be applied!";
                    }
                }
                break;
            case "favorite":
                if (user.getSubscription().equals("BASIC")) {
                    message = "Recommendation cannot be applied!";
                } else {
                    List<Video> allUnseenVideos = new ArrayList<>();
                    for (Video video : Database.getInstance().getVideosList()) {
                        if (!user.getHistory().containsKey(video.getName())) {
                            allUnseenVideos.add(video);
                        }
                    }

                    List<Video> tempList = allUnseenVideos;
                    Collections.sort(allUnseenVideos, new VideosFavoriteRecComparator(tempList));
                    List<Video> toRemove = new ArrayList<>();
                    for (Video crtVideo : allUnseenVideos) {
                        if (crtVideo.getMarkedFavourite() == 0) {
                            toRemove.add(crtVideo);
                        }
                    }
                    for (Video crtVideo : toRemove) {
                        allUnseenVideos.remove(crtVideo);
                    }

                    Collections.reverse(allUnseenVideos);
                    if (allUnseenVideos.size() > 0) {
                        message = "FavoriteRecommendation result: " + allUnseenVideos.get(0).getName();
                    } else {
                        message = "FavoriteRecommendation cannot be applied!";
                    }
                }
                break;
            default:
                object = null;
        }
        object = fileWriter.writeFile(id, "", message);
        return object;
    }

    public JSONObject searchRec(final String username, final String genre, final int id, final Writer fileWriter) throws IOException {
        JSONObject object = null;
        String message = "";
        User user = Database.getInstance().usersMap.get(username);

        if (user.getSubscription().equals("BASIC")) {
            message = "SearchRecommendation cannot be applied!";
        } else {
            List<Video> allUnseenFromGenre = new ArrayList<>();
            for (Video film : Database.getInstance().getFilmsMap().values()) {
                if (!user.getHistory().containsKey(film.getName())) {
                    if (film.getGenres().contains(genre)) {
                        allUnseenFromGenre.add(film);
                    }
                }
            }
            for (Video serial : Database.getInstance().getSeriesMap().values()) {
                if (!user.getHistory().containsKey(serial.getName())) {
                    if (serial.getGenres().contains(genre)) {
                        allUnseenFromGenre.add(serial);
                    }
                }
            }

            Collections.sort(allUnseenFromGenre, new SearchComparator());
            if (allUnseenFromGenre.size() > 0) {
                message = "SearchRecommendation result: [";
                for (Video video : allUnseenFromGenre) {

                    message = message + video.getName();
                    if (!video.equals(allUnseenFromGenre.get(allUnseenFromGenre.size() - 1))) {
                        message =  message + ", ";
                    }

                }
                message = message + "]";
            } else {
                message = "SearchRecommendation cannot be applied!";
            }


        }
        object = fileWriter.writeFile(id, "", message);
        return object;
    }


}
