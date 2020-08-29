package spacegame3.userinterface.planetscreen;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

public abstract class PseudoTab extends Tab {

    public PseudoTab(String name){
        Label l = new Label(name);
        l.setRotate(-90);
        StackPane stp = new StackPane(new Group(l));
        stp.setRotate(-90);
        setGraphic(stp);
    }
}
