package spacegame3.util.tablikepane;

import javafx.scene.layout.Pane;

/**
 * A very basic TabLike in which we only need to set the content pane and its name
 */
public class SimpleTabLike extends TabLike{
    private final String name;

    /**
     * Creates the tab with the given Pane and name
     * @param name the Tab's name
     * @param base the Tab's content
     */
    public SimpleTabLike(String name, Pane base) {
        super(base);
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }
}
