package spacegame3;

import javafx.application.Application;
import javafx.stage.Stage;
import spacegame3.gamedata.StoryTellingScheme;

import java.util.logging.Logger;

public class SpaceGame extends Application {
    private static final Logger LOG = Logger.getLogger(SpaceGame.class.getName());

    public static void main(String[] args) {
        launch(args);
    }

    private StoryTellingScheme storyTellingScheme;

    @Override
    public void start(Stage primaryStage) {

    }
}
