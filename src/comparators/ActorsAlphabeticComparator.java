package comparators;

import actor.Actor;

import java.util.Comparator;

public final class ActorsAlphabeticComparator implements Comparator<Actor> {
    @Override
    public int compare(final Actor o1, final Actor o2) {
        int result = o1.getName().compareTo(o2.getName());
        if (result < 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
