package DBAccessObj;


import Utility.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.Appointment;

import java.sql.*;

import Model.Appointment;
import Model.Contact;
import Model.Media_Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.*;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;


/** This class handles the logic for appointments within the database.
 *
 * @author Ajuane Rogers*/
public class DBAccessAppointments {



    /**
     * This method will return all appointments listed in the database.
     *
     * @return appointments listed in the database
     */
    public static ObservableList<Appointment> getAllAppointments () {

        ObservableList<Appointment> listOfAppointments = FXCollections.observableArrayList();

        try {

            String sqlGetAppts = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, media_members.Member_ID, users.User_ID " +
                    "FROM appointments, contacts, media_members, users WHERE appointments.Member_ID = media_members.Member_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID  ORDER BY Appointment_ID";
            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlGetAppts);
            ResultSet resSet = preState.executeQuery();

            while (resSet.next()) {

                int appointment_Id = resSet.getInt("Appointment_ID");
                String title = resSet.getString("Title");
                String description = resSet.getString("Description");
                String location = resSet.getString("Location");
                String type = resSet.getString("Type");
                Timestamp startOfAppt = resSet.getTimestamp("Start");
                Timestamp endOfAppt = resSet.getTimestamp("End");
                int member_Id = resSet.getInt("Member_ID");
                int user_Id = resSet.getInt("User_ID");
                int contact_Id = resSet.getInt("Contact_ID");
                //String contactName = rs.getString("Contact_Name");


                Appointment appt = new Appointment(appointment_Id, title, description, location, type, startOfAppt, endOfAppt, member_Id, user_Id, contact_Id);
                listOfAppointments.add(appt);

            }

        } catch (SQLException expt) {

            expt.printStackTrace();

        }

