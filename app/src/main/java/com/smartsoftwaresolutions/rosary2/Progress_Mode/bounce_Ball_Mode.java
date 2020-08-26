package com.smartsoftwaresolutions.rosary2.Progress_Mode;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;
import com.smartsoftwaresolutions.rosary2.R;

public class bounce_Ball_Mode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce_ball__mode);
View view=findViewById(R.id.bounce_v);
doBounceAnimation(view);
//        LinearLayout flowLayout = findViewById(R.id.layout_flow);
//
//        for (Ease ease : Ease.values()) {
//            flowLayout.addView(new EasingGraphView(this, ease));}

    }
    private void doBounceAnimation(View targetView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationX", 0, 25, 0);
        animator.setInterpolator(new EasingInterpolator(Ease.ELASTIC_IN_OUT));
        animator.setStartDelay(500);
        animator.setDuration(1500);
        animator.start();
}
}
