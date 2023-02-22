package Model;


/** This class will be used to handle customers.
 *
 * @author Ajuane Rogers*/

public class Customer {

    public int customer_Id;
    public String customerName;
    public String address;
    public String postalCode;
    public String phone;
    public int country_Id;
    public int division_Id;
    //public String divisionName;

    /** This is the constructor used for building a customer.
     *
     * @param customer_Id The id of the customer.
     * @param customerName The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param country_Id The country id for customer.
     * @param division_Id The division id for the customer.
    // @param divisionName The division name for the customer.
     */
    public Customer (int customer_Id, String customerName, String address, String postalCode, String phone, int country_Id, int division_Id) {

        this.customer_Id = customer_Id;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country_Id = country_Id;
        this.division_Id = division_Id;
        //this.divisionName = divisionName;

    }




    /**
     * Getters listed below
     */




    /**
     * @return will return the customer_Id
     */
    public int getCustomer_Id() {

        return customer_Id;

    }

    /**
     * @return will return the customerName
     */
    public String getCustomerName() {

        return customerName;

    }

    /**
     * @return will return the address
     */
    public String getAddress() {

        return address;

    }

    /**
     * @return will return the division_Id
     */
    public int getDivision_Id() {

        return division_Id;

    }

    /**
     * @return will return the postalCode
     */
    public String getPostalCode() {

        return postalCode;

    }

    /**
     * @return will return the phone
     */
    public String getPhone() {

        return phone;

    }

    /**
     * @return will returnr the divisionName
     */
    //public String getDivisionName() {

    //return divisionName;

    //}

}