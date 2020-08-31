package spacegame3.gamedata.systems;

import spacegame3.gamedata.systems.tabdata.TabRecord;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CelestialBody extends StellarObject{
    private static final Logger LOG = Logger.getLogger(CelestialBody.class.getName());

    private static final int PROP_NAME_POSITION = 0;
    private static final int PROP_VALUE_POSITION = 1;

    protected final List<TabRecord> tabInfo;

    public CelestialBody(String name, Map<String, List<String>> data) {
        super(name);
        tabInfo = new LinkedList<>();

        List<String> systemData = data.get(name);
        if (systemData != null && !systemData.isEmpty()) {
            for (String propraw : systemData) {
                String[] prop = propraw.split("\\|");
                switch (prop[PROP_NAME_POSITION]) {
                    case "contains":
                        String[] objects = prop[PROP_VALUE_POSITION].split(",");

                        for (String id : objects){
                            TabRecord tr = TabRecord.generate(data.get(id));
                            tabInfo.add(tr);
                        }

                        break;
                    default:
                        LOG.warning(() -> prop[PROP_NAME_POSITION] + " is not treated " + propraw);
                }
            }
        }
    }

    @Override
    public boolean isLandable(){
        return true;
    }

    public List<TabRecord> getTabs() {
        return tabInfo;
    }
}
