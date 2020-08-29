package spacegame3.gamedata;

import spacegame3.gamedata.player.Player;
import spacegame3.gamedata.time.StarDate;

public class GameState {

    private Player player;
    private StarDate currentTime;

    public GameState(Player player) {
        this.player = player;
        this.currentTime = new StarDate();
    }

    public String toSaveState() {
        StringBuilder sb = new StringBuilder();

        sb.append(player.toSaveStatePreviewPart()).append("\n");
        sb.append(currentTime.getTime()).append("\n");

        sb.append("--End Preview");

        sb.append(player.toSaveStateNotToPreviewPart()).append("\n");

        return sb.toString();
    }

    public void saved(){
        currentTime.saved();
        player.saved();
    }

    public boolean hasChanged(){
        return currentTime.hasChanged() ||
                player.hasChanged();
    }
}
