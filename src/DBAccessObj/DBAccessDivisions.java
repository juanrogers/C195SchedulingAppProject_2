package DBAccessObj;

import Model.Division;
import Utility.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;



/** This class handles the logic for divisions within the database.
 *
 * @author Ajuane Rogers*/
public class DBAccessDivisions {

    /**
     * This method returns all divisions located in the US.
     *
     * @return divisions that have a country Id as "1"
     */
    public static ObservableList<Division> getUSDivisions() {

        ObservableList<Division> listOfUsDivisions = FXCollections.observableArrayList();

        try {

            String sqlUSDivisions = "SELECT* FROM first_level_divisions WHERE COUNTRY_ID = 1";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlUSDivisions);

            ResultSet resultSet = preState.executeQuery();

            while (resultSet.next()) {

                int divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryId = resultSet.getInt("COUNTRY_ID");
                Division div = new Division(divisionId, divisionName, countryId);
                listOfUsDivisions.add(div);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return listOfUsDivisions;

    }



    /**
     * This method returns all divisions located in the UK.
     *
     * @return divisions that have a country Id as "2"
     */
    public static ObservableList<Division> getUKDivisions() {

        ObservableList<Division> listOfUKDivisions = FXCollections.observableArrayList();

        try {

            String sqlUKDivisions = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 2";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlUKDivisions);

            ResultSet resultSet = preState.executeQuery();

            while (resultSet.next()) {

                int divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryId = resultSet.getInt("COUNTRY_ID");
                Division div = new Division(divisionId, divisionName, countryId);
                listOfUKDivisions.add(div);

            }

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

        return listOfUKDivisions;

    }



    /**
     * This method returns all divisions located in the Canada.
     *
     * @return divisions that have a country Id as "3"
     */
    public static ObservableList<Division> getCANDivisions() {

        ObservableList<Division> listOfCANDivisions = FXCollections.observableArrayList();

        try {

            String sqlCANDivisions = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 3";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlCANDivisions);

            ResultSet resultSet = preState.executeQuery();

            while (resultSet.next()) {

                int divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryId = resultSet.getInt("COUNTRY_ID");
                Division div = new Division(divisionId, divisionName, countryId);
                listOfCANDivisions.add(div);

            }

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

        return listOfCANDivisions;

    }

}


