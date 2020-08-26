package com.smartsoftwaresolutions.rosary2.Duaa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Select_Tasbeh_List.Select_Tasbeh_Adapter;
import com.smartsoftwaresolutions.rosary2.Tasbeh_CRUD.Tasbeh_info;

import java.util.List;

public class Duaa_Adapter extends ArrayAdapter<Duaa_info> {
    private List<Duaa_info> UnitList;
    private Context context;
// private Filter planetFilter;
// private List<Tasbeh_info> origUnitList;
// private List<Tasbeh_info> emptyUnitList;

    public Duaa_Adapter(List<Duaa_info> UnitList, Context ctx) {
        super(ctx, R.layout.duaa_element, UnitList);
        this.UnitList = UnitList;
        this.context = ctx;
        // this.planetListOrig = new ArrayList<Planet>(planetList);
        // this.origUnitList =new ArrayList<Tasbeh_info>(UnitList);
        // this.emptyUnitList =new ArrayList<Tasbeh_info>();
    }

    public int getCount() {
        return UnitList.size();
    }

    public Duaa_info getItem(int position) {
        return UnitList.get(position);
    }

    public long getItemId(int position) {
        return UnitList.get(position).hashCode();
    }
    //each time a row is inserted the listview will call the getview method
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
// we creat a view holder
        Duaa_Adapter.PayInfoHolder holder = new Duaa_Adapter.PayInfoHolder();


        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.duaa_element, null);
            // Now we can fill the layout with the right values
            TextView unit_Id = v.findViewById(R.id.tv_Id_PayD);
            TextView Unit_name = v.findViewById(R.id.tv_name_PayD);
            TextView tv_duaa_dis = v.findViewById(R.id.tv_duaa_dis);

//       ImageButton remove_Tasbeh_Button= (ImageButton) v.findViewById(R.id.btn_delete);
//        ImageButton add_Tasbeh_Button = (ImageButton)v.findViewById(R.id.btn_add);
//       ImageButton Edite_Tasbeh_Button = (ImageButton)v.findViewById(R.id.btn_edit);
            holder.Unit_IdView = unit_Id;
            holder.UnitnameView = Unit_name;
            holder.Unitname_disView = tv_duaa_dis;
            //holder.Unit_counter_View = tv_tasbeh_counter;
//      holder.remove_Duaa_Button=remove_Tasbeh_Button;
//        holder.add_Duaa_Button=add_Tasbeh_Button;
//        holder.Edite_Duaa_Button=Edite_Tasbeh_Button;
            holder.duaa_info=UnitList.get(position);
//            ImageButton remove_Duaa_Button;
//            ImageButton add_Duaa_Button;
//            ImageButton Edite_Duaa_Button;

            holder.Unit_IdView.setTag(holder.duaa_info);
            holder.UnitnameView.setTag(holder.duaa_info);
            holder.Unitname_disView.setTag(holder.duaa_info);
           // holder.Unit_counter_View.setTag(holder.tasbeh_info);

            /** tag will get the information of the selected item in the list*/
//        holder.remove_Duaa_Button.setTag(holder.duaa_info);
//       holder.add_Duaa_Button.setTag(holder.duaa_info);
//        holder.Edite_Duaa_Button.setTag(holder.duaa_info);
            v.setTag(holder);
        }else{
            holder = (Duaa_Adapter.PayInfoHolder) v.getTag();
        }
        //   v.setTag(holder);

        // we use it to get the information of the element from customer info calss
        Duaa_info p = UnitList.get(position);
        // we read from the list and put it in the text view
        holder.Unit_IdView.setText(p.getU_id_List());

        holder.UnitnameView.setText(p.getU_nameList());

        holder.Unitname_disView.setText(p.getU_Diss_List());
       // holder.Unit_counter_View.setText(p.getU_counterList());



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
    private static class PayInfoHolder {
        Duaa_info duaa_info;
        public TextView Unit_IdView;
        public TextView UnitnameView;
        public TextView Unitname_disView;
      //  public TextView Unit_counter_View;
//    public ImageButton remove_Duaa_Button;
//    public ImageButton add_Duaa_Button;
//    public ImageButton Edite_Duaa_Button;

    }
}
