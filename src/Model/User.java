package Model;


/** This class will be used to handle users.
 *
 * @author Ajuane Rogers */

public class User {

    public int user_Id;
    public String userName;
    public String password;

    /** This is the constructor used for building a user.
     *
     * @param user_Id This holds the id of the user.
     * @param userName This holds the name of the user.
     * @param password This holds the password of the user.
     */
    public User (int user_Id, String userName, String password) {

        this.user_Id = user_Id;
        this.userName = userName;
        this.password = password;

    }



    /**
     * Getters listed below
     */



    /**
     * @return will return the user_Id
     */
    public int getUser_Id() {

        return user_Id;

    }

    /**
     * @return will return the userName
     */
    public String getUserName() {

        return userName;

    }

    /**
     * @return will return the password
     */
    public String getPassword() {

        return password;

    }



    /**
     * Setters listed below
     */



    /**
     * @param user_Id Setter for the user_Id
     */
    public void setUser_Id(int user_Id) {

        this.user_Id = user_Id;

    }

    /**
     * @param userName Setter for the userName
     */
    public void setUserName(String userName) {

        this.userName = userName;

    }

    /**
     * @param password Setter for the password
     */
    public void setPassword(String password) {

        this.password = password;

    }



    /**
     * @return This will return a user_Id & userName for use within a dropdown box.
     */
    @Override
    public String toString() {

        return "[" + user_Id + "] " + userName;

    }

}
