package spacegame3.util.tablikepane;

import javafx.scene.layout.Pane;

public abstract class TabLikeWithRecord<T extends TabRecord> extends TabLike {

    protected T tabRecord;

    public TabLikeWithRecord(T record, Pane base){
        super(base);
        tabRecord = record;

    }

    public String getName() {
        return tabRecord.getName();
    }

}
