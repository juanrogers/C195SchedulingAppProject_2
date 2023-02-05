package Model;

import java.sql.Timestamp;


/** This class will be used to handle appointments.
 *
 * @author Ajuane Rogers */
public class Appointment {

    public int appointment_Id;
    public String title;
    public String description;
    public String location;
    public String type;
    public Timestamp start;
    public Timestamp end;
    public int customer_Id;
    public int user_Id;
    public int contact_Id;
    //public String contactName;

    /** This is the constructor used for building an appointment.
     *
     * @param appointment_Id This holds the id of the appointment.
     * @param title This holds the title of the appointment.
     * @param description This holds the description of the appointment.
     * @param location This holds the location of the appointment.
     * @param type This holds the type of appointment.
     * @param start This holds the start time and date of the appointment.
     * @param end This holds the end time and date of the appointment.
     * @param customer_Id This holds the customer Id for the appointment.
     * @param user_Id This holds the user Id for with the appointment.
     * @param contact_Id This holds the contact Id for the appointment.
    // @param contactName This holds the contact name for the appointment.
     */
    public Appointment (int appointment_Id, String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_Id, int user_Id, int contact_Id) {

        this.appointment_Id = appointment_Id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_Id = customer_Id;
        this.user_Id = user_Id;
        this.contact_Id = contact_Id;
        //this.contactName = contactName;

    }

    public Appointment(int appointment_id, String title, String description, String location, int contact_id, String contactName, String type, Timestamp startTime, Timestamp endTime, int custId, int user_id) {

    }



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
    public Timestamp getStart() {

        return start;

    }

    /**
     * @return will return the end
     */
    public Timestamp getEnd() {

        return end;

    }

    /**
     * @return Getter for the customer_Id
     */
    public int getCustomer_Id() {

        return customer_Id;

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
     * @param start Setter for the start
     */
    public void setStart(Timestamp start) {

        this.start = start;

    }

    /**
     * @param end Setter for the end
     */
    public void setEnd(Timestamp end) {

        this.end = end;

    }

    /**
     * @param customer_Id Setter for the customer_Id
     */
    public void setCustomer_Id(int customer_Id) {

        this.customer_Id = customer_Id;

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
     * @param contactName Setter for the contactName
     */
    //public void setContactName(String contactName) {
    //    this.contactName = contactName;
    //}

}
