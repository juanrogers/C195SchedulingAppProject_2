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
     * This method shows all appointments in the table.
     *
     * @param event clicking on the 'ALL' radio button.
     */
    @FXML
    void onActionViewAll(ActionEvent event) {

        appointmentsTable.setItems(DBAccessAppointments.getAllAppointments());

    }

    /**
     * This method shows current month appointments in the table.
     *
     * @param event clicking on the 'CURRENT MONTH' radio button.
     */
    @FXML
    void onActionViewByMonth(ActionEvent event) {

        appointmentsTable.setItems(DBAccessAppointments.getMonthAppointments());

    }

    /**
     * This method shows current week appointments in the table.
     *
     * @param event clicking on the 'CURRENT WEEK' radio button.
     */
    @FXML
    void onActionViewByWeek(ActionEvent event) {

        appointmentsTable.setItems(DBAccessAppointments.getWeekAppointments());

    }

    /**
     * This method deletes an appointment from the database.
     *
     * @param event clicking on the delete appointment button.
     * @throws IOException The exception that will be thrown in an error.
     */
    @FXML
    void onActionGoToDeleteAppointment(ActionEvent event) throws IOException {

        if (appointmentsTable.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PLEASE SELECT AN APPOINTMENT");
            alert.setContentText("No appointment was selected to delete.");

            Optional<ButtonType> result = alert.showAndWait();

        }

        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("ARE YOU SURE?");
            alert.setContentText("The appointment will be deleted from the database, are you sure you want to continue? This action cannot be undone.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                int appointmentId = appointmentsTable.getSelectionModel().getSelectedItem().getAppointment_Id();

                String typeMessage = appointmentsTable.getSelectionModel().getSelectedItem().getType();

                String idMessage = String.valueOf(appointmentsTable.getSelectionModel().getSelectedItem().getAppointment_Id());

                DBAccessAppointments.deleteAppointment(appointmentId);

                appointmentsTable.setItems(DBAccessAppointments.getAllAppointments());

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText("DELETED");
                alert2.setContentText("You have successfully deleted appointment " + idMessage + ", a " + typeMessage + " appointment.");

                alert2.showAndWait();

            }

            else {

                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setHeaderText("NOT DELETED");
                alert3.setContentText("The selected appointment was not deleted.");

                alert3.showAndWait();

            }

        }

    }


    /**
     * This method sends the user to the 'ADD APPOINTMENT' screen.
     *
     * @param event clicking the add appointment button.
     * @throws IOException
     */
    @FXML
    void onActionGoToAddAppointment(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/addappointmentscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * This event will switch to the update appointment screen.
     *
     * @param event clicking the update appointment button.
     * @throws IOException
     */
    @FXML
    void onActionGoToUpdateAppointment(ActionEvent event) throws IOException {

        if (appointmentsTable.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PLEASE SELECT AN APPOINTMENT");
            alert.setContentText("No appointment was selected to update.");

            Optional<ButtonType> result = alert.showAndWait();

        }

        else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/updateappointmentscreen.fxml"));
            loader.load();

            //UpdateAppointmentController ADMController = loader.getController();
            // ADMController.sendAppointment(appointmentsTable.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }


    /**
     * This event will switch back to the main menu screen.
     *
     * @param event clicking the back to main menu button.
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

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        appointmentsTable.setItems(DBAccessAppointments.getAllAppointments());

    }

}



