package DBAccessObj;


import Utility.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.Appointment;

import java.sql.*;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;


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

            String sqlGetAppts = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID " +
                    "FROM appointments, contacts, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID  ORDER BY Appointment_ID";
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
                int customer_Id = resSet.getInt("Customer_ID");
                int user_Id = resSet.getInt("User_ID");
                int contact_Id = resSet.getInt("Contact_ID");
                //String contactName = rs.getString("Contact_Name");


                Appointment appt = new Appointment(appointment_Id, title, description, location, type, startOfAppt, endOfAppt, customer_Id, user_Id, contact_Id);
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

            String sqlGetWeekAppts = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID FROM appointments, contacts, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID AND week(Start, 0) = week(curdate(), 0) ORDER BY Appointment_ID";
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
                int customer_Id = resultSet.getInt("Customer_ID");
                int user_Id = resultSet.getInt("User_ID");
                int contact_Id = resultSet.getInt("Contact_ID");
                //String contactName = rs.getString("Contact_Name");


                Appointment appt = new Appointment(appointment_Id, title, description, location, type, startOfAppt, endOfAppt, customer_Id, user_Id, contact_Id);

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

            String sqlGetMonthAppts = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID FROM appointments, contacts, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID AND month(Start) = month(curdate()) ORDER BY Appointment_ID";
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
                int customer_Id = resSet.getInt("Customer_ID");
                int user_Id = resSet.getInt("User_ID");
                int contact_Id = resSet.getInt("Contact_ID");
                //String contactName = rs.getString("Contact_Name");


                Appointment appt = new Appointment(appointment_Id, title, description, location, type, startOfAppt, endOfAppt, customer_Id, user_Id, contact_Id);
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
     * @param customer_Id customerID for appointment.
     * @param user_Id     userID for appointment.
     * @param contact_Id  contact for appointment.
     */
    public static void addAppointment(String title, String description, String location, String type, Timestamp startOfAppt, Timestamp endOfAppt, int customer_Id, int user_Id, int contact_Id) {

        try {
            String sqlAddAppt = "INSERT INTO appointments VALUES (NULL, ?, ?, ?, ?, ?, ?, NOW(), 'RZ', NOW(), 'RZ', ?, ?, ?)";
            PreparedStatement addAppt = DBConnect.connection().prepareStatement(sqlAddAppt);
            addAppt.setString(1, title);
            addAppt.setString(2, description);
            addAppt.setString(3, location);
            addAppt.setString(4, type);
            addAppt.setTimestamp(5, startOfAppt);
            addAppt.setTimestamp(6, endOfAppt);
            addAppt.setInt(7, customer_Id);
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
     * @param customer_Id    customerID for appointment.
     * @param user_Id        userID for appointment.
     * @param contact_Id     contact Id of appointment.
     * @param appointment_Id Id of appointment.
     */
    public static void updateAppointment(String title, String description, String location, String type, Timestamp startOfAppt, Timestamp endOfAppt, int customer_Id, int user_Id, int contact_Id, int appointment_Id) {
        try {

            String sqlUpdateAppt = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement updateAppt = DBConnect.connection().prepareStatement(sqlUpdateAppt);
            updateAppt.setString(1, title);
            updateAppt.setString(2, description);
            updateAppt.setString(3, location);
            updateAppt.setString(4, type);
            updateAppt.setTimestamp(5, startOfAppt);
            updateAppt.setTimestamp(6, endOfAppt);
            updateAppt.setInt(7, customer_Id);
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
     * This method will get all of the appointments by customer Ids.
     *
     * @return will return a list of all appointments by customer Ids
     */
    public static ObservableList<Integer> getAllAppointmentsByCustomerId() {
        ObservableList<Integer> listOfAppointmentsByCustomerId = FXCollections.observableArrayList();

        try {

            String sqlDBQuery = "SELECT * FROM appointments";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlDBQuery);

            ResultSet resSet = preState.executeQuery();

            while (resSet.next()) {

                int customerID = resSet.getInt("Customer_ID");
                listOfAppointmentsByCustomerId.add(customerID);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listOfAppointmentsByCustomerId;

    }


    /**
     * This method will get all of the appointments by customer names.
     *
     * @return will return a list of all appointments by customer names
     */
    public static ObservableList<String> getAllAppointmentByCustomerName() {
        ObservableList<String> listOfAppointmentsByCustomerName = FXCollections.observableArrayList();

        try {
            String sqlDBQuery = "SELECT * FROM customers";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlDBQuery);

            ResultSet resSet = preState.executeQuery();

            while (resSet.next()) {

                if (getAllAppointmentsByCustomerId().contains(resSet.getInt("Customer_ID"))) {
                    String custName = resSet.getString("Customer_ID") + ": " + resSet.getString("Customer_Name");
                    listOfAppointmentsByCustomerName.add(custName);

                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listOfAppointmentsByCustomerName;

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
                    int customer_Id = resSet.getInt("Customer_ID");
                    int user_Id = resSet.getInt("User_ID");
                    int contact_Id = resSet.getInt("Contact_ID");
                    Appointment appt = new Appointment(appointment_Id, title, description, location, type, startOfAppt, endOfAppt, customer_Id,
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
     * Check if appointment time is available.
     * @param appointment_Id appointment_Id
     * @param sT sT
     * @param eT eT
     * @param customer_Id customer_Id
     * @return will return true: if an existing appointment is found for the customer, false: if not
     */
    public static boolean checkToSeeIfApptsOvelap (int appointment_Id, LocalDateTime sT, LocalDateTime eT, String customer_Id) {

        String sqlDBQuery = "SELECT * FROM appointments WHERE Appointment_ID != " + appointment_Id +
                " AND Customer_ID = " + customer_Id +
                " AND (( '" + sT + "' BETWEEN Start AND End) OR ( '" + eT + "' BETWEEN Start AND End ) /* Start or End lands during existing appointment */ " +
                "   OR ( '" + sT + "' < Start AND '" + eT + "' > End ) /* Start AND End encapsulate existing appointment */ " +
                "   OR ( '" + sT + "' = Start ) /* START matches Start */ " +
                "   OR ( '" +  eT  + "' = End ))  /* End matches End */ ";

        System.out.println(sqlDBQuery);

        try {

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlDBQuery);
            ResultSet resSet = preState.executeQuery();

            while ( resSet.next() ) {
                return true;
            }

        } catch (SQLException throwables) {
            System.out.println("Overlapping appointment found!");
            throwables.printStackTrace();
        }
        return false;
    }


    /**
     * Updates an appointment in the database.
     *
     * @param appt An appointment object instantiated from the ModifyAppointments_Controller.
     *
    public static boolean updateAppt(Appointment appt) {
    int appointment_Id = appt.getAppointment_Id();
    int user_Id = appt.getUser_Id();
    int customer_Id = appt.getCustomer_Id();
    String title = appt.getTitle();
    String description = appt.getDescription();
    String location = appt.getLocation();
    int contact_Id = appt.getContact_Id();
    String type = appt.getType();
    Timestamp startOfAppt = appt.getStartOfAppt();
    Timestamp endOfAppt = appt.getEndOfAppt();

    String afterUserLoggedIn = DBAccessUsers.getCurrentUser().getUserName();

    try {

    String sqlDBQuery = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, " +
    "Start = ?, End = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

    PreparedStatement preState = DBConnect.connection().prepareStatement(sqlDBQuery);

    preState.setString(1, title);
    preState.setString(2, description);
    preState.setString(3, location);
    preState.setString(4, type);
    preState.setTimestamp(5, startOfAppt);
    preState.setTimestamp(6, endOfAppt);
    preState.setString(7, afterUserLoggedIn);
    preState.setInt(8, customer_Id);
    preState.setInt(9, user_Id);
    preState.setInt(10, contact_Id);
    preState.setInt(11, appointment_Id);
    preState.executeUpdate();
    return true;

    } catch (SQLException throwables) {
    throwables.printStackTrace();

    return false;
    }

    }  */


    /**
     * Removes an appointment from the database.
     *
     * @param appt An appointment object instantiated from the Appointments_Controller.
     *
    public static boolean removeAppt(Appointment appt) {
    int apptID = appt.getApptID();

    try {
    String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

    ps.setInt(1, apptID);

    ps.executeUpdate();

    return true;

    } catch (SQLException throwables) {
    throwables.printStackTrace();

    return false;
    }
    }  */

}

























































