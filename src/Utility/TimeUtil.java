package Utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;


/** This class handles the logic for time conversion/other time related tasks within the application.
 *
 * @author Ajuane Rogers*/
public class TimeUtil {

    /** Static list for TimeUtil class
     *
     */
    private static ObservableList<LocalTime> startLocalTimes = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> endLocalTimes = FXCollections.observableArrayList();



    /** This method will load time list.
     *
     */
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



    /** This method will return local start times.
     *
     * @return will return start times
     */
    public static ObservableList<LocalTime> getStartLocalTimes() {

        if(startLocalTimes.size() == 0){

            loadTimeList();

        }

        return startLocalTimes;

    }



    /** This method will return local end times.
     *
     *@return will return end times
     */
    public static ObservableList<LocalTime> getEndLocalTimes() {

        if(endLocalTimes.size() == 0){

            loadTimeList();

        }

        return endLocalTimes;

    }

}
