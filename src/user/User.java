package user;

import entertainment.Film;
import entertainment.Season;
import entertainment.Series;
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

    /**
     * function that adds to the TotalViews variable of each video
     * the views that every user who is added
     * in the database has in its history
     */
    public void addHistoryViews() {
        for (String crtTitle : this.history.keySet()) {
            if (Database.getInstance().getSeriesMap().containsKey(crtTitle)) {
                int oldViews = Database.getInstance().getSeriesMap().get(crtTitle).getTotalViews();
                Database.getInstance().getSeriesMap().get(crtTitle).setTotalViews(oldViews
                        + history.get(crtTitle));
            } else {
                int oldViews = Database.getInstance().getFilmsMap().get(crtTitle).getTotalViews();
                Database.getInstance().getFilmsMap().get(crtTitle).setTotalViews(oldViews
                        + history.get(crtTitle));
            }
        }
    }

    /**
     * function that increments with 1 the markedFavorite variable of each video
     * that every user who is added in the database has in its favorite list
     */
    public void addFavoriteShows() {
        for (String crtTitle : this.favourite) {
            if (Database.getInstance().getSeriesMap().containsKey(crtTitle)) {
                int old = Database.getInstance().getSeriesMap().get(crtTitle).getMarkedFavourite();
                Database.getInstance().getSeriesMap().get(crtTitle).setMarkedFavourite(old + 1);
            } else {
                int old = Database.getInstance().getFilmsMap().get(crtTitle).getMarkedFavourite();
                Database.getInstance().getFilmsMap().get(crtTitle).setMarkedFavourite(old + 1);
            }
        }
    }

    /**
     * function that adds a video to the favorite list
     * of a certain user, in case he has seen it and
     * not added it before
     * @param video the video to be added
     * @param id the action id
     * @param fileWriter the Writer used to wirte in files
     * @return JSON object with the message of success or error
     * of the action
     * @throws IOException
     */
    public JSONObject makeFavourite(final String video, final int id,
                                    final Writer fileWriter) throws IOException {
        JSONObject object = null;
        String message = null;
        if (history.containsKey(video)) {
            if (!favourite.contains(video)) {
                favourite.add(video);
                if (Database.getInstance().getSeriesMap().containsKey(video)) {
                    int old = Database.getInstance().getSeriesMap().get(video).getMarkedFavourite();
                    Database.getInstance().getSeriesMap().get(video).setMarkedFavourite(old + 1);
                } else {
                    int old = Database.getInstance().getFilmsMap().get(video).getMarkedFavourite();
                    Database.getInstance().getFilmsMap().get(video).setMarkedFavourite(old + 1);
                }
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

    /**
     *function to mark a video as seen and add it to the history
     * of a certain user
     * @param video the video to be added
     * @param id the action id
     * @param fileWriter the Writer used to write in files
     * @return JSON object that contains
     * the error of succes message for the action
     * @throws IOException
     */
    public JSONObject viewVideo(final String video, final int id,
                                final Writer fileWriter) throws IOException {

        String message = null;
        JSONObject object;
        int oldValue = 0;
        if (history.containsKey(video)) {
            oldValue = history.get(video);
        }
        int newValue = oldValue + 1;
        history.put(video, newValue);
        if (Database.getInstance().getSeriesMap().containsKey(video)) {
            int oldViews = Database.getInstance().getSeriesMap().get(video).getTotalViews();
            Database.getInstance().getSeriesMap().get(video).setTotalViews(oldViews + 1);
        } else {
            int oldViews = Database.getInstance().getFilmsMap().get(video).getTotalViews();
            Database.getInstance().getFilmsMap().get(video).setTotalViews(oldViews + 1);
        }

        message = "success -> " + video + " was viewed with total views of "
                + newValue;
        object = fileWriter.writeFile(id, "", message);
        return object;
    }

    /**
     * function to rate a film by a certain user
     * @param video the video to be rated
     * @param rating the grade
     * @param id the action id
     * @param fileWriter the Writer used for writing in files
     * @return a JSON object with the error or success
     * message of he action
     * @throws IOException
     */
    public JSONObject rateVideo(final String video, final double rating,
                                final int id, final Writer fileWriter) throws IOException {
        String message = null;
        JSONObject object;
        if (history.containsKey(video)) {
            if (!ratings.containsKey(video)) {
                ratings.put(video, rating);
                Film film = Database.getInstance().getFilmsMap().get(video);
                film.getRatings().add(rating);
                message = "success -> " + video + " was rated with "
                        + rating + " by " + this.getUsername();
            } else {
                message = "error -> " + video + " has been already rated";
            }
        } else {
            message = "error -> " + video + "" + " is not seen";
        }
        object = fileWriter.writeFile(id, "", message);
        return object;
    }

    /**
     * function to rate a season from a series by a certain user
     * @param video the series containing the season
     * @param rating the grade
     * @param season the season to be rated
     * @param id the action id
     * @param fileWriter the Writer used for writing in files
     * @return a JSON object with the error or success
     * message of he action
     * @throws IOException
     */
    public JSONObject rateVideo(final String video, final double rating, final int season,
                          final int id, final Writer fileWriter) throws IOException {
        String message = null;
        JSONObject object;
        if (history.containsKey(video)) {
            String newKey = video + season;
            if (!ratings.containsKey(newKey)) {
                ratings.put(newKey, rating);
                Series auxSeriesVar = Database.getInstance().getSeriesMap().get(video);
                Season crtSeason = auxSeriesVar.getSeasons().get(season - 1);
                crtSeason.getRatings().add(rating);
                message = "success -> " + video + " was rated with "
                        + rating + " by " + this.getUsername();
            } else {
                message = "error -> " + video + " has been already rated";
            }
        } else {
            message = "error -> " + video + "" + " is not seen";
        }
        object = fileWriter.writeFile(id, "", message);
        return object;
    }

}
