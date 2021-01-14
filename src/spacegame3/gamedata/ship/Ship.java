package spacegame3.gamedata.ship;

import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Ship {

    private final Chassis model;

    private final Map<String, DoubleProperty> stats;

    public Ship(Chassis model) {
        this.model = model;
        stats = new HashMap<>();
    }

    public Image getIcon() {
        // TODO return the composite image of this ship (chassis plus modifications from outfits, etcs.)
        return model.getImage();
    }

    public String getModelName() {
        return model.getName();
    }
}
