package com.smartsoftwaresolutions.rosary2.Swip;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smartsoftwaresolutions.rosary2.R;

public class Swipe_View_Holder extends RecyclerView.ViewHolder {

    LinearLayout cv;
    TextView title,title_native;
//    TextView description;
//    ImageView imageView;

    Swipe_View_Holder(View itemView) {
        super(itemView);
        cv =  itemView.findViewById(R.id.swipe_rowView);
        title = itemView.findViewById(R.id.swipe_title);
        title_native = itemView.findViewById(R.id.swipe_title_native);
//        description = (TextView) itemView.findViewById(R.id.description);
//        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}