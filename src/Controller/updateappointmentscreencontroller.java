package Controller;

import Utility.TimeUtil;
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
import Model.*;

import DBAccessObj.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;



/** This controller will be used as the logic for the update appointment screen.
 *
 * @author Ajuane Rogers*/
public class updateappointmentscreencontroller implements Initializable {

    /**
     * FX IDs for main menu screen.
     */
    @FXML
    private Label updateAppointmentLabel;
    @FXML
    private Label selectCustomerLabel;
    @FXML
    private Label appointmentIdLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label contactLabel;
    @FXML
    private Label typeLabel;
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
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private Button saveUpdateApptButton;
    @FXML
    private Button cancelUpdateApptButton;


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
    void onActionApptIdTxtFld(){
    };

    @FXML
    void onActionTitleTxtFld(){
    };

    @FXML
    void onActionDescriptionTxtFld(){
    };

    @FXML
    void onActionLocationTxtFld (){
    };

    @FXML
    void onActionConDropDownBox(){
    };

    @FXML
    void onActionTypeDropDownBox (){
    };

    @FXML
    void onActionSrtTimeDropDownBox(){
    };

    @FXML
    void onActionEndTimeDropDownBox(){
    };

    @FXML
    void onActionDatePicker(){
    };

    @FXML
    void onActionCustIdTxtFld(){
    };

    @FXML
    void onActionUserIdDropDownBox (){
    };

    @FXML
    void onActionSaveUpdateAppt (){
    };

    @FXML
    void onActionCancelUpdateAppt (){
    };

    Appointment appointment;


    /** This method will set the pre-determined meeting types for type dropdown box.
     *
     */
    private void prePopForTypeDropDownBox() {

        ObservableList<String> optionsForAppts = FXCollections.observableArrayList();
        optionsForAppts.addAll("Quick Meeting", "De-Briefing", "Follow-up", "1-on-1", "Open Session", "Group Meeting", "Board Meeting", "Planning Meeting", "Breakfast Meeting", "Brunch Meeting", "Lunch Meeting", "Dinner Meeting");
        typeDropDownBox.setItems(optionsForAppts);

    }



    /**
     * This method will input a value into customer Id text field from a selected customer in the table.
     *
     * @param event clicking on customer in the table
     */
    @FXML
    void onMouseClickInputToCustTxtFld(MouseEvent event) {

        customerIdTxtFld.setText(String.valueOf(customerTable.getSelectionModel().getSelectedItem().getCustomer_Id()));

    }



    /**
     * This method will send the appointment selected in table to update appointment screen.
     *
     * @param appointment appointment to send
     */
    public void appointmentToBeSentToUpdate(Appointment appointment) {
        this.appointment = appointment;

        appointmentIdTxtFld.setText(Integer.toString(appointment.getAppointment_Id()));
        titleTxtFld.setText(appointment.getTitle());
        descriptionTxtFld.setText(appointment.getDescription());
        locationTxtFld.setText(appointment.getLocation());

        for (Contact cont : contactDropDownBox.getItems()) {

            if (appointment.getContact_Id() == cont.getContact_Id()) {

                contactDropDownBox.setValue(cont);
                break;

            }

        }

        typeDropDownBox.setValue(appointment.getType());

        LocalTime startOfAppt = appointment.getStartOfAppt().toLocalDateTime().toLocalTime();
        startTimeDropDownBox.setValue(startOfAppt);

        LocalTime endOfAppt = appointment.getEndOfAppt().toLocalDateTime().toLocalTime();
        endTimeDropDownBox.setValue(endOfAppt);

        LocalDate appointmentDate = appointment.getStartOfAppt().toLocalDateTime().toLocalDate();
        datePickerBox.setValue(appointmentDate);

        customerIdTxtFld.setText(String.valueOf(appointment.getCustomer_Id()));

        for (User user : userIdDropDownBox.getItems()) {

            if (appointment.getUser_Id() == user.getUser_Id()) {

                userIdDropDownBox.setValue(user);
                break;

            }

        }

    }



    /** This method will cancel the "update appointment" action, and send user back to the appointments screen.
     *
     * @param event clicking cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancelUpdateAppt(ActionEvent event) throws IOException {

        Alert alertUserMsg4 = new Alert(Alert.AlertType.CONFIRMATION);
        alertUserMsg4.setHeaderText("ARE YOU SURE?");
        alertUserMsg4.setContentText("This action will close the update appointment screen, do you want to continue?");
        Optional<ButtonType> result = alertUserMsg4.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/appointmentsscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }



    /** This method will update the appointment in database, and after appointment is updated, will take user back to the appointments screen.
     *
     * @param event clicking save button
     * @throws IOException
     */
    @FXML
    void onActionSaveUpdateAppt(ActionEvent event) throws IOException, SQLException {

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
               // System.out.println("got here");
                if(DBAccessAppointments.checkForOverlap(startOfAppt, endOfAppt, 0)){

                    DBAccessAppointments.updateAppointment(title, description, location, type, Timestamp.valueOf(startOfAppt), Timestamp.valueOf(endOfAppt), customer_Id, user.getUser_Id(), contact.getContact_Id(), appointment.getAppointment_Id());

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("../view/appointmentsscreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                }

                else {

                    Alert alertUserMsg2 = new Alert(Alert.AlertType.ERROR);
                    alertUserMsg2.setHeaderText("OVERLAPPING APPOINTMENT(S)!");
                    alertUserMsg2.setContentText("Overlapping appointments with existing customers detected! Please try again");
                    alertUserMsg2.showAndWait();

                }

            }


        }

        else{

            Alert alertUserMsg2 = new Alert(Alert.AlertType.ERROR);
            alertUserMsg2.setHeaderText("Data entered is invalid!");
            alertUserMsg2.setContentText("Please enter valid values for all required fields.");
            alertUserMsg2.showAndWait();
        }

    }



    /**
     * This method initializes the update appointment screen and populates customer table, contact and user dropdown boxes, and convert time between local.
     *
     * @param url the location
     * @param resourceBundle the resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        prePopForTypeDropDownBox();
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customer_Id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        customerTable.setItems(DBAccessCustomers.getAllCustomers());

        contactDropDownBox.setItems(DBAccessContacts.getAllContacts());
        userIdDropDownBox.setItems(DBAccessUsers.getAllUsers());

        startTimeDropDownBox.setItems(TimeUtil.getStartLocalTimes());
        endTimeDropDownBox.setItems(TimeUtil.getEndLocalTimes());

    }

}
