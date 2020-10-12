package spacegame3.gamedata.systems.tabdata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import spacegame3.gamedata.ship.Ship;
import spacegame3.userinterface.planetscreen.tabs.Shipyard;

import java.util.ArrayList;

public class ShipyardRecord implements TabRecord {

    private String name;

    private final ObservableList<Ship> newShips;
    private final ObservableList<Ship> secondhandShips;

    public ShipyardRecord(){
        name = "Shipyard";

        newShips = FXCollections.observableList(new ArrayList<>());
        secondhandShips = FXCollections.observableList(new ArrayList<>());
    }


    @Override
    public @SuppressWarnings("unchecked") Shipyard toTab() {
        return new Shipyard(this);
    }

    @Override
    public String getName() {
        return name;
    }

    public ObservableList<Ship> getNewShips(){
        return newShips;
    }

    public ObservableList<Ship> getSecondhandShips() {
        return secondhandShips;
    }
}
