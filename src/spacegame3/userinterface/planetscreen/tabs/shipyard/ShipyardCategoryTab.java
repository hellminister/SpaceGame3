package spacegame3.userinterface.planetscreen.tabs.shipyard;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.commons.lang3.StringUtils;
import spacegame3.gamedata.ship.Ship;
import spacegame3.util.Utilities;
import spacegame3.util.tablikepane.TabLike;

import java.util.Map;
import java.util.logging.Logger;

public class ShipyardCategoryTab extends TabLike {
    private static final Logger LOG = Logger.getLogger(ShipyardCategoryTab.class.getName());

    protected final String name;
    protected final ObservableList<Ship> ships;
    protected final boolean used;

    protected final FlowPane tp;

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

        tp = new FlowPane();

        tp.setHgap(10);
        tp.setVgap(10);

        sp.setContent(tp);
        sp.pannableProperty().set(true);

        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: black");
        tp.setStyle("-fx-background-color: black");

        this.basePane.getChildren().add(sp);
        this.basePane.setStyle("-fx-background-color: black");
        setStyle("-fx-background-color: black");

        for (Ship ship : ships){
            insertShip(ship);
            LOG.info(() -> "Adding " + ship.getModelName());
            LOG.info(() -> "Image " + ship.getIcon().toString());
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

    private static class ShipIcon extends StackPane{
        protected Ship me;
        private HBox layout;

        private Button test;

        public ShipIcon(Ship ship){
            super();
            ImageView iv = new ImageView(ship.getIcon());
            me = ship;
            iv.setFitHeight(150);
            iv.setFitWidth(150);
            iv.setPreserveRatio(true);
            setStyle("-fx-background-color: black");

            VBox rightSide = new VBox();

            Label modelName = new Label(StringUtils.capitalize(me.getModelName()));

            modelName.setFont(new Font("French Script MT", 24));
            modelName.setTextFill(Color.DEEPSKYBLUE);

            rightSide.getChildren().addAll(modelName, iv);

            layout = new HBox();

            test = new Button("Test");

            layout.getChildren().addAll(rightSide);

            this.getChildren().add(layout);

            unmarkSelected();
        }

        public void markSelected(){
            setStyle("-fx-border-width: 2px; -fx-border-color: lightblue;");
            layout.getChildren().add(test);
        }

        public void unmarkSelected(){
            setStyle("-fx-border-width: 2px; -fx-border-color: transparent;");
            layout.getChildren().remove(test);
        }

        public Ship getShip(){
            return me;
        }
    }
}
