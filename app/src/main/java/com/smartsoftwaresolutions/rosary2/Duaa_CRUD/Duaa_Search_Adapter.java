package com.smartsoftwaresolutions.rosary2.Duaa_CRUD;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Tasbeh_CRUD.Tasbeh_Search_Adapter;
import com.smartsoftwaresolutions.rosary2.Tasbeh_CRUD.Tasbeh_info;

import java.util.List;

public class Duaa_Search_Adapter extends ArrayAdapter<Duaa_info2> {
    private List<Duaa_info2> UnitList;
    private Context context;
    // private Filter planetFilter;
    // private List<Duaa_info> origUnitList;
    // private List<Duaa_info> emptyUnitList;

    public Duaa_Search_Adapter(List<Duaa_info2> UnitList, Context ctx) {
        super(ctx, R.layout.duaa_element2, UnitList);
        this.UnitList = UnitList;
        this.context = ctx;

    }

    public int getCount() {
        return UnitList.size();
    }

    public Duaa_info2 getItem(int position) {
        return UnitList.get(position);
    }

    public long getItemId(int position) {
        return UnitList.get(position).hashCode();
    }
    //each time a row is inserted the listview will call the getview method
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
// we creat a view holder
        DuaaInfoHolder holder =null;


        // First let's verify the convertView is not null
      //  if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.duaa_element2, null);
            holder=new DuaaInfoHolder();
            // Now we can fill the layout with the right values
            TextView unit_Id = v.findViewById(R.id.tv_Id_Payd);
            TextView Unit_name = v.findViewById(R.id.tv_name_Payd);
            TextView tv_Duaa_native = v.findViewById(R.id.tv_tasbeh_natived);

            ImageButton remove_Duaa_Button= v.findViewById(R.id.btn_deletedd);
            ImageButton add_Duaa_Button = v.findViewById(R.id.btn_addd);
            ImageButton Edite_Duaa_Button = v.findViewById(R.id.btn_editd);
            holder.Unit_IdView = unit_Id;
            holder.UnitnameView = Unit_name;
            holder.Unitname_nativeView = tv_Duaa_native;

            holder.remove_Duaa_Button=remove_Duaa_Button;
            holder.add_Duaa_Button=add_Duaa_Button;
            holder.Edite_Duaa_Button=Edite_Duaa_Button;
            holder.Duaa_info2=UnitList.get(position);
//            ImageButton remove_Duaa_Button;
//            ImageButton add_Duaa_Button;
//            ImageButton Edite_Duaa_Button;

            holder.Unit_IdView.setTag(holder.Duaa_info2);
            holder.UnitnameView.setTag(holder.Duaa_info2);
            holder.Unitname_nativeView.setTag(holder.Duaa_info2);


            /** tag will get the information of the selected item in the list*/
            holder.remove_Duaa_Button.setTag(holder.Duaa_info2);
            holder.add_Duaa_Button.setTag(holder.Duaa_info2);
            holder.Edite_Duaa_Button.setTag(holder.Duaa_info2);
            v.setTag(holder);
   //     }
//        else
//            {
//            holder = (Duaa_Search_Adapter.PayInfoHolder) v.getTag();
//        }
        //   v.setTag(holder);

        // we use it to get the information of the element from customer info calss
        Duaa_info2 p = UnitList.get(position);
        // we read from the list and put it in the text view
        holder.Unit_IdView.setText(p.getU_id_Listd());

        holder.UnitnameView.setText(p.getU_nameListd());

        holder.Unitname_nativeView.setText(p.getU_name_native_Listd());




//*****************************Color listview********************
//        if (position % 2 == 0) {
//            v.setBackgroundColor(Color.parseColor("#ffffff"));
//        } else {
//            v.setBackgroundColor(Color.parseColor("#BCF7F0"));
//        }
        //****************************************************************

        return v;
    }

    /* *********************************
     * We use the holder pattern
     * It makes the view faster and avoid finding the component
     * **********************************/
    private static class DuaaInfoHolder {
        Duaa_info2 Duaa_info2;
        public TextView Unit_IdView;
        public TextView UnitnameView;
        public TextView Unitname_nativeView;
        public ImageButton remove_Duaa_Button;
        public ImageButton add_Duaa_Button;
        public ImageButton Edite_Duaa_Button;

    }
    /*
     * We create our filter
     */


}
