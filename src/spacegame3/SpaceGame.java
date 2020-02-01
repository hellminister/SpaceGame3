package spacegame3;

import javafx.application.Application;
import javafx.stage.Stage;
import spacegame3.gamedata.GameScheme;
import spacegame3.userinterface.startscreen.StartScreen;

import java.util.logging.Logger;

public class SpaceGame extends Application {
    private static final Logger LOG = Logger.getLogger(SpaceGame.class.getName());


    private final StartScreen startScreen;

    private Stage stage;

    private GameScheme gameScheme;

    private boolean gameStarted;

    public SpaceGame() {
        startScreen = new StartScreen(this);
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
    }

    public static void main(String[] args) {
        launch(args);
    }

    public boolean gameStarted() {
        return gameStarted;
    }


}
