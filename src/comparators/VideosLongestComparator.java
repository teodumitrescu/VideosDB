package comparators;

import entertainment.Video;
import main.Database;

import java.util.Comparator;

//comparator used to sort videos by their total duration
public final class VideosLongestComparator implements Comparator<Video> {

    @Override
    public int compare(final Video o1, final Video o2) {

        int d1;
        int d2;

        //each video stores a precomputed value of the total duration
        if (Database.getInstance().getFilmsMap().containsKey(o1.getName())) {
            d1 = Database.getInstance().getFilmsMap().get(o1.getName()).getDuration();
        } else {
            d1 = Database.getInstance().getSeriesMap().get(o1.getName()).computeTotalDuration();
        }
        if (Database.getInstance().getFilmsMap().containsKey(o2.getName())) {
            d2 = Database.getInstance().getFilmsMap().get(o2.getName()).getDuration();
        } else {
            d2 = Database.getInstance().getSeriesMap().get(o2.getName()).computeTotalDuration();
        }

        if (Integer.compare(d1, d2) == 0) {
            return o1.getName().compareTo(o2.getName());
        }
        return Integer.compare(d1, d2);
    }
}
