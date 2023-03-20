package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Model.Country;
import Model.Media_Member;
import Model.Division;

import DBAccessObj.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



/** This controller will be used as the logic for the update member screen.
 *
 * @author Ajuane Rogers */
public class updatememberscreencontroller implements Initializable {

    /**
     * FX IDs for add member screen
     *
     */
    @FXML
    private Label updateMemberLabel;
    @FXML
    private Label memberIdLabel;
    @FXML
    private Label memberNameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label divWSwitchableLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private TextField memberIdTxtFld;
    @FXML
    private TextField memberNameTxtFld;
    @FXML
    private TextField addressTxtFld;
    @FXML
    private ComboBox<Country> countryDropDownBox;
    @FXML
    private ComboBox<Division> divisionDropDownBox;
    @FXML
    private TextField postalCodeTxtFld;
    @FXML
    private TextField phoneTxtFld;
    @FXML
    private Button saveUpdateMemberButton;
    @FXML
    private Button cancelUpdateMemberButton;

    //@FXML
    //private Label divisionLabel;



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
    void onActionMembIdTxtFld(){

    };

    @FXML
    void onActionMembNameTxtFld(){

    };

    @FXML
    void onActionAddrTxtFld(){

    };

//    @FXML
//    void onActionCoutDropDownBox(){
//
//    };

    @FXML
    void onActionDivDropDownBox(){

    };

    @FXML
    void onActionPostCodeTxtFld (){

    };

    @FXML
    void onActionPhoneTxtFld(){

    };

    Media_Member member;



    /**
     * This method will choose the division label and populate the divisions dropdown box option, based on the option that was selected in the country dropdown box.
     *
     * @param event selection in country dropdown box
     */
    @FXML
    void onActionCoutDropDownBox(ActionEvent event) {

        Country count = countryDropDownBox.getSelectionModel().getSelectedItem();


        if (count.getCountry_Id() == 3) {

            divWSwitchableLabel.setText("Province: ");

        }

        else if (count.getCountry_Id() == 2) {

            divWSwitchableLabel.setText("Sub-division: ");

        }

        else if (count.getCountry_Id() == 1) {

            divWSwitchableLabel.setText("State: ");

        }

        if (count.getCountry_Id() == 3) {

            divisionDropDownBox.setItems(DBAccessDivisions.getCANDivisions());

        }

        else if (count.getCountry_Id() == 2) {

            divisionDropDownBox.setItems(DBAccessDivisions.getUKDivisions());

        }

        else if (count.getCountry_Id() == 1) {

            divisionDropDownBox.setItems(DBAccessDivisions.getUSDivisions());

        }

        else {

            divisionDropDownBox.isDisabled();

        }

    }



    /**
     * This method will send the member selected in table to update member screen.
     *
     * @param member member to send
     */
    public void memberToBeSentToUpdate(Media_Member member) {

        this.member = member;

        memberIdTxtFld.setText(Integer.toString(member.getMember_Id()));
        memberNameTxtFld.setText(member.getMemberName());
        addressTxtFld.setText(member.getAddress());
        Country country = null;
        for (Country cout : countryDropDownBox.getItems()) {

            if(member.country_Id == cout.getCountry_Id()) {
                country = cout;
                countryDropDownBox.setValue(cout);

                break;
            }

        }

        if (country.getCountry_Id() == 3) {

            divWSwitchableLabel.setText("Province: ");
            divisionDropDownBox.setItems(DBAccessDivisions.getCANDivisions());

        }
        else if (country.getCountry_Id() == 2) {

            divWSwitchableLabel.setText("Sub-division:");
            divisionDropDownBox.setItems(DBAccessDivisions.getUKDivisions());

        }
        else if (country.getCountry_Id() == 1) {

            divWSwitchableLabel.setText("State: ");
            divisionDropDownBox.setItems(DBAccessDivisions.getUSDivisions());

        }
        else {
            divisionDropDownBox.isDisabled();
        }

        for(Division div : divisionDropDownBox.getItems()) {
            if(member.division_Id == div.getDivision_Id()) {

                divisionDropDownBox.setValue(div);

                break;
            }

        }

        postalCodeTxtFld.setText(member.getPostalCode());
        phoneTxtFld.setText(member.getPhone());

    }



    /** This method will update the member in database, and after member is updated, will take user back to the media_members screen.
     *
     * @param event clicking the save button
     * @throws IOException
     */
    @FXML
    void onActionSaveUpdateMember(ActionEvent event) throws IOException {

        Alert alertUserMsg = new Alert(Alert.AlertType.CONFIRMATION);
        alertUserMsg.setHeaderText("ARE YOU SURE?");
        alertUserMsg.setContentText("The member will be updated, do you want to continue?");

        Optional<ButtonType> result = alertUserMsg.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            String memberName = memberNameTxtFld.getText();
            String address = addressTxtFld.getText();
            String postalCode = postalCodeTxtFld.getText();
            String phone = phoneTxtFld.getText();
            Division division = divisionDropDownBox.getValue();

            if (!memberName.isEmpty() && !address.isEmpty() && !postalCode.isEmpty() && !phone.isEmpty() && !(division == null)) {

                DBAccessMedia_Members.updateMedia_Member(memberName, address, postalCode, phone, division.getDivision_Id(), member.getMember_Id());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../View/media_membersscreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }

            else {

                Alert alertUserMsg2 = new Alert(Alert.AlertType.ERROR);
                alertUserMsg2.setHeaderText("Data entered is invalid!");
                alertUserMsg2.setContentText("Please enter valid values for all required fields.");
                alertUserMsg2.showAndWait();

            }

        }

    }



    /** This method will cancel the "update member" action, and send user back to the media_members screen.
     *
     * @param event clicking cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancelUpdateMember(ActionEvent event) throws IOException {

        Alert alertUserMsg3 = new Alert(Alert.AlertType.CONFIRMATION);
        alertUserMsg3.setHeaderText("ARE YOU SURE?");
        alertUserMsg3.setContentText("This action will close the update member screen, do you want to continue?");

        Optional<ButtonType> result = alertUserMsg3.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../View/media_membersscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }



    /**
     * This method initializes the update member screen and populate country dropdown box options.
     *
     * @param url the location
     * @param resourceBundle the resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryDropDownBox.setItems(DBAccessCountries.getAllCountries());

    }

}