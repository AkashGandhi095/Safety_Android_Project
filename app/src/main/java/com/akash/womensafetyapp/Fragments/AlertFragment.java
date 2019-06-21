package com.akash.womensafetyapp.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.akash.womensafetyapp.Activities.DisplayContactActivity;
import com.akash.womensafetyapp.Activities.ETrickActivity;
import com.akash.womensafetyapp.R;
import com.akash.womensafetyapp.files.DatabaseHelper;
import com.akash.womensafetyapp.files.DetailsModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlertFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks , GoogleApiClient.OnConnectionFailedListener {


    public AlertFragment() {
        // Required empty public constructor
    }


    private GoogleApiClient apiClient;
    private FusedLocationProviderClient providerClient;
    private LocationRequest locationRequest;
    private DatabaseHelper helper;
    private ArrayList<DetailsModel> getPhoneNo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_alert, container, false);

        Button SMSLOC = view.findViewById(R.id.SendSMSLOC);
        final Button ETrick = view.findViewById(R.id.ETrick);
        helper = new DatabaseHelper(getActivity());

        //Request for permission in fragment onCreateView

        if(ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION)
        +ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()) , Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED)
        {
            requestForPermission();
        }

        //Initialize provider client

        providerClient = LocationServices.getFusedLocationProviderClient(getActivity());
        // initialize the google api Client

        apiClient = new GoogleApiClient.Builder(Objects.requireNonNull(getActivity()))
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();


        //Initialize request properties for location updates

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);



        SMSLOC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()) , Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()) ,
                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED))
                {
                    Toast.makeText(Objects.requireNonNull(getActivity()) , "Location and SMS permissions Required" , Toast.LENGTH_SHORT).show();
                    return;
                }
                providerClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null)
                        {
                            double lat = location.getLatitude();
                            double lon = location.getLongitude();
                            getPhoneNo = helper.getAllData();
                            String Message = "Here is my Location -> http://maps.google.com/maps?saddr="+ lat+","+lon;

                            if(getPhoneNo.size() > 0)
                            {
                                for(int i=0;i<getPhoneNo.size();i++)
                                {

                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage("+91"+ getPhoneNo.get(i).getPhoneNo(), null , Message , null , null);


                                }
                            }
                            else
                            {
                                Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                        "No Contacts have been found", Snackbar.LENGTH_SHORT);

                                snackBar.show();
                            }
                        }
                        else
                        {

                            new AlertDialog.Builder(getActivity()).setTitle("GPS")
                                    .setMessage("Something Went Wrong .\n" +
                                            "Make Sure Gps is Enabled")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                        }
                    }
                });
            }
        });
        FloatingActionButton FAB = view.findViewById(R.id.floatingActionButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewAct = new Intent(getActivity() , DisplayContactActivity.class);
                startActivity(NewAct);
            }
        });


        ETrick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trickActivity = new Intent(getActivity() , ETrickActivity.class);
                startActivity(trickActivity);
            }
        });


        return view;
    }


    private void requestForPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()) , Manifest.permission.SEND_SMS))
        {
            new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setTitle("Needed permission")
                    .setMessage("Location permission required for sending your location")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()) , new String[] {
                                    Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.SEND_SMS
                            } , 1);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
        else
        {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()) , new String []
                    {
                            Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.SEND_SMS
                    } , 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(Objects.requireNonNull(getActivity()) , "Pemission Granted" , Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(Objects.requireNonNull(getActivity()) , "Permission not Granted" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()) , Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        providerClient.requestLocationUpdates(locationRequest , new LocationCallback(), null);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //start client when fragment start
    @Override
    public void onStart() {
        super.onStart();
        if(apiClient!=null)
            apiClient.connect();
    }

    //stop client to save battery
    @Override
    public void onStop() {
        super.onStop();

        if(apiClient!=null)
            apiClient.disconnect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(apiClient!=null && apiClient.isConnected())
        {
            //providerClient.removeLocationUpdates(new LocationCallback());
            apiClient.disconnect();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        checkGooglePlayServices();
    }

    private void checkGooglePlayServices() {


        int errorCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Objects.requireNonNull(getActivity()));

        if(errorCode != ConnectionResult.SUCCESS)
        {
            Dialog errorDialog = GoogleApiAvailability.getInstance().getErrorDialog(Objects.requireNonNull(getActivity()), errorCode, errorCode, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Toast.makeText(Objects.requireNonNull(getActivity()) , "No Services Available" , Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            });

            errorDialog.show();
        }

    }

}
