package com.smartsoftwaresolutions.rosary2.YouTube_videos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.video.Video_Activity;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VideoInfoHolder> {

    //these ids are the unique id for each video
    String[] VideoID = {"k58vGSR7OUk"
            , "ni4j4sXEMxg"
            , "67Pbeq_TUKU"
            , "2YKNLkWo0_4"
            , "f5bNK78JL1g"
            , "_eAVIgsOX84"
            , "1LSKA6wWHIg"
            , "psrr6olFXVY"
            , "Xts8Ew26xD4"
            , "M-IxyZ_8Auk"
            , "8iWSGM5Mtm8"
    ,"M-IxyZ_8Auk"
    ,"aAC9Xx7yI94"
    ,"f5bNK78JL1g"
    ,"o-X6WDRk3vM"
   ,"l6G9ZLYQhoM" };

    String[] videoData = {"سورة التوبة عبد الرحمن العوسي تلاوة خاشعة - Abd rahman al ossi Sourate al Tawba",
            "صلاة التراويح للقارئ عبدالرحمن العوسي سورة الحاقة",
            "Holy Quran - Surah 2 - Al Baqarah - Sheikh Abdulrahman Al Ossi"
            , "عبدالرحمن العوسي"
            , "(( أجمل صوت هزّ العالم )) للشيخ عبدالرحمن العوسي (سورة الرحمن)"
            , "تلاوة خيالية تفوق الوصف لسورةالواقعة للقارئ عبدالرحمن العوسي1437هـ أنشرالمقطع واكسب الحسنات من الل"
            , "فجرية جميلة وهادئة من سورة النازعات بصوت القارئ : رعد بـن محمد الكردي \" Full HD"
            , "سورة الحجر بصوت القارئ رعد الكردي"
            , "اوآخر سورة آل عمران للشيخ أحمد النفيس مؤثرة جداً"
            , "Surah Al-Haqqah - Syaikh Abdul Rahman Al-Ausiy"
            , " Sourate An Naba - Idris Al Hashemi سورة النبأ إدريس_الهاشمي"
    ,"Surah Al-Haqqah - Syaikh Abdul Rahman Al-Ausiy"
    ,"أجمل وأروع تلاوة لسورة الشورى بصوت القارئ عبدالرحمن العوسي"
    ,"للشيخ عبدالرحمن العوسي (سورة الرحمن)"
    ,"سورة يـوسف تــلاوة بصــوت الشيـخ عبدالـرحمن الـعوسي"
   ,"دعاء ليلة 27 من رمضان 1439 للشيخ عبد الرحمن بن جمال العوسي" };
    Context ctx;

    public RecyclerAdapter(Context context) {
        this.ctx = context;
    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_item, parent, false);
        return new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {


//        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
//            @Override
//            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
//
//            }
//
//            @Override
//            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
//                youTubeThumbnailView.setVisibility(View.VISIBLE);
//                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
//
//
//
//            }
//        };

        holder.youTubeThumbnailView.initialize("AIzaSyBqPrA5NVa8_3vKJMO0ml2UhPpq0IhZ70I", new YouTubeThumbnailView.OnInitializedListener() {
            @Override

            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(VideoID[position]);

                //  youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailView.setVisibility(View.VISIBLE);
                        holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                    }
                });
                // youTubeThumbnailLoader.release();
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });
        holder.tv_yt.setText(videoData[position]);
    }

    @Override
    public int getItemCount() {
        return VideoID.length;
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;
        protected TextView tv_yt;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            playButton = itemView.findViewById(R.id.btnYoutube_player);
            tv_yt = itemView.findViewById(R.id.tv_yt);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = itemView.findViewById(R.id.youtube_thumbnail);

        }

        @Override
        public void onClick(View v) {

//            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, "AIzaSyBqPrA5NVa8_3vKJMO0ml2UhPpq0IhZ70I", VideoID[getLayoutPosition()]);
//            ctx.startActivity(intent);
            Intent intent = new Intent(ctx, Video_Activity.class);
            String v_id = VideoID[getLayoutPosition()];
            intent.putExtra("data", v_id);
            ctx.startActivity(intent);
        }
    }
}