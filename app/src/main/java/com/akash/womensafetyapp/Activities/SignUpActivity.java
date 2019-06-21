package com.akash.womensafetyapp.Activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.akash.womensafetyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private EditText userEmail , userPassword;
    private FirebaseAuth auth;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userEmail = findViewById(R.id.Email);
        userPassword = findViewById(R.id.Password);
        auth = FirebaseAuth.getInstance();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SignUpActivity.this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = layoutInflater.inflate(R.layout.alert_dialog_signup , null);
        alertBuilder.setView(dialogView);
        alertBuilder.setCancelable(false);
        dialog = alertBuilder.create();
    }

    public void AlreadyAccount(View view) {
        onBackPressed();
    }

    public void SignUpAccount(View view) {
        String Email = userEmail.getText().toString();
        String Password = userPassword.getText().toString();

        if (isValidation(Email,Password))
        {
            userEmail.setError(null);
            userPassword.setError(null);
            dialog.show();
            CreateUserWithEmailAndPassword(Email,Password);
        }
    }

    private void CreateUserWithEmailAndPassword(String email, String password) {
        auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();
                            onBackPressed();

                        } else {
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "SignUp Failed : " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private boolean isValidation(String email, String password) {
        boolean isValid = true;
        if(TextUtils.isEmpty(email))
        {
            userEmail.setError("Email Required");
            isValid = false;
        }
        else if(TextUtils.isEmpty(password))
        {
            userPassword.setError("Password Required");
            isValid = false;
        }

        return isValid;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipe_in_left , R.anim.swipe_out_right);
        finish();
    }
}
