package com.smartsoftwaresolutions.rosary2.Help;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.smartsoftwaresolutions.rosary2.R;



import java.util.Collections;
import java.util.List;

public class Help_Adapter extends RecyclerView.Adapter<Help_View_Holder> {
    List<Help_Data> list = Collections.emptyList();
    Context context;

    public Help_Adapter(List<Help_Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public Help_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_row_layout, parent, false);
        Help_View_Holder holder = new Help_View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(Help_View_Holder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.title.setText(list.get(position).title);
        holder.description.setText(list.get(position).description);
        holder.imageView.setImageResource(list.get(position).imageId);

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
    public void insert(int position,Help_Data data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Swipe_Data object
    public void remove(Help_Data data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

}
