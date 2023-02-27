package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.Customer;

import DBAccessObj.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



/** This controller will be used as the logic for the customers screen.
 *
 * @author Ajuane Rogers*/
public class customersscreencontroller implements Initializable {

    /**
     * FX IDs for main menu screen.
     */
    @FXML
    private Label customersLabel;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button updateCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Button backToMainMenuButton;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdCol;
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    @FXML
    private TableColumn<Customer, String> addressCol;
    @FXML
    private TableColumn<Customer, Integer> divisionCol;
    @FXML
    private TableColumn<Customer, String> postalCodeCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;



    /**
     * Variables for stages and scenes.
     */
    Stage stage;
    Parent scene;



    /**
     * This event will switch to the add customer screen.
     *
     * @param event clicking on the add customer button.
     * @throws IOException IOException
     */
    @FXML
    void onActionGoToAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/addcustomerscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * This event will switch back to the main menu screen.
     *
     * @param event clicking the main menu button.
     * @throws IOException IOException
     */
    @FXML
    void onActionGoToMainMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/mainscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }



    /**
     * This method will delete a customer from the database.
     *
     * @param event clicking the delete customer button.
     * @throws IOException IOException
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws IOException {

        if (customerTable.getSelectionModel().isEmpty()) {

            Alert alertUserMsg = new Alert(Alert.AlertType.ERROR);
            alertUserMsg.setHeaderText("PLEASE SELECT A CUSTOMER TO DELETE.");
            alertUserMsg.setContentText("No customer was selected to delete.");

            Optional<ButtonType> result = alertUserMsg.showAndWait();

        }

        else {

            Alert alertUserMsg2 = new Alert(Alert.AlertType.CONFIRMATION);
            alertUserMsg2.setHeaderText("ARE YOU SURE?");
            alertUserMsg2.setContentText("The customer selected will be deleted, do you want to complete this action? This action CANNOT be undone.");

            Optional<ButtonType> result = alertUserMsg2.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                int customerId = customerTable.getSelectionModel().getSelectedItem().getCustomer_Id();

                DBAccessCustomers.deleteCustomer(customerId);

                customerTable.setItems(DBAccessCustomers.getAllCustomers());


                Alert alertUserMsg3 = new Alert(Alert.AlertType.INFORMATION);
                alertUserMsg3.setHeaderText("DELETED!");
                alertUserMsg3.setContentText("Customer deleted.");

                alertUserMsg3.showAndWait();

            }

            else {

                Alert alertUserMsg4 = new Alert(Alert.AlertType.INFORMATION);
                alertUserMsg4.setHeaderText("NOT DELETED!");
                alertUserMsg4.setContentText("Customer not deleted.");

                alertUserMsg4.showAndWait();

            }

        }

    }



    /**
     * This event will switch to the update customer screen.
     *
     * @param event clicking on the update customer button.
     * @throws IOException IOException
     */
    @FXML
    void onActionGoToUpdateCustomer(ActionEvent event) throws IOException {

        if (customerTable.getSelectionModel().isEmpty()) {

            Alert alertUserMsg5 = new Alert(Alert.AlertType.ERROR);
            alertUserMsg5.setHeaderText("PLEASE SELECT A CUSTOMER.");
            alertUserMsg5.setContentText("No customer was selected to update.");

            Optional<ButtonType> result = alertUserMsg5.showAndWait();

        }

        else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/updatecustomerscreen.fxml"));
            loader.load();

            updatecustomerscreencontroller ADMController = loader.getController();
            ADMController.customerToBeSentToUpdate(customerTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }



    /**
     * This method initializes the customers screen, populated with all customers.
     * @param url the location.
     * @param resourceBundle the resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_Id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division_Id"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        customerTable.setItems(DBAccessCustomers.getAllCustomers());

    }

}




