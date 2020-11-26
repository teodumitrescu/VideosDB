package main;

import actor.Actor;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import entertainment.Film;
import entertainment.Series;
import fileio.ActorInputData;
import fileio.UserInputData;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        List<ActorInputData> actorsData = input.getActors();
        List<UserInputData> usersData = input.getUsers();
        List<ActionInputData> commandsData = input.getCommands();
        List<MovieInputData> moviesData = input.getMovies();
        List<SerialInputData> serialsData = input.getSerials();
        Database.getInstance().emptyDatabase();
        JSONObject object = new JSONObject();

        for (ActorInputData crtActorData : actorsData) {
            Actor actor = new Actor(crtActorData);
            Database.getInstance().getActorsMap().put(crtActorData.getName(), actor);
            Database.getInstance().getActorsMap().get(crtActorData.getName()).findKeywords();
        }

        for (MovieInputData crtMovieData : moviesData) {
            Film film = new Film(crtMovieData);
            Database.getInstance().getFilmsMap().put(crtMovieData.getTitle(), film);
            Database.getInstance().getFilmsList().add(film);
            Database.getInstance().getVideosList().add(film);
        }

        for (SerialInputData crtSerialData : serialsData) {
            Series serial = new Series(crtSerialData);
            Database.getInstance().getSeriesMap().put(crtSerialData.getTitle(), serial);
            Database.getInstance().getSeriesList().add(serial);
            Database.getInstance().getVideosList().add(serial);
        }

        for (UserInputData crtUserData : usersData) {
            User user = new User(crtUserData);
            Database.getInstance().getUsersMap().put(crtUserData.getUsername(), user);
            Database.getInstance().getUsersMap().get(crtUserData.getUsername()).addHistoryViews();
            Database.getInstance().getUsersMap().get(crtUserData.getUsername()).addFavoriteShows();
        }

        for (ActionInputData crtCommand : commandsData) {
            int id = crtCommand.getActionId();

            switch (crtCommand.getActionType()) {
                case "command":
                    String crtUsername = crtCommand.getUsername();
                    User crtUser = Database.getInstance().getUsersMap().get(crtUsername);

                    if (crtCommand.getType().equals("favorite")) {

                        object = crtUser.makeFavourite(crtCommand.getTitle(), id, fileWriter);
                    } else if (crtCommand.getType().equals("rating")) {

                        String title = crtCommand.getTitle();
                        if (Database.getInstance().getFilmsMap().containsKey(title)) {
                            object = crtUser.rateVideo(title, crtCommand.getGrade(), id, fileWriter);
                        } else if (Database.getInstance().getSeriesMap().containsKey(title)) {
                            object = crtUser.rateVideo(title, crtCommand.getGrade(), crtCommand.getSeasonNumber(), id, fileWriter);
                        }
                    } else if (crtCommand.getType().equals("view")) {

                        object =  crtUser.viewVideo(crtCommand.getTitle(), id, fileWriter);
                    }
                    arrayResult.add(object);
                    break;
                case "query":
                    object = Database.getInstance().sortForQuery(crtCommand.getObjectType(), crtCommand.getSortType(), crtCommand.getNumber(),
                            crtCommand.getFilters(), crtCommand.getCriteria(), id, fileWriter);
                    arrayResult.add(object);
                    break;
                case "recommendation":
                    if (crtCommand.getType().equals("search")) {
                        object = Database.getInstance().searchRec(crtCommand.getUsername(), crtCommand.getGenre(), id, fileWriter);
                    } else {
                        object = Database.getInstance().makeRecommendation(crtCommand.getType(), crtCommand.getUsername(), id, fileWriter);
                    }
                    arrayResult.add(object);
                    break;
                default:
                    object = null;
            }
        }

        fileWriter.closeJSON(arrayResult);
    }
}
