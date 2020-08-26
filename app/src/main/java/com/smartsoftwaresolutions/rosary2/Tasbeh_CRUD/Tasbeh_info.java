package com.smartsoftwaresolutions.rosary2.Tasbeh_CRUD;

public class Tasbeh_info {
    private String U_id_List,U_nameList,U_name_native_List,U_counterList ;

    // the unit that represent the element in the list ====>the type of the list it is a constractor
    public Tasbeh_info(String U_id_List, String U_nameList,String U_name_native_List,String U_counterList){
        this.U_id_List=U_id_List;
        this.U_nameList=U_nameList;
        this.U_name_native_List=U_name_native_List;
        this.U_counterList=U_counterList;
    }
// in get u will read a name and after u read it u will return it. it is the name that u define first

    public String getU_id_List() {
        return U_id_List;
    }

    // in set u will write the name so u want to save the writen in the name
    public void setU_id_List(String U_id_List) {
        this.U_id_List = U_id_List;

    }  public String getU_name_native_List() {
        return U_name_native_List;
    }

    // in set u will write the name so u want to save the writen in the name
    public void setU_name_native_List(String U_name_native_List) {
        this.U_name_native_List = U_name_native_List;

    }  public String  getU_counterList() {
        return U_counterList;
    }

    // in set u will write the name so u want to save the writen in the name
    public void setU_counterList(String U_counterList) {
        this.U_counterList = U_counterList;

    }

    // in get u will read a name and after u read it u will return it. it is the name that u define first

    public String getU_nameList() {
        return U_nameList;
    }

    // in set u will write the name so u want to save the written in the name
    public void setU_nameList(String U_nameList) {
        this.U_nameList = U_nameList;

    }
}
