package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

/** This class will be used to handle appointments.
 *
 * @author Ajuane Rogers */
public class Appointment {

    private int appointment_Id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startOfAppt;
    private Timestamp endOfAppt;
    private int member_Id;
    private int user_Id;
    private int contact_Id;
    //private Date dateForCloseAppts;
   // private Time timeForCloseAppts;
    //public String contactName;

    /**
     * This is the constructor used for building an appointment.
     *
     * @param appointment_Id This holds the id of the appointment.
     * @param title          This holds the title of the appointment.
     * @param description    This holds the description of the appointment.
     * @param location       This holds the location of the appointment.
     * @param type           This holds the type of appointment.
     * @param startOfAppt    This holds the start time and date of the appointment.
     * @param endOfAppt      This holds the end time and date of the appointment.
     * @param member_Id      This holds the member Id for the appointment.
     * @param user_Id        This holds the user Id for with the appointment.
     * @param contact_Id     This holds the contact Id for the appointment.
     *                       // @param contactName This holds the contact name for the appointment.
     */
    public Appointment(int appointment_Id, String title, String description, String location, String type, Timestamp startOfAppt, Timestamp endOfAppt, int member_Id, int user_Id, int contact_Id) {

        this.appointment_Id = appointment_Id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startOfAppt = startOfAppt;
        this.endOfAppt = endOfAppt;
        this.member_Id = member_Id;
        this.user_Id = user_Id;
        this.contact_Id = contact_Id;
        //this.contactName = contactName;

    }


    /**
     * List that holds all appointments.
     */
    private static ObservableList<Appointment> allAppts = FXCollections.observableArrayList();


    //public Appointment(int appointment_id, String title, String description, String location, int contact_id, String contactName, String type, Timestamp startTime, Timestamp endTime, int memberId, int user_id) {
    //}


    /**
     * Getters listed below
     */


    /**
     * @return will return the appointment_Id
     */
    public int getAppointment_Id() {

        return appointment_Id;

    }

    /**
     * @return will return the title
     */
    public String getTitle() {

        return title;

    }

    /**
     * @return will return the description
     */
    public String getDescription() {

        return description;

    }

    /**
     * @return will return the location
     */
    public String getLocation() {

        return location;

    }

    /**
     * @return will return the type
     */
    public String getType() {

        return type;

    }

    /**
     * @return will return the start
     */
    public Timestamp getStartOfAppt() {

        return startOfAppt;

    }

    /**
     * @return will return the end
     */
    public Timestamp getEndOfAppt() {

        return endOfAppt;

    }

    /**
     * @return Getter for the member_Id
     */
    public int getMember_Id() {

        return member_Id;

    }

    /**
     * @return Getter for the user_Id
     */
    public int getUser_Id() {

        return user_Id;

    }

    /**
     * @return Getter for the contact_Id
     */
    public int getContact_Id() {

        return contact_Id;

    }

    /**
     * @return Getter for the contactName
     */
    //public String getContactName() {
    //    return contactName;
    // }



    /**
     * Setters listed below
     */



    /**
     * @param appointment_Id Setter for the appointment_Id
     */
    public void setAppointment_Id(int appointment_Id) {

        this.appointment_Id = appointment_Id;

    }

    /**
     * @param title Setter for the title
     */
    public void setTitle(String title) {

        this.title = title;

    }

    /**
     * @param description Setter for the description
     */
    public void setDescription(String description) {

        this.description = description;

    }

    /**
     * @param location Setter for the location
     */
    public void setLocation(String location) {

        this.location = location;

    }

    /**
     * @param type Setter for the type
     */
    public void setType(String type) {

        this.type = type;

    }

    /**
     * @param startOfAppt Setter for the start
     */
    public void getStartOfAppt(Timestamp startOfAppt) {

        this.startOfAppt = startOfAppt;

    }

    /**
     * @param endOfAppt Setter for the end
     */
    public void getEndOfAppt(Timestamp endOfAppt) {

        this.endOfAppt = endOfAppt;

    }

    /**
     * @param member_Id Setter for the member_Id
     */
    public void setMember_Id(int member_Id) {

        this.member_Id = member_Id;

    }

    /**
     * @param user_Id Setter for the user_Id
     */
    public void setUser_Id(int user_Id) {

        this.user_Id = user_Id;

    }

    /**
     * @param contact_Id Setter for the contact_Id
     */
    public void setContact_Id(int contact_Id) {

        this.contact_Id = contact_Id;

    }



    /**
     * @return all parts
     */
    public static ObservableList<Appointment> getAllAppts() {

        return allAppts;
    }



    /**
     * @param apptID method searches for a appointment by appt ID number.
     * @return null if no appt ID is found.
     */
    public static Appointment lookupAppt(int apptID) {
        ObservableList<Appointment> allAppts = getAllAppts();

        for (int i = 0; i < allAppts.size(); i++) {
            Appointment appt = allAppts.get(i);
            if (appt.getAppointment_Id() == apptID) {
                return appt;
            }
        }
        return null;
    }




    /**
     * @param titleOfAppt method searches appointment by appt title.
     * @return filtered appts list
     */
    public static ObservableList<Appointment> lookupAppt(String titleOfAppt) {
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();

        for (Appointment appt : getAllAppts()) {
            if (appt.getTitle().contains(titleOfAppt)) {
                filteredAppts.add(appt);
            }
        }
        return filteredAppts;
    }



}

