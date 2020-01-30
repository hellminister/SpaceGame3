package spacegame3.gamedata;

import spacegame3.gamedata.objectstructure.PlayerStructure;
import spacegame3.userinterface.startscreen.PlayerList;
import spacegame3.userinterface.startscreen.PlayerSaveInfo;
import spacegame3.userinterface.startscreen.QuestionBox;
import spacegame3.util.Utilities;

import java.nio.file.Path;
import java.util.logging.Logger;

public class StoryTellingScheme {
    private static final Logger LOG = Logger.getLogger(StoryTellingScheme.class.getName());

    private static final String DESCRIPTION_FILE = "info.txt";

    private final Path resourcesPath;
    private String description;
    private String storyName;

    private PlayerList playerList;
    private PlayerSaveInfo currentPlayer;
    private PlayerStructure playerStructure;

    public StoryTellingScheme(Path resourcesPath) {
        this.resourcesPath = resourcesPath;
        this.description = null;
        this.playerStructure = null;
        this.playerList = null;
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

    public void setCurrentPlayer(PlayerSaveInfo selectedPlayer) {
        currentPlayer = selectedPlayer;
    }

    public PlayerList getPlayerList(){
        if (playerList == null) {
            playerList = new PlayerList(this);
        }
        return playerList;
    }

    public PlayerSaveInfo getCurrentPlayer(){
        return currentPlayer;
    }

    public void save(){
        currentPlayer.save("Game State");
    }

    public void saveAs(String filename, QuestionBox question){
        currentPlayer.saveAs(filename, "gameState", question);
    }
}
