package comparators;

import user.User;

import java.util.Comparator;

public final class UsersActiveComparator implements Comparator<User> {
    @Override
    public int compare(final User o1, final User o2) {
        if (o1.getRatings().size() < o2.getRatings().size()) {
            return 1;
        }
        return 0;
    }
}
