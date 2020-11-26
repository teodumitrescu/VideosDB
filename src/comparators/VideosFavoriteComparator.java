package comparators;

import entertainment.Video;

import java.util.Comparator;

//comparator used to sort videos by the number they were marked as favorite
public final class VideosFavoriteComparator implements Comparator<Video> {

    @Override
    public int compare(final Video o1, final Video o2) {

        //we compare the precomputed value stored in each video
        if (Integer.compare(o1.getMarkedFavourite(), o2.getMarkedFavourite()) == 0) {
            return o1.getName().compareTo(o2.getName());
        }
        return Integer.compare(o1.getMarkedFavourite(), o2.getMarkedFavourite());
    }
}
