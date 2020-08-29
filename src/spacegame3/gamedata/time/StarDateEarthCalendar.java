package spacegame3.gamedata.time;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class StarDateEarthCalendar extends StarDateFormatter {
    private static final Logger LOG = Logger.getLogger(StarDateEarthCalendar.class.getName());

    private final Chronology calendar;
    private final ChronoLocalDateTime<? extends ChronoLocalDate> date;
    private final Map<String, DateTimeFormatter> formatters;

    StarDateEarthCalendar(Path calendarPath, String calendarName){
        formatters = new HashMap<>();
        calendar = Chronology.of(calendarName);
        var datetime = calendar.localDateTime(LocalDateTime.now());
        try (BufferedReader reader = Files.newBufferedReader(calendarPath)){

            String line = reader.readLine();
            while (line != null){
                if (!line.startsWith("#") && !line.startsWith(" ")) {
                    String[] parts = line.split(" \"|\"|(?<=FORMAT|START) ");

                    switch (parts[0]) {
                        case "FORMAT" -> formatters.put(parts[1], DateTimeFormatter.ofPattern(parts[2]));
                        case "START" -> datetime = calendar.localDateTime(LocalDateTime.parse(parts[1],
                                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                        default -> LOG.warning("Keyword " + parts[0] + " not recognize!");
                    }

                    line = reader.readLine();
                }
            }

        } catch (IOException ex) {
            LOG.severe(ex::toString);
        }
        date = datetime;
    }

    @Override
    public String toString(StarDate timestamp, String formatName) {
        DateTimeFormatter format = formatters.get(formatName);
        return date.plus(timestamp.getTime(), ChronoUnit.SECONDS).format(format);
    }

    @Override
    public long toFutureTimestamp(StarDate currentTimestamp, long value, String unit){
        var from = date.plus(currentTimestamp.getTime(), ChronoUnit.SECONDS);
        var to = from.plus(value, ChronoUnit.valueOf(unit));

        Duration t = Duration.between(date, to);

        return t.getSeconds();
    }

    @Override
    public String getCalendarName() {
        return calendar.getCalendarType();
    }
}
