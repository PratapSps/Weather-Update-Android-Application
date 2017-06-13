package pratap.weatherupdate;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by survya on 28-Mar-16.
 */
@SuppressWarnings("ALL")
public class LocationUpdater {


    static  LocationManager locate;
    static String locationProvider;
    static Geocoder geocoder;
    static boolean StopTask=true;


  public void  LocationFinder(Context mycontext){

        locate=(LocationManager)mycontext.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria=new Criteria();
        ListenLocation checkLoc = new ListenLocation();
        locationProvider=locate.getBestProvider(criteria, false);
        if(locationProvider!=null && !locationProvider.equals("")) {
          Location getloc = locate.getLastKnownLocation(locationProvider);
          locate.requestLocationUpdates(locationProvider,0, 0, checkLoc);
          if(getloc!=null){
              checkLoc.onLocationChanged(getloc);
          }
          else{
              ConstantVariables.Message="No Location Found";


          }

      }else{
            ConstantVariables.Message="No Location Found";


      }
    }

   static public void getLocationAddress(){
        geocoder = new Geocoder(MainApp.mycontext, Locale.getDefault());
       MainApp mp=new MainApp();
        List<Address> address = null;
        try {
            address = geocoder.getFromLocation(ConstantVariables.lati, ConstantVariables.longi, 1);
            ConstantVariables.CITY=address.get(0).getLocality();
        } catch (IOException e) {
            ConstantVariables.Message="Cannot Find City";



        }
       catch(Exception kk){
           ConstantVariables.Message="Cannot Find City";
          
       }

    }

}

class ListenLocation implements LocationListener{
    @Override
    public void onLocationChanged(Location location) {

        ConstantVariables.lati=location.getLatitude();
        ConstantVariables.longi=location.getLongitude();

        if(First_page.checkNetowrk(MainApp.mycontext)) {
            MainApp.loc.getLocationAddress();
            ConstantVariables.Message="";
        }
        else{

            ConstantVariables.Message="No Internet Connectivity";
        }
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}