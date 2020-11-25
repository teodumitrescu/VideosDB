package comparators;

import entertainment.Video;
import main.Database;

import java.util.Comparator;

public final class VideosViewsComparator implements Comparator<Video> {
    @Override
    public int compare(final Video o1, final Video o2) {
        double v1;
        double v2;
        if (Database.getInstance().getFilmsMap().containsKey(o1.getName())) {
            v1 = Database.getInstance().getFilmsMap().get(o1.getName()).getTotalViews();
        } else {
            v1 = Database.getInstance().getSeriesMap().get(o1.getName()).getTotalViews();
        }
        if (Database.getInstance().getFilmsMap().containsKey(o2.getName())) {
            v2 = Database.getInstance().getFilmsMap().get(o2.getName()).getTotalViews();
        } else {
            v2 = Database.getInstance().getSeriesMap().get(o2.getName()).getTotalViews();
        }

        if (v1 < v2) {
            return 1;
        }
        return 0;
    }
}
