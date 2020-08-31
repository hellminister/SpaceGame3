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
    private String name;
    private final Map<String, CelestialBody> celestialBodies;


    public StarSystem(Map<String, List<String>> data){
        celestialBodies = new HashMap<>();

        List<String> systemData = data.get(ID_NAME);

        for (String propraw : systemData){
            String[] prop = propraw.split("\\|");
            switch (prop[PROP_NAME_POSITION]) {
                case "name" :
                    this.name = prop[PROP_VALUE_POSITION];
                    break;
                case "contains" :
                    String[] objects = prop[PROP_VALUE_POSITION].split(",");
                    for (String id : objects){
                        CelestialBody cb = new CelestialBody(id, data);
                        cb.setInSystem(this);
                        celestialBodies.put(id, cb);
                    }
                    break;
                default:
                    LOG.warning(() -> prop[PROP_NAME_POSITION] + " is not treated " + propraw);
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
        return name;
    }
}
