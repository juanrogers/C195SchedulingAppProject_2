package Utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.*;
import Utility.DBConnect;
import Model.Appointment;
import java.sql.*;
import javafx.scene.control.Alert;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import Utility.TimeUtil;
import com.sun.jdi.Value;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Model.Contact;
import Model.Customer;
import Model.User;
import DBAccessObj.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;


/** This class wiil be used to check for validation of added or updated appointments.
 *
 * @author Ajuane Rogers*/
public class ValidationForAppt {

    /** This method will check to see if added or updated appointment's timeframe collides with an appointment also belonging to either the contact or customer.
     *
     * @param appointment appointment
     * @return will return true: if added or updated appointment's timeframe collides with a pre-existing appointment for the same contact or customer
     */
    public static boolean checkToSeeIfApptsOvelap(Appointment appointment) {
        String olaps = "";
        boolean apptsOverlap = false;

        for(Appointment appt : DBAccessAppointments.getAllAppointments()) {
            if(appointment.getAppointment_Id() == appt.getAppointment_Id() || appointment.getCustomer_Id() != appt.getCustomer_Id()) {
                continue;
            }
                if (appt.getStartOfAppt().equals(appointment.getStartOfAppt()) && appt.getEndOfAppt().equals(appointment.getEndOfAppt())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("This appointment overlaps with another customer's appointment(s), Please make a new selection.");
                    alert.showAndWait();
                    return false;
                }

                else if (Timestamp.valueOf(String.valueOf(appt.getStartOfAppt())).before(Timestamp.valueOf(String.valueOf(appointment.getStartOfAppt()))) && appt.getEndOfAppt().equals(appointment.getEndOfAppt())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("This appointment overlaps with another customer's appointment(s), Please make a new selection.");
                    alert.showAndWait();
                    return false;
                }

                else if (Timestamp.valueOf(String.valueOf(appt.getStartOfAppt())).after(Timestamp.valueOf(String.valueOf(appointment.getStartOfAppt()))) && appt.getEndOfAppt().equals(appointment.getEndOfAppt())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("This appointment overlaps with another customer's appointment(s), Please make a new selection.");
                    alert.showAndWait();
                    return false;
                }

                else if (Timestamp.valueOf(String.valueOf(appt.getStartOfAppt())).after(Timestamp.valueOf(String.valueOf(appointment.getStartOfAppt()))) && appt.getEndOfAppt().before(appointment.getEndOfAppt())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("This appointment overlaps with another customer's appointment(s), Please make a new selection.");
                    alert.showAndWait();
                    return false;
                }

                else if (appt.getStartOfAppt().equals(appointment.getStartOfAppt()) && appt.getEndOfAppt().before(appointment.getEndOfAppt())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("This appointment overlaps with another customer's appointment(s), Please make a new selection.");
                    alert.showAndWait();
                    return false;
                }

                else if (Timestamp.valueOf(String.valueOf(appt.getStartOfAppt())).before(Timestamp.valueOf(String.valueOf(appointment.getStartOfAppt()))) && appt.getEndOfAppt().before(appointment.getEndOfAppt())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("This appointment overlaps with another customer's appointment(s), Please make a new selection.");
                    alert.showAndWait();
                    return false;
                }

                else if (Timestamp.valueOf(String.valueOf(appt.getStartOfAppt())).before(Timestamp.valueOf(String.valueOf(appointment.getStartOfAppt()))) && appt.getEndOfAppt().after(appointment.getEndOfAppt())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("This appointment overlaps with another customer's appointment(s), Please make a new selection.");
                    alert.showAndWait();
                    return false;
                }

            }
            return true;

    }



    /** This method will assist in checking to see if the date selected is set for a future date and if the start and end times make logical sense.
     *
     * @param startOfAppt startOfAppt
     * @param endOfAppt endOfAppt
     * @return will returns true: if all checks are true, false: if not
     *
    public static boolean checkApptToBeSave(Timestamp startOfAppt, Timestamp endOfAppt) {
        String errorMsgToUser = "";

        if(endOfAppt.before(startOfAppt) || startOfAppt.equals(endOfAppt)) {   //Giving a "Null point error"
            errorMsgToUser = errorMsgToUser + " The end time selection must be set to a time after the start time selection.\n Please try again.";
        }
        if(endOfAppt.before(Timestamp.from(Instant.now())) || startOfAppt.before(Timestamp.from(Instant.now()))) {
            errorMsgToUser = errorMsgToUser + " The appointment date must be set for a future date. Please try again.";
        }

        if(errorMsgToUser.isEmpty()) {
            return true;
        }

        else {
            Alert alertUserMsg = new Alert(Alert.AlertType.ERROR);
            alertUserMsg.setTitle("See message below details...");
            alertUserMsg.setHeaderText("Please enter data into all fields and make all selections.");
            alertUserMsg.setContentText(errorMsgToUser);
            alertUserMsg.showAndWait();
            return false;
        }

    } */



    /**
     * This method will check to see if an appointment is being schedule outside of business hours (based on 8AM - 10PM EST).
     *
     * @param appt appt
     * @return will return true: if appointment to be schedule is outside of business hours (based on 8AM - 10PM EST), false: if so
     *
    public static boolean isApptToBeSetWithinBizHrs(Appointment appt) {
    ZoneId currentZoneId = ZoneId.systemDefault();
    ZoneId eastZoneId = ZoneId.of("America/New_York");

    ZonedDateTime zonedStartDateTimeEST = appt.getStartOfAppt().toInstant().atZone(eastZoneId);
    ZonedDateTime zonedEndDateTimeEST = appt.getStartOfAppt().toInstant().atZone(eastZoneId);
    int apptYear = appt.getStartOfAppt().toLocalDateTime().getYear();
    int apptMonth = appt.getStartOfAppt().toLocalDateTime().getMonth().getValue();
    int apptDay = appt.getStartOfAppt().toLocalDateTime().getDayOfMonth();
    int apptHourAM = 8;
    int apptHourPM = 22;
    int apptMin = 00;
    int apptSec = 00;

    LocalDateTime startTime = LocalDateTime.of(apptYear, apptMonth, apptDay, apptHourAM, apptMin, apptSec);
    LocalDateTime endTime = LocalDateTime.of(apptYear, apptMonth, apptDay, apptHourPM, apptMin, apptSec);

    ChronoZonedDateTime zonedStartTime = ZonedDateTime.of(startTime, eastZoneId);
    ChronoZonedDateTime zonedEndTime = ZonedDateTime.of(endTime, eastZoneId);

    System.out.println("ZonedStartTime: " + zonedStartTime);
    System.out.println("ZonedEndTime: " + zonedEndTime);

    if (zonedStartDateTimeEST.isBefore(zonedStartTime)) {

    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Appointment");
    alert.setHeaderText("The appointment time is outside of office hours.");
    alert.setContentText("Appointments cannot be scheduled before 8:00AM EST. \n Office hours are 8AM to 10PM EST. Please try again");
    alert.showAndWait();
    return false;

    } else if (zonedEndDateTimeEST.isAfter(zonedEndTime) || zonedStartDateTimeEST.isAfter(zonedEndTime)) {

    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Appointment");
    alert.setHeaderText("The appointment time is outside of office hours.");
    alert.setContentText("Appointments cannot be scheduled past 10:00PM EST. \n Office hours are 8AM to 10PM EST. Please try again.");
    alert.showAndWait();
    return false;

    } else {

    return true;
    }

    }  */


}










