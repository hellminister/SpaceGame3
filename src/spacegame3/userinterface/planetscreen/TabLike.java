package spacegame3.userinterface.planetscreen;

import javafx.scene.layout.Pane;
import spacegame3.gamedata.systems.tabdata.TabRecord;

public abstract class TabLike<T extends TabRecord> extends Pane {

    protected T tabRecord;
    protected TabPaneLike container;

    public TabLike(T record){
        tabRecord = record;
    }

    public String getName() {
        return tabRecord.getName();
    }

    public void setContainer(TabPaneLike tabPaneLike){
        container = tabPaneLike;
        attachTo();
    }

    protected abstract void attachTo();
}
