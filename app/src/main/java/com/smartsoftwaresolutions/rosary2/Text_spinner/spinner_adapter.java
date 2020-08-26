package com.smartsoftwaresolutions.rosary2.Text_spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartsoftwaresolutions.rosary2.R;

import java.util.List;

public class spinner_adapter extends BaseAdapter {
    private List<spinner_item> SpinnerList;
    private Context context;
    LayoutInflater inflter;

    public spinner_adapter(List<spinner_item> spinnerList, Context ctx) {
        //super(ctx, R.layout.user_type_spinner, spinnerList);
        this.SpinnerList = spinnerList;
        this.context = ctx;
        inflter = (LayoutInflater.from(ctx));


    }
    public int getCount() {
        return SpinnerList.size();
    }

    public spinner_item getItem(int position) {
        return SpinnerList.get(position);
    }

    public long getItemId(int position) {
        return SpinnerList.get(position).hashCode();
    }
    //each time a row is inserted the listview will call the getview method
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
// we creat a view holder
        spinner_adapter.PayInfoHolder holder = new spinner_adapter.PayInfoHolder();

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.zeker_spinner, null);
            // Now we can fill the layout with the right values
            TextView Id = v.findViewById(R.id.zeker_id1);
            TextView name = v.findViewById(R.id.tv_zeker_des);

            holder.zeker_id1 = Id;

            holder.zeker_des = name;

            v.setTag(holder);
        }
        else
            holder = (spinner_adapter.PayInfoHolder) v.getTag();
        // we use it to get the information of the element from customer info calss
        spinner_item p =SpinnerList.get(position);
        // we read from the list and put it in the text view
        holder.zeker_id1.setText(p.getZeker_id());
        holder.zeker_des.setText(p.getZeker());



        return v;
    }

    /* *********************************
     * We use the holder pattern
     * It makes the view faster and avoid finding the component
     * **********************************/
    private static class PayInfoHolder {
        public TextView zeker_id1;
        public TextView zeker_des;


    }
}
