package comparators;

import actor.Actor;

import java.util.Collection;
import java.util.Comparator;

public final class ActorsAwardsComparator implements Comparator<Actor> {

    @Override
    public int compare(final Actor o1, final Actor o2) {

        int totalAwards1 = 0;
        int totalAwards2 = 0;

        Collection<Integer> numbersEachAward1 = o1.getAwards().values();
        for (Integer crtAward : numbersEachAward1) {
            totalAwards1 += crtAward;
        }

        Collection<Integer> numbersEachAward2 = o2.getAwards().values();
        for (Integer crtAward : numbersEachAward2) {
            totalAwards2 += crtAward;
        }

        if (totalAwards1 < totalAwards2) {
            return 1;
        }
        return 0;
    }
}
