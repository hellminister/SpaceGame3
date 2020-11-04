package spacegame3.util.tablikepane;

import javafx.scene.layout.Pane;

/**
 * A TabLike with TabRecord data
 *
 * @param <T> The type of the TabRecord
 */
public abstract class TabLikeWithRecord<T extends TabRecord> extends TabLike {

    protected T tabRecord;

    /**
     * Creates the Tab
     * @param record this Tab data
     * @param base   this Tab content (UI)
     */
    public TabLikeWithRecord(T record, Pane base){
        super(base);
        tabRecord = record;

    }

    @Override
    public String getName() {
        return tabRecord.getName();
    }

}
