package spacegame3.userinterface.planetscreen.tabs.shipyard;

import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import spacegame3.gamedata.ship.Ship;
import spacegame3.util.tablikepane.TabLike;

import java.util.Map;

public class ShipyardCategoryTab extends TabLike {

    protected final String name;
    protected final ObservableList<Ship> ships;
    protected final boolean used;

    protected final TilePane tp;

    public ShipyardCategoryTab(Map.Entry<String, ObservableList<Ship>> cat, boolean used) {
        super(new StackPane());
        name = cat.getKey();
        ships = cat.getValue();
        this.used = used;


        ScrollPane sp = new ScrollPane();

        sp.maxWidthProperty().bind(basePane.widthProperty());
        sp.minWidthProperty().bind(basePane.widthProperty());
        sp.prefWidthProperty().bind(basePane.widthProperty());

        sp.maxHeightProperty().bind(basePane.heightProperty());
        sp.minHeightProperty().bind(basePane.heightProperty());
        sp.prefHeightProperty().bind(basePane.heightProperty());

        tp = new TilePane();

        tp.setHgap(10);
        tp.setVgap(10);

        sp.setContent(tp);

        sp.setFitToWidth(true);

    }

    @Override
    public String getName() {
        return name;
    }
}
