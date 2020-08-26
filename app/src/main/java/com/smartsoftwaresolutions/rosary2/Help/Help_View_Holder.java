package com.smartsoftwaresolutions.rosary2.Help;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smartsoftwaresolutions.rosary2.R;

public class Help_View_Holder extends RecyclerView.ViewHolder {
    RelativeLayout cv;
    TextView title;
    TextView description;
    ImageView imageView;

    Help_View_Holder(View itemView) {
        super(itemView);
        cv =  itemView.findViewById(R.id.help_rowView);
        title = itemView.findViewById(R.id.help_title);
        description = itemView.findViewById(R.id.help_description);
        imageView = itemView.findViewById(R.id.help_imageView);
    }
}
