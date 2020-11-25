package actor;

import entertainment.Film;
import entertainment.Series;
import fileio.ActorInputData;
import main.Database;

import java.util.ArrayList;
import java.util.Map;

public final class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> keywords = new ArrayList<>();
    private ArrayList<String> filmography;
    private Map<ActorsAwards, Integer> awards;

    public Actor(final ActorInputData actorData) {
        this.name = actorData.getName();
        this.careerDescription = actorData.getCareerDescription();
        this.filmography = actorData.getFilmography();
        this.awards = actorData.getAwards();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(final Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(final ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public void findKeywords() {
        String[] splitWords = this.careerDescription.split("\\W+");
        for (String word : splitWords) {
            this.keywords.add(word.toLowerCase());
        }
    }

    public Double computeAverage() {
        double sumRatings = 0;
        int numVideos = 0;

        for (String video : this.getFilmography()) {
            if (Database.getInstance().getSeriesMap().containsKey(video)) {

                Series series = Database.getInstance().getSeriesMap().get(video);
                sumRatings += series.computeRating();

                if (series.computeRating() != 0) {
//                    numVideos += series.getNumberOfSeasons();
                    numVideos += 1;
                }
            }
            if (Database.getInstance().getFilmsMap().containsKey(video)) {
                Film film = Database.getInstance().getFilmsMap().get(video);
                sumRatings += film.computeRating();
                if (film.computeRating() != 0) {
                    numVideos += 1;
                }
            }
        }
        return (numVideos != 0) ? sumRatings / numVideos : 0;
    }

}
