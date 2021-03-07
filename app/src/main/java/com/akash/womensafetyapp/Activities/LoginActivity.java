package com.akash.womensafetyapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {

    public static final  String prefKey = "com.login.key";
    public static final String LoginKey ="User.Login.key";
    private EditText MyEmail , MyPassword;
    private FirebaseAuth auth;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyEmail = findViewById(R.id.email);
        MyPassword = findViewById(R.id.Password);
        auth = FirebaseAuth.getInstance();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = layoutInflater.inflate(R.layout.alert_dialog , null);
        alertBuilder.setView(dialogView);
        alertBuilder.setCancelable(false);
        dialog = alertBuilder.create();
    }

    public void AccountCreating(View view) {
        Intent SignUpIntent = new Intent(LoginActivity.this , SignUpActivity.class);
        startActivity(SignUpIntent);
        overridePendingTransition(R.anim.swipe_in_right , R.anim.swipe_out_left);
    }

    public void LoginAccount(View view) {

        String Email = MyEmail.getText().toString();
        String Password = MyPassword.getText().toString();

        if(isValidation(Email , Password))
        {
            MyEmail.setError(null);
            MyPassword.setError(null);
            dialog.show();
            SignInWithEmailAndPassword(Email , Password);
        }

    }

    /**
     * this function SignedInWithEMailAndPassword
     * @param email users email
     * @param password users password
     */
    private void SignInWithEmailAndPassword(String email, String password) {

        auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    SharedPreferences sp =getSharedPreferences(prefKey , MODE_PRIVATE);
                    MyPreference(sp);
                    dialog.dismiss();
                    HomeActivity();
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this , "Login Failed : " + Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void HomeActivity() {
        Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void MyPreference(SharedPreferences sp) {

        SharedPreferences.Editor editor =sp.edit();
        editor.putBoolean(LoginKey , true);
        editor.apply();

    }

    private boolean isValidation(String email, String password) {
        boolean isValidate = true;

        if(TextUtils.isEmpty(email))
        {
            MyEmail.setError("Email Required");
            isValidate = false;
        }
        else if(TextUtils.isEmpty(password))
        {
            MyPassword.setError("Password Required");
            isValidate = false;
        }

        return isValidate;
    }

    public void ForgotPassword(View view) {
        Intent forgotActivity = new Intent(LoginActivity.this , ForgotPasswordActivity.class);
        startActivity(forgotActivity);
    }
}
