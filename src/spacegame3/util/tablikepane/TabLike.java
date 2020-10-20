package spacegame3.util.tablikepane;

import javafx.scene.layout.Pane;

public abstract class TabLike extends Pane {

    protected TabPaneLike container;

    protected final Pane basePane;

    public TabLike(Pane base){
        basePane = base;

        this.getChildren().add(basePane);

    }

    public abstract String getName();

    public final void setContainer(TabPaneLike tabPaneLike){
        container = tabPaneLike;
        attachTo();
    }

    protected void attachTo(){
        basePane.maxWidthProperty().bind(container.getContentPane().widthProperty());
        basePane.minWidthProperty().bind(container.getContentPane().widthProperty());
        basePane.prefWidthProperty().bind(container.getContentPane().widthProperty());

        basePane.maxHeightProperty().bind(container.getContentPane().heightProperty());
        basePane.minHeightProperty().bind(container.getContentPane().heightProperty());
        basePane.prefHeightProperty().bind(container.getContentPane().heightProperty());
    }


}
