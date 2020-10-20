package spacegame3.util.tablikepane;

import spacegame3.gamedata.systems.tabdata.DescriptionTabRecord;
import spacegame3.gamedata.systems.tabdata.ShipyardRecord;
import spacegame3.gamedata.systems.tabdata.SpaceportTabRecord;

import java.util.List;

public interface TabRecord {
    static TabRecord generate(List<String> strings) {

        TabRecord tr = null;

        for (String s : strings){
            String[] prop = s.split("\\|");
            if (prop[0].equals("tab")){
                switch (prop[1]){
                    case "DESCRIPTION" -> tr = new DescriptionTabRecord(strings);
                    case "SPACEPORT" -> tr = new SpaceportTabRecord(strings);
                    case "SHIPYARD" -> tr = new ShipyardRecord(strings);
                }
            }
        }

        return tr;
    }

    <T extends TabRecord> TabLikeWithRecord<T> toTab();
    String getName();
}
