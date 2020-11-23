package main;

import actor.Actor;
import entertainment.Film;
import entertainment.Series;
import fileio.Writer;
import net.sf.json.JSONObject;
import user.User;

import java.util.HashMap;
import java.util.Map;

public final class Database {

    private final Map<String, User> usersMap;
    private final Map<String, Actor> actorsMap;
    private final Map<String, Film> filmsMap;
    private final Map<String, Series> seriesMap;

    private static Database instance = null;

    private Database() {
        this.usersMap = new HashMap<>();
        this.actorsMap = new HashMap<>();
        this.filmsMap = new HashMap<>();
        this.seriesMap = new HashMap<>();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Map<String, Actor> getActorsMap() {
        return actorsMap;
    }

    public Map<String, User> getUsersMap() {
        return usersMap;
    }

    public Map<String, Film> getFilmsMap() {
        return filmsMap;
    }

    public Map<String, Series> getSeriesMap() {
        return seriesMap;
    }


}
