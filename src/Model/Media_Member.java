package Model;


/** This class will be used to handle media_members.
 *
 * @author Ajuane Rogers*/

public class Media_Member {

    public int member_Id;
    public String memberName;
    public String address;
    public String postalCode;
    public String phone;
    public int country_Id;
    public int division_Id;
    //public String divisionName;

    /** This is the constructor used for building a media_member.
     *
     * @param member_Id The id of the media_member.
     * @param memberName The name of the media_member.
     * @param address The address of the media_member.
     * @param postalCode The postal code of the media_member.
     * @param phone The phone number of the media_member.
     * @param country_Id The country id for media_member.
     * @param division_Id The division id for the media_member.
    // @param divisionName The division name for the media_member.
     */
    public Media_Member (int member_Id, String memberName, String address, String postalCode, String phone, int country_Id, int division_Id) {

        this.member_Id = member_Id;
        this.memberName = memberName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country_Id = country_Id;
        this.division_Id = division_Id;
        //this.divisionName = divisionName;

    }


    /** Gets member ID from a member name, where the name given is from a String object that contains the ID.
     * @return will return a member Id parsed from a member name
     **/
    public static int getMembIdByMembName(String memberName) {

        return Integer.parseInt(memberName.substring(0, memberName.indexOf(":")));
    }




    /**
     * Getters listed below
     */




    /**
     * @return will return the member_Id
     */
    public int getMember_Id() {

        return member_Id;

    }

    /**
     * @return will return the memberName
     */
    public String getMemberName() {

        return memberName;

    }

    /**
     * @return will return the address
     */
    public String getAddress() {

        return address;

    }

    /**
     * @return will return the division_Id
     */
    public int getDivision_Id() {

        return division_Id;

    }

    /**
     * @return will return the postalCode
     */
    public String getPostalCode() {

        return postalCode;

    }

    /**
     * @return will return the phone
     */
    public String getPhone() {

        return phone;

    }

    /**
     * @return will returnr the divisionName
     */
    //public String getDivisionName() {

    //return divisionName;

    //}

}