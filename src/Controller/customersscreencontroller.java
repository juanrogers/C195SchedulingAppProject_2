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
     * This method deletes a customer from the database.
     *
     * @param event clicking the delete customer button.
     * @throws IOException
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws IOException {

        if (customerTable.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PLEASE SELECT A CUSTOMER TO DELETE.");
            alert.setContentText("No customer was selected to delete.");

            Optional<ButtonType> result = alert.showAndWait();

        }

        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("ARE YOU SURE?");
            alert.setContentText("The customer selected will be deleted, do you want to complete this action? This action CANNOT be undone.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                int customerId = customerTable.getSelectionModel().getSelectedItem().getCustomer_Id();

                DBAccessCustomers.deleteCustomer(customerId);

                customerTable.setItems(DBAccessCustomers.getAllCustomers());


                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText("DELETED");
                alert2.setContentText("Customer deleted.");

                alert2.showAndWait();

            }

            else {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setHeaderText("NOT DELETED");
                alert3.setContentText("Customer not deleted.");

                alert3.showAndWait();

            }

        }

    }



    /**
     * This event will switch to the add customer screen.
     *
     * @param event clicking on the add customer button.
     * @throws IOException
     */
    @FXML
    void onActionGoToAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/addcustomerscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }



    /**
     * This event will switch to the update customer screen.
     *
     * @param event clicking on the update customer button.
     * @throws IOException
     */
    @FXML
    void onActionGoToUpdateCustomer(ActionEvent event) throws IOException {

        if (customerTable.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PLEASE SELECT A CUSTOMER.");
            alert.setContentText("No customer was selected to update.");

            Optional<ButtonType> result = alert.showAndWait();

        }

        else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/updatecustomerscreen.fxml"));
            loader.load();

            //updatecustomerscreencontroller ADMController = loader.getController();
            //ADMController.customerToBeSentToUpdate(customerTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }



    /**
     * This event will switch back to the main menu screen.
     *
     * @param event clicking the main menu button.
     * @throws IOException
     */
    @FXML
    void onActionGoToMainMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/mainscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }



    /**
     * This method initializes the customers screen, populated with all customers.
     * @param url the location.
     * @param resourceBundle the resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        customerTable.setItems(DBAccessCustomers.getAllCustomers());

    }

}




