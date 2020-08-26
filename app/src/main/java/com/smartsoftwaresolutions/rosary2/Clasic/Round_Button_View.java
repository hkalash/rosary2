package com.smartsoftwaresolutions.rosary2.Clasic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.smartsoftwaresolutions.rosary2.R;

public class Round_Button_View  extends FrameLayout implements View.OnClickListener  {
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    ImageView ivStar_r;
    Circle_view_r vCircle_r;
    Dotes_small_view vDotsView_r;
 public  TextView tv_clasic_counter;
    public static final String PREFS_NAME = "Saved_Type";
    public  SharedPreferences SpType;
Integer set_counter;
   private Context mContext;

public int counter_x=0;
    private boolean isChecked;
    private AnimatorSet animatorSet;

    public Round_Button_View(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public Round_Button_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public Round_Button_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Round_Button_View(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        init();
    }



    private void init() {

        // link the view to the layout

        LayoutInflater.from(getContext()).inflate(R.layout.view_rounded_button, this, true);
        //ButterKnife.bind(this);
       ivStar_r=findViewById((R.id.ivStar_r));
        vCircle_r=findViewById(R.id.vCircle_r);
        vDotsView_r=findViewById((R.id.vDotsView_r));
        tv_clasic_counter=findViewById(R.id.tv_clasic_counter);
tv_clasic_counter.setText("0");

         setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
              //  ivStar_r.animate().scaleX(0.7f).scaleY(0.7f).setDuration(150).setInterpolator(DECCELERATE_INTERPOLATOR);
                setPressed(true);
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                boolean isInside = (x > 0 && x < getWidth() && y > 0 && y < getHeight());
                if (isPressed() != isInside) {
                    setPressed(isInside);
                }
                break;

            case MotionEvent.ACTION_UP:
               // ivStar_r.animate().scaleX(1).scaleY(1).setInterpolator(DECCELERATE_INTERPOLATOR);
                if (isPressed()) {
                    performClick();
                    setPressed(false);
                }
                break;

            case MotionEvent.ACTION_CANCEL:
              //  ivStar_r.animate().scaleX(1).scaleY(1).setInterpolator(DECCELERATE_INTERPOLATOR);
                setPressed(false);
                break;

        }
        return true;
    }

    public boolean isChecked() {
        return isChecked;
    }
    @Override
    public void onClick(View view) {
     //   isChecked = !isChecked;
        // link the view to the image
       // ivStar_r.setImageResource(isChecked ? R.drawable.ic_star_rate_on : R.drawable.ic_star_rate_off);

        if (animatorSet != null) {
            animatorSet.cancel();
        }

      //  if (isChecked) {
           // ivStar_r.animate().cancel();
         //   ivStar_r.setScaleX(0);
           // ivStar_r.setScaleY(0);
        vCircle_r.setInnerCircleRadiusProgress(0);
          vCircle_r.setOuterCircleRadiusProgress(0);
            vDotsView_r.setCurrentProgress(0);

            animatorSet = new AnimatorSet();

            ObjectAnimator outerCircleAnimator = ObjectAnimator.ofFloat(vCircle_r, Circle_view_r.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            outerCircleAnimator.setDuration(250);
            outerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator innerCircleAnimator = ObjectAnimator.ofFloat(vCircle_r, Circle_view_r.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            innerCircleAnimator.setDuration(200);
            innerCircleAnimator.setStartDelay(200);
            innerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(ivStar_r, ImageView.SCALE_Y, 0.2f, 1f);
            starScaleYAnimator.setDuration(350);
            starScaleYAnimator.setStartDelay(250);
            starScaleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(ivStar_r, ImageView.SCALE_X, 0.2f, 1f);
            starScaleXAnimator.setDuration(350);
            starScaleXAnimator.setStartDelay(250);
            starScaleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(vDotsView_r, Dotes_small_view.DOTS_PROGRESS, 0, 1f);
            dotsAnimator.setDuration(900);
            dotsAnimator.setStartDelay(50);
            dotsAnimator.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);

            animatorSet.playTogether(
                    outerCircleAnimator,
                   innerCircleAnimator,
                    starScaleYAnimator,
                    starScaleXAnimator,
                    dotsAnimator
            );

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                   vCircle_r.setInnerCircleRadiusProgress(0);
                   vCircle_r.setOuterCircleRadiusProgress(0);
                    vDotsView_r.setCurrentProgress(0);
                    ivStar_r.setScaleX(1);
                   ivStar_r.setScaleY(1);
                }
            });

            animatorSet.start();
     //   }
        counter_x++;

        if (tv_clasic_counter.getText().equals("Finsh")){
            counter_x=0;
            counter_x++;
        }
        SpType=mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        set_counter=  SpType.getInt("set_counter",33);
        if (set_counter==counter_x){
          //  tv_clasic_counter.setText("Finsh");
            tv_clasic_counter.setText(R.string.god_bless_you);
            counter_x=0;
        }else {
            tv_clasic_counter.setText(String.valueOf(counter_x));
        }


    }
    private void Read_counter(){

    }


}
