package spacegame3.gamedata.systems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class StarSystem {
    private static final Logger LOG = getLogger(StarSystem.class.getName());

    public static final String ID_NAME = "SYSTEM";
    private static final int PROP_NAME_POSITION = 0;
    private static final int PROP_VALUE_POSITION = 1;
    private final Map<String, String> attributes;
    private final Map<String, CelestialBody> celestialBodies;


    public StarSystem(Map<String, List<String>> data, CelestialBodyStructure cbs){
        celestialBodies = new HashMap<>();
        attributes = new HashMap<>();

        List<String> systemData = data.get(ID_NAME);

        for (String propraw : systemData){
            String[] prop = propraw.split("\\|");
            switch (prop[PROP_NAME_POSITION]) {
                case "contains" -> {
                    String[] objects = prop[PROP_VALUE_POSITION].split(",");
                    for (String id : objects) {
                        CelestialBody cb = new CelestialBody(id, data, cbs);
                        cb.setInSystem(this);
                        celestialBodies.put(id, cb);
                    }
                }
                default -> attributes.put(prop[PROP_NAME_POSITION], prop[PROP_VALUE_POSITION]);
            }
        }


    }

    public void insertCelestialBody(CelestialBody so){
        celestialBodies.put(so.getName(), so);
    }

    public CelestialBody getCelestialBody(String planetName) {
        return celestialBodies.get(planetName);
    }

    public String getName() {
        return attributes.get("name");
    }

    public String getAttribute(String attribName){
        return attributes.get(attribName);
    }
}