        return listOfAppointments;

    }



    /**
     * This method will return all appointments in the database that are scheduled in the current week.
     *
     * @return appointments in the database from the current week
     */
    public static ObservableList<Appointment> getWeekAppointments() {

        ObservableList<Appointment> weekAppointmentsList = FXCollections.observableArrayList();

        try {

            String sqlGetWeekAppts = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, media_members.Member_ID, users.User_ID FROM appointments, contacts, media_members, users WHERE appointments.Member_ID = media_members.Member_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID AND week(Start, 0) = week(curdate(), 0) ORDER BY Appointment_ID";
            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlGetWeekAppts);
            ResultSet resultSet = preState.executeQuery();

            while (resultSet.next()) {

                int appointment_Id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp startOfAppt = resultSet.getTimestamp("Start");
                Timestamp endOfAppt = resultSet.getTimestamp("End");
                int member_Id = resultSet.getInt("Member_ID");
                int user_Id = resultSet.getInt("User_ID");
                int contact_Id = resultSet.getInt("Contact_ID");
                //String contactName = rs.getString("Contact_Name");


                Appointment appt = new Appointment(appointment_Id, title, description, location, type, startOfAppt, endOfAppt, member_Id, user_Id, contact_Id);

                weekAppointmentsList.add(appt);

            }

        } catch (SQLException expt) {

            expt.printStackTrace();

        }

        return weekAppointmentsList;

    }



    /**
     * This method will return all appointments in the database that are scheduled in the current month.
     *
     * @return appointments in the database from the current month
     */
    public static ObservableList<Appointment> getMonthAppointments() {

        ObservableList<Appointment> monthAppointmentsList = FXCollections.observableArrayList();

        try {

            String sqlGetMonthAppts = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, media_members.Member_ID, users.User_ID FROM appointments, contacts, media_members, users WHERE appointments.Member_ID = media_members.Member_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID AND month(Start) = month(curdate()) ORDER BY Appointment_ID";
            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlGetMonthAppts);
            ResultSet resSet = preState.executeQuery();

            while (resSet.next()) {

                int appointment_Id = resSet.getInt("Appointment_ID");
                String title = resSet.getString("Title");
                String description = resSet.getString("Description");
                String location = resSet.getString("Location");
                String type = resSet.getString("Type");
                Timestamp startOfAppt = resSet.getTimestamp("Start");
                Timestamp endOfAppt = resSet.getTimestamp("End");
                int member_Id = resSet.getInt("Member_ID");
                int user_Id = resSet.getInt("User_ID");
                int contact_Id = resSet.getInt("Contact_ID");
                //String contactName = rs.getString("Contact_Name");


                Appointment appt = new Appointment(appointment_Id, title, description, location, type, startOfAppt, endOfAppt, member_Id, user_Id, contact_Id);
                monthAppointmentsList.add(appt);

            }

        } catch (SQLException expt) {

            expt.printStackTrace();

        }

        return monthAppointmentsList;

    }



    /**
     * This method adds an appointment to the database.
     *
     * @param title       title of appointment.
     * @param description description of appointment.
     * @param location    location of appointment.
     * @param type        type of appointment.
     * @param startOfAppt       start time, date of appointment.
     * @param endOfAppt         end time, date of appointment.
     * @param member_Id     memberID for appointment.
     * @param user_Id     userID for appointment.
     * @param contact_Id  contact for appointment.
     */
    public static void addAppointment(String title, String description, String location, String type, Timestamp startOfAppt, Timestamp endOfAppt, int member_Id, int user_Id, int contact_Id) {

        try {
            String sqlAddAppt = "INSERT INTO appointments VALUES (NULL, ?, ?, ?, ?, ?, ?, NOW(), 'RZ', NOW(), 'RZ', ?, ?, ?)";
            PreparedStatement addAppt = DBConnect.connection().prepareStatement(sqlAddAppt);
            addAppt.setString(1, title);
            addAppt.setString(2, description);
            addAppt.setString(3, location);
            addAppt.setString(4, type);
            addAppt.setTimestamp(5, startOfAppt);
            addAppt.setTimestamp(6, endOfAppt);
            addAppt.setInt(7, member_Id);
            addAppt.setInt(8, user_Id);
            addAppt.setInt(9, contact_Id);
            addAppt.execute();

        } catch (SQLException expt) {
            expt.printStackTrace();
        }

    }



    /**
     * This method updates an appointment in the database.
     *
     * @param title          title of appointment.
     * @param description    description of appointment.
     * @param location       location of appointment.
     * @param type           type of appointment.
     * @param startOfAppt    start time, date of appointment.
     * @param endOfAppt      end time, date of appointment.
     * @param member_Id    memberID for appointment.
     * @param user_Id        userID for appointment.
     * @param contact_Id     contact Id of appointment.
     * @param appointment_Id Id of appointment.
     */
    public static void updateAppointment(String title, String description, String location, String type, Timestamp startOfAppt, Timestamp endOfAppt, int member_Id, int user_Id, int contact_Id, int appointment_Id) {
        try {

            String sqlUpdateAppt = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Member_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement updateAppt = DBConnect.connection().prepareStatement(sqlUpdateAppt);
            updateAppt.setString(1, title);
            updateAppt.setString(2, description);
            updateAppt.setString(3, location);
            updateAppt.setString(4, type);
            updateAppt.setTimestamp(5, startOfAppt);
            updateAppt.setTimestamp(6, endOfAppt);
            updateAppt.setInt(7, member_Id);
            updateAppt.setInt(8, user_Id);
            updateAppt.setInt(9, contact_Id);
            updateAppt.setInt(10, appointment_Id);
            updateAppt.execute();

        } catch (SQLException expt) {
            expt.printStackTrace();
        }

    }



    /**
     * This method will delete an appointment from the database.
     *
     * @param appointment_Id id of appointment.
     */
    public static void deleteAppointment(int appointment_Id) {

        try {
            String sqldeleteAppt = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement deleteAppt = DBConnect.connection().prepareStatement(sqldeleteAppt);
            deleteAppt.setInt(1, appointment_Id);
            deleteAppt.execute();
        } catch (SQLException expt) {
            expt.printStackTrace();
        }

    }



    /**
     * This method will return all appointment types in the database.
     *
     * @return appointment types in the database
     */
    public static ObservableList<String> getAllTypesOfAppts() {

        ObservableList<String> typesOfApptsList = FXCollections.observableArrayList();

        try {

            String sqlGetAllTypes = "SELECT DISTINCT type FROM appointments";
            PreparedStatement getAllTypes = DBConnect.connection().prepareStatement(sqlGetAllTypes);
            ResultSet resSet = getAllTypes.executeQuery();
            while (resSet.next()) {
                typesOfApptsList.add(resSet.getString(1));
            }

        } catch (SQLException expt) {
            expt.printStackTrace();
        }
        return typesOfApptsList;

    }



    /**
     * This method will return a specific type and number or appointments and that are in a specific month.
     *
     * @param month a specific month
     * @param type  a specific type
     * @return total number of appointments
     */
    public static int getTypeAndMonthCount(String month, String type) {

        int total = 0;
        try {
            String sqlTypeAndMonth = "SELECT count(*) FROM appointments WHERE type = ? AND monthname(start) = ?";
            PreparedStatement typeAndMonth = DBConnect.connection().prepareStatement(sqlTypeAndMonth);
            typeAndMonth.setString(1, type);
            typeAndMonth.setString(2, month);
            ResultSet resSet = typeAndMonth.executeQuery();

            if (resSet.next()) {
                return resSet.getInt(1);
            }

        } catch (SQLException expt) {
            expt.printStackTrace();
        }

        return total;

    }


    /**
     * This method will check for overlaps with appointments by checking time, time ranges and appointment_Id.
     *
     * @param startTime startTime
     * @param endTime endTime
     * @param thisAppointmentID thisAppointmentID
     * @return will return true: if overlap is found, false: if not
     */
    public static boolean checkForOverlap(LocalDateTime startTime, LocalDateTime endTime, int thisAppointmentID) throws SQLException {
        // Prepare SQL statement
        PreparedStatement preState =  DBConnect.connection.prepareStatement(
                "SELECT * FROM appointments "
                        + "WHERE (? BETWEEN Start AND End OR ? BETWEEN Start AND End OR ? < Start AND ? > End) "
                        + "AND (Appointment_ID != ?)");

        preState.setTimestamp(1, Timestamp.valueOf(startTime));
        preState.setTimestamp(2, Timestamp.valueOf(endTime));
        preState.setTimestamp(3, Timestamp.valueOf(startTime));
        preState.setTimestamp(4, Timestamp.valueOf(endTime));
        preState.setInt(5, thisAppointmentID);
        ResultSet resSet = preState.executeQuery();

        return !resSet.next();

    }



    /**
     * This method will get all of the appointment timestamps from all appointments.
     *
     * @return will return a list of timestamps for each appointment, with no duplicates
     */
    public static ObservableList<Timestamp> getAllAppointmentByTimestamps() {
        ObservableList<Timestamp> listOfAppointmentByMonTimeSt = FXCollections.observableArrayList();

        try {

            String sqlDBQuery = "SELECT * FROM appointments";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlDBQuery);

            ResultSet resSet = preState.executeQuery();

            while (resSet.next()) {

                Timestamp start = resSet.getTimestamp("Start");
                listOfAppointmentByMonTimeSt.add(start);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listOfAppointmentByMonTimeSt;

    }



    /**
     * This method will get all of the appointments by contact Ids.
     *
     * @return will return a list of all appointments by contact Ids
     */
    public static ObservableList<Integer> getAllAppointmentsByContactId() {
        ObservableList<Integer> listOfAppointmentsByContactId = FXCollections.observableArrayList();

        try {

            String sqlDBQuery = "SELECT * FROM appointments";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlDBQuery);

            ResultSet resSet = preState.executeQuery();

            while (resSet.next()) {

                int contactID = resSet.getInt("Contact_ID");
                listOfAppointmentsByContactId.add(contactID);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listOfAppointmentsByContactId;

    }



    /**
     * This method will get all of the appointments by contact names.
     *
     * @return will return a list of all appointments by contact names
     */
    public static ObservableList<String> getAllAppointmentsByContactName() {
        ObservableList<String> listOfAppointmentsByContactName = FXCollections.observableArrayList();

        try {

            String sqlDBQuery = "SELECT * FROM contacts";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlDBQuery);

            ResultSet resSet = preState.executeQuery();

            while (resSet.next()) {

                if (getAllAppointmentsByContactId().contains(resSet.getInt("Contact_ID"))) {
                    String contactName = resSet.getString("Contact_ID") + ": " + resSet.getString("Contact_Name");
                    listOfAppointmentsByContactName.add(contactName);

                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listOfAppointmentsByContactName;

    }



    /**
     * This method will get all of the appointments by media_member Ids.
     *
     * @return will return a list of all appointments by media_member Ids
     */
    public static ObservableList<Integer> getAllAppointmentsByMediaMemberId() {
        ObservableList<Integer> listOfAppointmentsByMemberId = FXCollections.observableArrayList();

        try {

            String sqlDBQuery = "SELECT * FROM appointments";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlDBQuery);

            ResultSet resSet = preState.executeQuery();

            while (resSet.next()) {

                int memberID = resSet.getInt("Member_ID");
                listOfAppointmentsByMemberId.add(memberID);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listOfAppointmentsByMemberId;

    }



    /**
     * This method will get all of the appointments by media_member names.
     *
     * @return will return a list of all appointments by media_member names
     */
    public static ObservableList<String> getAllAppointmentByMemberName() {
        ObservableList<String> listOfAppointmentsByMemberName = FXCollections.observableArrayList();

        try {
            String sqlDBQuery = "SELECT * FROM media_members";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlDBQuery);

            ResultSet resSet = preState.executeQuery();

            while (resSet.next()) {

                if (getAllAppointmentsByMediaMemberId().contains(resSet.getInt("Member_ID"))) {
                    String membName = resSet.getString("Member_ID") + ": " + resSet.getString("Member_Name");
                    listOfAppointmentsByMemberName.add(membName);

                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listOfAppointmentsByMemberName;

    }



    /**
     * This method will get all of the appointments for the selected contact.
     *
     * @param contact contact
     * @return will return a list of all appointments by selected contact
     */
    public static ObservableList<Appointment> appointmentsPerContact(String contact) {
        ObservableList<Appointment> listOfAppointmentsPerContact = FXCollections.observableArrayList();

        try {

            String sqlDBQuery = "SELECT * FROM appointments";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlDBQuery);

            ResultSet resSet = preState.executeQuery();

            while (resSet.next()) {
                if (Contact.getContactIdByContactName(contact) == resSet.getInt("Contact_ID")) {
                    int appointment_Id = resSet.getInt("Appointment_ID");
                    String title = resSet.getString("Title");
                    String description = resSet.getString("Description");
                    String location = resSet.getString("Location");
                    String type = resSet.getString("Type");
                    Timestamp startOfAppt = resSet.getTimestamp("Start");
                    Timestamp endOfAppt = resSet.getTimestamp("End");
                    int member_Id = resSet.getInt("Member_ID");
                    int user_Id = resSet.getInt("User_ID");
                    int contact_Id = resSet.getInt("Contact_ID");
                    Appointment appt = new Appointment(appointment_Id, title, description, location, type, startOfAppt, endOfAppt, member_Id,
                            user_Id, contact_Id);
                    listOfAppointmentsPerContact.add(appt);

                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listOfAppointmentsPerContact;

    }



    /**
     * This method will check the database for all appointments for a specific media_member on a specific date.
     *
     * @param apptDate apptDate
     * @param memb_Id memb_Id
     * @return willl return list of appointment for a specific media_member
     * @throws SQLException SQLException
     */
    public static ObservableList<Appointment> getApptsByDate(LocalDate apptDate, Integer memb_Id) throws SQLException {
        // Prepare SQL statement
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();
        PreparedStatement preState = DBConnect.connection().prepareStatement(
                "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c " +
                        "ON a.Contact_ID = c.Contact_ID WHERE datediff(a.Start, ?) = 0 AND Member_ID = ?;"
        );

        preState.setInt(2, memb_Id);

        preState.setString(1, apptDate.toString());  //Giving a "Null point error"

        ResultSet resSet = preState.executeQuery();

        while( resSet.next() ) {
            int appointment_Id = resSet.getInt("Appointment_ID");
            String title = resSet.getString("Title");
            String description = resSet.getString("Description");
            String location = resSet.getString("Location");
            String type = resSet.getString("Type");
            Timestamp startOfAppt = resSet.getTimestamp("Start");
            Timestamp endOfAppt = resSet.getTimestamp("End");
            int member_Id = resSet.getInt("Member_ID");
            int user_Id = resSet.getInt("User_ID");
            int contact_Id = resSet.getInt("Contact_ID");

            Appointment newAppt = new Appointment(
                    appointment_Id, title, description, location, type, startOfAppt, endOfAppt, member_Id, user_Id, contact_Id);
            filteredAppts.add(newAppt);

        }

        preState.close();
        return filteredAppts;

    }

}
