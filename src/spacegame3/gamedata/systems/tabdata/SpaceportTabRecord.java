package spacegame3.gamedata.systems.tabdata;

import spacegame3.userinterface.planetscreen.Spaceport;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class SpaceportTabRecord implements TabRecord{
    private static final Logger LOG = Logger.getLogger(SpaceportTabRecord.class.getName());

    private String name = "Spaceport";

    public SpaceportTabRecord(List<String> strings) {
        for (String s : strings){
            String[] prop = s.split("\\|");
            switch (prop[0]){
                case "name" -> name = prop[1];
                case "tab" -> name = name;
                default -> LOG.warning(() -> prop[0] + " not treated " + Arrays.toString(prop));
            }
        }
    }

    @Override
    public @SuppressWarnings("unchecked") Spaceport toTab() {
        return new Spaceport(this);
    }

    @Override
    public String getName() {
        return name;
    }
}
