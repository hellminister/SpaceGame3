package spacegame3.gamedata.time;

import java.nio.file.Path;

public final class StarDateCustomCalendar extends StarDateFormatter{

    private static final String TIME_FORMAT_FOLDER_URL = "data/timeformat/";


    StarDateCustomCalendar(Path calendarPath) {

    }

    @Override
    public String toString(StarDate timestamp, String formatName) {
        return null;
    }

    @Override
    public long toFutureTimestamp(StarDate currentTimestamp, long value, String unit) {
        return 0;
    }

    @Override
    public String getCalendarName() {
        return null;
    }
}
