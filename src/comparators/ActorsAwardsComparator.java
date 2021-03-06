package comparators;

import actor.Actor;

import java.util.Collection;
import java.util.Comparator;

//comparator used in sorting actors by their number of awards
public final class ActorsAwardsComparator implements Comparator<Actor> {

    @Override
    public int compare(final Actor o1, final Actor o2) {

        int totalAwards1 = 0;
        int totalAwards2 = 0;

        //we go through the awards an actor has and sum them
        Collection<Integer> numbersEachAward1 = o1.getAwards().values();
        for (Integer crtAward : numbersEachAward1) {
            totalAwards1 += crtAward;
        }

        Collection<Integer> numbersEachAward2 = o2.getAwards().values();
        for (Integer crtAward : numbersEachAward2) {
            totalAwards2 += crtAward;
        }

        if (Integer.compare(totalAwards1, totalAwards2) == 0) {
            return o1.getName().compareTo(o2.getName());
        }
        return Integer.compare(totalAwards1, totalAwards2);

    }
}
