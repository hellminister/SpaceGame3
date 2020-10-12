package spacegame3.userinterface.planetscreen.tabs;

import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import spacegame3.gamedata.ship.Ship;
import spacegame3.gamedata.systems.tabdata.ShipyardRecord;
import spacegame3.userinterface.planetscreen.TabLike;

public class Shipyard extends TabLike<ShipyardRecord> {

    private ObservableList<Ship> newShips;

    private ObservableList<Ship> secondhandShip;

    public Shipyard(ShipyardRecord shipyardRecord) {
        super(shipyardRecord, new Pane());

        newShips = shipyardRecord.getNewShips();
        secondhandShip = shipyardRecord.getSecondhandShips();

    }
}
