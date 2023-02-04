package DBAccessObj;

import Utility.DBConnect;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;



/** This class handles the logic for customers within the database.
 *
 * @author Ajuane Rogers*/
public class DBAccessCustomers {

    /**
     * This method returns all customers in the database.
     *
     * @return all customers in the database
     */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> listOfCustomers = FXCollections.observableArrayList();

        try {

            String sqlGetCusts = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, " +
                    "first_level_divisions.COUNTRY_ID, first_level_divisions.Division FROM customers, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID ORDER BY Customer_ID";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlGetCusts);
            ResultSet resultSet = preState.executeQuery();

            while (resultSet.next()) {

                int customer_Id = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                int division_Id = resultSet.getInt("Division_ID");
                int country_Id = resultSet.getInt("COUNTRY_ID");
                String divisionName = resultSet.getString("Division");

                Customer custs = new Customer(customer_Id, customerName, address, postalCode, phone, country_Id, division_Id);
                listOfCustomers.add(custs);
            }

        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        return listOfCustomers;
    }



    /**
     * This method will add a customer to the database.
     *
     * @param customerName name of customer
     * @param address address of customer
     * @param postalCode postal code of customer
     * @param phone phone number of customer
     * @param divisionId division Id of customer
     */
    public static void addCustomer(String customerName, String address, String postalCode, String phone, int divisionId) {

        try {

            String sqladdCustomer = "INSERT INTO customers VALUES (NULL, ?, ?, ?, ?, NOW(), 'RZ', NOW(), 'RZ', ?)";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqladdCustomer);

            preState.setString(1, customerName);
            preState.setString(2, address);
            preState.setString(3, postalCode);
            preState.setString(4, phone  );
            preState.setInt(5, divisionId);

            preState.execute();

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

    }



    /**
     * This method updates a customer in the database.
     *
     * @param customerName name of customer.
     * @param address address of customer
     * @param postalCode postal code of customer
     * @param phone phone number of customer
     * @param divisionId division Id of the customer
     * @param customerId Id of customer
     */
    public static void updateCustomer(String customerName, String address, String postalCode, String phone, int divisionId, int customerId) {

        try {

            String sqlupdateCust = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";


            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlupdateCust);

            preState.setString(1, customerName);
            preState.setString(2, address);
            preState.setString(3, postalCode);
            preState.setString(4, phone  );
            preState.setInt(5, divisionId);
            preState.setInt(6, customerId);

            preState.execute();

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

    }



    /**
     * This method deletes a customer from the database.
     *
     * @param customerId id of customer.
     */
    public static void deleteCustomer(int customerId) {

        try {

            String sqldeleteAppt = "DELETE FROM appointments where Customer_ID = ?";

            PreparedStatement preStateDelAppt = DBConnect.connection().prepareStatement(sqldeleteAppt);

            preStateDelAppt.setInt(1, customerId);

            preStateDelAppt.execute();


            String sqldeleteCust = "DELETE FROM customers where Customer_ID = ?";

            PreparedStatement preStateDelCust = DBConnect.connection().prepareStatement(sqldeleteCust);

            preStateDelCust.setInt(1, customerId);

            preStateDelCust.execute();

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

    }

}


