package spacegame3.userinterface.startscreen;

import spacegame3.gamedata.GameScheme;
import spacegame3.util.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

/**
 * keeps and loads the list of players available for the chosen story
 * Also takes care of the save structure
 */
public class PlayerList {
    private static final Logger LOG = Logger.getLogger(PlayerList.class.getName());


    private static final String SAVES_DIR = "saves";
    private static final String FILE_NAME = "playerList.txt";

    private static final int SAVE_FOLDER_NAME_POSITION = 0;
    private static final int PLAYER_NAME_POSITION = 1;
    private static final int CURRENT_PLAYER_MARKER_POSITION = 2;

    private final GameScheme gameScheme;                // The GameScheme currently loaded
    private final Path savePaths;                       // The path of the save folders
    private Map<String, PlayerSaveInfo> playerListMap;  // The list of players

    private int nextNewPlayerNumber;                    // The folder number that will be used for the next new player

    /**
     * Creates the PlayerList for the given GameScheme
     * @param sts the given gameScheme
     */
    public PlayerList(GameScheme sts){
        gameScheme = sts;
        savePaths = gameScheme.getStoryPath().resolve(SAVES_DIR);

        nextNewPlayerNumber = 1;
        populatePlayerListMap();
    }

    /**
     * @return the list of players (Unmodifiable)
     */
    public Set<String> getPlayerList(){
        return Collections.unmodifiableSet(playerListMap.keySet());
    }

    /**
     * loads the list of players from the save information file
     */
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
                    gameScheme.setCurrentPlayer(playerListMap.get(parts[PLAYER_NAME_POSITION]));
                }

                line = reader.readLine();
            }
        } catch (IOException ex) {
            updatePlayerListFile();
            LOG.warning(() -> String.format("%s%n creating file", ex.toString()));
        }
    }

    /**
     * @param selectedItem The wanted player
     * @return the wanted player's PlayerSaveInfo
     */
    public PlayerSaveInfo getPlayer(String selectedItem) {
        return playerListMap.get(selectedItem);
    }

    /**
     * Adds a new player to the GameScheme
     * Creates its save folder and informations
     * @param playerAttribs The player's attributes
     */
    public void addPlayer(Map<String, String> playerAttribs) {
        String folderNumber = String.format("%03d", nextNewPlayerNumber++);
        Path savePath = savePaths.resolve(folderNumber);
        PlayerSaveInfo newPlayer = new PlayerSaveInfo(savePath,
                gameScheme.getStoryTellingScheme().getPlayerStructure().getSaveFileName(playerAttribs),
                gameScheme.getStoryTellingScheme().getPlayerStructure().getDescription(playerAttribs),
                gameScheme.getStoryTellingScheme().getPlayerStructure().attribToString(playerAttribs));
        playerListMap.put(newPlayer.getName(), newPlayer);
        gameScheme.setCurrentPlayer(newPlayer);
        updatePlayerListFile();
    }

    /**
     * Updates the file containing the players list, associated save folder and last played player
     */
    public void updatePlayerListFile() {
        StringBuilder sb = new StringBuilder();

        sb.append(nextNewPlayerNumber).append("\n");
        PlayerSaveInfo currentPlayer = gameScheme.getCurrentPlayer();
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
