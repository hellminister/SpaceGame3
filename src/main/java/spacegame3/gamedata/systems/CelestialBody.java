package spacegame3.gamedata.systems;

import spacegame3.gamedata.StoryTellingScheme;
import spacegame3.util.tablikepane.TabRecord;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CelestialBody extends StellarObject{
    private static final Logger LOG = Logger.getLogger(CelestialBody.class.getName());

    private static final int PROP_NAME_POSITION = 0;
    private static final int PROP_VALUE_POSITION = 1;

    protected final List<TabRecord> tabInfo;

    protected final Map<String, String> properties;

    public CelestialBody(String name, Map<String, List<String>> data, StoryTellingScheme story) {
        super(name);
        tabInfo = new LinkedList<>();
        properties = new HashMap<>();

        List<String> systemData = data.get(name);
        CelestialBodyStructure cbs = story.getCelestialBodyStructure();
        if (systemData != null && !systemData.isEmpty()) {
            for (String propraw : systemData) {
                String[] prop = propraw.split("\\|");
                switch (prop[PROP_NAME_POSITION]) {
                    case "contains":
                        String[] objects = prop[PROP_VALUE_POSITION].split(",");

                        for (String id : objects){
                            TabRecord tr = TabRecord.generate(data.get(id), story);
                            tabInfo.add(tr);
                        }

                        break;
                    default:

                        if (cbs.validateValue(prop[PROP_NAME_POSITION], prop[PROP_VALUE_POSITION])){
                            properties.put(prop[PROP_NAME_POSITION], prop[PROP_VALUE_POSITION]);
                        } else {
                            LOG.warning(() -> prop[PROP_NAME_POSITION] + " " + prop[PROP_VALUE_POSITION] + " is not " +
                                    "treated " + propraw);
                        }
                }
            }
        }
    }

    @Override
    public boolean isLandable(){
        return !tabInfo.isEmpty();
    }

    public List<TabRecord> getTabs() {
        return tabInfo;
    }

    public String getAttribValue(String attrib){
        return "name".equals(attrib) ? getName() : properties.get(attrib);
    }
}
