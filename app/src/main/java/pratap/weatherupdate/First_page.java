package pratap.weatherupdate;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class First_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        if(checkNetowrk(this)){
            checkLocation();
            Intent mainActivity=new Intent(First_page.this,MainApp.class);
            finish();
            startActivity(mainActivity);
        }
    else{

            handleException();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkNetowrk(this)){
            checkLocation();
            Intent mainActivity=new Intent(First_page.this,MainApp.class);
            finish();
            startActivity(mainActivity);
        }
        else{

            handleException();
        }
    }

    public static boolean checkNetowrk(Context firstContext) {
        boolean checking = false;
        ConnectivityManager con = (ConnectivityManager) firstContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkAvailibility = con.getActiveNetworkInfo();
        if (networkAvailibility != null) {
            checking = true;
        }

        return checking;
    }

    public void handleException() {
        new AlertDialog.Builder(this)
                .setMessage("No Internet Connectivity")
                .setTitle("")
                .setCancelable(false)
                .setPositiveButton("Check Setting", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    }
                })
                .show();
    }

    public void checkLocation() {
        Geocoder checkCity = new Geocoder(this, Locale.getDefault());
        LocationManager checkingLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String LocProvider = checkingLocation.getBestProvider(criteria, false);
        Location setlocation = checkingLocation.getLastKnownLocation(LocProvider);
        ConstantVariables.lati = setlocation.getLatitude();
        ConstantVariables.longi = setlocation.getLongitude();
        if (checkNetowrk(this)) {
            List<Address> getAddress = null;
            try {
                getAddress = checkCity.getFromLocation(ConstantVariables.lati, ConstantVariables.longi, 1);
                ConstantVariables.CITY = getAddress.get(0).getLocality();
            } catch (IOException e) {
                handleException();

            } catch (Exception kk) {
                handleException();
            }

        }
        else{
            handleException();
        }

    }

    }

