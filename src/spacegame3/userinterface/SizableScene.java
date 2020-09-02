package spacegame3.userinterface;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import spacegame3.SpaceGame;

public abstract class SizableScene extends Scene {

    protected final SpaceGame mainTheater;




    /**
     * Creates a Scene for a specific root Node.
     *
     * @param root The root node of the scene graph
     * @throws NullPointerException if root is null
     */
    public SizableScene(Pane root, SpaceGame spaceGame) {
        super(root);
        mainTheater = spaceGame;
    }

    public void setSize(double width, double height){
        ((Pane) getRoot()).setPrefWidth(width);
        ((Pane) getRoot()).setPrefHeight(height);
    }

    public abstract void refresh();

}
