package com.smartsoftwaresolutions.rosary2.Swip;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.smartsoftwaresolutions.rosary2.R;

import java.util.Collections;
import java.util.List;

public class swipe_Mode_Adapter extends RecyclerView.Adapter<Swipe_View_Holder> {

    List<Swipe_Data> list = Collections.emptyList();
    Context context;
    Swipe_View_Holder holder;
    public swipe_Mode_Adapter(List<Swipe_Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public Swipe_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_element, parent, false);
      //  Swipe_View_Holder holder = new Swipe_View_Holder(v);
         holder = new Swipe_View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(Swipe_View_Holder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.title.setText(list.get(position).title);
        holder.title_native.setText(list.get(position).title_native);
//        holder.description.setText(list.get(position).description);
//        holder.imageView.setImageResource(list.get(position).imageId);

        //animate(holder);

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Swipe_Data data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Swipe_Data object
    public void remove(Swipe_Data data) {
        int position = list.indexOf(data);
        list.remove(position);
      ///  holder.title.setBackgroundColor(Color.BLUE);
        notifyItemRemoved(position);
    }


}
