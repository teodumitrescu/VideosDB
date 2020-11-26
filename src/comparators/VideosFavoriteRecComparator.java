package comparators;

import entertainment.Video;

import java.util.Comparator;
import java.util.List;

//comparator used to sort videos by the number they were marked as favorite
//by a user, and then by who appeared first in the database
public final class VideosFavoriteRecComparator implements Comparator<Video> {
    private final List<Video> favVideos;

    public VideosFavoriteRecComparator(final List<Video> favVideos) {
        this.favVideos = favVideos;
    }

    @Override
    public int compare(final Video o1, final Video o2) {

        if (Integer.compare(o1.getMarkedFavourite(), o2.getMarkedFavourite()) == 0) {
            return Integer.compare(favVideos.indexOf(o2), favVideos.indexOf(o1));
        }
        return Integer.compare(o1.getMarkedFavourite(), o2.getMarkedFavourite());
    }
}
