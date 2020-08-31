package spacegame3.gamedata.systems.tabdata;

import spacegame3.userinterface.planetscreen.TabLike;

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
                }
            }
        }

        return tr;
    }

    <T extends TabRecord> TabLike<T> toTab();
    String getName();
}
