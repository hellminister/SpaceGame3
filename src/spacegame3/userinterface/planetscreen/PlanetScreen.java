package spacegame3.userinterface.planetscreen;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import spacegame3.SpaceGame;
import spacegame3.gamedata.systems.CelestialBody;
import spacegame3.gamedata.systems.tabdata.TabRecord;
import spacegame3.gamedata.time.StarDate;
import spacegame3.gamedata.time.StarDateFormatter;
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

        Button bt = new Button("depart");
        bt.setMaxWidth(100);
        bt.setMinWidth(100);
        bt.setPrefWidth(100);

        gp.add(bt, 3, 0);

        return gp;
    }

    private void updateTopBar() {

        Label left = new Label();
        left.setText(" System - " + landedOn.getInSystem().getName() + " Currently on - " + landedOn.getName());


        Label right = new Label();
        StarDateFormatter sdf = mainTheater.getGameScheme().getStoryTellingScheme().getFormatter("iso8601");
        StarDate sd = mainTheater.getGameScheme().getGameState().getCurrentTime();

        StringExpression se = Bindings.format("Stardate - %s", sd.toString(sdf, "date_time_show"));

        right.textProperty().bind(se);

        Label center = new Label("");

        topBar.add(left, 0, 0);
        topBar.add(center, 1,0);
        topBar.add(right, 2, 0);


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

        updateTopBar();
    }

}
