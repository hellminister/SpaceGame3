package spacegame3.userinterface.planetscreen.tabs.shipyard;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import spacegame3.gamedata.systems.tabdata.ShipyardRecord;
import spacegame3.util.tablikepane.TabLikeWithRecord;
import spacegame3.util.tablikepane.TabPaneLike;

public class Shipyard extends TabLikeWithRecord<ShipyardRecord> {

    public Shipyard(ShipyardRecord shipyardRecord) {
        super(shipyardRecord, new HBox());

        VBox leftSide = new VBox();
        StackPane rightSide = new StackPane();
        rightSide.setStyle("-fx-background-color: darkslategray");

        leftSide.maxWidthProperty().bind(basePane.widthProperty().multiply(0.66));
        leftSide.minWidthProperty().bind(basePane.widthProperty().multiply(0.66));
        leftSide.prefWidthProperty().bind(basePane.widthProperty().multiply(0.66));

        leftSide.maxHeightProperty().bind(basePane.heightProperty());
        leftSide.minHeightProperty().bind(basePane.heightProperty());
        leftSide.prefHeightProperty().bind(basePane.heightProperty());

        rightSide.maxWidthProperty().bind(basePane.widthProperty().multiply(0.34));
        rightSide.minWidthProperty().bind(basePane.widthProperty().multiply(0.34));
        rightSide.prefWidthProperty().bind(basePane.widthProperty().multiply(0.34));

        rightSide.maxHeightProperty().bind(basePane.heightProperty());
        rightSide.minHeightProperty().bind(basePane.heightProperty());
        rightSide.prefHeightProperty().bind(basePane.heightProperty());


        TabPaneLike shipYard = new TabPaneLike(TabPaneLike.TabSide.TOP);

        shipYard.maxWidthProperty().bind(leftSide.widthProperty());
        shipYard.minWidthProperty().bind(leftSide.widthProperty());
        shipYard.prefWidthProperty().bind(leftSide.widthProperty());

        shipYard.maxHeightProperty().bind(leftSide.heightProperty());
        shipYard.minHeightProperty().bind(leftSide.heightProperty());
        shipYard.prefHeightProperty().bind(leftSide.heightProperty());

        shipYard.add(new ShipyardSellingTab("New", tabRecord.getNewShips(), false));
        shipYard.add(new ShipyardSellingTab("Used", tabRecord.getSecondhandShips(), true));


        leftSide.getChildren().addAll(shipYard);




        basePane.getChildren().addAll(leftSide, rightSide);

    }
}
