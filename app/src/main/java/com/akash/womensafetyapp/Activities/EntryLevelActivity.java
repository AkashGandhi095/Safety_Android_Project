package com.akash.womensafetyapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class EntryLevelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences(LoginActivity.prefKey , MODE_PRIVATE);
        boolean isLogin = sp.getBoolean(LoginActivity.LoginKey,false);

        Intent startActivity ;
        if(isLogin)
        {
            startActivity = new Intent(EntryLevelActivity.this , HomeActivity.class);

        }
        else
        {
            startActivity  = new Intent(EntryLevelActivity.this , LoginActivity.class);
        }

        startActivity(startActivity);
        finish();
    }
}
