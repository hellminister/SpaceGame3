package spacegame3.util.tablikepane;

import javafx.scene.layout.Pane;

public class SimpleTabLike extends TabLike{
    private final String name;

    public SimpleTabLike(String name, Pane base) {
        super(base);
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }
}
