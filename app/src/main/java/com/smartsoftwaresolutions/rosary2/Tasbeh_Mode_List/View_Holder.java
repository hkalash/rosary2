package com.smartsoftwaresolutions.rosary2.Tasbeh_Mode_List;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.smartsoftwaresolutions.rosary2.R;

public class View_Holder extends RecyclerView.ViewHolder {

    RelativeLayout cv;
    TextView title;
    TextView description;
    ImageView imageView;

    View_Holder(View itemView) {
        super(itemView);
        cv =  itemView.findViewById(R.id.rowView);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        imageView = itemView.findViewById(R.id.imageView);
    }
}