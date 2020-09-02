package spacegame3.gamedata.systems.tabdata;

import spacegame3.userinterface.planetscreen.DescriptionTab;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class DescriptionTabRecord implements TabRecord {
    private static final Logger LOG = Logger.getLogger(DescriptionTabRecord.class.getName());

    private String name = "Description";
    private String text = "";
    private String imageURL = "";

    public DescriptionTabRecord(List<String> strings) {
        for (String s : strings){
            String[] prop = s.split("\\|");
                switch (prop[0]){
                    case "name" -> name = prop[1];
                    case "text" -> text = ("".equals(text) ? "" : (text + "/n")) + prop[1];
                    case "image" -> imageURL = prop[1];
                    case "tab" -> { /* do nothing it was already treated */ }
                    default -> LOG.warning(() -> prop[0] + " not treated " + Arrays.toString(prop));
                }
        }
    }

    public String getName(){
        return name;
    }

    public String getDescription() {
        return text;
    }

    public String getImageURL(){
        return imageURL;
    }

    @Override
    public @SuppressWarnings("unchecked") DescriptionTab toTab() {
        return new DescriptionTab(this);
    }
}
