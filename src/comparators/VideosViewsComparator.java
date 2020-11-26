package comparators;

import entertainment.Video;
import main.Database;

import java.util.Comparator;

public final class VideosViewsComparator implements Comparator<Video> {
    @Override
    public int compare(final Video o1, final Video o2) {
        int v1;
        int v2;
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

        if (Integer.compare(v1, v2) == 0) {
            return o1.getName().compareTo(o2.getName());
        }
        return Integer.compare(v1, v2);

    }
}
