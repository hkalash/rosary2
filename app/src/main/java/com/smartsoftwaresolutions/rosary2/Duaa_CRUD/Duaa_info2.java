package com.smartsoftwaresolutions.rosary2.Duaa_CRUD;

public class Duaa_info2 {
    private String U_id_Listd,U_nameListd,U_name_native_Listd ;

    // the unit that represent the element in the list ====>the type of the list it is a constractor
    public Duaa_info2(String U_id_Listd, String U_nameListd,String U_name_native_Listd){
        this.U_id_Listd=U_id_Listd;
        this.U_nameListd=U_nameListd;
        this.U_name_native_Listd=U_name_native_Listd;

    }
// in get u will read a name and after u read it u will return it. it is the name that u define first

    public String getU_id_Listd() {
        return U_id_Listd;
    }

    // in set u will write the name so u want to save the writen in the name
    public void setU_id_Listd(String U_id_Listd) {
        this.U_id_Listd = U_id_Listd;

    }
    public String getU_name_native_Listd() {
        return U_name_native_Listd;
    }

    // in set u will write the name so u want to save the writen in the name
    public void setU_name_native_Listd(String U_name_native_Listd) {
        this.U_name_native_Listd = U_name_native_Listd;

    }


    // in get u will read a name and after u read it u will return it. it is the name that u define first

    public String getU_nameListd() {
        return U_nameListd;
    }

    // in set u will write the name so u want to save the written in the name
    public void setU_nameListd(String U_nameListd) {
        this.U_nameListd = U_nameListd;

    }
}
