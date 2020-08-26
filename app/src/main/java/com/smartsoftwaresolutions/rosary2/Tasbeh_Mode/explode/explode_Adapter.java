package com.smartsoftwaresolutions.rosary2.Tasbeh_Mode.explode;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartsoftwaresolutions.rosary2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class explode_Adapter extends RecyclerView.Adapter<explode_Adapter.ViewHolder> {
    private String[] mData;
   int set_counter=T_explode.set_counter;
    public boolean[] Ispressed_array=new boolean[set_counter];

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    //private List<pressed> L_pressed;

    // data is passed into the constructor
    explode_Adapter(Context context, String[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;

      //setHasStableIds(true);
        //setIsRecyclable(false);
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.explode_item, parent, false);


        return new ViewHolder(view);

    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      //  pressed pressed= L_pressed.get(position);
        holder.myButton.setText(mData[position]);
       // holder.myButton.setTextColor(Color.BLUE);
        if (Ispressed_array[position]==true){
           // holder.myButton.setTextColor(Color.parseColor("#D3D0DA"));
            // holder.myButton.setBackgroundColor(Color.WHITE);
            holder.myButton.setBackgroundColor(Color.parseColor("#4E7BA7"));
        }else {
           // holder.myButton.setTextColor(Color.parseColor("#D3D0DA"));
            // holder.myButton.setBackgroundColor(Color.WHITE);
            holder.myButton.setBackgroundColor(Color.parseColor("#D28F1B"));
        }

   // holder.setIsRecyclable(false);
     // return holder;
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button myButton;

        ViewHolder(View itemView) {
            super(itemView);
            myButton = itemView.findViewById(R.id.btn_grid_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null){
                mClickListener.onItemClick(view, getAdapterPosition());
                // change the value in the list
             //   L_pressed.get(getAdapterPosition()).set_Ispressed(true);
             //   Ispressed_array[getAdapterPosition()]=true;
            }
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);

    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }


}
