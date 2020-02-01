package spacegame3.userinterface.startscreen;

import spacegame3.gamedata.StoryTellingScheme;
import spacegame3.util.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

public class PlayerList {
private static final Logger LOG = Logger.getLogger(PlayerList.class.getName());


    private static final String SAVES_DIR = "saves";
    private static final String FILE_NAME = "playerList.txt";

    private static final int SAVE_FOLDER_NAME_POSITION = 0;
    private static final int PLAYER_NAME_POSITION = 1;
    private static final int CURRENT_PLAYER_MARKER_POSITION = 2;

    private final StoryTellingScheme storyTellingScheme;
    private final Path savePaths;
    private Map<String, PlayerSaveInfo> playerListMap;

    private int nextNewPlayerNumber;

    public PlayerList(StoryTellingScheme sts){
        storyTellingScheme = sts;
        savePaths = storyTellingScheme.getStoryPath().resolve(SAVES_DIR);

        nextNewPlayerNumber = 1;
        populatePlayerListMap();


    }

    public Set<String> getPlayerList(){
        return Collections.unmodifiableSet(playerListMap.keySet());
    }

    private void populatePlayerListMap() {
        playerListMap = new TreeMap<>();

        try (BufferedReader reader = Files.newBufferedReader(savePaths.resolve(FILE_NAME))) {
            String line = reader.readLine();
            nextNewPlayerNumber = Integer.parseInt(line);
            line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(" \"|\" ?");
                playerListMap.put(parts[PLAYER_NAME_POSITION],
                        new PlayerSaveInfo(parts[PLAYER_NAME_POSITION],
                                savePaths.resolve(parts[SAVE_FOLDER_NAME_POSITION])));
                if (parts.length > CURRENT_PLAYER_MARKER_POSITION){
                    storyTellingScheme.setCurrentPlayer(playerListMap.get(parts[PLAYER_NAME_POSITION]));
                }

                line = reader.readLine();
            }
        } catch (IOException ex) {
            updatePlayerListFile();
            LOG.warning(() -> String.format("%s%n creating file", ex.toString()));
        }
    }


    public PlayerSaveInfo getPlayer(String selectedItem) {
        return playerListMap.get(selectedItem);
    }

    public void addPlayer(Map<String, String> playerAttribs) {
        String folderNumber = String.format("%03d", nextNewPlayerNumber++);
        Path savePath = savePaths.resolve(folderNumber);
        PlayerSaveInfo newPlayer = new PlayerSaveInfo(savePath,
                storyTellingScheme.getPlayerStructure().getSaveFileName(playerAttribs),
                storyTellingScheme.getPlayerStructure().getDescription(playerAttribs),
                storyTellingScheme.getPlayerStructure().attribToString(playerAttribs));
        playerListMap.put(newPlayer.getName(), newPlayer);
        storyTellingScheme.setCurrentPlayer(newPlayer);
        updatePlayerListFile();
    }

    public void updatePlayerListFile() {
        StringBuilder sb = new StringBuilder();

        sb.append(nextNewPlayerNumber).append("\n");
        PlayerSaveInfo currentPlayer = storyTellingScheme.getCurrentPlayer();
        for (PlayerSaveInfo psi : playerListMap.values()){
            sb.append(psi.getFolderNumber()).append(" \"").append(psi.getName()).append("\"");
            if (psi == currentPlayer) {
                sb.append(" r");
            }
            sb.append("\n");
        }

        Utilities.writeSimpleTextFile(savePaths.resolve(FILE_NAME), sb.toString());
    }
}
