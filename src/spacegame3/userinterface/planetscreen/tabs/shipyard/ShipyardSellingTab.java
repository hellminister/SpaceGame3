package spacegame3.userinterface.planetscreen.tabs.shipyard;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import spacegame3.gamedata.ship.Ship;
import spacegame3.util.tablikepane.TabLike;
import spacegame3.util.tablikepane.TabPaneLike;

import java.util.Map;

public class ShipyardSellingTab extends TabLike {

    protected String name;

    public ShipyardSellingTab(String name, ObservableMap<String, ObservableList<Ship>> inventory, boolean used) {
        super(new TabPaneLike(TabPaneLike.TabSide.LEFT));
        this.name = name;

        var root = (TabPaneLike) basePane;

        for (Map.Entry<String, ObservableList<Ship>> cat : inventory.entrySet()){
            root.add(new ShipyardCategoryTab(cat, used));
        }

    }

    @Override
    public String getName() {
        return name;
    }
}
