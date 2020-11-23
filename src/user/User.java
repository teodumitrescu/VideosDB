package user;

import entertainment.Season;
import fileio.UserInputData;
import fileio.Writer;
import main.Database;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class User {
    private String username;
    private String subscription;
    private Map<String, Integer> history;
    private ArrayList<String> favourite;
    private Map<String, Double> ratings = new HashMap<>();

    public User(final UserInputData userData) {
        this.username = userData.getUsername();
        this.subscription = userData.getSubscriptionType();
        this.history = userData.getHistory();
        this.favourite = userData.getFavoriteMovies();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(final String subscription) {
        this.subscription = subscription;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(final Map<String, Integer> history) {
        this.history = history;
    }

    public ArrayList<String> getFavourite() {
        return favourite;
    }

    public void setFavourite(final ArrayList<String> favourite) {
        this.favourite = favourite;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(final Map<String, Double> ratings) {
        this.ratings = ratings;
    }

    public JSONObject makeFavourite(final String video, final int id, final Writer fileWriter) throws IOException {
        JSONObject object = null;
        String message = null;
        if (history.containsKey(video)) {
            if (!favourite.contains(video)) {
                favourite.add(video);
                message = "success -> " + video + " was added as favourite";
            } else {
                message = "error -> " + video + " is already in favourite list";
            }
        } else {
            message = "error -> " + video + "" + " is not seen";
        }
        object = fileWriter.writeFile(id, "", message);
        return object;
    }

    public JSONObject viewVideo(final String video, final int id, final Writer fileWriter) throws IOException {

        String message = null;
        JSONObject object;
        int oldValue = 0;
        if (history.containsKey(video)) {
            oldValue = history.get(video);
        }
        int newValue = oldValue + 1;
        history.put(video, newValue);
        message = "success -> " + video + " was viewed with total views of "
                + newValue;
        object = fileWriter.writeFile(id, "", message);
        return object;
    }

    public JSONObject rateVideo(final String video, final double rating,
                                final int id, final Writer fileWriter) throws IOException {
        String message = null;
        JSONObject object;
        if (history.containsKey(video)) {
            if (!ratings.containsKey(video)) {
                ratings.put(video, rating);
                message = "success -> " + video + " was rated with "
                        + rating + " by " + this.getUsername();
            }
        } else {
            message = "error -> " + video + "" + " is not seen";
        }
        object = fileWriter.writeFile(id, "", message);
        return object;
    }

    public JSONObject rateVideo(final String video, final double rating, final int season,
                          final int id, final Writer fileWriter) throws IOException {
        String message = null;
        JSONObject object;
        if (history.containsKey(video)) {
            if (!ratings.containsKey(video)) {
                Season crtSeason = Database.getInstance().getSeriesMap().get(video).getSeasons().get(season - 1);
                crtSeason.getRatings().add(rating);
                message = "success -> " + video + " was rated with "
                        + rating + " by " + this.getUsername();
            }
        } else {
            message = "error -> " + video + "" + " is not seen";
        }
        object = fileWriter.writeFile(id, "", message);
        return object;
    }

}
