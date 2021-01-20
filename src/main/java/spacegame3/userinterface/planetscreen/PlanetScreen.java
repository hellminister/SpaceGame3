package spacegame3.userinterface.planetscreen;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import spacegame3.SpaceGame;
import spacegame3.gamedata.GameScheme;
import spacegame3.gamedata.systems.CelestialBody;
import spacegame3.util.tablikepane.TabRecord;
import spacegame3.userinterface.SizableScene;
import spacegame3.util.tablikepane.TabPaneLike;

/**
 * This class is the scene representing the CelestialBody the player has landed on
 * This Scene is composed of tabs, 1 per building / site on the Celestial Body
 */
public class PlanetScreen extends SizableScene {

    private final TabPaneLike pane;

    private CelestialBody landedOn;

    private final GridPane topBar;
    private final GridPane bottomBar;
    private Button departButton;

    /**
     * Creates the PlanetScreen linked to its instance of the game
     */
    public PlanetScreen(SpaceGame spaceGame) {
        super(new BorderPane(), spaceGame);

        pane = new TabPaneLike(TabPaneLike.TabSide.RIGHT);
        var root = ((BorderPane)getRoot());
        root.setCenter(pane);
        pane.setStyle("-fx-background-color: black");

        topBar = createTopBar();
        bottomBar = createBottomBar();
        root.setTop(topBar);
        root.setBottom(bottomBar);

        setOnKeyReleased(event -> {
            // using switch to ease maintainability (ie when other key released event will be added)
            switch (event.getCode()) {
                case ESCAPE: mainTheater.showStartScreen(this);
                break;
                default:
            }
        });
    }

    /**
     * Creates the GridPane used for the Bottom bar
     * @return the created GridPane
     */
    private GridPane createBottomBar() {
        GridPane gp = new GridPane();
        gp.setStyle("-fx-background-color: lightgrey");
        gp.setHgap(5);

        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setHalignment(HPos.LEFT);
        cc1.setHgrow(Priority.ALWAYS);

        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setHalignment(HPos.CENTER);
        cc2.setHgrow(Priority.ALWAYS);

        ColumnConstraints cc3 = new ColumnConstraints();
        cc3.setHalignment(HPos.RIGHT);
        cc3.setHgrow(Priority.ALWAYS);

        ColumnConstraints cc4 = new ColumnConstraints(100,100,100);
        cc4.setHalignment(HPos.RIGHT);

        gp.getColumnConstraints().addAll(cc1, cc2, cc3, cc4);

        departButton = new Button("depart");
        departButton.setMaxWidth(100);
        departButton.setMinWidth(100);
        departButton.setPrefWidth(100);

        gp.add(departButton, 3, 0);

        return gp;
    }

    /**
     * Creates the GridPane used for the Top bar
     * @return the created GridPane
     */
    private GridPane createTopBar() {
        GridPane gp = new GridPane();
        gp.setStyle("-fx-background-color: lightgrey");
        gp.setHgap(5);

        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setHalignment(HPos.CENTER);
        cc2.setHgrow(Priority.ALWAYS);

        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setHalignment(HPos.LEFT);
        cc1.setHgrow(Priority.ALWAYS);

        ColumnConstraints cc3 = new ColumnConstraints();
        cc3.setHalignment(HPos.RIGHT);
        cc3.setHgrow(Priority.ALWAYS);

        gp.getColumnConstraints().addAll(cc1, cc2, cc3);

        return gp;
    }

    /**
     * Set the CelestialBody the PlanetScreen will be showing/shows
     * @param body The CelestialBody the player is currently on
     */
    public void setLandedOn(CelestialBody body){
        landedOn = body;
    }

    /**
     * Refreshes the PlanetScreen to set all the tabs and information to the correct CelestialBody
     */
    @Override
    public void refresh() {
        pane.clear();

        for (TabRecord rec : landedOn.getTabs()){
            var tab = rec.toTab();
            pane.add(tab);
        }

        topBar.getChildren().clear();
        PlanetScreenBarMaker psbm = mainTheater.getGameScheme().getStoryTellingScheme().getPlanetScreenBarMaker();
        psbm.updateTopBar(this);

        bottomBar.getChildren().retainAll(departButton);
        psbm.updateBottomBar(this);

    }

    /**
     * @return The CelestialBody currently shown by the PlanetScreen
     */
    public CelestialBody getLandedOn() {
        return landedOn;
    }

    /**
     * @return The current GameScheme
     */
    public GameScheme getGameScheme(){
        return mainTheater.getGameScheme();
    }

    /**
     * Places a Label in the Top Bar
     * @param columnNum The column number where the label will be placed
     * @param rowNum    The row number where the label will be placed
     * @param label     The label to place
     */
    public void setInTopGrid(int columnNum, int rowNum, Label label) {
        topBar.add(label, columnNum, rowNum);
    }

    /**
     * Places a Label in the Bottom Bar
     * @param columnNum The column number where the label will be placed
     * @param rowNum    The row number where the label will be placed
     * @param label     The label to place
     */
    public void setInBottomGrid(int columnNum, int rowNum, Label label) {
        bottomBar.add(label, columnNum, rowNum);
    }
}
