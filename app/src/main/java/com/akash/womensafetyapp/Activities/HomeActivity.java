package com.akash.womensafetyapp.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.akash.womensafetyapp.Fragments.AlertFragment;
import com.akash.womensafetyapp.Fragments.DefenceTricksFragment;
import com.akash.womensafetyapp.Fragments.EquipmentFragment;
import com.akash.womensafetyapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);

        Fragment fragment = new AlertFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.myFrameLayout , fragment);
        ft.commit();

        DrawerLayout drawerLayout = findViewById(R.id.myDrawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout , toolbar , R.string.open_Drawer , R.string.close_Drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

    }


    /**
     * This function listen when item selected from navigation Drawer
     * @param menuItem a drawer menu selected item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int ItemID = menuItem.getItemId();

        Fragment fragment = null ;



        if(ItemID == R.id.nav_equipments)
        {
            fragment = new EquipmentFragment();

        }
        else if(ItemID == R.id.nav_defence)
        {
            fragment = new DefenceTricksFragment();
        }
        else if(ItemID == R.id.Change_Password)
        {

            Intent ChangePassActvity = new Intent(HomeActivity.this , ChangePasswordActivity.class);
            startActivity(ChangePassActvity);
        }
        else if(ItemID == R.id.Logout)
        {

            LogOut();

        }
        else
        {
            fragment = new AlertFragment();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(fragment == null)
        {
            return false;
        }
        ft.replace(R.id.myFrameLayout , fragment);
        ft.commit();

        DrawerLayout drawerLayout = findViewById(R.id.myDrawerLayout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     * this function logout from the signed In account.
     */
    private void LogOut() {

        DrawerLayout drawerLayout = findViewById(R.id.myDrawerLayout);
        drawerLayout.closeDrawer(GravityCompat.START);
        new AlertDialog.Builder(this).setTitle("Log Out")
                .setTitle("Are you sure you want to LogOut")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        SharedPreferences logoutPreference = getSharedPreferences(LoginActivity.prefKey , MODE_PRIVATE);
                        SharedPreferences.Editor edit = logoutPreference.edit();
                        edit.clear();
                        edit.apply();
                        startActivity(new Intent(HomeActivity.this , LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.myDrawerLayout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }
}
