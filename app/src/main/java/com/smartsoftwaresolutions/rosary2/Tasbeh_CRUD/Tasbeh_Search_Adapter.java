package com.smartsoftwaresolutions.rosary2.Tasbeh_CRUD;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.smartsoftwaresolutions.rosary2.R;

import java.util.ArrayList;
import java.util.List;

public class Tasbeh_Search_Adapter extends ArrayAdapter<Tasbeh_info>  {
    private List<Tasbeh_info> UnitList;
    private Context context;
   // private Filter planetFilter;
   // private List<Tasbeh_info> origUnitList;
   // private List<Tasbeh_info> emptyUnitList;

    public Tasbeh_Search_Adapter(List<Tasbeh_info> UnitList, Context ctx) {
        super(ctx, R.layout.tasbeh_element, UnitList);
        this.UnitList = UnitList;
        this.context = ctx;
        // this.planetListOrig = new ArrayList<Planet>(planetList);
       // this.origUnitList =new ArrayList<Tasbeh_info>(UnitList);
       // this.emptyUnitList =new ArrayList<Tasbeh_info>();
    }

    public int getCount() {
        return UnitList.size();
    }

    public Tasbeh_info getItem(int position) {
        return UnitList.get(position);
    }

    public long getItemId(int position) {
        return UnitList.get(position).hashCode();
    }
    //each time a row is inserted the listview will call the getview method
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
// we creat a view holder
       // PayInfoHolder holder = new PayInfoHolder();
        PayInfoHolder holder =null;


        // First let's verify the convertView is not null
       // if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.tasbeh_element, null);
            // Now we can fill the layout with the right values
        holder=new PayInfoHolder();
            TextView unit_Id = v.findViewById(R.id.tv_Id_Pay);
            TextView Unit_name = v.findViewById(R.id.tv_name_Pay);
            TextView tv_tasbeh_native = v.findViewById(R.id.tv_tasbeh_native);
            TextView tv_tasbeh_counter = v.findViewById(R.id.tv_tasbeh_counter);
            ImageButton remove_Tasbeh_Button= v.findViewById(R.id.btn_delete);
            ImageButton add_Tasbeh_Button = v.findViewById(R.id.btn_add);
            ImageButton Edite_Tasbeh_Button = v.findViewById(R.id.btn_edit);
            holder.Unit_IdView = unit_Id;
            holder.UnitnameView = Unit_name;
            holder.Unitname_nativeView = tv_tasbeh_native;
            holder.Unit_counter_View = tv_tasbeh_counter;
            holder.remove_Tasbeh_Button=remove_Tasbeh_Button;
            holder.add_Tasbeh_Button=add_Tasbeh_Button;
            holder.Edite_Tasbeh_Button=Edite_Tasbeh_Button;
            holder.tasbeh_info=UnitList.get(position);
//            ImageButton remove_Tasbeh_Button;
//            ImageButton add_Tasbeh_Button;
//            ImageButton Edite_Tasbeh_Button;

            holder.Unit_IdView.setTag(holder.tasbeh_info);
            holder.UnitnameView.setTag(holder.tasbeh_info);
            holder.Unitname_nativeView.setTag(holder.tasbeh_info);
            holder.Unit_counter_View.setTag(holder.tasbeh_info);

            /** tag will get the information of the selected item in the list*/
            holder.remove_Tasbeh_Button.setTag(holder.tasbeh_info);
            holder.add_Tasbeh_Button.setTag(holder.tasbeh_info);
            holder.Edite_Tasbeh_Button.setTag(holder.tasbeh_info);
           v.setTag(holder);
 //   }
//       else{
//            holder = (PayInfoHolder) v.getTag();
//        }
//        v.setTag(holder);

        // we use it to get the information of the element from customer info calss when we use the position
        Tasbeh_info p = UnitList.get(position);
        // we read from the list and put it in the text view
      //  holder.Unit_IdView.setText(p.getU_id_List());
        holder.Unit_IdView.setText(p.getU_id_List());

        holder.UnitnameView.setText(p.getU_nameList());

        holder.Unitname_nativeView.setText(p.getU_name_native_List());
        holder.Unit_counter_View.setText(p.getU_counterList());



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
        Tasbeh_info tasbeh_info;
        public TextView Unit_IdView;
        public TextView UnitnameView;
        public TextView Unitname_nativeView;
        public TextView Unit_counter_View;
        public ImageButton remove_Tasbeh_Button;
        public ImageButton add_Tasbeh_Button;
        public ImageButton Edite_Tasbeh_Button;

    }
    /*
     * We create our filter
     */

//    @Override
//    public Filter getFilter() {
//        if (planetFilter == null)
//            planetFilter = new PlanetFilter();
//
//        return planetFilter;
//    }

    //private class PlanetFilter extends Filter {



//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults results = new FilterResults();
//            // We implement here the filter logic
//            if (constraint == null || constraint.length() == 0) {
//                // No filter implemented we return all the list
//                results.values = origUnitList;
//                results.count = origUnitList.size();
//            }
//            else {
//                // We perform filtering operation we creat a new list to put the filterd items in it
//                List<Tasbeh_info> nCustomerList = new ArrayList<Tasbeh_info>();
//
//                for (Tasbeh_info p : UnitList) {
//                    //  if (p.getMobileNumberList().toUpperCase().startsWith(constraint.toString().toUpperCase()))
//
//                    if (p.getU_nameList().toUpperCase().contains(constraint.toString().toUpperCase())){
//                        nCustomerList.add(p);
//                    }else{
//                        // if the search didnt find any matches reset the list
//
//                        //emptylist();
//
//                    }
//                }
//
//                results.values = nCustomerList;
//                results.count = nCustomerList.size();
//
//            }
//            //result is the search result
//            return results;
//        }

//        @Override
//        protected void publishResults(CharSequence constraint,FilterResults results) {
//
//            // Now we have to inform the adapter about the new list filtered
//            if (results.count == 0){
//                UnitList = (List<Tasbeh_info>) results.values;
//                notifyDataSetInvalidated();}
//            else {
//                UnitList = (List<Tasbeh_info>) results.values;
//                notifyDataSetChanged();
//            }
//
//        }

   // }

    // this is a method so we can call it from another class
//    public void resetData() {
//        UnitList = origUnitList;
//        notifyDataSetChanged();
//    }
}
