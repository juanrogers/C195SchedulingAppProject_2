package DBAccessObj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utility.DBConnect;



/** This class handles the logic between the database and the users.
 *
 * @author Ajuane Rogers */
public class DBAccessUsers {

    /**
     * This method will return all users listed in the database.
     *
     * @return users listed in the database
     */
    public static ObservableList<User> getAllUsers() {

        ObservableList<User> listOfUsers = FXCollections.observableArrayList();

        try {

            String sqlFromDB = "SELECT* FROM users";

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlFromDB);

            ResultSet resultSet = preState.executeQuery();

            while (resultSet.next()) {

                int userId = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                User user = new User(userId, userName, password);
                listOfUsers.add(user);
            }

        }
        catch (SQLException e) {

            e.printStackTrace();

        }

        return listOfUsers;
    }



    /**
     * This method will validate the login credentials provided by the user.
     *
     * @param username username of the user
     * @param password password of the user
     * @return If the data entered is valid: true, if data entered is invalid: false.
     */
    public static int validationOfUser(String username, String password) {

        String sqlValidate = "SELECT* FROM users WHERE user_name = '" + username + "' AND password = '" + password +"'";

        try {

            PreparedStatement preState = DBConnect.connection().prepareStatement(sqlValidate);
            ResultSet resultSet = preState.executeQuery();

            {
                resultSet.next();
                if (resultSet.getString("User_Name").equals(username)) {

                    if (resultSet.getString("Password").equals(password)) {

                        return resultSet.getInt("User_ID");

                    }

                }

            }

        }

        catch (SQLException e) {

            e.printStackTrace();
        }

        return -1;

    }

}
