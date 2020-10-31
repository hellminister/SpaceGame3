package spacegame3;
import javafx.application.Application;
import javafx.stage.Stage;
import spacegame3.gamedata.GameScheme;
import spacegame3.gamedata.systems.CelestialBody;
import spacegame3.gamedata.systems.StarSystem;
import spacegame3.userinterface.ImageLibrary;
import spacegame3.userinterface.SizableScene;
import spacegame3.userinterface.planetscreen.PlanetScreen;
import spacegame3.userinterface.startscreen.StartScreen;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.LogManager;
import java.util.logging.Logger;
public final class SpaceGame extends Application {
    private static final Logger LOG = Logger.getLogger(SpaceGame.class.getName());

    static {
        Path logConfigFile = Paths.get("src/resources/commons/logging.properties");
        LOG.info(() -> logConfigFile.toAbsolutePath().toString());
        System.setProperty("java.util.logging.config.file", logConfigFile.toAbsolutePath().toString());
        try {
            LogManager.getLogManager().readConfiguration();
        } catch (IOException e) {
            LOG.info(e::toString);
        }
    }
    /*
        *Instance variables
    */
    private final StartScreen startScreen;
    private final PlanetScreen planetScreen;

    private Stage stage;

    private SizableScene previousScene = null;

    private GameScheme gameScheme;

    private boolean gameStarted;

    /**
     *links startScreen and planetScreen to this context
    */
    public SpaceGame()
    {
        startScreen = new StartScreen(this);
        planetScreen = new PlanetScreen(this);
        gameScheme = null;
        gameStarted = false;
    }

    //game scheme getter
    public GameScheme getGameScheme() {
        return gameScheme;
    }

    /**
     * sets the current GameScheme to a new one
     * updates the ImageLibrary with a new story path from the gamescheme
     */
    public void setGameScheme(GameScheme gameScheme) {
        this.gameScheme = gameScheme;
        ImageLibrary.setStoryTellingPath(gameScheme.getStoryPath());
    }

    /**
        *starts up the the game
        *creates title and scene + shows
    **/
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        stage.setTitle("My magic space game (find a better title)!");
        stage.setScene(startScreen);
        stage.sizeToScene();

        stage.show();

        stage.setMinHeight(primaryStage.getHeight());
        stage.setMinWidth(primaryStage.getWidth());

        planetScreen.setSize(stage.getWidth(), stage.getHeight());
    }

    /**
     * takes in a SizeableScene and sizes it to the stage
     * refreshes the scene then sets the stage scene to the scene
     */
    public void giveSceneTo(SizableScene scene){
        scene.setSize(stage.getWidth(), stage.getHeight());
        scene.refresh();
        stage.setScene(scene);
    }

    /**
     * main method - runs + launches game
     * takes in String[] args
    **/
    public static void main(String[] args) {
        launch(args);
    }

    public boolean gameStarted() {
        return gameStarted;
    }

    /**
     *sets the planet screen to have landed on a given CelestialBody
     *returns planet screen
    */
    public SizableScene getPlanetScreen(CelestialBody planet) {
        planetScreen.setLandedOn(planet);
        return planetScreen;
    }

    /**
     * stores the current scene
     * transfers to the start screen
    */
    public void showStartScreen(SizableScene scene) {
        previousScene = scene;
        giveSceneTo(startScreen);
    }

    public SizableScene previousScene(){
        return previousScene;
    }

    /**
     * gets the current CelestialBody a player is on
     * if that CelestialBody is not null then the scene is set for that celestial body
     * if it is null, the StarSystem the player is in is found and,
    */
    public void sendToRightScene() {
        CelestialBody cb = gameScheme.getGameState().getPlanet();
        if (cb != null){
            giveSceneTo(getPlanetScreen(cb));
        } else {
            StarSystem ss = gameScheme.getGameState().getStarSystem();
            // TODO once the star system scene is created
        }
    }
}
