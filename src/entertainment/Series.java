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

    /**
     * calculates the total duration of a
     * series by suming up all the durations
     * of the seasons
     * @return total sum of durations
     */
    public int computeTotalDuration() {
        int maketotalDuration = 0;
        for (Season season : this.getSeasons()) {
            maketotalDuration += season.getDuration();
        }
        return maketotalDuration;
    }

    @Override
    public double computeRating() {

        double totalSumRating = 0;

        //we compute the average rating per season
        //and then use the values to calculate the average per series
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
