package comparators;

import java.util.Comparator;
import java.util.Map;

//comparator used to sort genres by their popularity
public final class GenreComparator implements Comparator<String> {

    private Map<String, Integer> genrePopularity;

    //we sort them by their corresponding value in the map, where we precomputed
    //how many times videos of that genre were seen
    public GenreComparator(final Map<String, Integer> genrePopularity) {
        this.genrePopularity = genrePopularity;
    }

    @Override
    public int compare(final String o1, final String o2) {
        return Integer.compare(genrePopularity.get(o1), genrePopularity.get(o2));
    }
}
