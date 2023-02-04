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
import java.time.LocalDateTime;
import java.time.ZoneId;
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



    /**
     * This method authenticates the user name and password, and tracks the user activity by recording user login attempts to a text file.
     *
     * This event will sign check the user's credentials, validate, and sign the user in to the application. If credentials are not matched, user will not be signed in.
     *
     * Re-write this comment-----> Discussion of lambda - I implemented a lambda expression to facilitate the filtering of the appointments list by user id to check for any within 15 minutes of login, for the user that is logging in.
     *
     * @param event clicking the sign in button
     * @throws IOException
     */
    @FXML
    void onActionSignin(ActionEvent event) throws IOException {

        String username = usernameTxtFld.getText();
        String password = passwordTxtFld.getText();

        DateTimeFormatter dtTimeFormt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localdttime = LocalDateTime.now();
        String strDttimeFormt = dtTimeFormt.format(localdttime);

        int userId = DBAccessUsers.validationOfUser(username, password);

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fileWriter);

        if (userId > 0) {

            outputFile.println(strDttimeFormt + " " + usernameTxtFld.getText() + " successfully logged in");
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

                {
                    if (appt.getStart().toLocalDateTime().isAfter(now) && appt.getStart().toLocalDateTime().isBefore(now.plusMinutes(15))) {

                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setHeaderText("UPCOMING APPOINTMENT");
                        alert3.setContentText("You have an appointment scheduled within the next 15 minutes: appointment " + appt.getAppointment_Id() + " at " + appt.getStart().toLocalDateTime());
                        alert3.showAndWait();

                        name = true;

                    }

                }

            }

            if (!name) {

                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setHeaderText("YOU HAVE NO UPCOMING APPOINTMENTS");
                alert3.setContentText("There no scheduled appointments within the next 15 minutes.");
                alert3.showAndWait();

            }


            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/mainscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

        else {
            outputFile.println(strDttimeFormt + " " + usernameTxtFld.getText() + " Login attempt was unsuccessful.");
            outputFile.close();

            Alert alert = new Alert(Alert.AlertType.ERROR, "Username or password is incorrect, please try again.");
            Optional<ButtonType> result = alert.showAndWait();


            alert.setHeaderText(loginDataNotValid);
            alert.setContentText(pleaseEnterValidLoginData);


            if (result.isPresent() && result.get() == ButtonType.OK) {

                usernameTxtFld.clear();
                passwordTxtFld.clear();

            }

        }

    }




/***********************************************************************
 // /**
 //  * This method authenticates the user name and password, and tracks the user activity by recording user login attempts to a text file.
 //  *
 //  * this method is the same as the first one, but activated by pressing the enter key instead.
 //  *
 //  * Discussion of lambda - I implemented a lambda expression to facilitate the filtering of the appointments list by user id to check for any within 15 minutes of login, for the user that is logging in.
 //  *
 //  * @param event pressing the ENTER key on the keyboard.
 // * @throws IOException The exception that will be thrown in an error.
 //

 @FXML
 void onPressEnterKeyLogin(KeyEvent event) throws IOException {

 if(event.getCode().equals(KeyCode.ENTER))
 {
 String username = usernameText.getText();
 String password = passwordText.getText();

 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
 LocalDateTime ldt = LocalDateTime.now();
 String s = dtf.format(ldt);

 int userId = DBUsers.validateUser(username, password);

 FileWriter fWriter = new FileWriter("login_activity.txt", true);
 PrintWriter outputFile = new PrintWriter(fWriter);

 if (userId > 0)
 {
 outputFile.println(s + " " + usernameText.getText() + " successfully logged in");
 outputFile.close();

 LocalDateTime now = LocalDateTime.now();

 ObservableList<Appointment> aList = DBAppointments.getAllAppointments();
 ObservableList<Appointment> uList = aList.filtered(ap ->
 {
 if (ap.getUserId() == userId)
 {
 return true;
 }
 return false;
 });

 boolean name = false;

 for (Appointment a : uList)
 {

 {
 if (a.getStart().toLocalDateTime().isAfter(now) && a.getStart().toLocalDateTime().isBefore(now.plusMinutes(15)))
 {
 Alert alert3 = new Alert(Alert.AlertType.ERROR);
 alert3.setHeaderText("UPCOMING APPOINTMENT");
 alert3.setContentText("You have an appointment scheduled within the next 15 minutes: appointment " + a.getAppointmentId() + " at " + a.getStart().toLocalDateTime());
 alert3.showAndWait();

 name = true;
 }
 }
 }

 if (!name)
 {
 Alert alert3 = new Alert(Alert.AlertType.ERROR);
 alert3.setHeaderText("NO UPCOMING APPOINTMENTS");
 alert3.setContentText("You have no appointments scheduled within the next 15 minutes.");
 alert3.showAndWait();

 }

 stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
 scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
 stage.setScene(new Scene(scene));
 stage.show();
 }
 else
 {
 outputFile.println(s + " " + usernameText.getText() + " unsuccessfully attempted to log in");
 outputFile.close();

 Alert alert = new Alert(Alert.AlertType.ERROR);
 alert.setHeaderText(invalidLoginData);
 alert.setContentText(pleaseEnterValid);

 Optional<ButtonType> result = alert.showAndWait();

 if (result.isPresent() && result.get() == ButtonType.OK)
 {
 usernameText.clear();
 passwordText.clear();
 }
 }
 }
 }


 ***********************************************************************/





    /** This method exits the application.
     *
     * @param event clicking the exit button.
     */
    @FXML
    void onActionExit(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to exit the application?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);

       // alert.setHeaderText(confirmSure);
       // alert.setContentText(confirmExit);

       // Optional<ButtonType> result = alert.showAndWait();

       // if (result.isPresent() && result.get() == ButtonType.OK) {
        //    ((Button)(event.getSource())).getScene().getWindow().hide();

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

            ResourceBundle resBundle = ResourceBundle.getBundle("languages/language", Locale.getDefault());

            if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {

                schedulingAssistantLabel.setText(resBundle.getString("title"));
                loginLabel.setText(resBundle.getString("subtitle"));
                usernameLabel.setText(resBundle.getString("usernameLabel"));
                passwordLabel.setText(resBundle.getString("passwordLabel"));
                zoneIdLabel.setText(resBundle.getString("zoneIdLabel"));
                signinButton.setText(resBundle.getString("loginButton"));
                exitButton.setText(resBundle.getString("exit"));
                zoneIdSwitchLabel.setText((ZoneId.systemDefault()).getId());
                sureConfirmation = resBundle.getString("confirmSure");
                exitConfirmation = resBundle.getString("confirmExit");
                loginDataNotValid = resBundle.getString("loginDataNotValid");
                pleaseEnterValidLoginData = resBundle.getString("pleaseEnterValidLoginData");

            }

        }
        catch (Exception e) {

            System.out.println();

        }

    }

}