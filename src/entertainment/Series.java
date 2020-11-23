package entertainment;

import fileio.SerialInputData;

import java.util.ArrayList;

public final class Series extends Video {

    private int numberOfSeasons;
    private ArrayList<Season> seasons;

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
}
