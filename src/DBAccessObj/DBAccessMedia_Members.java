package DBAccessObj;

import Utility.DBConnect;
import Model.Media_Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;



/** This class handles the logic for media_members within the database.
 *
 * @author Ajuane Rogers*/
public class DBAccessMedia_Members {


    /**
     * This method deletes a media_member from the database.
     *
     * @param memberId id of media_member.
     */
    public static void deleteMedia_Member(int memberId) {

        try {

            String sqldeleteAppt = "DELETE FROM appointments WHERE Member_ID = ?";

            PreparedStatement preStateDelAppt = DBConnect.connection().prepareStatement(sqldeleteAppt);

            preStateDelAppt.setInt(1, memberId);

            preStateDelAppt.execute();


            String sqldeleteMemb = "DELETE FROM customers WHERE Member_ID = ?";

            PreparedStatement preStateDelMemb = DBConnect.connection().prepareStatement(sqldeleteMemb);

            preStateDelMemb.setInt(1, memberId);

            preStateDelMemb.execute();

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

    }


    /**
     * This method will add a media_member to the database.
     *
     * @param memberName name of media_member
     * @param address address of media_member
     * @param postalCode postal code of media_member
     * @param phone phone number of media_member
     * @param divisionId division Id of media_member
     */
    public static void addMedia_Member(String memberName, String address, String postalCode, String phone, int divisionId) {

        try {

            String sqladdMember = "INSERT INTO media_members VALUES (NULL, ?, ?, ?, ?, NOW(), 'RZ', NOW(), 'RZ', ?)";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqladdMember);

            preState.setString(1, memberName);
            preState.setString(2, address);
            preState.setString(3, postalCode);
            preState.setString(4, phone  );
            preState.setInt(5, divisionId);

            preState.execute();

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

    }



    /**
     * This method returns all media_members in the database.
     *
     * @return all media_members in the database
     */
    public static ObservableList<Media_Member> getAllMedia_Members() {

        ObservableList<Media_Member> listOfMedia_Members = FXCollections.observableArrayList();

        try {

            String sqlGetMembs = "SELECT Member_ID, Member_Name, Address, Postal_Code, Phone, media_members.Division_ID, " +
                    "first_level_divisions.COUNTRY_ID, first_level_divisions.Division FROM media_members, first_level_divisions WHERE media_members.Division_ID = first_level_divisions.Division_ID ORDER BY Member_ID";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlGetMembs);
            ResultSet resultSet = preState.executeQuery();

            while (resultSet.next()) {

                int member_Id = resultSet.getInt("Member_ID");
                String memberName = resultSet.getString("Member_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                int division_Id = resultSet.getInt("Division_ID");
                int country_Id = resultSet.getInt("COUNTRY_ID");
                String divisionName = resultSet.getString("Division");

                Media_Member membs = new Media_Member(member_Id, memberName, address, postalCode, phone, country_Id, division_Id);
                listOfMedia_Members.add(membs);

            }

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

        return listOfMedia_Members;

    }



    /** Gets member ID from a member name, where the name given is from a String object that contains the ID.
     *
     **/
    public static int getMembIdByName(String memberName) {

        int member_Id = Integer.parseInt(memberName.substring(0, memberName.indexOf(":")));

        return member_Id;

    }



    /**
     * This method updates a media_member in the database.
     *
     * @param memberName name of media_member
     * @param address address of media_member
     * @param postalCode postal code of media_member
     * @param phone phone number of media_member
     * @param divisionId division Id of the media_member
     * @param memberId Id of media_member
     */
    public static void updateMedia_Member(String memberName, String address, String postalCode, String phone, int divisionId, int memberId) {

        try {

            String sqlupdateMemb = "UPDATE media_members SET Member_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Member_ID = ?";


            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlupdateMemb);

            preState.setString(1, memberName);
            preState.setString(2, address);
            preState.setString(3, postalCode);
            preState.setString(4, phone);
            preState.setInt(5, divisionId);
            preState.setInt(6, memberId);

            preState.execute();

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

    }

}


