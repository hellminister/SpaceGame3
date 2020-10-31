package spacegame3.userinterface.planetscreen.tabs.shipyard;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import spacegame3.gamedata.ship.Ship;
import spacegame3.util.Utilities;
import spacegame3.util.tablikepane.TabLike;

import java.util.Map;

public class ShipyardCategoryTab extends TabLike {

    protected final String name;
    protected final ObservableList<Ship> ships;
    protected final boolean used;

    protected final TilePane tp;

    protected ShipIcon selected;

    /**
     *
     * @param cat
     * @param used
     */
    public ShipyardCategoryTab(Map.Entry<String, ObservableList<Ship>> cat, boolean used) {
        super(new StackPane());
        name = cat.getKey();
        ships = cat.getValue();
        this.used = used;


        ScrollPane sp = new ScrollPane();

        Utilities.attach(sp, basePane.widthProperty(), basePane.heightProperty());

        tp = new TilePane();

        tp.setHgap(10);
        tp.setVgap(10);

        sp.setContent(tp);

        sp.setFitToWidth(true);

        for (Ship ship : ships){
            insertShip(ship);
        }

        ships.addListener((ListChangeListener<Ship>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Ship ship : change.getAddedSubList()){
                     insertShip(ship);
                    }
                } else if(change.wasRemoved()){
                    for (Ship ship : change.getRemoved()){
                        removeShip(ship);
                    }
                }
            }
        });

    }

    private void removeShip(Ship ship) {
        ShipIcon toRemove = null;
        for (Node nd : tp.getChildren()){
            if (nd instanceof ShipIcon && ((ShipIcon) nd).getShip() == ship){
                toRemove = (ShipIcon) nd;
                break;
            }
        }
        if (toRemove != null) {
            tp.getChildren().remove(toRemove);
        }
    }

    private void insertShip(Ship ship) {
        ShipIcon iv = new ShipIcon(ship);
        iv.setOnMouseClicked(ev -> {
            if (selected != null){
                selected.unmarkSelected();
            }

            selected = (iv != selected) ? iv : null;

            if (selected != null){
                selected.markSelected();
            }
        });
        tp.getChildren().add(iv);
    }

    @Override
    public String getName() {
        return name;
    }

    private static class ShipIcon extends ImageView{
        protected Ship me;

        public ShipIcon(Ship ship){
            super(ship.getIcon());
            me = ship;
            setFitHeight(200);
            setFitWidth(200);
            setPreserveRatio(true);
            unmarkSelected();
        }

        public void markSelected(){
            setStyle("-fx-background-color: darkslategray");
        }

        public void unmarkSelected(){
            setStyle("-fx-background-color: black");
        }

        public Ship getShip(){
            return me;
        }
    }
}
