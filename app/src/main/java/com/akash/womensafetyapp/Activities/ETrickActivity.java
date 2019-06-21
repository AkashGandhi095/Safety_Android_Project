package com.akash.womensafetyapp.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.akash.womensafetyapp.R;
import com.akash.womensafetyapp.files.ImagePagerAdapter;

public class ETrickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etrick);
        ViewPager pager = findViewById(R.id.pager);
        ImagePagerAdapter Adapter = new ImagePagerAdapter(this);
        pager.setAdapter(Adapter);
    }
}
