package Utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

public class TimeUtil {


    private static ObservableList<LocalTime> startLocalTimes = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> endLocalTimes = FXCollections.observableArrayList();


    private static void loadTimeList() {
        ZonedDateTime startMinEST = ZonedDateTime.of(LocalDate.now(),LocalTime.of(8,0),ZoneId.of("America/New_York"));
        ZonedDateTime startMinLocal = startMinEST.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime startMaxLocal = startMinLocal.plusHours(14);

        while (startMinLocal.isBefore(startMaxLocal)) {
            startLocalTimes.add(startMinLocal.toLocalTime());
            startMinLocal = startMinLocal.plusMinutes(15);
            endLocalTimes.add(startMinLocal.toLocalTime());
        }
    }

    public static ObservableList<LocalTime> getStartLocalTimes() {
        if(startLocalTimes.size() == 0){
            loadTimeList();
        }
        return startLocalTimes;
    }

    public static ObservableList<LocalTime> getEndLocalTimes() {
        if(endLocalTimes.size() == 0){
            loadTimeList();
        }
        return endLocalTimes;
    }

}
