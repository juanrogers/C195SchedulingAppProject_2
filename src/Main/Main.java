package Main;

import Utility.DBConnect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Random;


/**
 * @author Ajuane Rogers-----
 *
 * This Class contains the main method for the press World Press Tour Scheduler Application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/loginscreen.fxml"));
        primaryStage.setTitle("Keeping Up with KBC: World Press Tour Scheduler");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }



    /**
     * This is the main method for the World Press Tour Scheduler Application.
     * This method gets call when the application starts.
     */
    public static void main(String[] args) {

        DBConnect.openConnection();

        launch(args);

        DBConnect.closeConnection();


    }

}