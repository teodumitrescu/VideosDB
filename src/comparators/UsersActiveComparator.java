package comparators;

import user.User;

import java.util.Comparator;

public final class UsersActiveComparator implements Comparator<User> {
    @Override
    public int compare(final User o1, final User o2) {

        if (Integer.compare(o1.getRatings().size(), o2.getRatings().size()) == 0) {
            return o1.getUsername().compareTo(o2.getUsername());
        }
        return Integer.compare(o1.getRatings().size(), o2.getRatings().size());
    }
}
