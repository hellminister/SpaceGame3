package spacegame3.userinterface.startscreen;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import spacegame3.SpaceGame;
import spacegame3.gamedata.GameScheme;
import spacegame3.gamedata.player.Player;
import spacegame3.userinterface.ImageLibrary;
import spacegame3.userinterface.SizableScene;
import spacegame3.util.Utilities;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class StartScreen extends SizableScene {
    private static final Logger LOG = Logger.getLogger(StartScreen.class.getName());

    private static final int HEIGHT = 480;
    private static final String BACK_IMAGE_FILE_PATH = "backgrounds/PIA17563-1920x1200.jpg";
    private static final Path CREDIT_FILE = Paths.get("src/resources/commons/credits.txt");

    private static final int GRID_PANE_MENU_THIRD = 50;
    private static final int BUTTONS_X_TRANSLATE = -10;
    private static final int LEFT_BUTTON_INSET = 16;
    private static final int BOTTOM_BUTTON_INSET = 10;
    private static final int RIGHT_BUTTON_INSET = 0;
    private static final int TOP_BUTTON_INSET = 0;
    private static final int BUTTONS_VERTICAL_SPACING = 12;
    private static final int BUTTON_MAX_HEIGHT = 30;
    private static final int BUTTON_PREF_HEIGHT = 30;
    private static final int BUTTON_MIN_HEIGHT = 15;
    private static final int BUTTON_MAX_WIDTH = 100;
    private static final int BUTTON_PREF_WIDTH = 100;
    private static final int BUTTON_MIN_WIDTH = 50;
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

    private static final int MIDDLE_INFO_BOX_MAX_HEIGHT = 288;
    private static final int MIDDLE_INFO_BOX_PREF_HEIGHT = 288;
    private static final int MIDDLE_INFO_BOX_MIN_HEIGHT = 288;
    private static final int MIDDLE_INFO_BOX_MAX_WIDTH = 400;
    private static final int MIDDLE_INFO_BOX_PREF_WIDTH = 400;
    private static final int MIDDLE_INFO_BOX_MIN_WIDTH = 200;


    private final Pane insideStoryButtons;
    private final Pane chooseStoryButtons;
    private final Pane loadGameButtons;
    private final TextArea infoBox;
    private final ListView<String> infoBox2;

    private final StoryList storyList;

    private Mode mode;
    private final QuestionBox questionner;

    public StartScreen(SpaceGame spaceGame) {
        super(new StackPane(), spaceGame);
        questionner = new QuestionBox();

        storyList = new StoryList();

        var root = (StackPane) getRoot();
        root.setStyle("-fx-background-color: black");

        // Create background Pane
        var backgroundPlate = new ImageView(ImageLibrary.getImage(BACK_IMAGE_FILE_PATH));
        backgroundPlate.setPreserveRatio(true);

        backgroundPlate.setFitHeight(HEIGHT);

        // this pane is to permit resizing of the window
        var backgroundImagePane = new Pane();
        backgroundImagePane.getChildren().add(backgroundPlate);

        root.setPrefSize(backgroundPlate.getLayoutBounds().getWidth(), HEIGHT);

        backgroundPlate.fitHeightProperty().bind(root.heightProperty());
        backgroundPlate.fitWidthProperty().bind(root.widthProperty());

        // Create Start Screen

        var mainPane = new StackPane();
        mainPane.setAlignment(Pos.CENTER);

        mainPane.getChildren().add(backgroundImagePane);

        // Create start menu grid pane

        var startScreenGridPane = createGridPane();
        startScreenGridPane.setPrefSize(mainPane.getPrefWidth(), mainPane.getPrefHeight());
        mainPane.getChildren().add(startScreenGridPane);

        // End Create start menu grid pane

        // Create Buttons
        insideStoryButtons = createChoosePlayerButtons();
        startScreenGridPane.add(insideStoryButtons, FIRST_COLUMN, FIRST_ROW);
        insideStoryButtons.setVisible(false);

        chooseStoryButtons = createChooseStoryButtons();
        startScreenGridPane.add(chooseStoryButtons, FIRST_COLUMN, FIRST_ROW);
        chooseStoryButtons.setVisible(true);

        loadGameButtons = createLoadGameButtons();
        startScreenGridPane.add(loadGameButtons, FIRST_COLUMN, FIRST_ROW);
        loadGameButtons.setVisible(false);

        // End Create Buttons

        infoBox = createInfoBox();
        startScreenGridPane.add(infoBox, THIRD_COLUMN, FIRST_ROW);


        // Create Middle Pane

        infoBox2 = createMiddleInfoBox();

        startScreenGridPane.add(infoBox2, SECOND_COLUMN, FIRST_ROW);

        showStorySelectionItems();
        // End create Start Screen


        root.getChildren().add(createCenteringPanesFor(mainPane));


        setOnKeyReleased(event -> {
            switch (event.getCode()){
                case ESCAPE:
                    var previous = spaceGame.previousScene();
                    if (previous != null){
                        spaceGame.giveSceneTo(previous);
                    }
            }
                }
        );

    }

    private ListView<String> createMiddleInfoBox() {
        var mInfoBox = new ListView<String>();
        mInfoBox.setEditable(false);
        mInfoBox.setPrefSize(MIDDLE_INFO_BOX_PREF_WIDTH, MIDDLE_INFO_BOX_PREF_HEIGHT);
        mInfoBox.setMaxSize(MIDDLE_INFO_BOX_MAX_WIDTH, MIDDLE_INFO_BOX_MAX_HEIGHT);
        mInfoBox.setMinSize(MIDDLE_INFO_BOX_MIN_WIDTH, MIDDLE_INFO_BOX_MIN_HEIGHT);
        mInfoBox.setStyle("-fx-background-color: grey");
        mInfoBox.setOnMouseReleased(e -> {
            String selected = mInfoBox.getSelectionModel().getSelectedItem();
            switch (mode){
                case STORY:
                    if ((selected != null) && !selected.isEmpty()) {
                        infoBox.setText(storyList.get(selected).getDescription());
                    }
                    break;
                case PLAYER:
                    if ((selected != null) && !selected.isEmpty()) {
                        infoBox.setText(mainTheater.getGameScheme().getPlayerList().getPlayer(selected).getDescription());
                        insideStoryButtons.getChildren().get(0).setDisable(false);
                    }
                    break;
                case LOAD:
                    if ((selected != null) && !selected.isEmpty()) {
                        infoBox.setText(mainTheater.getGameScheme().getCurrentPlayer().previewFile(selected));
                        loadGameButtons.getChildren().get(2).setDisable(false);
                        if (mainTheater.gameStarted()){
                            loadGameButtons.getChildren().get(4).setDisable(false);
                        }
                    }
                    break;
            }
        });
        return mInfoBox;
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

    private VBox createChoosePlayerButtons() {
        Button choosePlayer = createButton("Choose Player", event -> {
            mainTheater.getGameScheme().setCurrentPlayer(mainTheater.getGameScheme().getPlayerList().getPlayer(infoBox2.getSelectionModel().getSelectedItem()));
            mainTheater.getGameScheme().getPlayerList().updatePlayerListFile();
            showLoadGameItems();
        });
        Button newPlayer = createButton("New Player", event -> {
            mainTheater.getGameScheme().createNewPlayer(questionner);
            showLoadGameItems();
        });
        Button changeStory = createButton("Change Story", event -> showStorySelectionItems());
        Button quit = createButton("Quit", event -> quitGame());

        VBox insideStory = createButtonsVBox();

        insideStory.getChildren().addAll(choosePlayer, newPlayer, changeStory, quit);
        insideStory.setTranslateX(BUTTONS_X_TRANSLATE);
        return insideStory;
    }

    private VBox createLoadGameButtons() {
        Button resume = createButton("Resume Game", event -> mainTheater.giveSceneTo(mainTheater.previousScene()));
        Button changePlayer = createButton("Change Player", event -> showChoosePlayerItems());
        Button newGame = createButton("New Game", event -> {
            Player player = new Player(mainTheater.getGameScheme().getCurrentPlayer().loadPlayerAttribs());
            mainTheater.getGameScheme().newGameState(player);
            mainTheater.sendToRightScene();
        });
        Button loadGame = createButton("Load Game", event -> {});
        Button saveAsGame = createButton("Save Game As...", event -> {
            String fileName = questionner.getAnswer("Save file name:");
            fileName += ".sav";
            mainTheater.getGameScheme().saveAs(fileName, questionner);
        });
        Button saveGame = createButton("Save Game", event -> {
            String filename = infoBox2.getSelectionModel().getSelectedItem();
            mainTheater.getGameScheme().saveAs(filename, questionner);
        });
        Button changeStory = createButton("Change Story", event -> showStorySelectionItems());
        Button quit = createButton("Quit", event -> quitGame());

        VBox insideStory = createButtonsVBox();

        insideStory.getChildren().addAll(resume, changePlayer, newGame, loadGame, saveAsGame, saveGame, changeStory,
                quit);
        insideStory.setTranslateX(BUTTONS_X_TRANSLATE);
        return insideStory;
    }

    private VBox createButtonsVBox() {
        VBox insideStory = new VBox(BUTTONS_VERTICAL_SPACING);
        Insets padding = new Insets(TOP_BUTTON_INSET, RIGHT_BUTTON_INSET, BOTTOM_BUTTON_INSET, LEFT_BUTTON_INSET);
        insideStory.setPadding(padding);
        insideStory.setAlignment(Pos.CENTER);
        return insideStory;
    }

    private void quitGame() {
        Platform.exit();
    }

    private void showLoadGameItems() {
        insideStoryButtons.setVisible(false);
        loadGameButtons.setVisible(true);
        chooseStoryButtons.setVisible(false);

        infoBox2.setVisible(true);
        infoBox2.setItems(savedGameList());
        infoBox.setText("");

        if (infoBox2.getItems().isEmpty()){
            loadGameButtons.getChildren().get(3).setDisable(true);
            loadGameButtons.getChildren().get(5).setDisable(true);
        } else {
            infoBox2.getSelectionModel().selectFirst();
            loadGameButtons.getChildren().get(3).setDisable(false);
            loadGameButtons.getChildren().get(5).setDisable(false);
        }

        if (mainTheater.gameStarted()){
            loadGameButtons.getChildren().get(4).setDisable(false);
        } else {
            loadGameButtons.getChildren().get(4).setDisable(true);
            loadGameButtons.getChildren().get(5).setDisable(true);
        }

        loadGameButtons.getChildren().get(0).setDisable(mainTheater.previousScene() == null);


        mode = Mode.LOAD;
    }

    private ObservableList<String> savedGameList() {
        return FXCollections.observableArrayList(mainTheater.getGameScheme().getCurrentPlayer().getSavedGameList());
    }

    private VBox createChooseStoryButtons() {
        Button chooseStory = createButton("Choose story", null);
        chooseStory.setOnAction(event -> {
            if ("Show stories".equals(chooseStory.getText())){
                chooseStory.setText("Choose story");
                infoBox.setText("");
                showStorySelectionItems();
            } else {
                GameScheme selected = storyList.get(infoBox2.getSelectionModel().getSelectedItem());
                if (selected != mainTheater.getGameScheme()) {
                    mainTheater.setGameScheme(selected);
                    //selected.setCurrentPlayer(null);
                }
                showChoosePlayerItems();
            }
        });

        String creditsText = Utilities.readFile(CREDIT_FILE);

        Button credits = createButton("Credits", event -> {
            infoBox.setText(creditsText);
            chooseStory.setText("Show stories");
            hideMiddle();
        });

        Button quit = createButton("Quit", event -> quitGame());

        VBox insideStory = createButtonsVBox();

        insideStory.getChildren().addAll(chooseStory, credits, quit);
        insideStory.setTranslateX(BUTTONS_X_TRANSLATE);
        return insideStory;
    }

    private void hideMiddle() {
        infoBox2.setVisible(false);
    }

    private void showChoosePlayerItems() {
        insideStoryButtons.setVisible(true);
        loadGameButtons.setVisible(false);
        chooseStoryButtons.setVisible(false);


        infoBox2.setVisible(true);
        infoBox2.setItems(playerList());
        infoBox.setText("");

        String selected = "";

        if (mainTheater.getGameScheme().getCurrentPlayer() == null){
            if (infoBox2.getItems().isEmpty()){
                insideStoryButtons.getChildren().get(0).setDisable(true);
            } else {
                infoBox2.getSelectionModel().selectFirst();
                selected = infoBox2.getSelectionModel().getSelectedItem();
                insideStoryButtons.getChildren().get(0).setDisable(false);
            }
        } else {
            infoBox2.getSelectionModel().select(mainTheater.getGameScheme().getCurrentPlayer().getName());
            selected = infoBox2.getSelectionModel().getSelectedItem();
            insideStoryButtons.getChildren().get(0).setDisable(false);
        }

        if (!selected.isEmpty()) {
            infoBox.setText(mainTheater.getGameScheme().getPlayerList().getPlayer(selected).getDescription());
        }

        mode = Mode.PLAYER;
    }

    private ObservableList<String> playerList() {
        return FXCollections.observableArrayList(mainTheater.getGameScheme().getPlayerList().getPlayerList());
    }

    private void showStorySelectionItems() {
        insideStoryButtons.setVisible(false);
        loadGameButtons.setVisible(false);
        chooseStoryButtons.setVisible(true);

        infoBox2.setVisible(true);
        infoBox2.setItems(storyList());
        if (mainTheater.getGameScheme() == null){
            infoBox2.getSelectionModel().selectFirst();
        } else {
            infoBox2.getSelectionModel().select(mainTheater.getGameScheme().getStoryName());
        }

        String selected = infoBox2.getSelectionModel().getSelectedItem();

        infoBox.setText(storyList.get(selected).getDescription());
        mode = Mode.STORY;
    }

    private ObservableList<String> storyList() {
        return FXCollections.observableArrayList(storyList.getStoryNames());
    }

    private GridPane createGridPane() {
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

        btn.setMaxSize(BUTTON_MAX_WIDTH, BUTTON_MAX_HEIGHT);
        btn.setMinSize(BUTTON_MIN_WIDTH, BUTTON_MIN_HEIGHT);
        btn.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

        btn.setOnAction(action);

        return btn;
    }

    @Override
    public void refresh() {
        switch (mode){
            case STORY -> showStorySelectionItems();
            case PLAYER -> showChoosePlayerItems();
            case LOAD -> showLoadGameItems();
        }
    }

    public enum Mode {
        STORY,
        PLAYER,
        LOAD,
        ;
    }


}
