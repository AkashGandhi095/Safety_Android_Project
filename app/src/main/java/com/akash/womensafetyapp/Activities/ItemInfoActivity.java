package com.akash.womensafetyapp.Activities;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.akash.womensafetyapp.Fragments.EquipmentFragment;
import com.akash.womensafetyapp.R;
import com.akash.womensafetyapp.files.InfoViewModel;
import com.akash.womensafetyapp.files.PagerImageUrls;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;

public class ItemInfoActivity extends AppCompatActivity {

    private TextView desc , specs;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);


        final int ID = getIntent().getIntExtra(EquipmentFragment.EXTRA_ID , 0);


        desc = findViewById(R.id.about);
        specs = findViewById(R.id.specs);
        mToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(mToolbar);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference reference = database.collection("Equipment");

        final ViewPager pager = findViewById(R.id.imagePager);

        reference.get(Source.SERVER).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<InfoViewModel> dataList = new ArrayList<>();

                for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                {
                    InfoViewModel model = snapshot.toObject(InfoViewModel.class);
                    dataList.add(model);
                }

                if(dataList.size() != 0)
                {
                    String []imageUrl = {dataList.get(ID).getImage_1() , dataList.get(ID).getImage_2() ,
                            dataList.get(ID).getImage_3() , dataList.get(ID).getImage_4()};

                    PagerImageUrls image = new PagerImageUrls(ItemInfoActivity.this  , imageUrl);
                    pager.setAdapter(image);

                    String description = dataList.get(ID).getAbout();
                    desc.setText(description);

                    String Specification = dataList.get(ID).getSpecification().replace("/n" , "\n");
                    specs.setText(Specification);

                    mToolbar.setTitle(dataList.get(ID).getName());
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        new AlertDialog.Builder(ItemInfoActivity.this)
                                .setTitle("Network Problem !!")
                                .setMessage("Make Sure Mobile Network is enabled.")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
}
