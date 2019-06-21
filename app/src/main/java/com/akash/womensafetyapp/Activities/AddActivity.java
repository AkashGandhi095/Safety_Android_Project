package com.akash.womensafetyapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akash.womensafetyapp.R;
import com.akash.womensafetyapp.files.DatabaseHelper;
import com.akash.womensafetyapp.files.DetailsModel;

public class AddActivity extends AppCompatActivity {

    public static final String CountsPrefKey = "com.android.record.5.data";
    private EditText Name  , PhoneNo;
    private DetailsModel detailsModel;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final SharedPreferences RecordPreference = getSharedPreferences(CountsPrefKey , MODE_PRIVATE);
        final SharedPreferences.Editor EditRecord = RecordPreference.edit();

        Name = findViewById(R.id.UserName);
        PhoneNo = findViewById(R.id.UserPhoneNo);
        databaseHelper = new DatabaseHelper(this);

        Button saveButton = findViewById(R.id.SaveData);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String phoneNo = PhoneNo.getText().toString();

                int val = RecordPreference.getInt(CountsPrefKey,0);
                if(isValidation(name , phoneNo))
                {
                    Name.setError(null);
                    PhoneNo.setError(null);
                    if(val<5)
                    {
                        detailsModel = new DetailsModel(name , phoneNo);
                        databaseHelper.InsertData(detailsModel);
                        val++;
                        EditRecord.putInt(CountsPrefKey ,val).apply();
                        Toast.makeText(AddActivity.this , "Data save Successfully " , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(AddActivity.this , "only 5 contact required.." , Toast.LENGTH_SHORT).show();
                    }
                }
                Name.getText().clear();
                PhoneNo.setText("");
            }
        });

    }

    private boolean isValidation(String UserName, String UserPhoneNo) {
        boolean isValidate = true;

        if(TextUtils.isEmpty(UserName))
        {
            Name.setError("Name Required");
            isValidate = false;
        }
        else if(TextUtils.isEmpty(UserPhoneNo))
        {
            PhoneNo.setError("PhoneNo Required");
            isValidate = false;
        }

        return isValidate;
    }

    public void BackTo(View view) {

        BackToDisplay();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BackToDisplay();

    }
    public void BackToDisplay()
    {
        Intent intent = new Intent(AddActivity.this , DisplayContactActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.swipe_in_left , R.anim.swipe_out_right);
        finish();
    }
}
