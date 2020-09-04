package spacegame3.userinterface.planetscreen;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import spacegame3.SpaceGame;
import spacegame3.gamedata.GameScheme;
import spacegame3.gamedata.systems.CelestialBody;
import spacegame3.gamedata.systems.tabdata.TabRecord;
import spacegame3.userinterface.SizableScene;

public class PlanetScreen extends SizableScene {


    private final TabPaneLike pane;

    private CelestialBody landedOn;

    private final GridPane topBar;

    /**
     * Creates a Scene for a specific root Node.
     */
    public PlanetScreen(SpaceGame spaceGame) {
        super(new StackPane(), spaceGame);

        pane = new TabPaneLike();
        ((Pane)getRoot()).getChildren().add(pane);
        pane.setStyle("-fx-background-color: black");

        topBar = createTopBar();
        pane.setTopBar(topBar);




        setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case ESCAPE: mainTheater.showStartScreen(this);
                break;
                default:
            }
        });
    }

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

        ColumnConstraints cc4 = new ColumnConstraints(100,100,100);
        cc4.setHalignment(HPos.RIGHT);

        gp.getColumnConstraints().addAll(cc1, cc2, cc3, cc4);

        Button bt = new Button("depart");
        bt.setMaxWidth(100);
        bt.setMinWidth(100);
        bt.setPrefWidth(100);

        gp.add(bt, 3, 0);

        return gp;
    }

    public void setLandedOn(CelestialBody body){
        landedOn = body;
    }

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

    public CelestialBody getLandedOn() {
        return landedOn;
    }

    public GameScheme getGameScheme(){
        return mainTheater.getGameScheme();
    }

    public void setInTopGrid(int columnNum, int rowNum, Label label) {
        topBar.add(label, columnNum, rowNum);
    }

    public void setInBottomGrid(int columnNum, int rowNum, Label label) {
        bottomBar.add(label, columnNum, rowNum);
    }
}
