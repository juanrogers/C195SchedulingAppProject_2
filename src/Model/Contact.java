package Model;


/** This class will be used to handle contacts.
 *
 * @author Ajuane Rogers */

public class Contact {

    public int contact_Id;
    public String contactName;
    public String contactEmail;

    /** This is the constructor used for building a contact.
     *
     * @param contact_Id This holds the id of the contact.
     * @param contactName This holds the name of the contact.
     * @param contactEmail This holds the email of the contact.
     */
    public Contact (int contact_Id, String contactName, String contactEmail) {

        this.contact_Id = contact_Id;
        this.contactName = contactName;
        this.contactEmail = contactEmail;

    }



    /**
     * Getters listed below
     */




    /**
     * @return Getter for the contact_Id
     */
    public int getContact_Id() {

        return contact_Id;

    }

    /**
     * @return Getter for the contactName
     */
    public String getContactName() {

        return contactName;

    }

    /**
     * @return Getter for the contactEmail
     */
    public String getContactEmail() {

        return contactEmail;

    }




    /**
     * Setters listed below
     */





    /**
     * @param contact_Id Setter for the contact_Id
     */
    public void setContact_Id(int contact_Id) {

        this.contact_Id = contact_Id;

    }

    /**
     * @param contactName Setter for the contactName
     */
    public void setContactName(String contactName) {

        this.contactName = contactName;

    }

    /**
     * @param contactEmail Setter for the contactEmail
     */
    public void setEmail(String contactEmail) {

        this.contactEmail = contactEmail;

    }


    /**
     * @return This will return a contact name for use within a combobox.
     */
    @Override
    public String toString() {

        return (contactName);

    }

}