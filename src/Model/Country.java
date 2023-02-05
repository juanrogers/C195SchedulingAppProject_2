package Model;


/** This class will be used to handle countries.
 *
 * @author Ajuane Rogers*/

public class Country {

    private int country_Id;
    private String countryName;

    /** This is the constructor used for building a country.
     *
     * @param country_Id This holds the id of the country.
     * @param countryName This holds the name of the country.
     */
    public Country(int country_Id, String countryName) {

        this.country_Id = country_Id;
        this.countryName = countryName;

    }



    /**
     * Getters listed below
     */



    /**
     * @return will return the country_Id
     */
    public int getCountry_Id() {

        return country_Id;

    }

    /**
     * @return will return the countryName
     */
    public String getCountryName() {

        return countryName;

    }

    /**
     * @return This will return a country name for use within a dropdown box.
     */
    @Override
    public String toString() {

        return (countryName);

    }

}

