package spacegame3.gamedata;

import spacegame3.util.Utilities;

import java.nio.file.Path;
import java.util.logging.Logger;

public class PlayerSaveInfo {
    private static final Logger LOG = Logger.getLogger(PlayerSaveInfo.class.getName());
    private static final String DESCRIPTION_FILE = "info.txt";
    private static final String ATTRIBUTE_FILE = "PlayerAttributes.txt";

    private final String playerName;
    private final Path saveFolder;
    private String description;

    public PlayerSaveInfo(String name, Path saveFolder) {
        playerName = name;
        this.saveFolder = saveFolder;
        description = null;
    }

    public PlayerSaveInfo(Path saveFolder, String name, String description, String attribString) {
        playerName = name;
        this.saveFolder = saveFolder;
        this.description = description;
        Utilities.createFolder(saveFolder);
        Utilities.writeSimpleTextFile(saveFolder.resolve(DESCRIPTION_FILE), description);
        Utilities.writeSimpleTextFile(saveFolder.resolve(ATTRIBUTE_FILE), attribString);
    }

    public String getDescription(){
        if (description == null){
            description = Utilities.readFile(saveFolder.resolve(DESCRIPTION_FILE));
        }
        return description;
    }

    public String getName() {
        return playerName;
    }

    public String getFolderNumber() {
        return saveFolder.getFileName().toString();
    }
}
