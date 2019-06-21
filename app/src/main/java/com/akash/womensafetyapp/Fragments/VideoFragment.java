package com.akash.womensafetyapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akash.womensafetyapp.Activities.Video_infoActivity;
import com.akash.womensafetyapp.R;
import com.akash.womensafetyapp.files.VideoAdapter;
import com.akash.womensafetyapp.files.VideoModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment implements VideoAdapter.itemClickListener {


    public VideoFragment() {
        // Required empty public constructor
    }

    public static final String Video_String = "com.fragment.video.android";
    private RecyclerView VideoRecyclerView;
    private VideoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_video, container, false);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        VideoRecyclerView = view.findViewById(R.id.videoRecyclerView);
        VideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CollectionReference reference = database.collection("Defense_Videos");

        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                {
                    Toast.makeText(getActivity() , "Error : " +e.getMessage() , Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<VideoModel> videoModels = new ArrayList<>();
                if(queryDocumentSnapshots!= null)
                {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                    {
                        VideoModel model = new VideoModel(snapshot.getString("Video_name") , snapshot.getString("Image"));
                        videoModels.add(model);
                    }

                    adapter = new VideoAdapter(videoModels , VideoFragment.this);
                    VideoRecyclerView.setAdapter(adapter);
                }

            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {

        Intent  videoActivity = new Intent(getActivity() , Video_infoActivity.class);
        videoActivity.putExtra(Video_String , position);
        startActivity(videoActivity);
    }
}
