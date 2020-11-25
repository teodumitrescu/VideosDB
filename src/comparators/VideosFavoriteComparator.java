package comparators;

import entertainment.Video;

import java.util.Comparator;

public final class VideosFavoriteComparator implements Comparator<Video> {
    @Override
    public int compare(final Video o1, final Video o2) {
        if (o1.getMarkedFavourite() < o2.getMarkedFavourite()) {
            return 1;
        }
        return 0;
    }
}
