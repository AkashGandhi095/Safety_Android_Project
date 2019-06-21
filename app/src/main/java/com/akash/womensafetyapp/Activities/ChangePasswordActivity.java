package com.akash.womensafetyapp.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.womensafetyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView ChangePasswordField;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ChangePasswordField = findViewById(R.id.ChangePassword);

        user = FirebaseAuth.getInstance().getCurrentUser();


    }

    public void ChangePassword(View view) {
        String Password = ChangePasswordField.getText().toString();
        if(TextUtils.isEmpty(Password))
        {
            ChangePasswordField.setError("Email Required");
            return;
        }
        ChangePasswordField.setError(null);

        user.updatePassword(Password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext() , "Password change Successfully.." , Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext()  , "Password can't be change !!" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
