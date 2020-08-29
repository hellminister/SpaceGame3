package spacegame3.userinterface.planetscreen;


import javafx.scene.control.Label;

public class LandableDescription extends PseudoTab{

    String name;

    public String getTabName() {
        return name;
    }

    public LandableDescription(String s){
        super(s);
        name = s;
        
    }
}
