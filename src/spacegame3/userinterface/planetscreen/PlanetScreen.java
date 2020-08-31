package spacegame3.userinterface.planetscreen;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import spacegame3.SpaceGame;
import spacegame3.gamedata.systems.CelestialBody;
import spacegame3.gamedata.systems.tabdata.TabRecord;
import spacegame3.gamedata.time.StarDate;
import spacegame3.gamedata.time.StarDateFormatter;
import spacegame3.userinterface.SizableScene;

public class PlanetScreen extends SizableScene {


    private final TabPaneLike pane;

    private CelestialBody landedOn;

    /**
     * Creates a Scene for a specific root Node.
     */
    public PlanetScreen(SpaceGame spaceGame) {
        super(new StackPane(), spaceGame);

        pane = new TabPaneLike();
        ((Pane)getRoot()).getChildren().add(pane);
        pane.setStyle("-fx-background-color: black");




        setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case ESCAPE: mainTheater.showStartScreen(this);
                break;
                default:
            }
        });
    }

    private Pane makeTopBar() {
        GridPane gp = new GridPane();
        gp.setGridLinesVisible(true);
        gp.setStyle("-fx-background-color: lightgrey");

        Button bt = new Button("depart");

        Label l = new Label();
        l.setText("System - " + landedOn.getInSystem().getName() + " Currently on - " + landedOn.getName());


        Label time = new Label();
        StarDateFormatter sdf = mainTheater.getGameScheme().getStoryTellingScheme().getFormatter("iso8601");
        StarDate sd = mainTheater.getGameScheme().getGameState().getCurrentTime();
        time.setText("Stardate - " + sdf.toString( sd,"date_time_show"));

        gp.addRow(0, l, time, bt);

        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setHalignment(HPos.LEFT);
        cc1.setPercentWidth(33);

        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setHalignment(HPos.CENTER);
        cc2.setPercentWidth(33);
        ColumnConstraints cc3 = new ColumnConstraints();
        cc3.setHalignment(HPos.RIGHT);
        cc3.setPercentWidth(33);
        gp.getColumnConstraints().addAll(cc1, cc2, cc3);

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

        pane.setTopBar(makeTopBar());
    }

    public void setCelectialBody(CelestialBody planet) {
        landedOn = planet;
    }
}
