package Model;


/** This class will be used to handle divisions.
 *
 * @author Ajuane Rogers */

public class Division {

    private int division_Id;
    private String divisionName;
    private int country_Id;


    /** This is the constructor used for building a division.
     *
     * @param division_Id This holds the id of the division.
     * @param divisionName This holds the name of the division.
     * @param country_Id This holds the id for the country.
     */
    public Division (int division_Id, String divisionName, int country_Id) {

        this.division_Id = division_Id;
        this.divisionName = divisionName;
        this.country_Id = country_Id;

    }





    /**
     * Getters listed below
     */






    /**
     * @return Getter for the division_Id
     */
    public int getDivision_Id() {

        return division_Id;

    }

    /**
     * @return Getter for the divisionName
     */
    public String getName() {

        return divisionName;

    }

    /**
     * @return Getter for the country_Id
     */
    public int getCountry_Id() {

        return country_Id;

    }


    /**
     * @param division_Id Setter for the divisionId
     */
    public void setDivision_Id(int division_Id) {

        this.division_Id = division_Id;

    }

    /**
     * @param divisionName Setter for the divisionName
     */
    public void setDivisionName(String divisionName) {

        this.divisionName = divisionName;

    }

    /**
     * @param country_Id Setter for the country_Id
     */
    public void setCountry_Id(int country_Id) {

        this.country_Id = country_Id;

    }


    /**
     * @return This will return a division name for use within a combobox
     */
    @Override
    public String toString() {

        return (divisionName);

    }

}