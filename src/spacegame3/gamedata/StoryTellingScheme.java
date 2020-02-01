package spacegame3.gamedata;

import spacegame3.gamedata.objectstructure.PlayerStructure;
import spacegame3.util.Utilities;

import java.nio.file.Path;

public class StoryTellingScheme {
    private static final String DESCRIPTION_FILE = "info.txt";

    private final Path resourcesPath;
    private String description;
    private String storyName;
    private PlayerStructure playerStructure;

    public StoryTellingScheme(Path resourcesPath) {
        this.resourcesPath = resourcesPath;
        this.description = null;
        this.playerStructure = null;
        this.storyName = resourcesPath.getFileName().toString();
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
