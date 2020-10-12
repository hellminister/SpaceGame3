package spacegame3.userinterface.planetscreen;

import javafx.scene.layout.Pane;
import spacegame3.gamedata.systems.tabdata.TabRecord;

public abstract class TabLike<T extends TabRecord> extends Pane {

    protected T tabRecord;
    protected TabPaneLike container;

    protected final Pane basePane;

    public TabLike(T record, Pane base){
        tabRecord = record;
        basePane = base;

        this.getChildren().add(basePane);

    }

    public String getName() {
        return tabRecord.getName();
    }

    public void setContainer(TabPaneLike tabPaneLike){
        container = tabPaneLike;
        attachTo();
    }

    protected void attachTo(){
    }


}
