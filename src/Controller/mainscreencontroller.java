package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This controller will be used as the logic for the main menu screen.
 *
 * @author Ajuane Rogers */
public class mainscreencontroller implements Initializable {

    /**
     * FX IDs for main menu screen.
     */
    @FXML
    private Label schedulingSystemLabel;
    @FXML
    private Label mainMenuLabel;
    @FXML
    private Button viewCustomersButton;
    @FXML
    private Button viewAppointmentsButton;
    @FXML
    private Button viewReportsButton;
    @FXML
    private Button logoutButton;



    /**
     * Variables for stages and scenes.
     */
    Stage stage;
    Parent scene;



    /**
     * @param event This event will switch to the appointments screen.
     * @throws IOException
     */
    @FXML
    void onActionViewAppointments(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/appointmentsscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }



    /**
     * @param event This event will switch to the customers screen.
     * @throws IOException
     */
    @FXML
    void onActionViewCustomers(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/customersscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }



    /**
     * @param event This event will switch to the reports screen.
     * @throws IOException
     */
    @FXML
    void onActionViewReports(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/reportsscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }



    /**
     * @param event This event will switch back to the login screen.
     * @throws IOException
     */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText("This will log you out of the application, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/loginscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }



    /** This method initializes the main screen.
     * @param url The location.
     * @param resourceBundle The resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}