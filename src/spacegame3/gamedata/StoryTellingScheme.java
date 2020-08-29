package spacegame3.gamedata;

import spacegame3.gamedata.objectstructure.PlayerStructure;
import spacegame3.gamedata.time.StarDateFormatter;
import spacegame3.util.Utilities;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class StoryTellingScheme {
    private static final String DESCRIPTION_FILE = "info.txt";

    private final Path resourcesPath;
    private String description;
    private final String storyName;
    private PlayerStructure playerStructure;
    private final Map<String, StarDateFormatter> timeFormatters;

    public StoryTellingScheme(Path resourcesPath) {
        this.resourcesPath = resourcesPath;
        this.description = null;
        this.playerStructure = null;
        this.storyName = resourcesPath.getFileName().toString();
        this.timeFormatters = new HashMap<>();
    }

    public StarDateFormatter getFormatter(String calendarName){
        StarDateFormatter formatter = timeFormatters.get(calendarName);
        if (formatter == null){
            formatter = StarDateFormatter.build(resourcesPath, calendarName);
            timeFormatters.put(calendarName, formatter);
        }
        return formatter;
    }

    public PlayerStructure getPlayerStructure(){
        if (playerStructure == null){
            playerStructure = new PlayerStructure(resourcesPath);
        }
        return playerStructure;
    }

    public String getDescription() {
        if (description == null){
            description = Utilities.readFile(resourcesPath.resolve(DESCRIPTION_FILE));
        }
        return description;
    }

    public String getStoryName(){
        return storyName;
    }

    public Path getStoryPath() {
        return resourcesPath;
    }
}
