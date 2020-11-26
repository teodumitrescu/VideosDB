package comparators;

import entertainment.Video;
import main.Database;

import java.util.Comparator;
import java.util.List;

//comparator used to sort videos by their rating
public final class BestUnseenComparator implements Comparator<Video> {

    private List<Video> unseenVids;

    //the list has all the unseen videos of a user, appearing in the same order as in
    //the database
    public BestUnseenComparator(final List<Video> unseenVids) {
        this.unseenVids = unseenVids;
    }

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

        //if the ratings are equal, we compare their position in the list of unseen videos
        if (r1 == r2) {
            return Integer.compare(unseenVids.indexOf(o1), unseenVids.indexOf(o2));
        }
        return Double.compare(r2, r1);
    }
}
