package spacegame3.userinterface.startscreen;

import spacegame3.gamedata.GameScheme;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Extracts the list of available stories
 * Each Story is under a folder in the stories folder of the game
 */
public class StoryList {
    private static final Logger LOG = Logger.getLogger(StoryList.class.getName());
    private static final Path STORIES_HEAD = Paths.get("src/resources/stories");

    private Map<String, GameScheme> stories;

    /**
     * Loads the list of stories available
     */
    public StoryList(){

        try (Stream<Path> walk = Files.walk(STORIES_HEAD, 1)){
            stories = walk.filter(Files::isDirectory)
                    .skip(1)
                    .map(GameScheme::new)
                    .collect(Collectors.toMap(GameScheme::getStoryName, Function.identity()));
        } catch (IOException e) {
            LOG.severe(e::toString);
        }
    }

    /**
     * @return The list of stories name
     */
    public Set<String> getStoryNames() {
        return Collections.unmodifiableSet(stories.keySet());
    }

    /**
     * returns the specified GameScheme
     * @param selected The name of the story
     * @return The story GameScheme
     */
    public GameScheme get(String selected) {
        return stories.get(selected);
    }
}
