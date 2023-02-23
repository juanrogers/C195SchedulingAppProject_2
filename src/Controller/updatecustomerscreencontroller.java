package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Model.Country;
import Model.Customer;
import Model.Division;

import DBAccessObj.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



/** This controller will be used as the logic for the update customer screen.
 *
 * @author Ajuane Rogers */
public class updatecustomerscreencontroller implements Initializable {

    /**
     * FX IDs for add customer screen
     *
     */
    @FXML
    private Label updateCustomerLabel;
    @FXML
    private Label customerIdLabel;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label divWSwitchableLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private TextField customerIdTxtFld;
    @FXML
    private TextField customerNameTxtFld;
    @FXML
    private TextField addressTxtFld;
    @FXML
    private ComboBox<Country> countryDropDownBox;
    @FXML
    private ComboBox<Division> divisionDropDownBox;
    @FXML
    private TextField postalCodeTxtFld;
    @FXML
    private TextField phoneTxtFld;
    @FXML
    private Button saveUpdateCustomerButton;
    @FXML
    private Button cancelUpdateCustomerButton;
    //@FXML
    //private Label divisionLabel;



    /**
     * Variables for stages and scenes.
     */
    Stage stage;
    Parent scene;



    /**
     * Declared methods (not yet defined)
     *
     */
    @FXML
    void onActionCustomerIdTxtFld(){

    };

    @FXML
    void onActionCustNameTxtFld(){

    };

    @FXML
    void onActionAddrTxtFld(){

    };

    @FXML
    void onActionCoutDropDownBox(){

    };

    @FXML
    void onActionDivDropDownBox(){

    };

    @FXML
    void onActionPostCodeTxtFld (){

    };

    @FXML
    void onActionPhoneTxtFld(){

    };

    Customer customer;



    /** This method will update the customer in database, and after customer is updated, will take user back to the customers screen.
     *
     * @param event clicking the save button
     * @throws IOException
     */
    @FXML
    void onActionSaveUpdateCustomer(ActionEvent event) throws IOException {

        Alert alertUserMsg = new Alert(Alert.AlertType.CONFIRMATION);
        alertUserMsg.setHeaderText("ARE YOU SURE?");
        alertUserMsg.setContentText("The customer will be updated, do you want to continue?");

        Optional<ButtonType> result = alertUserMsg.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            String customerName = customerNameTxtFld.getText();
            String address = addressTxtFld.getText();
            String postalCode = postalCodeTxtFld.getText();
            String phone = phoneTxtFld.getText();
            Division division = divisionDropDownBox.getValue();

            if (!customerName.isEmpty() && !address.isEmpty() && !postalCode.isEmpty() && !phone.isEmpty() && !(division == null)) {

                DBAccessCustomers.updateCustomer(customerName, address, postalCode, phone, division.getDivision_Id(), customer.getCustomer_Id());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../view/customersscreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }

            else {

                Alert alertUserMsg2 = new Alert(Alert.AlertType.ERROR);
                alertUserMsg2.setHeaderText("Data entered is invalid!");
                alertUserMsg2.setContentText("Please enter valid values for all required fields.");
                alertUserMsg2.showAndWait();

            }

        }

    }



    /** This method will cancel the "update customer" action, and send user back to the customers screen.
     *
     * @param event clicking cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancelUpdateCustomer(ActionEvent event) throws IOException {

        Alert alertUserMsg3 = new Alert(Alert.AlertType.CONFIRMATION);
        alertUserMsg3.setHeaderText("ARE YOU SURE?");
        alertUserMsg3.setContentText("This action will close the update customer screen, do you want to continue?");

        Optional<ButtonType> result = alertUserMsg3.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/customersscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }



    /**
     * This method will choose the division label and populate the divisions dropdown box option, based on the option that was selected in the country dropdown box.
     *
     * @param event selection in country dropdown box
     */
    @FXML
    void onActionCoutDropDownBox(ActionEvent event) {

        Country count = countryDropDownBox.getSelectionModel().getSelectedItem();


        if (count.getCountry_Id() == 3) {

            divWSwitchableLabel.setText("Province: ");

        }

        else if (count.getCountry_Id() == 2) {

            divWSwitchableLabel.setText("Sub-division: ");

        }

        else if (count.getCountry_Id() == 1) {

            divWSwitchableLabel.setText("State: ");

        }

        if (count.getCountry_Id() == 3) {

            divisionDropDownBox.setItems(DBAccessDivisions.getCANDivisions());

        }

        else if (count.getCountry_Id() == 2) {

            divisionDropDownBox.setItems(DBAccessDivisions.getUKDivisions());

        }

        else if (count.getCountry_Id() == 1) {

            divisionDropDownBox.setItems(DBAccessDivisions.getUSDivisions());

        }

        else {

            divisionDropDownBox.isDisabled();

        }

    }



    /**
     * This method will send the customer selected in table to update customer screen.
     *
     * @param customer customer to send
     */
    public void customerToBeSentToUpdate(Customer customer) {

        this.customer = customer;

        customerIdTxtFld.setText(Integer.toString(customer.getCustomer_Id()));
        customerNameTxtFld.setText(customer.getCustomerName());
        addressTxtFld.setText(customer.getAddress());

        for (Country cout : countryDropDownBox.getItems()) {

            if(customer.country_Id == cout.getCountry_Id()) {

                countryDropDownBox.setValue(cout);

                break;
            }

        }

        Country cout = countryDropDownBox.getSelectionModel().getSelectedItem();


        //Will see if a switch statement works better for the code below------------------------



      /*  if (cout.getCountry_Id() == 3) {

            divWSwitchableLabel.setText("Province: ");

        }

        else if (cout.getCountry_Id() == 2) {

            divWSwitchableLabel.setText("Sub-division:");

        }

        else if (cout.getCountry_Id() == 1) {

            divWSwitchableLabel.setText("State: ");

        }

        if (cout.getCountry_Id() == 3) {

            divisionDropDownBox.setItems(DBAccessDivisions.getCANDivisions());

        }

        else if (cout.getCountry_Id() == 2) {

           divisionDropDownBox.setItems(DBAccessDivisions.getUKDivisions());

        }

        else if (cout.getCountry_Id() == 1) {

            divisionDropDownBox.setItems(DBAccessDivisions.getUSDivisions());

        }

        else {

            divisionDropDownBox.isDisabled();

        } */

        for(Division div : divisionDropDownBox.getItems()) {

            if(customer.division_Id == div.getDivision_Id()) {

               divisionDropDownBox.setValue(div);

                break;
            }

        }

        postalCodeTxtFld.setText(customer.getPostalCode());
        phoneTxtFld.setText(customer.getPhone());

    }



    /**
     * This method initializes the update customer screen and populate counttry dropdown box options.
     *
     * @param url the location
     * @param resourceBundle the resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryDropDownBox.setItems(DBAccessCountries.getAllCountries());

    }

}