package comparators;

import entertainment.Video;
import main.Database;
import java.util.Comparator;

public final class VideosRatingComparator implements Comparator<Video> {
    @Override
    public int compare(final Video o1, final Video o2) {
        double r1;
        double r2;
        if (Database.getInstance().getFilmsMap().containsKey(o1.getName())) {
            r1 = Database.getInstance().getFilmsMap().get(o1.getName()).computeRating();
        } else {
            r1 = Database.getInstance().getSeriesMap().get(o1.getName()).computeRating();
        }
        if (Database.getInstance().getFilmsMap().containsKey(o2.getName())) {
            r2 = Database.getInstance().getFilmsMap().get(o2.getName()).computeRating();
        } else {
            r2 = Database.getInstance().getSeriesMap().get(o2.getName()).computeRating();
        }

        if (r1 < r2) {
            return 1;
        }
        return 0;
    }
}
