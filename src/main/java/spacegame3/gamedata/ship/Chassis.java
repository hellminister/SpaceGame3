package spacegame3.gamedata.ship;

import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Chassis {

    private final Image image;
    private final String modelName;
    
    private final Map<String, DoubleProperty> stats;


    public Chassis(Image image, String modelName, Map<String, String> data) {
        this.image = image;
        this.modelName = modelName;
        stats = new HashMap<>();
    }

    public String getName() {
        return modelName;
    }

    public Image getImage() {
        return image;
    }
}
