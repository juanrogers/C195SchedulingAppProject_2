package DBAccessObj;

import Model.Country;
import Utility.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


/** This class handles the logic for countries within the database.
 *
 * @author Ajuane Rogers*/
public class DBAccessCountries {

    /**
     * This method returns all countries in the database.
     *
     * @return countries in the database
     */
    public static ObservableList<Country> getAllCountries() {

        ObservableList<Country> listOfCountries = FXCollections.observableArrayList();

        try {

            String sqlCountrylist = "SELECT * FROM countries";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlCountrylist);

            ResultSet resultSet = preState.executeQuery();

            while (resultSet.next()) {

                int countryId = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                Country contr = new Country(countryId, countryName);
                listOfCountries.add(contr);

            }

        }

        catch (SQLException e) {

            e.printStackTrace();

        }

        return listOfCountries;
    }

}


