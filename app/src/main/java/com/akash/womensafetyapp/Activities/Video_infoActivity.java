package com.akash.womensafetyapp.Activities;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.TextView;

import com.akash.womensafetyapp.Fragments.VideoFragment;
import com.akash.womensafetyapp.R;
import com.akash.womensafetyapp.files.VideoViewModel;
import com.akash.womensafetyapp.files.youtubeConfig;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;

public class Video_infoActivity extends YouTubeBaseActivity {


    private YouTubePlayerView playerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private TextView video_name , source_name  , link , about_video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);

        playerView = findViewById(R.id.yvideo);
        video_name = findViewById(R.id.Video_Name);
        source_name = findViewById(R.id.source);
        link = findViewById(R.id.Link);

        about_video = findViewById(R.id.Desc);

        final int VIDEO_ID = getIntent().getIntExtra(VideoFragment.Video_String , 0);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference reference  = database.collection("Defense_Videos");
        reference.get(Source.SERVER).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                ArrayList<VideoViewModel> videoModelList  = new ArrayList<>();

                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                {

                    VideoViewModel model = snapshot.toObject(VideoViewModel.class);

                    videoModelList.add(model);
                }


                if(videoModelList.size()!= 0)
                {

                    //store youtube video id's to youtube video player view from firestore
                    AddYoutubeVideo(videoModelList.get(VIDEO_ID).getVideo_id());

                    video_name.setText(videoModelList.get(VIDEO_ID).getVideo_name());

                    source_name.setText(String.format("Source : %s", videoModelList.get(VIDEO_ID).getSource()));

                    link.setText(String.format("Channel Link : %s", videoModelList.get(VIDEO_ID).getLink()));

                    about_video.setText(videoModelList.get(VIDEO_ID).getVideo_desc().replace("/n" , "\n"));




                }

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new AlertDialog.Builder(Video_infoActivity.this).setTitle("Internet Problem").
                        setTitle("Make sure internet is enabled").setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                startActivity(getIntent());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });


    }

    private void AddYoutubeVideo(final String video_id) {

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(video_id);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        playerView.initialize(youtubeConfig.getApiKey() , onInitializedListener);



    }
}
