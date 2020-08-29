package spacegame3.gamedata.time;

import javafx.beans.property.*;

public class StarDate {

    private LongProperty time;
    private boolean hasChanged;

    public StarDate() {
        this(0);
    }

    /**
     * creates a new stardate starting at the specified date
     * @param time the number of seconds to set the date with
     */
    public StarDate(long time) {
        this.time = new SimpleLongProperty(time);
        hasChanged = true;
    }

    public void addSeconds(long seconds){
        time.set(time.get() + seconds);
        hasChanged = true;
    }

    public void saved(){
        hasChanged = false;
    }

    public boolean hasChanged(){
        return hasChanged;
    }

    public ReadOnlyLongProperty timeProperty() {
        return time;
    }

    public long getTime(){
        return time.get();
    }
}
