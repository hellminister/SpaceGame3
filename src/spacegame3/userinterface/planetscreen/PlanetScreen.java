package spacegame3.userinterface.planetscreen;

import javafx.geometry.Side;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import spacegame3.SpaceGame;
import spacegame3.userinterface.SizableScene;

public class PlanetScreen extends SizableScene {


    private final TabPane pane;

    /**
     * Creates a Scene for a specific root Node.
     */
    public PlanetScreen(SpaceGame spaceGame) {
        super(new StackPane(), spaceGame);

        pane = new TabPane();
        ((Pane)getRoot()).getChildren().add(pane);
        pane.setStyle("-fx-background-color: black");

        pane.setSide(Side.RIGHT);
        pane.setRotateGraphic(true);

        pane.getTabs().addAll(new LandableDescription("tab 1"), new LandableDescription("tab 2"));
        pane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        pane.setTabDragPolicy(TabPane.TabDragPolicy.FIXED);

        pane.setTabMinHeight(200);
        pane.setTabMaxHeight(200);


        setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case ESCAPE: mainTheater.showStartScreen(this);
                break;
                default:
            }
        });



    }


    @Override
    public void refresh() {
        // nothing to refresh...
    }
}
