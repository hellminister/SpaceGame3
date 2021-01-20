package spacegame3.util.tablikepane;

import spacegame3.gamedata.StoryTellingScheme;
import spacegame3.gamedata.systems.tabdata.DescriptionTabRecord;
import spacegame3.gamedata.systems.tabdata.ShipyardRecord;
import spacegame3.gamedata.systems.tabdata.SpaceportTabRecord;

import java.util.List;

/**
 * An interface for objects containing the data for my custom tabs
 * It also has a factory method to generate the tab records
 */
public interface TabRecord {

    /**
     * A factory method returning the correct TabRecord object from the given data
     * The data must contain which tab type it is
     * @param strings The tabRecord data
     * @param story The story telling scheme
     * @return The generated TabRecord of the correct type
     */
    static TabRecord generate(List<String> strings, StoryTellingScheme story) {

        TabRecord tr = null;

        for (String s : strings) {
            String[] prop = s.split("\\|");
            if (prop[0].equals("tab")) {
                switch (prop[1]) {
                    case "DESCRIPTION" -> tr = new DescriptionTabRecord(strings, story);
                    case "SPACEPORT" -> tr = new SpaceportTabRecord(strings, story);
                    case "SHIPYARD" -> tr = new ShipyardRecord(strings, story);
                }
            }
        }

        return tr;
    }

    /**
     * return a TabLikeWithRecord from this TabRecord
     * @param <T> The TabRecord type
     * @return The resulting TabLikeWithRecord
     */
    <T extends TabRecord> TabLikeWithRecord<T> toTab();

    /**
     * @return the name of the Tab
     */
    String getName();
}
