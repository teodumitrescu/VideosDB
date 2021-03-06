package comparators;

import actor.Actor;

import java.util.Comparator;

//comparator for an alphabetical sort of actors' names
public final class ActorsAlphabeticComparator implements Comparator<Actor> {

    @Override
    public int compare(final Actor o1, final Actor o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
