package entertainment;

import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public final class Film extends Video {

    private int duration;
    private List<Double> ratings = new ArrayList<Double>();

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

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public double computeRating() {
        int numRatings = 0;
        double sumRatings = 0;

        if (!this.getRatings().isEmpty()) {
            for (Double rating : this.getRatings()) {
                sumRatings = sumRatings + rating;
                numRatings += 1;
            }
            double finalRating = sumRatings / numRatings;
            return finalRating;
        }
        return 0;
    }
}
