package spacegame3.util.tablikepane;

import javafx.scene.layout.Pane;
import spacegame3.util.Utilities;

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
        Utilities.attach(basePane, container.getContentPane().widthProperty(),
                container.getContentPane().heightProperty());
    }


}
