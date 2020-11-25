package comparators;

import entertainment.Video;
import main.Database;

import java.util.Comparator;

public final class VideosLongestComparator implements Comparator<Video> {
    @Override
    public int compare(final Video o1, final Video o2) {
        double d1;
        double d2;
        if (Database.getInstance().getFilmsMap().containsKey(o1.getName())) {
            d1 = Database.getInstance().getFilmsMap().get(o1.getName()).getDuration();
        } else {
            d1 = Database.getInstance().getSeriesMap().get(o1.getName()).getTotalDuration();
        }
        if (Database.getInstance().getFilmsMap().containsKey(o2.getName())) {
            d2 = Database.getInstance().getFilmsMap().get(o2.getName()).getDuration();
        } else {
            d2 = Database.getInstance().getSeriesMap().get(o2.getName()).getTotalDuration();
        }

        if (d1 < d2) {
            return 1;
        }
        return 0;
    }
}
