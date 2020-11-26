package comparators;

import java.util.Comparator;
import java.util.Map;

public final class GenreComparator implements Comparator<String> {

    private Map<String, Integer> genrePopularity;

    public GenreComparator(final Map<String, Integer> genrePopularity) {
        this.genrePopularity = genrePopularity;
    }

    @Override
    public int compare(final String o1, final String o2) {

        return Integer.compare(genrePopularity.get(o1), genrePopularity.get(o2));
    }
}
