package comparators;

import entertainment.Video;
import main.Database;

import java.util.Comparator;
import java.util.List;

public final class VideosFavoriteComparator implements Comparator<Video> {
    @Override
    public int compare(final Video o1, final Video o2) {


        if (Integer.compare(o1.getMarkedFavourite(), o2.getMarkedFavourite()) == 0) {
//            return Integer.compare(Database.getInstance().getVideosList().indexOf(o1),
//                    Database.getInstance().getVideosList().indexOf(o2));
            return o1.getName().compareTo(o2.getName());
        }
        return Integer.compare(o1.getMarkedFavourite(), o2.getMarkedFavourite());
    }
}
