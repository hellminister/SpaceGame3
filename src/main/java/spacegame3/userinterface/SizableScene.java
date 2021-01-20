package spacegame3.userinterface;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import spacegame3.SpaceGame;

/**
 * An extension to Scene that keeps a link to the Application object
 * And that permits easy sizing of the root Pane
 * Contains a refresh() method so the scene can adjust to its new context
 */
public abstract class SizableScene extends Scene {

    protected final SpaceGame mainTheater;


    /**
     * Creates a Scene for a specific root Node.
     *
     * @param root      The root node of the scene graph
     * @param spaceGame The Application Object
     * @throws NullPointerException if root is null
     */
    public SizableScene(Pane root, SpaceGame spaceGame) {
        super(root);
        mainTheater = spaceGame;
    }

    /**
     * Sets the prefered size of the scene to the given value
     *
     * @param width  the prefered width of the scene
     * @param height the prefered height of the scene
     */
    public void setSize(double width, double height) {
        ((Pane) getRoot()).setPrefWidth(width);
        ((Pane) getRoot()).setPrefHeight(height);
    }

    /**
     * The logic to execute when this scene has to be refreshed
     */
    public abstract void refresh();

}
