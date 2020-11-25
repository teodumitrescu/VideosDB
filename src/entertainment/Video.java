package entertainment;

import fileio.MovieInputData;
import fileio.SerialInputData;
import java.util.ArrayList;

public abstract class Video {
    private String name;
    private int year;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private int markedFavourite;
    private int totalViews = 0;

    public Video(final MovieInputData movieData) {
        this.name = movieData.getTitle();
        this.year = movieData.getYear();
        this.genres = movieData.getGenres();
        this.actors = movieData.getCast();
    }

    public Video(final SerialInputData movieData) {
        this.name = movieData.getTitle();
        this.year = movieData.getYear();
        this.genres = movieData.getGenres();
        this.actors = movieData.getCast();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public int getMarkedFavourite() {
        return markedFavourite;
    }

    public void setMarkedFavourite(final int markedFavourite) {
        this.markedFavourite = markedFavourite;
    }

    public abstract double computeRating();

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(final int totalViews) {
        this.totalViews = totalViews;
    }
}
