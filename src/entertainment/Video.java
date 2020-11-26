package entertainment;

import fileio.MovieInputData;
import fileio.SerialInputData;
import java.util.ArrayList;

// the class is abstract because it's a
// base for the Film and Series classes
public abstract class Video {
    private String name;
    private int year;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private int markedFavourite = 0;
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

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final int getYear() {
        return year;
    }

    public final void setYear(final int year) {
        this.year = year;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public final void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public final int getMarkedFavourite() {
        return markedFavourite;
    }

    public final void setMarkedFavourite(final int markedFavourite) {
        this.markedFavourite = markedFavourite;
    }

    /**
     * Calculates the rating for a video
     * @return
     */
    public abstract double computeRating();

    public final ArrayList<String> getActors() {
        return actors;
    }

    public final void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public final int getTotalViews() {
        return totalViews;
    }

    public final void setTotalViews(final int totalViews) {
        this.totalViews = totalViews;
    }
}
