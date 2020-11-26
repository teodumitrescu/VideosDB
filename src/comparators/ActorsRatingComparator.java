package comparators;

import actor.Actor;

import java.util.Comparator;

//comparator used in sorting actors by their average rating
public final class ActorsRatingComparator implements Comparator<Actor> {

    @Override
    public int compare(final Actor o1, final Actor o2) {

        if (o1.computeAverage() < o2.computeAverage()) {
            return -1;
        } else if (o1.computeAverage() > o2.computeAverage()) {
            return 1;
        } else {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
