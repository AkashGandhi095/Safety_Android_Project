package com.akash.womensafetyapp.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.akash.womensafetyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.SendEmail);
    }

    /**
     * function to reset your account password
     * @param view
     */
    public void ForgotPassword(View view) {
        String EMAIL = email.getText().toString();
        if(TextUtils.isEmpty(EMAIL))
        {
            email.setError("Email Required");
            return;
        }
        email.setError(null);

        auth.sendPasswordResetEmail(EMAIL).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {

                    Toast.makeText(ForgotPasswordActivity.this , "Email has been sent  , Check your mail" ,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
