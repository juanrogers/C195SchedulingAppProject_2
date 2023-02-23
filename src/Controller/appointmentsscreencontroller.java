package Controller;

import DBAccessObj.DBAccessAppointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.Appointment;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;



/** This controller will be used as the logic for the appointments screen.
 *
 * @author Ajuane Rogers */
public class appointmentsscreencontroller implements Initializable {

    /**
     * FX IDs for Add product form.
     */
    @FXML
    private Label appointmentsLabel;
    @FXML
    private Label viewByLabel;
    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button updateAppointmentButton;
    @FXML
    private Button deleteAppointmentButton;
    @FXML
    private Button backToMainMenuButton;
    @FXML
    private RadioButton viewByWeekRadioButton;
    @FXML
    private RadioButton viewByMonthRadioButton;
    @FXML
    private RadioButton viewAllRadioButton;
    @FXML
    private ToggleGroup radioButtonToggleGroup;
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, String> contactCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;



    /**
     * Variables for stages and scenes.
     */
    Stage stage;
    Parent scene;



    /**
     * This method will show all appointments in the table.
     *
     * @param event clicking on "all" radio button
     */
    @FXML
    void onActionViewAll(ActionEvent event) {
        appointmentsTable.setItems(DBAccessAppointments.getAllAppointments());
    }

    /**
     * This method will show current month appointments in the table.
     *
     * @param event clicking on "current month" radio button
     */
    @FXML
    void onActionViewByMonth(ActionEvent event) {
        appointmentsTable.setItems(DBAccessAppointments.getMonthAppointments());
    }

    /**
     * This method will show current week appointments in the table.
     *
     * @param event clicking on "current week" radio button
     */
    @FXML
    void onActionViewByWeek(ActionEvent event) {
        appointmentsTable.setItems(DBAccessAppointments.getWeekAppointments());
    }

    /**
     * This method will delete an appointment from the database.
     *
     * @param event clicking on delete appointment button
     * @throws IOException
     */
    @FXML
    void onActionGoToDeleteAppointment(ActionEvent event) throws IOException {

        if (appointmentsTable.getSelectionModel().isEmpty()) {

            Alert alertUserMsg = new Alert(Alert.AlertType.ERROR);
            alertUserMsg.setHeaderText("PLEASE SELECT AN APPOINTMENT!");
            alertUserMsg.setContentText("No appointment was selected to delete.");
            Optional<ButtonType> result = alertUserMsg.showAndWait();

        }

        else {
            Alert alertUserMsg2 = new Alert(Alert.AlertType.CONFIRMATION);
            alertUserMsg2.setHeaderText("ARE YOU SURE?");
            alertUserMsg2.setContentText("The appointment will be deleted, do you want to continue? This action CANNOT be undone.");
            Optional<ButtonType> result = alertUserMsg2.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                int appointId = appointmentsTable.getSelectionModel().getSelectedItem().getAppointment_Id();
                String typeIdMsg = appointmentsTable.getSelectionModel().getSelectedItem().getType();
                String apptIdMessage = String.valueOf(appointmentsTable.getSelectionModel().getSelectedItem().getAppointment_Id());

                DBAccessAppointments.deleteAppointment(appointId);
                appointmentsTable.setItems(DBAccessAppointments.getAllAppointments());

                Alert alertUserMsg3 = new Alert(Alert.AlertType.INFORMATION);
                alertUserMsg3.setHeaderText("DELETED!");
                alertUserMsg3.setContentText("You have successfully deleted appointment " + typeIdMsg + ", a " + apptIdMessage + " appointment.");
                alertUserMsg3.showAndWait();

            }

            else {
                Alert alertUserMsg4 = new Alert(Alert.AlertType.INFORMATION);
                alertUserMsg4.setHeaderText("NOT DELETED!");
                alertUserMsg4.setContentText("The selected appointment was not deleted.");
                alertUserMsg4.showAndWait();
            }
        }
    }



    /**
     * This event will switch to the add appointment screen.
     *
     * @param event clicking the add appointment button
     * @throws IOException
     */
    @FXML
    void onActionGoToAddAppointment(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/addappointmentscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }



    /**
     * This event will switch to the update appointment screen.
     *
     * @param event clicking the update appointment button
     * @throws IOException
     */
    @FXML
    void onActionGoToUpdateAppointment(ActionEvent event) throws IOException {

        if (appointmentsTable.getSelectionModel().isEmpty()) {
            Alert alertUserMsg5 = new Alert(Alert.AlertType.ERROR);
            alertUserMsg5.setHeaderText("PLEASE SELECT AN APPOINTMENT!");
            alertUserMsg5.setContentText("No appointment was selected to update.");
            Optional<ButtonType> result = alertUserMsg5.showAndWait();

        }

        else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/updateappointmentscreen.fxml"));
            loader.load();
            updateappointmentscreencontroller ADMController = loader.getController();
            ADMController.appointmentToBeSentToUpdate(appointmentsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }


    /**
     * This event will switch back to the main screen.
     *
     * @param event clicking back to main menu button
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
     * This method initializes the appointments screen with all appointments..
     *
     * @param url the location.
     * @param resourceBundle the resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        viewAllRadioButton.setSelected(true);

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointment_Id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startOfAppt"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endOfAppt"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_Id"));

        appointmentsTable.setItems(DBAccessAppointments.getAllAppointments());

    }

}



