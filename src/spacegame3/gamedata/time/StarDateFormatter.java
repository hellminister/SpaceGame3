package spacegame3.gamedata.time;

import java.nio.file.Path;

public abstract class StarDateFormatter {

        private static final String TIME_FORMAT_FOLDER_URL = "data/timeformat/";

        public static StarDateFormatter build(Path storyPath, String calendarName) {
            Path structurePath = storyPath.resolve(TIME_FORMAT_FOLDER_URL + calendarName + ".txt");

            StarDateFormatter formatter = null;

            return switch (calendarName){
                case "iso8601" -> new StarDateEarthCalendar(structurePath, calendarName);
                default -> new StarDateCustomCalendar(structurePath);
            };
        }

        public abstract String toString(StarDate timestamp, String formatName);

    public abstract long toFutureTimestamp(StarDate currentTimestamp, long value, String unit);

    public abstract String getCalendarName();
}
