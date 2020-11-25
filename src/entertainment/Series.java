package entertainment;

import fileio.SerialInputData;

import java.util.ArrayList;

public final class Series extends Video {

    private int numberOfSeasons;
    private ArrayList<Season> seasons;
    private int totalDuration;

    public Series(final SerialInputData seriesData) {
        super(seriesData);
        this.numberOfSeasons = seriesData.getNumberSeason();
        this.seasons = seriesData.getSeasons();
    }


    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(final int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(final ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(final int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public void computeTotalDuration() {
        for (Season season : this.getSeasons()) {
            this.totalDuration += season.getDuration();
        }
    }

    @Override
    public double computeRating() {

        double totalSumRating = 0;

        for (Season season : this.getSeasons()) {
            double sumRating = 0;
            int numSeasonRatings = 0;

            if (!season.getRatings().isEmpty()) {
                for (Double crtSeasonRating : season.getRatings()) {
                    sumRating = sumRating + crtSeasonRating;
                    numSeasonRatings += 1;
                }
                sumRating = sumRating / numSeasonRatings;
            }
            totalSumRating += sumRating;

        }
            return totalSumRating / numberOfSeasons;

    }
}
