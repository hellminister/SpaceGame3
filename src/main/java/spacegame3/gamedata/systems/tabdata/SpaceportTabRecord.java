package spacegame3.gamedata.systems.tabdata;

import spacegame3.gamedata.StoryTellingScheme;
import spacegame3.userinterface.planetscreen.tabs.Spaceport;
import spacegame3.util.tablikepane.TabRecord;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class SpaceportTabRecord implements TabRecord {
    private static final Logger LOG = Logger.getLogger(SpaceportTabRecord.class.getName());

    private String name = "Spaceport";

    public SpaceportTabRecord(List<String> strings, StoryTellingScheme story) {
        for (String s : strings){
            String[] prop = s.split("\\|");
            switch (prop[0]){
                case "name" -> name = prop[1];
                case "tab" -> { /* do nothing, it was already treated*/ }
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
