package Controller;

import Model.*;
import Utility.TimeUtil;
import Utility.ValidationForAppt;
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
    void onActionSaveAddAppointment(ActionEvent event) throws IOException, SQLException {

        Alert alertUserMsg = new Alert(Alert.AlertType.CONFIRMATION);
        alertUserMsg.setHeaderText("ARE YOU SURE?");
        alertUserMsg.setContentText("A new appointment will be added.");
        Optional<ButtonType> result = alertUserMsg.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            String title = titleTxtFld.getText();
            String description = descriptionTxtFld.getText();
            String location = locationTxtFld.getText();
            Contact contact = contactDropDownBox.getValue();
            String type = typeDropDownBox.getValue();
            LocalTime sTChosen = startTimeDropDownBox.getValue();
            LocalTime eTChosen = endTimeDropDownBox.getValue();
            LocalDate dateChosen = datePickerBox.getValue();
            User user = userIdDropDownBox.getValue();
            int customer_Id = Integer.parseInt(customerIdTxtFld.getText());//Customer.getCustomer_Id();

            if (!title.isEmpty() && !description.isEmpty() && !location.isEmpty() && (contact != null) && !type.isEmpty()
                    && (sTChosen != null) && (eTChosen != null) && (dateChosen != null) && (user != null) &&
                    (startTimeDropDownBox.getValue().isBefore(endTimeDropDownBox.getValue()) && (datePickerBox.getValue() != null))) {

                LocalDateTime startOfAppt = LocalDateTime.of(datePickerBox.getValue(), startTimeDropDownBox.getValue());
                LocalDateTime endOfAppt = LocalDateTime.of(datePickerBox.getValue(), endTimeDropDownBox.getValue());

                    if(DBAccessAppointments.checkForOverlap(startOfAppt, endOfAppt, 0)){

                        DBAccessAppointments.addAppointment(title, description, location, type, Timestamp.valueOf(startOfAppt), Timestamp.valueOf(endOfAppt), customer_Id, user.getUser_Id(), contact.getContact_Id());

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("../view/appointmentsscreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();

                    } else {

                        Alert alertUserMsg2 = new Alert(Alert.AlertType.ERROR);
                        alertUserMsg2.setHeaderText("OVERLAPPING APPOINTMENT(S)!");
                        alertUserMsg2.setContentText("Overlapping appointments with existing customers detected! Please try again");
                        alertUserMsg2.showAndWait();

                    }

            } else{

                Alert alertUserMsg2 = new Alert(Alert.AlertType.ERROR);
                alertUserMsg2.setHeaderText("Data entered is invalid!");
                alertUserMsg2.setContentText("Please enter valid values for all required fields.");
                alertUserMsg2.showAndWait();

            }
        }
    }





//    public boolean logicalTimeSelectCheck(ComboBox startTimeDropDownBox, ComboBox endTimeDropDownBox, DatePicker datePickerBox) {
//
//        LocalDateTime startOfAppt = LocalDateTime.of(datePickerBox.getValue(), (LocalTime) startTimeDropDownBox.getValue());
//        LocalDateTime endOfAppt = LocalDateTime.of(datePickerBox.getValue(), (LocalTime) endTimeDropDownBox.getValue());
//        LocalDate datePicked = datePickerBox.getValue();
//
//        if (endOfAppt.isBefore(startOfAppt)) {
//            Alert alertUserMsg14 = new Alert(Alert.AlertType.ERROR);
//            alertUserMsg14.setTitle("Error!");
//            alertUserMsg14.setContentText("The appointment end time must be after the start time. Please try again.");
//            alertUserMsg14.showAndWait();
//            return false;
//        }
//
//
//                //Will need to find a way to check to make sure date selected is a future date.
////        if (datePicked.isBefore(Timestamp.from((Instant.now())))){
////            Alert alertUserMsg15 = new Alert(Alert.AlertType.ERROR);
////            alertUserMsg15.setTitle("Error!");
////            alertUserMsg15.setContentText("The appointment date must be set for a future date. Please try again.");
////            alertUserMsg15.showAndWait();
////            return false;
////        }
//        return true;
//    }


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


               /* Appointment a = new Appointment(0,title, description, location, type,Timestamp.valueOf(startOfAppt), Timestamp.valueOf(endOfAppt),customer_Id, user.getUser_Id(),contact.getContact_Id());

                if(ValidationForAppt.checkToSeeIfApptsOvelap(a)){
                    System.out.println("overlap.");
                    return;
                }  */


