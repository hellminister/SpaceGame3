package spacegame3.gamedata.time;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;

public class StarDate {

    private final LongProperty time;
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

    public StringBinding toString(StarDateFormatter sdf, String format){
        return new StarDateStringBinding(sdf, format, this);
    }

    private static class StarDateStringBinding extends StringBinding{

        private final StarDateFormatter sdf;
        private final String format;
        private final StarDate starDate;

        public StarDateStringBinding(StarDateFormatter sdf, String format, StarDate starDate) {
            this.bind(starDate.timeProperty());
            this.sdf = sdf;
            this.format = format;
            this.starDate = starDate;
        }

        @Override
        protected String computeValue() {
            return sdf.toString(starDate, format);
        }
    }
}
