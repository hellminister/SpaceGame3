package spacegame3;

import javafx.application.Application;
import javafx.stage.Stage;
import spacegame3.gamedata.GameScheme;
import spacegame3.gamedata.systems.CelestialBody;
import spacegame3.gamedata.systems.StarSystem;
import spacegame3.userinterface.SizableScene;
import spacegame3.userinterface.planetscreen.PlanetScreen;
import spacegame3.userinterface.startscreen.StartScreen;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class SpaceGame extends Application {
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


    private final StartScreen startScreen;
    private final PlanetScreen planetScreen;

    private Stage stage;

    private SizableScene previousScene = null;

    private GameScheme gameScheme;

    private boolean gameStarted;

    public SpaceGame() {
        startScreen = new StartScreen(this);
        planetScreen = new PlanetScreen(this);
        gameScheme = null;
        gameStarted = false;
    }

    public GameScheme getGameScheme() {
        return gameScheme;
    }

    public void setGameScheme(GameScheme gameScheme) {
        this.gameScheme = gameScheme;
    }

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

    public void giveSceneTo(SizableScene scene){
        scene.setSize(stage.getWidth(), stage.getHeight());
        scene.refresh();
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public boolean gameStarted() {
        return gameStarted;
    }


    public SizableScene getPlanetScreen(CelestialBody planet) {
        planetScreen.setLandedOn(planet);
        return planetScreen;
    }

    public void showStartScreen(SizableScene scene) {
        previousScene = scene;
        giveSceneTo(startScreen);
    }

    public SizableScene previousScene(){
        return previousScene;
    }

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
