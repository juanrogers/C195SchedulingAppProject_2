package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.Media_Member;

import DBAccessObj.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



/** This controller will be used as the logic for the media_members screen.
 *
 * @author Ajuane Rogers*/
public class media_membersscreencontroller implements Initializable {

    /**
     * FX IDs for main menu screen.
     */
    @FXML
    private Label mediaMembersLabel;
    @FXML
    private Button addMemberButton;
    @FXML
    private Button updateMemberButton;
    @FXML
    private Button deleteMemberButton;
    @FXML
    private Button backToMainMenuButton;
    @FXML
    private TableView<Media_Member> mediaMembersTable;
    @FXML
    private TableColumn<Media_Member, Integer> memberIdCol;
    @FXML
    private TableColumn<Media_Member, String> memberNameCol;
    @FXML
    private TableColumn<Media_Member, String> addressCol;
    @FXML
    private TableColumn<Media_Member, Integer> divisionCol;
    @FXML
    private TableColumn<Media_Member, String> postalCodeCol;
    @FXML
    private TableColumn<Media_Member, String> phoneCol;



    /**
     * Variables for stages and scenes.
     */
    Stage stage;
    Parent scene;



    /**
     * This event will switch to the add member screen.
     *
     * @param event clicking on the add member button.
     * @throws IOException IOException
     */
    @FXML
    void onActionGoToAddMember(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/addmemberscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * This event will switch back to the main menu screen.
     *
     * @param event clicking the main menu button.
     * @throws IOException IOException
     */
    @FXML
    void onActionGoToMainMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/mainscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }



    /**
     * This method will delete a member from the database.
     *
     * @param event clicking the delete member button.
     * @throws IOException IOException
     */
    @FXML
    void onActionDeleteMember(ActionEvent event) throws IOException {

        if (mediaMembersTable.getSelectionModel().isEmpty()) {

            Alert alertUserMsg = new Alert(Alert.AlertType.ERROR);
            alertUserMsg.setHeaderText("PLEASE SELECT A MEMBER TO DELETE.");
            alertUserMsg.setContentText("No member was selected to delete.");

            Optional<ButtonType> result = alertUserMsg.showAndWait();

        }

        else {

            Alert alertUserMsg2 = new Alert(Alert.AlertType.CONFIRMATION);
            alertUserMsg2.setHeaderText("ARE YOU SURE?");
            alertUserMsg2.setContentText("The member selected will be deleted, do you want to complete this action? This action CANNOT be undone.");

            Optional<ButtonType> result = alertUserMsg2.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                int memberId = mediaMembersTable.getSelectionModel().getSelectedItem().getMember_Id();

                DBAccessMedia_Members.deleteMedia_Member(memberId);

                mediaMembersTable.setItems(DBAccessMedia_Members.getAllMedia_Members());


                Alert alertUserMsg3 = new Alert(Alert.AlertType.INFORMATION);
                alertUserMsg3.setHeaderText("DELETED!");
                alertUserMsg3.setContentText("Member deleted.");

                alertUserMsg3.showAndWait();

            }

            else {

                Alert alertUserMsg4 = new Alert(Alert.AlertType.INFORMATION);
                alertUserMsg4.setHeaderText("NOT DELETED!");
                alertUserMsg4.setContentText("Member not deleted.");

                alertUserMsg4.showAndWait();

            }

        }

    }



    /**
     * This event will switch to the update member screen.
     *
     * @param event clicking on the update member button.
     * @throws IOException IOException
     */
    @FXML
    void onActionGoToUpdateMember(ActionEvent event) throws IOException {

        if (mediaMembersTable.getSelectionModel().isEmpty()) {

            Alert alertUserMsg5 = new Alert(Alert.AlertType.ERROR);
            alertUserMsg5.setHeaderText("PLEASE SELECT A MEMBER.");
            alertUserMsg5.setContentText("No member was selected to update.");

            Optional<ButtonType> result = alertUserMsg5.showAndWait();

        }

        else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/updatememberscreen.fxml"));
            loader.load();

            updatememberscreencontroller ADMController = loader.getController();
            ADMController.memberToBeSentToUpdate(mediaMembersTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }



    /**
     * This method initializes the members screen, populated with all members.
     * @param url the location.
     * @param resourceBundle the resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        memberIdCol.setCellValueFactory(new PropertyValueFactory<>("member_Id"));
        memberNameCol.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division_Id"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        mediaMembersTable.setItems(DBAccessMedia_Members.getAllMedia_Members());

    }

}




