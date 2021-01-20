package spacegame3.util.tablikepane;

import javafx.scene.layout.Pane;
import spacegame3.util.Utilities;

/**
 * The superclass for all Tabs that TabPaneLike can accept
 * A tab can only be linked to 1 TabPaneLike object
 */
public abstract class TabLike extends Pane {

    protected TabPaneLike container;

    protected final Pane basePane;

    /**
     * Creates a TabLike with base as content
     * @param base The content of the tab
     */
    public TabLike(Pane base){
        basePane = base;

        this.getChildren().add(basePane);

    }

    /**
     * @return the name of the tab
     */
    public abstract String getName();

    /**
     * @param tabPaneLike the container containing this tab
     */
    final void setContainer(TabPaneLike tabPaneLike){
        container = tabPaneLike;
        attachTo();
    }

    /**
     * attach the size of this tab to the size of the container's content pane
     */
    void attachTo(){
        Utilities.attach(basePane, container.getContentPaneWidthProperty(),
                container.getContentPaneHeightProperty());
    }


}
