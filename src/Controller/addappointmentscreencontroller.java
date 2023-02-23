package Controller;

import Utility.TimeUtil;
import com.sun.jdi.Value;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;

import DBAccessObj.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;



/** This controller will be used as the logic for the add appointment screen.
 *
 * @author Ajuane Rogers*/
public class addappointmentscreencontroller implements Initializable {

    /**
     * FX IDs for Add product form.
     */
    @FXML
    private Label addAppointmentLabel;
    @FXML
    private Label selectCustomerLabel;
    @FXML
    private Label appointmentIdLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label contactLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label customerIdLabel;
    @FXML
    private Label userIdLabel;
    @FXML
    private TextField appointmentIdTxtFld;
    @FXML
    private TextField titleTxtFld;
    @FXML
    private TextField descriptionTxtFld;
    @FXML
    private TextField locationTxtFld;
    @FXML
    private ComboBox<Contact> contactDropDownBox;
    @FXML
    private ComboBox<String> typeDropDownBox;
    @FXML
    private ComboBox<LocalTime> startTimeDropDownBox;
    @FXML
    private ComboBox<LocalTime> endTimeDropDownBox;
    @FXML
    private DatePicker datePickerBox;
    @FXML
    private TextField customerIdTxtFld;
    @FXML
    private ComboBox<User> userIdDropDownBox;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdCol;
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    @FXML
    private Button saveAddAppointmentButton;
    @FXML
    private Button cancelAddAppointmentButton;



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
    void onActionAppointmentIdTxtFld (ActionEvent event){
    };

    @FXML
    void onActionTitleTxtFld (ActionEvent event){
    };

    @FXML
    void onActionDescriptionTxtFld (ActionEvent event){
    };

    @FXML
    void onActionLocationTxtFld (ActionEvent event){
    };

    @FXML
    void onActionContactDropDownBox (ActionEvent event){
    };

    @FXML
    void onActionTypeTxtFld (ActionEvent event){
    };

    @FXML
    void onActionStartTimeDropDownBox (ActionEvent event){
    };

    @FXML
    void onActionEndTimeDropDownBox (ActionEvent event){
    };

    @FXML
    void onActionDatePickerBox (ActionEvent event){
    };

    @FXML
    void onActionCustomerIdTxtFld (ActionEvent event){
    };

    @FXML
    void onActionUserIdDropDownBox (ActionEvent event){
    };
    @FXML
    void onActionTypeDropDownBox (ActionEvent event){
    };



    /**
     * This method will input a value into customer Id text field from a selected customer in the table.
     *
     * @param event clicking on customer in the table
     */
    @FXML
    void onMouseClickInputToCustTxtFld(MouseEvent event) {
        customerIdTxtFld.setText(String.valueOf(customerTable.getSelectionModel().getSelectedItem().getCustomer_Id()));
    }



    /** This method will save the appointment in database, and after appointment is saved, will take user back to the appointments screen.
     *
     * @param event clicking save button
     * @throws IOException
     */
    @FXML
    void onActionSaveAddAppointment(ActionEvent event) throws IOException {

        try {

            if(Appointment.checkApptToBeSave(titleTxtFld, descriptionTxtFld, locationTxtFld, contactDropDownBox, typeDropDownBox, startTimeDropDownBox, endTimeDropDownBox)) {
                int appointment_Id = 0;
                String title = titleTxtFld.getText();
                String description = descriptionTxtFld.getText();
                String location = locationTxtFld.getText();
                String type = typeDropDownBox.getValue();

                LocalDateTime startOfAppt = LocalDateTime.of(datePickerBox.getValue(),startTimeDropDownBox.getValue());
                LocalDateTime endOfAppt = LocalDateTime.of(datePickerBox.getValue(),endTimeDropDownBox.getValue());

                String contactName = "";
                String customerName = "";
                int contact_Id = Contact.getContactIdByContactName(contactName);
                int customer_Id = Customer.getCustIdByCustName(customerName);
                int user_Id = DBAccessUsers.getCurrentUserID();

                Appointment appt = new Appointment(appointment_Id, title, description, location, type, Timestamp.valueOf(startOfAppt), Timestamp.valueOf(endOfAppt), customer_Id, user_Id, contact_Id);

                if(DBAccessAppointments.isApptToBeSetWithinBizHrs(appt))
                {
                    if(DBAccessAppointments.checkToSeeIfApptsOvelap(appt))
                    {
                        DBAccessAppointments.addAppointment(title, description, location, type, Timestamp.valueOf(startOfAppt), Timestamp.valueOf(endOfAppt), customer_Id, user_Id, contact_Id);
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("../view/appointmentsscreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                }
            }
        }
        catch (NullPointerException nullPointexpt) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointments");
            alert.setHeaderText("Appointment Time is Incomplete");
            alert.setContentText("Please enter a valid date and time.");
            alert.showAndWait();
        }

    }

    /** This method will cancel the "add appointment" action, and send user back to the appointment screen.
     *
     * @param event clicking the cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancelAddAppointment(ActionEvent event) throws IOException {

        Alert alertUserMsg5 = new Alert(Alert.AlertType.CONFIRMATION);
        alertUserMsg5.setHeaderText("ARE YOU SURE?");
        alertUserMsg5.setContentText("This action will close the add appointment screen, do you want to continue?");

        Optional<ButtonType> result = alertUserMsg5.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/appointmentsscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }


    /** This method will set the pre-determined meeting types for type dropdown box.
     *
     */
    private void prePopForTypeDropDownBox() {

        ObservableList<String> optionsForAppts = FXCollections.observableArrayList();
        optionsForAppts.addAll("Quick Meeting", "De-Briefing", "Follow-up", "1-on-1", "Open Session", "Group Meeting", "Board Meeting", "Planning Meeting", "Breakfast Meeting", "Brunch Meeting", "Lunch Meeting", "Dinner Meeting");
        typeDropDownBox.setItems(optionsForAppts);

    }


        /**
         * This method initializes the add appointment screen and populates customer table, contact and user dropdown boxes, and convert time between local time and EST.
         *
         * @param url the location
         * @param resourceBundle the resources
         */
        @Override
        public void initialize (URL url, ResourceBundle resourceBundle) {

            prePopForTypeDropDownBox();
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_Id"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

            customerTable.setItems(DBAccessCustomers.getAllCustomers());
            contactDropDownBox.setItems(DBAccessContacts.getAllContacts());
            userIdDropDownBox.setItems(DBAccessUsers.getAllUsers());

            startTimeDropDownBox.setItems(TimeUtil.getStartLocalTimes());
            endTimeDropDownBox.setItems(TimeUtil.getEndLocalTimes());
        }

    }


