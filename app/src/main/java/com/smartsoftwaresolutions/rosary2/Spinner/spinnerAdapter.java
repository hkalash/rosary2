package com.smartsoftwaresolutions.rosary2.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartsoftwaresolutions.rosary2.R;

import java.util.List;

public class spinnerAdapter extends BaseAdapter {
    private List<spinner_info> SpinnerList;
    private Context context;
    LayoutInflater inflter;

    public spinnerAdapter(List<spinner_info> spinnerList, Context ctx) {
        //super(ctx, R.layout.user_type_spinner, spinnerList);
        this.SpinnerList = spinnerList;
        this.context = ctx;
        inflter = (LayoutInflater.from(ctx));


    }
    public int getCount() {
        return SpinnerList.size();
    }

    public spinner_info getItem(int position) {
        return SpinnerList.get(position);
    }

    public long getItemId(int position) {
        return SpinnerList.get(position).hashCode();
    }
    //each time a row is inserted the listview will call the getview method
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
// we creat a view holder
        spinnerAdapter.PayInfoHolder holder = new spinnerAdapter.PayInfoHolder();

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.spinner_layout, null);
            // Now we can fill the layout with the right values
            TextView Id = v.findViewById(R.id.sp_tasbeh_id);
            TextView name = v.findViewById(R.id.sp_taspeh_name);

            holder.country_id = Id;

            holder.country_name = name;

            v.setTag(holder);
        }
        else
            holder = (spinnerAdapter.PayInfoHolder) v.getTag();
        // we use it to get the information of the element from customer info calss
        spinner_info p =SpinnerList.get(position);
        // we read from the list and put it in the text view
        holder.country_id.setText(p.getZeker_Id());
        holder.country_name.setText(p.getZeker_Name_Arabic());



        return v;
    }

    /* *********************************
     * We use the holder pattern
     * It makes the view faster and avoid finding the component
     * **********************************/
    private static class PayInfoHolder {
        public TextView country_id;
        public TextView country_name;
        public TextView country_name_ar;
        public TextView country_code;

    }
}
