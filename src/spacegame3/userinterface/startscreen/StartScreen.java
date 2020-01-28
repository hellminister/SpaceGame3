package spacegame3.userinterface.startscreen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import spacegame3.SpaceGame;
import spacegame3.userinterface.ImageLibrary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartScreen extends Scene {
    private static final Logger LOG = Logger.getLogger(StartScreen.class.getName());
    private static final int HEIGHT = 480;
    private static final String BACK_IMAGE_FILE_PATH = "PIA17563-1920x1200.jpg";
    private static final String CREDIT_FILE = "src/resources/commons/credits.txt";

    private static final int GRID_PANE_MENU_THIRD = 50;
    private static final int MAIN_BUTTONS_X_TRANSLATE = -10;
    private static final int LEFT_BUTTON_INSET = 16;
    private static final int BOTTOM_BUTTON_INSET = 10;
    private static final int RIGHT_BUTTON_INSET = 0;
    private static final int TOP_BUTTON_INSET = 0;
    private static final int MAIN_BUTTONS_VERTICAL_SPACING = 12;
    private static final int MAIN_BUTTON_MAX_HEIGHT = 30;
    private static final int MAIN_BUTTON_PREF_HEIGHT = 30;
    private static final int MAIN_BUTTON_MIN_HEIGHT = 15;
    private static final int MAIN_BUTTON_MAX_WIDTH = 100;
    private static final int MAIN_BUTTON_PREF_WIDTH = 100;
    private static final int MAIN_BUTTON_MIN_WIDTH = 50;
    private static final int FIRST_ROW = 0;
    private static final int SECOND_COLUMN = 1;
    private static final int THIRD_COLUMN = 2;
    private static final int FIRST_COLUMN = 0;
    private static final int FIRST_ROW_HEIGHT_PERCENT = 100;
    private static final int MAIN_INFO_BOX_MAX_HEIGHT = 330;
    private static final int MAIN_INFO_BOX_PREF_HEIGHT = 330;
    private static final int MAIN_INFO_BOX_MIN_HEIGHT = 330;
    private static final int MAIN_INFO_BOX_MAX_WIDTH = 200;
    private static final int MAIN_INFO_BOX_PREF_WIDTH = 200;
    private static final int MAIN_INFO_BOX_MIN_WIDTH = 200;
    private static final int MAIN_INFO_BOX_X_TRANSLATE = 20;


    private final SpaceGame mainTheater;
    private final Pane insideStoryButtons;
    private final Pane chooseStoryButtons;
    private final TextArea infoBox;

    public StartScreen(SpaceGame spaceGame) {
        super(new StackPane());
        mainTheater = spaceGame;

        Pane root = (StackPane) getRoot();
        root.setStyle("-fx-background-color: black");

        // Create background Pane
        ImageView backgroundPlate = new ImageView(ImageLibrary.getImage(BACK_IMAGE_FILE_PATH));
        backgroundPlate.setPreserveRatio(true);

        backgroundPlate.setFitHeight(HEIGHT);

        Pane backgroundImagePane = new Pane();
        backgroundImagePane.getChildren().add(backgroundPlate);

        root.setPrefSize(backgroundPlate.getLayoutBounds().getWidth(), HEIGHT);

        backgroundPlate.fitHeightProperty().bind(root.heightProperty());
        backgroundPlate.fitWidthProperty().bind(root.widthProperty());

        // Create Start Screen

        StackPane mainPane = new StackPane();
        mainPane.setAlignment(Pos.CENTER);

        mainPane.getChildren().add(backgroundImagePane);

        // Create start menu grid pane

        GridPane startScreenGridPane = getGridPane();
        startScreenGridPane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight());
        mainPane.getChildren().add(startScreenGridPane);

        // End Create start menu grid pane

        // Create Buttons
        insideStoryButtons = createInsideStoryButtons();
        startScreenGridPane.add(insideStoryButtons, FIRST_COLUMN, FIRST_ROW);
        insideStoryButtons.setVisible(false);

        chooseStoryButtons = createChooseStoryButtons();
        startScreenGridPane.add(chooseStoryButtons, FIRST_COLUMN, FIRST_ROW);
        chooseStoryButtons.setVisible(true);

        // End Create Buttons

        infoBox = createInfoBox();
        startScreenGridPane.add(infoBox, THIRD_COLUMN, FIRST_ROW);

  //      middlePane = new MiddlePane(this);
  //      startScreenGridPane.add(middlePane.getRootPane(), SECOND_COLUMN, FIRST_ROW);

        // End create Start Screen


        root.getChildren().add(createCenteringPanesFor(mainPane));
    }

    private TextArea createInfoBox() {
        TextArea infoBox = new TextArea();
        infoBox.setEditable(false);
        infoBox.setWrapText(true);
        infoBox.setPrefSize(MAIN_INFO_BOX_PREF_WIDTH, MAIN_INFO_BOX_PREF_HEIGHT);
        infoBox.setMaxSize(MAIN_INFO_BOX_MAX_WIDTH, MAIN_INFO_BOX_MAX_HEIGHT);
        infoBox.setMinSize(MAIN_INFO_BOX_MIN_WIDTH, MAIN_INFO_BOX_MIN_HEIGHT);
        infoBox.setTranslateX(MAIN_INFO_BOX_X_TRANSLATE);

        infoBox.setStyle("-fx-background-color: grey");
        return infoBox;
    }

    private VBox createInsideStoryButtons() {
        Button enterGame = createButton("Enter Game", event -> {});
        Button choosePlayer = createButton("Choose Player", event -> {});
        Button loadGame = createButton("Load Game", event -> {});
        Button changeStory = createButton("Change Story", event -> {
            insideStoryButtons.setVisible(false);
            chooseStoryButtons.setVisible(true);
        });

        VBox insideStory = new VBox(MAIN_BUTTONS_VERTICAL_SPACING);
        Insets padding = new Insets(TOP_BUTTON_INSET, RIGHT_BUTTON_INSET, BOTTOM_BUTTON_INSET, LEFT_BUTTON_INSET);
        insideStory.setPadding(padding);
        insideStory.setAlignment(Pos.CENTER);

        insideStory.getChildren().addAll(enterGame, choosePlayer, loadGame, changeStory);
        insideStory.setTranslateX(MAIN_BUTTONS_X_TRANSLATE);
        return insideStory;
    }

    private VBox createChooseStoryButtons() {
        Button chooseStory = createButton("Choose story", null);
        chooseStory.setOnAction(event -> {
            if ("Show stories".equals(chooseStory.getText())){
                chooseStory.setText("Choose story");
                infoBox.setText("");
            } else {
                chooseStoryButtons.setVisible(false);
                insideStoryButtons.setVisible(true);
            }
        });

        String creditsText = loadCreditText();

        Button credits = createButton("Credits", event -> {
            infoBox.setText(creditsText);
            chooseStory.setText("Show stories");
        });

        VBox insideStory = new VBox(MAIN_BUTTONS_VERTICAL_SPACING);
        Insets padding = new Insets(TOP_BUTTON_INSET, RIGHT_BUTTON_INSET, BOTTOM_BUTTON_INSET, LEFT_BUTTON_INSET);
        insideStory.setPadding(padding);
        insideStory.setAlignment(Pos.CENTER);

        insideStory.getChildren().addAll(chooseStory, credits);
        insideStory.setTranslateX(MAIN_BUTTONS_X_TRANSLATE);
        return insideStory;
    }

    private GridPane getGridPane() {
        GridPane startScreenGridPane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(GRID_PANE_MENU_THIRD);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(GRID_PANE_MENU_THIRD);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHalignment(HPos.CENTER);
        column3.setPercentWidth(GRID_PANE_MENU_THIRD);
        startScreenGridPane.getColumnConstraints().addAll(column1, column2, column3);
        RowConstraints row1 = new RowConstraints();
        row1.setValignment(VPos.CENTER);
        row1.setPercentHeight(FIRST_ROW_HEIGHT_PERCENT);
        startScreenGridPane.getRowConstraints().add(row1);
        return startScreenGridPane;
    }

    private VBox createCenteringPanesFor(Pane toCenter) {
        VBox verticalCentering = new VBox();
        verticalCentering.setAlignment(Pos.CENTER);

        HBox horizontalCentering = new HBox();
        horizontalCentering.setAlignment(Pos.CENTER);

        verticalCentering.getChildren().add(horizontalCentering);
        horizontalCentering.getChildren().add(toCenter);

        return verticalCentering;
    }

    private Button createButton(String text, EventHandler<ActionEvent> action){
        Button btn = new Button();

        btn.setText(text);

        btn.setMaxSize(MAIN_BUTTON_MAX_WIDTH, MAIN_BUTTON_MAX_HEIGHT);
        btn.setMinSize(MAIN_BUTTON_MIN_WIDTH, MAIN_BUTTON_MIN_HEIGHT);
        btn.setPrefSize(MAIN_BUTTON_PREF_WIDTH, MAIN_BUTTON_PREF_HEIGHT);

        btn.setOnAction(action);

        return btn;
    }

    private String loadCreditText() {
        StringBuilder text = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(CREDIT_FILE)))) {
            String line = reader.readLine();
            while (line != null) {
                text.append(line).append("\n");
                line = reader.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        return text.toString();
    }



}
