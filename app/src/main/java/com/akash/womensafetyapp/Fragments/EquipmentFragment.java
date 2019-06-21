package com.akash.womensafetyapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akash.womensafetyapp.Activities.HomeActivity;
import com.akash.womensafetyapp.Activities.ItemInfoActivity;
import com.akash.womensafetyapp.R;
import com.akash.womensafetyapp.files.FireStoreAdapter;
import com.akash.womensafetyapp.files.MainViewModel;
import com.akash.womensafetyapp.files.RecyclerAdapter;
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
public class EquipmentFragment extends Fragment implements FireStoreAdapter.itemClickListener{


    public EquipmentFragment() {
        // Required empty public constructor
    }


    public static final String EXTRA_ID = "com.safetyWomen.android.app";
    private RecyclerView recyclerView;
    private FireStoreAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_equipment, container, false);
        recyclerView = view.findViewById(R.id.recyclerData);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity() , 2));
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference reference = database.collection("Equipment");

        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(e!=null)
                {
                    Toast.makeText(getActivity() , "Something Went Wrong : " +e.getMessage() , Toast.LENGTH_SHORT).show();
                    return;
                }


                Log.i("firestore" , "Executed");
                ArrayList<MainViewModel> myModel = new ArrayList<>();
                if(queryDocumentSnapshots != null)
                {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                    {
                        MainViewModel model = new MainViewModel(snapshot.getString("Name") , snapshot.getString("Image"));


                        myModel.add(model);


                    }

                    adapter = new FireStoreAdapter(myModel ,EquipmentFragment.this);
                    recyclerView.setAdapter(adapter);
                }


            }
        });
        Log.i("onCreateView" , "View Returned");
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent InfoActivity = new Intent(getActivity() , ItemInfoActivity.class);
        InfoActivity.putExtra(EXTRA_ID , position);
        startActivity(InfoActivity);

    }
}
