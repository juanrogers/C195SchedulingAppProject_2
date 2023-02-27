package Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import Model.Appointment;

import DBAccessObj.DBAccessAppointments;
import DBAccessObj.DBAccessUsers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;



/** This controller will be used as the logic for the login screen.
 *
 * @author Ajuane Rogers */
public class loginscreencontroller implements Initializable {

    /**
     * FX IDs for main menu screen.
     */
    @FXML
    private Label schedulingAssistantLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label zoneIdLabel;
    @FXML
    private Label zoneIdSwitchLabel;
    @FXML
    private TextField usernameTxtFld;
    @FXML
    private TextField passwordTxtFld;
    @FXML
    private Button signinButton;
    @FXML
    private Button exitButton;

    ResourceBundle resBundle = ResourceBundle.getBundle("langBund", Locale.getDefault());


    /**
     * Variables for confirmation and login messages
     */
    private String loginDataNotValid;
    private String pleaseEnterValidLoginData;
    private String sureConfirmation;
    private String exitConfirmation;



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
    void onActionUsernameTxtFld (ActionEvent event) {

    };
    @FXML
    void onActionPasswordTxtFld (ActionEvent event) {

    };


    /** This method exits the application.
     *
     * @param event clicking the exit button.
     */
    @FXML
    void onActionExit(ActionEvent event) {

        Alert alertUserMsg4 = new Alert(Alert.AlertType.CONFIRMATION);
        alertUserMsg4.setHeaderText(resBundle.getString("sureConfirmation"));
        alertUserMsg4.setContentText(resBundle.getString("exitConfirmation"));
        Optional<ButtonType> result = alertUserMsg4.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);

            //alertUserMsg4.setHeaderText(sureConfirmation);
            //alertUserMsg4.setContentText(exitConfirmation);
            // Optional<ButtonType> result = alertUserMsg4.showAndWait();
            // if (result.isPresent() && result.get() == ButtonType.OK) {
            //    ((Button)(event.getSource())).getScene().getWindow().hide();
        }

    }



    /**
     * This method authenticates the user name and password, and tracks the user activity by recording user login attempts to a text file.
     *
     * This event will sign check the user's credentials, validate, and sign the user in to the application. If credentials are not matched, user will not be signed in.
     *
     * -----> Lambda comment - Used a lambda expression for filtering the appointments list by user_Id, to check for any appointments within 15 minutes of login, for the user that is logging in.
     *
     * @param event clicking the sign in button
     * @throws IOException SQLException
     */
    @FXML
    void onActionSignin(ActionEvent event) throws IOException, SQLException {

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTimePlus15 = localDateTime.plusMinutes(15);

        String username = usernameTxtFld.getText();
        String password = passwordTxtFld.getText();

        DateTimeFormatter dtTimeFormt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localdttime = LocalDateTime.now();
        String strDttimeFormt = dtTimeFormt.format(localdttime);

        int userId = DBAccessUsers.validationOfUser(username, password);

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fileWriter);

        if (userId > 0) {

            outputFile.println(strDttimeFormt + " " + usernameTxtFld.getText() + " Login status: Successful!");
            outputFile.close();

            LocalDateTime now = LocalDateTime.now();

            ObservableList<Appointment> aList = DBAccessAppointments.getAllAppointments();
            ObservableList<Appointment> uList = aList.filtered(ap -> {

                if (ap.getUser_Id() == userId) {
                    return true;
                }
                return false;
            });

            boolean name = false;

            for (Appointment appt : uList) {

                if (appt.getStartOfAppt().toLocalDateTime().isAfter(localDateTime) && appt.getStartOfAppt().toLocalDateTime().isBefore(localDateTimePlus15)) {
                    Alert alertUserMsg = new Alert(Alert.AlertType.INFORMATION);
                    alertUserMsg.setHeaderText("UPCOMING APPOINTMENT!");
                    alertUserMsg.setContentText("You have an appointment scheduled within the next 15 minutes: Appointment " + appt.getAppointment_Id() + " at " + appt.getStartOfAppt().toLocalDateTime().toLocalTime());
                    alertUserMsg.showAndWait();
                    name = true;

                }

            }

            if (!name) {

                Alert alertUserMsg2 = new Alert(Alert.AlertType.INFORMATION);
                alertUserMsg2.setHeaderText("YOU HAVE NO UPCOMING APPOINTMENTS!");
                alertUserMsg2.setContentText("No appointments scheduled within the next 15 minutes.");
                alertUserMsg2.showAndWait();

            }

            Locale.setDefault(new Locale("en","US"));

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/mainscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

        else {

            outputFile.println(strDttimeFormt + " " + usernameTxtFld.getText() + " Login status: Unsuccessful!");
            outputFile.close();

            Alert alertUserMsg3 = new Alert(Alert.AlertType.ERROR);
            alertUserMsg3.setHeaderText(resBundle.getString("loginDataNotValid"));
            alertUserMsg3.setContentText(resBundle.getString("pleaseEnterValidLoginData"));
            alertUserMsg3.showAndWait();

        }

    }



    /**
     * This method initializes the 'LOGIN' screen. It utilizes a language resource bundle to allow for the entire screen to be translated to French, based on the locale of the local machine.
     *
     * @param url the location.
     * @param resourceBundle the resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {

                schedulingAssistantLabel.setText(resBundle.getString("schedulingAssistantLabel"));
                loginLabel.setText(resBundle.getString("loginLabel"));
                usernameLabel.setText(resBundle.getString("usernameLabel"));
                passwordLabel.setText(resBundle.getString("passwordLabel"));
                zoneIdLabel.setText(resBundle.getString("zoneIdLabel"));
                signinButton.setText(resBundle.getString("signinButton"));
                exitButton.setText(resBundle.getString("exitButton"));
                zoneIdSwitchLabel.setText((ZoneId.systemDefault()).getId());
                sureConfirmation = resBundle.getString("sureConfirmation");
                exitConfirmation = resBundle.getString("exitConfirmation");
                loginDataNotValid = resBundle.getString("loginDataNotValid");
                pleaseEnterValidLoginData = resBundle.getString("pleaseEnterValidLoginData");

            }

        }

        catch (Exception e) {

            System.out.println();

        }

    }

}