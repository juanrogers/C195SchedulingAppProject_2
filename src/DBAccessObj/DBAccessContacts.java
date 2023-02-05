package DBAccessObj;

import Utility.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/** This class handles the logic for contacts within the database.
 *
 * @author Ajuane Rogers */
public class DBAccessContacts {

    /**
     * This method returns all contacts in the database.
     *
     * @return all contacts in the database
     */
    public static ObservableList<Contact> getAllContacts() {

        ObservableList<Contact> listOfContacts = FXCollections.observableArrayList();

        try {

            String sqlGetContacts = "SELECT * FROM contacts";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlGetContacts);

            ResultSet resultSet = preState.executeQuery();

            while (resultSet.next()) {

                int contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String contactEmail = resultSet.getString("Email");
                Contact cont = new Contact(contactId, contactName, contactEmail);
                listOfContacts.add(cont);

            }

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

        return listOfContacts;

    }

}



