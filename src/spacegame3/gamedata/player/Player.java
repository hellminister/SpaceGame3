package spacegame3.gamedata.player;

import java.util.Map;

public class Player {
    private final Map<String, String> playerAttribs;
    private boolean hasChanged;

    public Player(Map<String, String> playerAttribs) {
        this.playerAttribs = playerAttribs;
        hasChanged = true;
    }

    public String toSaveStatePreviewPart() {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }


    public String toSaveStateNotToPreviewPart() {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    public String getAttribValue(String attrib){
        return playerAttribs.get(attrib);
    }

    public void saved() {
        hasChanged = false;
    }

    public boolean hasChanged(){
        return hasChanged;
    }
}
