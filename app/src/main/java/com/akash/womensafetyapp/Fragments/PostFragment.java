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

import com.akash.womensafetyapp.Activities.WebActivity;
import com.akash.womensafetyapp.R;
import com.akash.womensafetyapp.files.PostModel;
import com.akash.womensafetyapp.files.PostRecyclerAdapter;
import com.google.firebase.firestore.CollectionReference;
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
public class PostFragment extends Fragment implements PostRecyclerAdapter.itemClickListener {


    public PostFragment() {
        // Required empty public constructor
    }


    public static final String POST_STRING = "com.post_fragment.android";
    private ArrayList<PostModel> modelList;
    private RecyclerView postRecyclerView;
    private PostRecyclerAdapter postAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_post, container, false);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference myreference = database.collection("Safety_Posts");

        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myreference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                {
                    Toast.makeText(getActivity() , "Error : " +e.getMessage() , Toast.LENGTH_SHORT).show();
                    return;
                }

                 modelList = new ArrayList<>();
                if(queryDocumentSnapshots!=null)
                {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                    {
                        PostModel postModel = snapshot.toObject(PostModel.class);
                        modelList.add(postModel);
                    }

                    postAdapter = new PostRecyclerAdapter(modelList , PostFragment.this);
                    postRecyclerView.setAdapter(postAdapter);

                }
            }
        });

        return view;
    }


    @Override
    public void onItemClick(int position) {
        String URL = modelList.get(position).getWeb_link();

        Intent webActivity = new Intent(getActivity() , WebActivity.class);
        webActivity.putExtra(POST_STRING , URL);
        startActivity(webActivity);
    }
}
