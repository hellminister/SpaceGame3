package spacegame3.gamedata;

import spacegame3.gamedata.player.Player;
import spacegame3.gamedata.systems.CelestialBody;
import spacegame3.gamedata.systems.StarSystem;
import spacegame3.gamedata.time.StarDate;

public class GameState {

    private Player player;
    private StarDate currentTime;

    private GameScheme gameScheme;

    private StarSystem ss = null;
    private CelestialBody cb = null;

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

    public StarDate getCurrentTime() {
        return currentTime;
    }

    public boolean hasChanged(){
        return currentTime.hasChanged() ||
                player.hasChanged();
    }

    public void setGameScheme(GameScheme gameScheme) {
        this.gameScheme = gameScheme;
    }

    public void setSystem(StarSystem starSystem) {
        ss = starSystem;
    }

    public void setPlanet(String planetName) {
        cb = ss.getCelestialBody(planetName);
    }

    public CelestialBody getPlanet() {
        return cb;
    }

    public StarSystem getStarSystem() {
        return ss;
    }
}
