package spacegame3.userinterface.planetscreen.tabs.shipyard;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import spacegame3.gamedata.systems.tabdata.ShipyardRecord;
import spacegame3.util.Utilities;
import spacegame3.util.tablikepane.TabLikeWithRecord;
import spacegame3.util.tablikepane.TabPaneLike;

public class Shipyard extends TabLikeWithRecord<ShipyardRecord> {

    public Shipyard(ShipyardRecord shipyardRecord) {
        super(shipyardRecord, new HBox());

        VBox leftSide = new VBox();
        StackPane rightSide = new StackPane();
        rightSide.setStyle("-fx-background-color: darkslategray");

        Utilities.attach(leftSide, basePane.widthProperty().multiply(0.66), basePane.heightProperty());
        Utilities.attach(rightSide, basePane.widthProperty().multiply(0.34), basePane.heightProperty());

        TabPaneLike shipYard = new TabPaneLike(TabPaneLike.TabSide.TOP);

        Utilities.attach(shipYard, leftSide.widthProperty(), leftSide.heightProperty());

        shipYard.add(new ShipyardSellingTab("New", tabRecord.getNewShips(), false));
        shipYard.add(new ShipyardSellingTab("Used", tabRecord.getSecondhandShips(), true));

        leftSide.getChildren().addAll(shipYard);

        basePane.getChildren().addAll(leftSide, rightSide);

    }
}
