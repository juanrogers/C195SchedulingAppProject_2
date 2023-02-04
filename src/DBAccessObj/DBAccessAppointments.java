package DBAccessObj;


import Utility.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.Appointment;

import java.sql.*;



/** This class handles the logic for appointments within the database.
 *
 * @author Ajuane Rogers*/
public class DBAccessAppointments {


    /**
     * This method will return all appointments listed in the database.
     *
     * @return appointments listed in the database
     */
    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> listOfAppointments = FXCollections.observableArrayList();

        try {

            String sqlGetAppts = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID " +
                    "FROM appointments, contacts, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID  ORDER BY Appointment_ID";
            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlGetAppts);
            ResultSet resultSet = preState.executeQuery();

            while (resultSet.next()) {

                int appointment_Id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                int customer_Id = resultSet.getInt("Customer_ID");
                int user_Id = resultSet.getInt("User_ID");
                int contact_Id = resultSet.getInt("Contact_ID");
                //String contactName = rs.getString("Contact_Name");


                Appointment appt = new Appointment(appointment_Id, title, description, location, type, start, end, customer_Id, user_Id, contact_Id);
                listOfAppointments.add(appt);

            }

        }

        catch (SQLException e) {

            e.printStackTrace();

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
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                int customer_Id = resultSet.getInt("Customer_ID");
                int user_Id = resultSet.getInt("User_ID");
                int contact_Id = resultSet.getInt("Contact_ID");
                //String contactName = rs.getString("Contact_Name");


                Appointment appt = new Appointment(appointment_Id, title, description, location, type, start, end, customer_Id, user_Id, contact_Id);

                weekAppointmentsList.add(appt);

            }

        }

        catch (SQLException e) {

            e.printStackTrace();

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
            ResultSet resultSet = preState.executeQuery();

            while (resultSet.next()) {

                int appointment_Id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                int customer_Id = resultSet.getInt("Customer_ID");
                int user_Id = resultSet.getInt("User_ID");
                int contact_Id = resultSet.getInt("Contact_ID");
                //String contactName = rs.getString("Contact_Name");


                Appointment appt = new Appointment(appointment_Id, title, description, location, type, start, end, customer_Id, user_Id, contact_Id);
                monthAppointmentsList.add(appt);

            }

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

        return monthAppointmentsList;

    }



    /**
     * This method will check to see if there are overlapping appointments.
     *
     * @param appointment appointment to be checked.
     * @return If there are overlapping appointments: true, if there are not overlapping appointments: false.
     */
    public static Boolean checkOverlappingAppointments (Appointment appointment) {

        try {

            String sqlChkOverlapAppts = "SELECT * FROM appointments WHERE ((? <= Start AND ? > Start) OR (? >= Start AND ? < End)) AND Customer_ID = ? AND Appointment_ID <> ?";

            PreparedStatement chkOverlapAppts = DBConnect.connection().prepareStatement(sqlChkOverlapAppts);

            chkOverlapAppts.setTimestamp(1, appointment.getStart());
            chkOverlapAppts.setTimestamp(2, appointment.getEnd());
            chkOverlapAppts.setTimestamp(3, appointment.getStart());
            chkOverlapAppts.setTimestamp(4, appointment.getStart());
            chkOverlapAppts.setInt(5, appointment.getCustomer_Id());
            chkOverlapAppts.setInt(6, appointment.getAppointment_Id());


            ResultSet resultSet = chkOverlapAppts.executeQuery();


            while (resultSet.next()) {

                return true;

            }


        }

        catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }




    /**
     * This method adds an appointment to the database.
     *
     * @param title title of appointment.
     * @param description description of appointment.
     * @param location location of appointment.
     * @param type type of appointment.
     * @param start start time, date of appointment.
     * @param end end time, date of appointment.
     * @param customer_Id customerID for appointment.
     * @param user_Id userID for appointment.
     * @param contact_Id contact for appointment.
     */
    public static void addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_Id, int user_Id, int contact_Id) {

        try {

            String sqlAddAppt = "INSERT INTO appointments VALUES (NULL, ?, ?, ?, ?, ?, ?, NOW(), 'RZ', NOW(), 'RZ', ?, ?, ?)";

            PreparedStatement addAppt = DBConnect.connection().prepareStatement(sqlAddAppt);

            addAppt.setString(1, title);
            addAppt.setString(2, description);
            addAppt.setString(3, location);
            addAppt.setString(4, type);
            addAppt.setTimestamp(5, start);
            addAppt.setTimestamp(6, end);
            addAppt.setInt(7, customer_Id);
            addAppt.setInt(8, user_Id);
            addAppt.setInt(9, contact_Id);

            addAppt.execute();

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

    }



    /**
     * This method updates an appointment in the database.
     *
     * @param title title of appointment.
     * @param description description of appointment.
     * @param location location of appointment.
     * @param type type of appointment.
     * @param start start time, date of appointment.
     * @param end end time, date of appointment.
     * @param customer_Id customerID for appointment.
     * @param user_Id userID for appointment.
     * @param contact_Id contact Id of appointment.
     * @param appointment_Id Id of appointment.
     */
    public static void updateAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_Id, int user_Id, int contact_Id, int appointment_Id) {

        try {

            String sqlUpdateAppt = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement updateAppt = DBConnect.connection().prepareStatement(sqlUpdateAppt);

            updateAppt.setString(1, title);
            updateAppt.setString(2, description);
            updateAppt.setString(3, location);
            updateAppt.setString(4, type);
            updateAppt.setTimestamp(5, start);
            updateAppt.setTimestamp(6, end);
            updateAppt.setInt(7, customer_Id);
            updateAppt.setInt(8, user_Id);
            updateAppt.setInt(9, contact_Id);
            updateAppt.setInt(10, appointment_Id);

            updateAppt.execute();

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

    }




    /**
     * This method will delete an appointment from the database.
     *
     * @param appointment_Id id of appointment.
     */
    public static void deleteAppointment(int appointment_Id) {

        try {

            String sqldeleteAppt = "DELETE FROM appointments where Appointment_ID = ?";

            PreparedStatement deleteAppt = DBConnect.connection().prepareStatement(sqldeleteAppt);

            deleteAppt.setInt(1, appointment_Id);

            deleteAppt.execute();

        }

        catch (SQLException e) {

            e.printStackTrace();

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


            ResultSet resultSet = getAllTypes.executeQuery();

            while (resultSet.next()) {

                typesOfApptsList.add(resultSet.getString(1));

            }

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

        return typesOfApptsList;

    }




    /**
     *
     * This method will return a specific type and number or appointments and that are in a specific month.
     *
     * @param month a specific month
     * @param type a specific type
     * @return total number of appointments
     */
    public static int getTypeAndMonthCount(String month, String type) {

        int total = 0;

        try {

            String sqlTypeAndMonth = "SELECT count(*) FROM appointments WHERE type = ? AND monthname(start) = ?";


            PreparedStatement typeAndMonth = DBConnect.connection().prepareStatement(sqlTypeAndMonth);


            typeAndMonth.setString(1, type);
            typeAndMonth.setString(2, month);

            ResultSet resultSet = typeAndMonth.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt(1);

            }

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

        return total;

    }

}