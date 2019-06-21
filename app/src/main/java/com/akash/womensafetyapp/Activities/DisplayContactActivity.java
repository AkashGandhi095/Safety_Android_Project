package com.akash.womensafetyapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.akash.womensafetyapp.R;
import com.akash.womensafetyapp.files.ClickListener;
import com.akash.womensafetyapp.files.DatabaseHelper;
import com.akash.womensafetyapp.files.DetailsModel;
import com.akash.womensafetyapp.files.RecyclerAdapter;

import java.util.ArrayList;

public class DisplayContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        Toolbar mToolbar = findViewById(R.id.ToolBar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final SharedPreferences preferences = getSharedPreferences(AddActivity.CountsPrefKey , MODE_PRIVATE);
        final SharedPreferences.Editor recordEditor = preferences.edit();

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final ArrayList<DetailsModel> dataLists;
        final DatabaseHelper helper = new DatabaseHelper(this);
        dataLists = helper.getAllData();

        if(dataLists.size()!=0)
        {
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(manager);
            final RecyclerView.Adapter adapter = new RecyclerAdapter(dataLists, new ClickListener() {
                @Override
                public void onPositionClicked(int position) {
                    PhoneCalls(dataLists.get(position).getPhoneNo());
                }
            });
            recyclerView.setAdapter(adapter);
            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
                private ColorDrawable color;
                private Drawable icon;
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    int pos = viewHolder.getAdapterPosition();
                    String name = dataLists.get(pos).getName();
                    String phoneno = dataLists.get(pos).getPhoneNo();
                    int val = preferences.getInt(AddActivity.CountsPrefKey , 0);
                    val--;
                    recordEditor.putInt(AddActivity.CountsPrefKey,val);
                    recordEditor.apply();
                    helper.DeleteData(name , phoneno);
                    ((RecyclerAdapter) adapter).DeleteItem(pos);
                }

                @Override
                public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    color = new ColorDrawable(Color.RED);
                    icon = ContextCompat.getDrawable(DisplayContactActivity.this, R.drawable.delete);

                    View itemView = viewHolder.itemView;
                    int backgroundCornerOffset = 10;
                    int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                    int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                    int iconBottom = iconTop + icon.getIntrinsicHeight();
                    if(dX > 0)
                    {

                        int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                        int iconRight = itemView.getLeft() + iconMargin;
                        icon.setBounds(iconRight, iconTop, iconLeft, iconBottom);
                        color.setBounds(itemView.getLeft() , itemView.getTop(),itemView.getLeft() + ((int) dX)+backgroundCornerOffset , itemView.getBottom());

                    }
                    else if(dX < 0)
                    {

                        int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                        int iconRight = itemView.getRight() - iconMargin;
                        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                        color.setBounds(itemView.getRight() + ((int)dX)-backgroundCornerOffset , itemView.getTop() , itemView.getRight() , itemView.getBottom());
                    }
                    else
                    {
                        color.setBounds(0,0,0,0);
                    }
                    color.draw(c);
                    icon.draw(c);
                }
            };



            new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        }
        else {
            Toast.makeText(this , "No data found" , Toast.LENGTH_SHORT).show();
        }

    }

    public void PhoneCalls(String phoneNo)
    {
        try
        {
            Intent call;
            if(Build.VERSION.SDK_INT > 22)
            {
                if(ActivityCompat.checkSelfPermission(this , Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.CALL_PHONE} , 1);
                    return;
                }
                call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" +phoneNo));
            }
            else
            {
                call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" +phoneNo));
            }
            startActivity(call);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }
    public void AddActivity(View view) {
        Intent Act = new Intent(DisplayContactActivity.this , AddActivity.class);
        startActivity(Act);
        finish();
    }
}
