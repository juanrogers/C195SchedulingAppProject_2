package Controller;

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
    private ComboBox<String> startTimeDropDownBox;
    @FXML
    private ComboBox<String> endTimeDropDownBox;
    @FXML
    private DatePicker datePicker;
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



    /**
     * This method will input a value into customer Id text field from a selected customer in the table.
     *
     * @param event clicking on customer in the table
     */
    @FXML
    void onMouseClickInputToCustTxtFld(MouseEvent event) {
        customerIdTxtFld.setText(String.valueOf(customerTable.getSelectionModel().getSelectedItem().getCustomer_Id()));
    }



    /** This method will update the appointment in database, and after appointment is updated, will take user back to the appointments screen.
     *
     * @param event clicking save button
     * @throws IOException
     */
    @FXML
    void onActionSaveUpdateAppt(ActionEvent event) throws IOException {

        try {

            if(Appointment.checkApptToBeSave(titleTxtFld, descriptionTxtFld, locationTxtFld, contactDropDownBox, typeDropDownBox, startTimeDropDownBox, endTimeDropDownBox)) {
                int appointment_Id = 0;
                String title = titleTxtFld.getText();
                String description = descriptionTxtFld.getText();
                String location = locationTxtFld.getText();
                String type = typeDropDownBox.getValue();
                String startOfAppt = startTimeDropDownBox.getValue();
                String endOfAppt = endTimeDropDownBox.getValue();

                String contactName = "";
                String customerName = "";
                int contact_Id = Contact.getContactIdByContactName(contactName);
                int customer_Id = Customer.getCustIdByCustName(customerName);
                int user_Id = DBAccessUsers.getCurrentUserID();

                Appointment appt = new Appointment(appointment_Id, title, description, location, type, Timestamp.valueOf(startOfAppt), Timestamp.valueOf(endOfAppt), customer_Id, user_Id, contact_Id);

                if(DBAccessAppointments.isApptToBeSetWithinBizHrs(appt)) {
                    if(DBAccessAppointments.checkToSeeIfApptsOvelap(appt)) {

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
        LocalTime setStartTime = appointment.getStartOfAppt().toLocalDateTime().toLocalTime();
        startTimeDropDownBox.setValue(String.valueOf(setStartTime));
        LocalTime setEndTime = appointment.getEndOfAppt().toLocalDateTime().toLocalTime();
        endTimeDropDownBox.setValue(String.valueOf(setEndTime));

        LocalDate appointmentDate = appointment.getStartOfAppt().toLocalDateTime().toLocalDate();
        datePicker.setValue(appointmentDate);
        customerIdTxtFld.setText(String.valueOf(appointment.getCustomer_Id()));

        for (User user : userIdDropDownBox.getItems()) {
            if (appointment.getUser_Id() == user.getUser_Id()) {
                userIdDropDownBox.setValue(user);
                break;
            }
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
     * This method initializes the update appointment screen and populates customer table, contact and user dropdown boxes, and convert time between local.
     *
     * @param url the location
     * @param resourceBundle the resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        prePopForTypeDropDownBox();
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        customerTable.setItems(DBAccessCustomers.getAllCustomers());

        contactDropDownBox.setItems(DBAccessContacts.getAllContacts());
        userIdDropDownBox.setItems(DBAccessUsers.getAllUsers());

        LocalTime appointmentStartTimeMinEST = LocalTime.of(8, 0);
        LocalDateTime startMinEST = LocalDateTime.of(LocalDate.now(), appointmentStartTimeMinEST);
        ZonedDateTime startMinZDT = startMinEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime startMinLocal = startMinZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentStartTimeMin = startMinLocal.toLocalTime();

        LocalTime appointmentStartTimeMaxEST = LocalTime.of(21, 45);
        LocalDateTime startMaxEST = LocalDateTime.of(LocalDate.now(), appointmentStartTimeMaxEST);
        ZonedDateTime startMaxZDT = startMaxEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime startMaxLocal = startMaxZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentStartTimeMax = startMaxLocal.toLocalTime();

        while (appointmentStartTimeMin.isBefore(appointmentStartTimeMax.plusSeconds(1))) {

            startTimeDropDownBox.getItems().add(String.valueOf(appointmentStartTimeMin));
            appointmentStartTimeMin = appointmentStartTimeMin.plusMinutes(15);

        }

        LocalTime appointmentEndTimeMinEST = LocalTime.of(8, 15);
        LocalDateTime endMinEST = LocalDateTime.of(LocalDate.now(), appointmentEndTimeMinEST);
        ZonedDateTime endMinZDT = endMinEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime endMinLocal = endMinZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentEndTimeMin = endMinLocal.toLocalTime();

        LocalTime appointmentEndTimeMaxEST = LocalTime.of(22, 0);
        LocalDateTime endMaxEST = LocalDateTime.of(LocalDate.now(), appointmentEndTimeMaxEST);
        ZonedDateTime endMaxZDT = endMaxEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime endMaxLocal = endMaxZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentEndTimeMax = endMaxLocal.toLocalTime();

        while (appointmentEndTimeMin.isBefore(appointmentEndTimeMax.plusSeconds(1))) {

            endTimeDropDownBox.getItems().add(String.valueOf(appointmentEndTimeMin));
            appointmentEndTimeMin = appointmentEndTimeMin.plusMinutes(15);

        }

    }

}
