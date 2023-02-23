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
     * Static variables & methods (and others)
     */
    private static ObservableList<Appointment> appointmentsThatAreNear = FXCollections.observableArrayList();



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
     * This method will check to see if there are overlapping appointments.
     *
     * @param appointment appointment to be checked.
     * @return If there are overlapping appointments: true, if there are not overlapping appointments: false.
     *
    public static Boolean checkOverlappingAppointments(Appointment appointment) {

        try {

            String sqlChkOverlapAppts = "SELECT * FROM appointments WHERE ((? <= Start AND ? > Start) OR (? >= Start AND ? < End)) AND Customer_ID = ? AND Appointment_ID <> ?";

            PreparedStatement chkOverlapAppts = DBConnect.connection().prepareStatement(sqlChkOverlapAppts);

            chkOverlapAppts.setTimestamp(1, appointment.getStartOfAppt());
            chkOverlapAppts.setTimestamp(2, appointment.getStartOfAppt());
            chkOverlapAppts.setTimestamp(3, appointment.getStartOfAppt());
            chkOverlapAppts.setTimestamp(4, appointment.getStartOfAppt());
            chkOverlapAppts.setInt(5, appointment.getCustomer_Id());
            chkOverlapAppts.setInt(6, appointment.getAppointment_Id());


            ResultSet resSet = chkOverlapAppts.executeQuery();


            while (resultSet.next()) {

                return true;

            }


        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }  */


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
     * Gets all appointments from the database.
     *
     * @return Returns an ObservableList of all Appointments.
     *
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                        userID, contactID);
                appointmentList.add(appt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */










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
     * Gets all weeks from all appointments.
     *
     * @return Returns an ObservableList of String type reflecting the week for each appointment.
     *
    public static ObservableList<String> getAllAppointmentWeeks() {
        ObservableList<String> appointmentWeekList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT STR_TO_DATE((IF( CAST(WEEK(start,5) AS UNSIGNED) = 0,(CONCAT(CAST((CAST(YEAR(start) " +
                    "AS UNSIGNED) - 1) AS CHAR),'52 Monday')),(CONCAT(CAST(YEAR(start) AS CHAR),IF( CAST(WEEK(start,5) " +
                    "AS UNSIGNED) < 10,'0','' ),CAST(WEEK(start,5) AS CHAR),' Monday')))),'%X%V %W') AS weekStartDate " +
                    " from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String startWeekDay = rs.getString("weekStartDate");
                appointmentWeekList.add(startWeekDay);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentWeekList;
    }  */



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
     * Gets all types from all appointments.
     *
     * @return Returns an ObservableList of String type reflecting all types from all appointments.
     *
    public static ObservableList<String> getAllAppointmentTypes() {
        ObservableList<String> appointmentTypes = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (getAllAppointmentCustomerIDs().contains(rs.getInt("Customer_ID"))) {
                    String typeName = rs.getString("Type");
                    appointmentTypes.add(typeName);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentTypes;
    }  */



    /**
     * Gets all appointments filtered by a selected customer.
     *
     * @param customer A string value selected from the Customer filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected customer.
     *
    public static ObservableList<Appointment> filterApptsViewByCustomer(String customer) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (Customer.getCustomerIDByName(customer) == rs.getInt("Customer_ID")) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */





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
     * Gets all appointments filtered by a selected type.
     *
     * @param typeName A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected type.
     *
    public static ObservableList<Appointment> filterApptsViewByType(String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments WHERE type = ?";


            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, typeName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                        userID, contactID);
                appointmentList.add(appt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected customer, contact, and type.
     *
     * @param customerName A string value selected from the Customer filter in the main Appointments screen.
     * @param contactName  A string value selected from the Contact filter in the main Appointments screen.
     * @param typeName     A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByCustomerContactType(String customerName, String contactName,
                                                                                   String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        int customer_ID = Customer.getCustomerIDByName(customerName);
        int contact_ID = Contact.getContactIDByName(contactName);

        try {
            String sql = "SELECT * from appointments WHERE Customer_ID = ? AND Contact_ID = ? AND Type = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, customer_ID);
            ps.setInt(2, contact_ID);
            ps.setString(3, typeName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                        userID, contactID);
                appointmentList.add(appt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected customer and type.
     *
     * @param customerName A string value selected from the Customer filter in the main Appointments screen.
     * @param typeName     A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByCustomerAndType(String customerName, String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        int customer_ID = Customer.getCustomerIDByName(customerName);

        try {
            String sql = "SELECT * from appointments WHERE Customer_ID = ? AND Type = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, customer_ID);
            ps.setString(2, typeName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                        userID, contactID);
                appointmentList.add(appt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected contact and type.
     *
     * @param contactName A string value selected from the Contact filter in the main Appointments screen.
     * @param typeName    A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the input filters.
     *
    public static ObservableList<Appointment> filterApptsViewByContactAndType(String contactName, String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        int contact_ID = Contact.getContactIDByName(contactName);

        try {
            String sql = "SELECT * from appointments WHERE Contact_ID = ? AND Type = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, contact_ID);
            ps.setString(2, typeName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                        userID, contactID);
                appointmentList.add(appt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected month, customer, and type.
     *
     * @param month        A string value selected from the Month filter in the main Appointments screen.
     * @param customerName A string value selected from the Customer filter in the main Appointments screen.
     * @param typeName     A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByMonthCustomerType(String month, String customerName,
                                                                                 String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        int customer_ID = Customer.getCustomerIDByName(customerName);

        try {
            String sql = "SELECT * from appointments WHERE Customer_ID = ? AND Type = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, customer_ID);
            ps.setString(2, typeName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (month.equals(rs.getTimestamp("Start").toLocalDateTime().getMonth().toString())) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected month, contact, and type.
     *
     * @param month       A string value selected from the Month filter in the main Appointments screen.
     * @param contactName A string value selected from the Contact filter in the main Appointments screen.
     * @param typeName    A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByMonthContactType(String month, String contactName, String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        int contact_ID = Contact.getContactIDByName(contactName);

        try {
            String sql = "SELECT * from appointments WHERE Contact_ID = ? AND Type = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, contact_ID);
            ps.setString(2, typeName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (month.equals(rs.getTimestamp("Start").toLocalDateTime().getMonth().toString())) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected month and type.
     *
     * @param month    A string value selected from the Month filter in the main Appointments screen.
     * @param typeName A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByMonthAndType(String month, String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments WHERE Type = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, typeName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (month.equals(rs.getTimestamp("Start").toLocalDateTime().getMonth().toString())) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected week and type.
     *
     * @param week     A string value selected from the Week filter in the main Appointments screen.
     * @param typeName A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByWeekAndType(String week, String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String getWeek = "SELECT *, STR_TO_DATE((IF( CAST(WEEK(start,5) AS UNSIGNED) = 0,(CONCAT(CAST((CAST(YEAR(start) " +
                    "AS UNSIGNED) - 1) AS CHAR),'52 Monday')),(CONCAT(CAST(YEAR(start) AS CHAR),IF( CAST(WEEK(start,5) " +
                    "AS UNSIGNED) < 10,'0','' ),CAST(WEEK(start,5) AS CHAR),' Monday')))),'%X%V %W') AS weekStartDate " +
                    " from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getWeek);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (typeName.equals(rs.getString("Type")) && week.equals(rs.getString("weekStartDate"))) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected week, customer, and type.
     *
     * @param week         A string value selected from the Week filter in the main Appointments screen.
     * @param customerName A string value selected from the Customer filter in the main Appointments screen.
     * @param typeName     A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByWeekCustomerType(String week, String customerName,
                                                                                String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        int customer_ID = Customer.getCustomerIDByName(customerName);

        try {
            String getWeek = "SELECT *, STR_TO_DATE((IF( CAST(WEEK(start,5) AS UNSIGNED) = 0,(CONCAT(CAST((CAST(YEAR(start) " +
                    "AS UNSIGNED) - 1) AS CHAR),'52 Monday')),(CONCAT(CAST(YEAR(start) AS CHAR),IF( CAST(WEEK(start,5) " +
                    "AS UNSIGNED) < 10,'0','' ),CAST(WEEK(start,5) AS CHAR),' Monday')))),'%X%V %W') AS weekStartDate " +
                    " from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getWeek);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (typeName.equals(rs.getString("Type")) && week.equals(rs.getString("weekStartDate")) &&
                        customer_ID == rs.getInt("Customer_ID")) ;
                {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected week, contact, and type.
     *
     * @param week        A string value selected from the Week filter in the main Appointments screen.
     * @param contactName A string value selected from the Contact filter in the main Appointments screen.
     * @param typeName    A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByWeekContactType(String week, String contactName, String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        int contact_ID = Contact.getContactIDByName(contactName);

        try {
            String getWeek = "SELECT *, STR_TO_DATE((IF( CAST(WEEK(start,5) AS UNSIGNED) = 0,(CONCAT(CAST((CAST(YEAR(start) " +
                    "AS UNSIGNED) - 1) AS CHAR),'52 Monday')),(CONCAT(CAST(YEAR(start) AS CHAR),IF( CAST(WEEK(start,5) " +
                    "AS UNSIGNED) < 10,'0','' ),CAST(WEEK(start,5) AS CHAR),' Monday')))),'%X%V %W') AS weekStartDate " +
                    " from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getWeek);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (typeName.equals(rs.getString("Type")) && week.equals(rs.getString("weekStartDate")) &&
                        contact_ID == rs.getInt("Contact_ID")) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected customer and contact.
     *
     * @param customer A string value selected from the Customer filter in the main Appointments screen.
     * @param contact  A string value selected from the Contact filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByCustomerAndContact(String customer, String contact) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (Customer.getCustomerIDByName(customer) == rs.getInt("Customer_ID") &&
                        Contact.getContactIDByName(contact) == rs.getInt("Contact_ID")) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected month, customer, contact, and type.
     *
     * @param month        A string value selected from the Month filter in the main Appointments screen.
     * @param customerName A string value selected from the Customer filter in the main Appointments screen.
     * @param contactName  A string value selected from the Contact filter in the main Appointments screen.
     * @param typeName     A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByMonthCustomerContactType(String month, String customerName,
                                                                                        String contactName, String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        int customer_ID = Customer.getCustomerIDByName(customerName);
        int contact_ID = Contact.getContactIDByName(contactName);

        try {
            String sql = "SELECT * from appointments WHERE Customer_ID = ? AND Contact_ID = ? AND Type = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, customer_ID);
            ps.setInt(2, contact_ID);
            ps.setString(3, typeName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (month.equals(rs.getTimestamp("Start").toLocalDateTime().getMonth().toString())) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected month, customer, and contact.
     *
     * @param month        A string value selected from the Month filter in the main Appointments screen.
     * @param customerName A string value selected from the Customer filter in the main Appointments screen.
     * @param contactName  A string value selected from the Contact filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByMonthCustomerContact(String month, String customerName,
                                                                                    String contactName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (month.equals(rs.getTimestamp("Start").toLocalDateTime().getMonth().toString()) &&
                        Customer.getCustomerIDByName(customerName) == rs.getInt("Customer_ID") &&
                        Contact.getContactIDByName(contactName) == rs.getInt("Contact_ID")) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected month and customer.
     *
     * @param month        A string value selected from the Month filter in the main Appointments screen.
     * @param customerName A string value selected from the Customer filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByMonthAndCustomer(String month, String customerName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (month.equals(rs.getTimestamp("Start").toLocalDateTime().getMonth().toString()) &&
                        Customer.getCustomerIDByName(customerName) == rs.getInt("Customer_ID")) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected month and contact.
     *
     * @param month       A string value selected from the Month filter in the main Appointments screen.
     * @param contactName A string value selected from the Contact filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByMonthAndContact(String month, String contactName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (month.equals(rs.getTimestamp("Start").toLocalDateTime().getMonth().toString()) &&
                        Contact.getContactIDByName(contactName) == rs.getInt("Contact_ID")) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected month.
     *
     * @param month A string value selected from the Month filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected month.
     *
    public static ObservableList<Appointment> filterApptsViewByMonth(String month) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (month.equals(rs.getTimestamp("Start").toLocalDateTime().getMonth().toString())) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  *



    /**
     * Gets all appointments filtered by a selected week.
     *
     * @param week A string value selected from the Week filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected week.
     *
    public static ObservableList<Appointment> filterApptsViewByWeek(String week) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String getWeek = "SELECT *, STR_TO_DATE((IF( CAST(WEEK(start,5) AS UNSIGNED) = 0,(CONCAT(CAST((CAST(YEAR(start) " +
                    "AS UNSIGNED) - 1) AS CHAR),'52 Monday')),(CONCAT(CAST(YEAR(start) AS CHAR),IF( CAST(WEEK(start,5) " +
                    "AS UNSIGNED) < 10,'0','' ),CAST(WEEK(start,5) AS CHAR),' Monday')))),'%X%V %W') AS weekStartDate " +
                    " from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getWeek);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (week.equals(rs.getString("weekStartDate"))) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected week, customer, contact, and type.
     *
     * @param week         A string value selected from the Week filter in the main Appointments screen.
     * @param customerName A string value selected from the Customer filter in the main Appointments screen.
     * @param contactName  A string value selected from the Contact filter in the main Appointments screen.
     * @param typeName     A string value selected from the Type filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByWeekCustomerContactType(String week, String customerName,
                                                                                       String contactName, String typeName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String getWeek = "SELECT *, STR_TO_DATE((IF( CAST(WEEK(start,5) AS UNSIGNED) = 0,(CONCAT(CAST((CAST(YEAR(start) " +
                    "AS UNSIGNED) - 1) AS CHAR),'52 Monday')),(CONCAT(CAST(YEAR(start) AS CHAR),IF( CAST(WEEK(start,5) " +
                    "AS UNSIGNED) < 10,'0','' ),CAST(WEEK(start,5) AS CHAR),' Monday')))),'%X%V %W') AS weekStartDate " +
                    " from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getWeek);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (week.equals(rs.getString("weekStartDate")) &&
                        Customer.getCustomerIDByName(customerName) == rs.getInt("Customer_ID") &&
                        Contact.getContactIDByName(contactName) == rs.getInt("Contact_ID") &&
                        typeName.equals(rs.getString("Type"))) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected week, customer, and contact.
     *
     * @param week         A string value selected from the Week filter in the main Appointments screen.
     * @param customerName A string value selected from the Customer filter in the main Appointments screen.
     * @param contactName  A string value selected from the Contact filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByWeekCustomerContact(String week, String customerName,
                                                                                   String contactName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String getWeek = "SELECT *, STR_TO_DATE((IF( CAST(WEEK(start,5) AS UNSIGNED) = 0,(CONCAT(CAST((CAST(YEAR(start) " +
                    "AS UNSIGNED) - 1) AS CHAR),'52 Monday')),(CONCAT(CAST(YEAR(start) AS CHAR),IF( CAST(WEEK(start,5) " +
                    "AS UNSIGNED) < 10,'0','' ),CAST(WEEK(start,5) AS CHAR),' Monday')))),'%X%V %W') AS weekStartDate " +
                    " from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getWeek);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (week.equals(rs.getString("weekStartDate")) &&
                        Customer.getCustomerIDByName(customerName) == rs.getInt("Customer_ID") &&
                        Contact.getContactIDByName(contactName) == rs.getInt("Contact_ID")) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  *



    /**
     * Gets all appointments filtered by a selected week and customer.
     *
     * @param week         A string value selected from the Week filter in the main Appointments screen.
     * @param customerName A string value selected from the Customer filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByWeekAndCustomer(String week, String customerName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String getWeek = "SELECT *, STR_TO_DATE((IF( CAST(WEEK(start,5) AS UNSIGNED) = 0,(CONCAT(CAST((CAST(YEAR(start) " +
                    "AS UNSIGNED) - 1) AS CHAR),'52 Monday')),(CONCAT(CAST(YEAR(start) AS CHAR),IF( CAST(WEEK(start,5) " +
                    "AS UNSIGNED) < 10,'0','' ),CAST(WEEK(start,5) AS CHAR),' Monday')))),'%X%V %W') AS weekStartDate " +
                    " from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getWeek);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (week.equals(rs.getString("weekStartDate")) &&
                        Customer.getCustomerIDByName(customerName) == rs.getInt("Customer_ID")) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments filtered by a selected week and contact.
     *
     * @param week        A string value selected from the Week filter in the main Appointments screen.
     * @param contactName A string value selected from the Contact filter in the main Appointments screen.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the selected filters.
     *
    public static ObservableList<Appointment> filterApptsViewByWeekAndContact(String week, String contactName) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String getWeek = "SELECT *, STR_TO_DATE((IF( CAST(WEEK(start,5) AS UNSIGNED) = 0,(CONCAT(CAST((CAST(YEAR(start) " +
                    "AS UNSIGNED) - 1) AS CHAR),'52 Monday')),(CONCAT(CAST(YEAR(start) AS CHAR),IF( CAST(WEEK(start,5) " +
                    "AS UNSIGNED) < 10,'0','' ),CAST(WEEK(start,5) AS CHAR),' Monday')))),'%X%V %W') AS weekStartDate " +
                    " from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(getWeek);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (week.equals(rs.getString("weekStartDate")) &&
                        Contact.getContactIDByName(contactName) == rs.getInt("Contact_ID")) {
                    int apptID = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    int customerID = rs.getInt("Customer_ID");
                    int userID = rs.getInt("User_ID");
                    int contactID = rs.getInt("Contact_ID");
                    Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                            userID, contactID);
                    appointmentList.add(appt);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }  */



    /**
     * Gets all appointments for a given customer.
     *
     * @param customer A Customer object passed by the calling function.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the given customer.
     *
    public static ObservableList<Appointment> getAllAppointmentsByCustomerID(Customer customer) {
        ObservableList<Appointment> associatedApptsList = FXCollections.observableArrayList();

        int ID = customer.getCustomerID();

        try {
            String sql = "SELECT * from appointments WHERE Customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, ID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                        userID, contactID);
                associatedApptsList.add(appt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return associatedApptsList;
    }  */



    /**
     * Gets all appointments for a given contact.
     *
     * @param contact A Contact object passed by the calling function.
     * @return Returns an ObservableList of Appointment type that reflects all appointments for the given contact.
     *
    public static ObservableList<Appointment> getAllAppointmentsByContactID(Contact contact) {
        ObservableList<Appointment> associatedApptsList = FXCollections.observableArrayList();

        int ID = contact.getContactID();

        try {
            String sql = "SELECT * from appointments WHERE Contact_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, ID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appt = new Appointment(apptID, title, description, location, type, start, end, customerID,
                        userID, contactID);
                associatedApptsList.add(appt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return associatedApptsList;
    }  */








    /**
     * This method will add an appointment to the database.
     *
     * @param appt    appt
     * @param apptStTimeStamp A timestamp of start time for new appointment
     * @param apptEdTimeStamp A timestamp of end time for new appointment
     *
    public static void addAppointment(Appointment appt, Timestamp apptStTimeStamp, Timestamp apptEdTimeStamp) {
        int appointment_Id = 0;
        int user_Id = appt.getUser_Id();
        int customer_Id = appt.getCustomer_Id();
        String title = appt.getTitle();
        String description = appt.getDescription();
        String location = appt.getLocation();
        int contact_Id = appt.getContact_Id();
        String type = appt.getType();
        Timestamp startOfAppt = apptStTimeStamp;
        Timestamp endOfAppt = apptEdTimeStamp;

        String userLoggingIn = DBAccessUsers.getCurrentUser().getUserName();

        try {

            String sql = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "Created_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sql);
            preState.setInt(1, appointment_Id);
            preState.setString(2, title);
            preState.setString(3, description);
            preState.setString(4, location);
            preState.setString(5, type);
            preState.setTimestamp(6, apptStTimeStamp);
            preState.setTimestamp(7, apptEdTimeStamp);
            preState.setString(8, userLoggingIn);
            preState.setInt(9, customer_Id);
            preState.setInt(10, user_Id);
            preState.setInt(11, contact_Id);

            preState.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }

    }  */



    /**
     * Updates an appointment in the database.
     *
     * @param appt An appointment object instantiated from the ModifyAppointments_Controller.
     */
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

    }



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



    /**
     * This will check to see if the user who logged in has an appointment starting within the next 15 minutes of recorded timestamp of login attempt.
     * Appointments detected will also be added to the 'imminentAppointments' list.
     *
     * @return will returns the count of appointments for user who logged in (appointments that are within 15 minutes of user's login)
     */
    public static int nearDateTimeAppointments() {

        int appointmentTimeCounter = 0;

        try {

            String sqlDBQuery = "SELECT Appointment_ID, Start, date(start), time(start) FROM appointments WHERE Start >= UTC_TIME()";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlDBQuery);

            ResultSet resSet = preState.executeQuery();

            while (resSet.next()) {

                if (resSet.getTimestamp("Start").before(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)))) {
                    Appointment imminentAppointment = new Appointment(resSet.getInt("Appointment_ID"),
                            resSet.getDate("date(start)"), resSet.getTime("time(start)"));
                    appointmentTimeCounter++;
                    appointmentsThatAreNear.add(imminentAppointment);

                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentTimeCounter;

    }



    /**
     * Gets the list of all appointments occuring within 15 minutes of user's login.
     *
     * @return Returns an ObservableList of Appointment type, reflecting all appointments occuring within 15 minutes of user's login.
     */
    public static ObservableList<Appointment> getImminentAppts() {

        return appointmentsThatAreNear;

    }



    /**
     * This method will check to see if an appointment is being schedule outside of business hours (based on 8AM - 10PM EST).
     *
     * @param appt appt
     * @return will return true: if appointment to be schedule is outside of business hours (based on 8AM - 10PM EST), false: if so
     */
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
            alert.setContentText("Appointment cannot be scheduled before 8:00AM EST. \n Office hours are 8AM to 10PM EST. Please try again");
            alert.showAndWait();
            return false;

        } else if (zonedEndDateTimeEST.isAfter(zonedEndTime) || zonedStartDateTimeEST.isAfter(zonedEndTime)) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Appointment");
            alert.setHeaderText("The appointment time is outside of office hours.");
            alert.setContentText("Appointment cannot be scheduled before 8:00AM EST. \n Office hours are 8AM to 10PM EST. Please try again.");
            alert.showAndWait();
            return false;

        } else {

            return true;
        }

    }



    /**
     * Validates of new appointment overlaps with customer's existing appointment time
     * Displays the alert messages
     *
     * @param cus_ID cus_ID
     * @param apt_ID apt_ID
     * @param start start
     * @param end  end
     * @throws SQLException
     *
    public static void checkToSeeIfApptsOvelap (int customer_Id, int appointment_Id, Timestamp startOfAppt, Timestamp endOfAppt) throws SQLException {

        Alert Appt_overlap_error = new Alert(Alert.AlertType.ERROR);
        Appt_overlap_error.setHeaderText("Selected time Overlaps with existing appointment");
        String error = "";


        ObservableList<Appointment> appointments = DBAccessAppointments.getAllAppointments();


        for (Appointment appt : appointments) {

            // Don't compare an appointment being edited to itself
            if (apZ == a.getCustomer_ID()) {
                continue;
            }

            if (a.getStart().isBefore(end) && start.isBefore(a.getEnd())) {
                error = ("Appointment input appointment date time conflicts with  appointment ID " + a.getAppointment_ID());
                Appt_overlap_flag = true;
            }


        }

        if (error.isBlank()) {
            Appt_overlap_error.hide();


        } else {
            Appt_overlap_error.setContentText(error);
            Appt_overlap_error.showAndWait();

        }


    }  */



    /**
     * returns the  ObservableList of   Appointments  associated with a particular customer in the SQL database
     *
     * @param customer_Id customer_Id
     * @return aList -  - Appointments  ObservableList
     * @throws SQLException
     *
    public static ObservableList<Appointment> getCustomerAppts(int customer_Id) throws SQLException {

        ObservableList<Appointment> aList = FXCollections.observableArrayList();
        String sql = ("SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Customer_ID= '" + customer_Id + "'");

        try{

            PreparedStatement preState = DBConnect.connection().prepareStatement(sql);
            ResultSet rs = preState.executeQuery();

            while (rs.next()) {
                int appointment_Id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");


                LocalDateTime startOfAppt = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endOfAppt = rs.getTimestamp("End").toLocalDateTime();


                int Customer_ID = (rs.getInt("Customer_ID"));
                int User_ID = rs.getInt("User_ID");
                int Contact_ID = rs.getInt("Contact_ID");
                Appointment appt = new Appointment(appointment_Id, title, description, location, type, startOfAppt, endOfAppt, Customer_ID, User_ID, Contact_ID);
                aList.add(appt);

                //System.out.print(aList);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return  null;
        }

        return aList;

    }  */



    /** Checks whether an added or modified appointment's timeframe conflicts with an appointment also belonging to either the contact or customer.
     *
     * @param appointment An appointment object instantiated from either the AddAppointments_Controller or ModifyAppointments_Controller.
     * @return Returns true if an added or modified appointment's timeframe conflicts with a pre-existing appointment for the same contact or customer.
     */
    public static boolean checkToSeeIfApptsOvelap(Appointment appointment)
    {
        String overlappingAppts = "";

        boolean apptsOverlap;

        for(Appointment appt : DBAccessAppointments.getAllAppointments()) {

            apptsOverlap =
                    (appt.getStartOfAppt().after(appt.getStartOfAppt()) || appt.getStartOfAppt().equals(appt.getStartOfAppt())) &&
                            (appt.getStartOfAppt().before(appt.getEndOfAppt()) || appt.getStartOfAppt().equals(appt.getEndOfAppt())) ||
                            (appt.getEndOfAppt().before(appt.getEndOfAppt()) || appt.getEndOfAppt().equals(appt.getEndOfAppt())) &&
                                    (appt.getEndOfAppt().after(appt.getStartOfAppt()) || appt.getEndOfAppt().equals(appt.getStartOfAppt())) ||
                            (appt.getStartOfAppt().after(appt.getStartOfAppt()) && appt.getEndOfAppt().before(appt.getEndOfAppt()));

            if(appt.getAppointment_Id() != appt.getAppointment_Id()) {

                // If selected appt starts within another appt
                if(apptsOverlap)
                {
                    if(appt.getContact_Id() == appt.getContact_Id() && appt.getCustomer_Id() == appt.getCustomer_Id())
                    {
                        if(overlappingAppts.isEmpty())
                        {
                            overlappingAppts = "Appointment " + appt.getAppointment_Id() + ": " + appt.getTitle() + " starting at " +
                                    appt.getStartOfAppt() + "\n" + " and ending at " + appt.getEndOfAppt() + " overlaps with another appointment. Please make a new selection.";

                        }
                        else
                        {
                            overlappingAppts = overlappingAppts + "Appointment " + appt.getAppointment_Id() + ": " + appt.getTitle() + " starting at " +
                                    appt.getStartOfAppt() + "\n" + " and ending at " + appt.getEndOfAppt() + " overlaps with another appointment. Please make a new selection.";
                        }
                    }
                    else if(appt.getContact_Id() == appt.getContact_Id())
                    {
                        if(overlappingAppts.isEmpty())
                        {
                            overlappingAppts = "Appointment " + appt.getAppointment_Id() + ": " + appt.getTitle() + " starting at " +
                                    appt.getStartOfAppt() + "\n" + " and ending at " + appt.getEndOfAppt() + " overlaps with another appointment. Please make a new selection.";
                        }
                        else
                        {
                            overlappingAppts = overlappingAppts + "Appointment " + appt.getAppointment_Id() + ": " + appt.getTitle() + " starting at " +
                                    appt.getStartOfAppt() + "\n" + " and ending at " + appt.getEndOfAppt() + " overlaps with another appointment. Please make a new selection.";
                        }
                    }
                    else if(appt.getCustomer_Id() == appt.getCustomer_Id())
                    {
                        if(overlappingAppts.isEmpty())
                        {
                            overlappingAppts = "Appointment " + appt.getAppointment_Id() + ": " + appt.getTitle() + " starting at " +
                                    appt.getStartOfAppt() + "\n" + " and ending at " + appt.getEndOfAppt() + " overlaps with another appointment. Please make a new selection.";
                        }
                        else
                        {
                            overlappingAppts = overlappingAppts + "Appointment " + appt.getAppointment_Id() + ": " + appt.getTitle() + " starting at " +
                                    appt.getStartOfAppt() + "\n" + " and ending at " + appt.getEndOfAppt() + " overlaps with another appointment. Please make a new selection.";
                        }
                    }
                }
                else
                {
                    continue;
                }
            }
        }
        if(overlappingAppts.isEmpty())
        {
            return true;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Appointments");
            alert.setHeaderText("This appointment overlaps with another customer's appointment(s), Please make a new selection.");
            alert.setContentText(overlappingAppts);
            alert.showAndWait();
            return false;
        }
    }



}





























































