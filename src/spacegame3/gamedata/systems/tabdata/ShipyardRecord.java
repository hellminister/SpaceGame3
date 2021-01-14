package spacegame3.gamedata.systems.tabdata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import spacegame3.gamedata.StoryTellingScheme;
import spacegame3.gamedata.ship.Ship;
import spacegame3.gamedata.ship.ShipFactory;
import spacegame3.userinterface.planetscreen.tabs.shipyard.Shipyard;
import spacegame3.util.tablikepane.TabRecord;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ShipyardRecord implements TabRecord {
    private static final Logger LOG = Logger.getLogger(ShipyardRecord.class.getName());

    private String name;

    private final ShipFactory factory;

    private final ObservableMap<String, ObservableList<Ship>> newShips;
    private final ObservableMap<String, ObservableList<Ship>> secondhandShips;

    public ShipyardRecord(List<String> strings, StoryTellingScheme story){
        name = "Shipyard";

        factory = story.getShipFactory();

        newShips = createMap();
        secondhandShips = createMap();

        for (String s : strings){
            String[] prop = s.split("\\|");
            switch (prop[0]){
                case "name" -> name = prop[1];
                case "tab" -> { /* do nothing, it was already treated*/ }
                case "newShips" -> {
                    String[] ships = prop[1].split(",");
                    for (String shipName : ships){
                        String[] shipVariant = shipName.split(":", 2);
                        Ship ship = factory.getShip(shipVariant[0], shipVariant[1]);

                        // TODO change this to fit good categories once this is done

                        newShips.get("A").add(ship);
                    }
                }
                default -> LOG.warning(() -> prop[0] + " not treated " + Arrays.toString(prop));
            }
        }
    }

    private ObservableMap<String, ObservableList<Ship>> createMap() {
        ObservableMap<String, ObservableList<Ship>> map = FXCollections.observableHashMap();

        map.put("A", FXCollections.observableArrayList());
        map.put("B", FXCollections.observableArrayList());
        map.put("C", FXCollections.observableArrayList());

        /*  to activate once i know how to access the category
        for (String cat : categories){
            map.put(cat, FXCollections.observableArrayList());
        }
        */

        return map;
    }


    @Override
    public @SuppressWarnings("unchecked") Shipyard toTab() {
        return new Shipyard(this);
    }

    @Override
    public String getName() {
        return name;
    }

    public ObservableMap<String, ObservableList<Ship>> getNewShips(){
        return newShips;
    }

    public ObservableMap<String, ObservableList<Ship>> getSecondhandShips() {
        return secondhandShips;
    }
}
