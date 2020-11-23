package entertainment;

import fileio.MovieInputData;

public final class Film extends Video {

    private int duration;
    private double rating;

    public Film(final MovieInputData filmData) {

        super(filmData);
        this.duration = filmData.getDuration();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }
}
