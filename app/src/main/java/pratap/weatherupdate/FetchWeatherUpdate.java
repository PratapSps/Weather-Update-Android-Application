package pratap.weatherupdate;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by survya on 28-Mar-16.
 */
public class FetchWeatherUpdate {

    HttpURLConnection connect;


    public String makeConnection(Context ctx) {
        String SetUrl;
        StringBuffer result=new StringBuffer();

        if(ConstantVariables.CITY.equals("NA")){
            SetUrl=ConstantVariables.URL+"?lat="+ConstantVariables.lati+"&lon="+ConstantVariables.longi+"&appid="+ConstantVariables.apiKey;
        }
         else
            SetUrl=ConstantVariables.URL+"?q="+ConstantVariables.CITY+"&appid="+ConstantVariables.apiKey;

        if (First_page.checkNetowrk(MainApp.mycontext)) {
            ConstantVariables.Message = "";
            if(ConstantVariables.CITY.equals(ConstantVariables.previous_City)&& ConstantVariables.checkTime%600==0) {

                try {
                    ConstantVariables.previous_City = ConstantVariables.CITY;
                    URL url = new URL(SetUrl);
                    connect = (HttpURLConnection) url.openConnection();
                    BufferedReader readBuffer = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    String check;
                    while ((check = readBuffer.readLine()) != null) {
                        result.append(check);
                    }
                    readBuffer.close();
                } catch (MalformedURLException e) {
                    ConstantVariables.Error = "No Location Found";



                } catch (IOException io) {
                    ConstantVariables.Error = "Check Connectivity";


                } catch (Exception ee) {
                    ConstantVariables.Error = "Check Connectivity !";



                } finally {
                    connect.disconnect();

                }
            }
            if(!(ConstantVariables.CITY.equals(ConstantVariables.previous_City))){
                try {
                    ConstantVariables.previous_City = ConstantVariables.CITY;
                    URL url = new URL(SetUrl);
                    connect = (HttpURLConnection) url.openConnection();
                    BufferedReader readBuffer = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    String check;
                    while ((check = readBuffer.readLine()) != null) {
                        result.append(check);
                    }
                    readBuffer.close();
                } catch (MalformedURLException e) {
                    ConstantVariables.Error = "No Location Found";



                } catch (IOException io) {
                    ConstantVariables.Error= "Check Connectivity";



                } catch (Exception ee) {
                    ConstantVariables.Error = "Check Connectivity !";



                } finally {
                    connect.disconnect();

                }
            }

        }
        else{
            ConstantVariables.checkTime=0;
            ConstantVariables.Message="Lost Internet Connection.";
        }

            return result.toString();
        }


    public int converttoF(double K){
        double converted=((K - 273.15)*1.8000)+32;

        return (int)Math.round(converted);
    }

    public int convertoC(double K){
        double converted=K-273.15;
        return (int)Math.round(converted);
    }
    public String convertunix(long l){
        long timestamp=l*1000;
        SimpleDateFormat date=new SimpleDateFormat("MMM dd, 'at' hh:mm a");
        String dt=date.format(timestamp);

        return dt.toString();
    }

    public String currentDate(long time){
        StringBuilder currentDate=new StringBuilder();
        long timestamp=time*1000;
        SimpleDateFormat date=new SimpleDateFormat("EEEE");
        String day=date.format(new Date());
        SimpleDateFormat mon=new SimpleDateFormat("MMM dd");
        String monthDay=mon.format(timestamp);
        currentDate.append(day);
        currentDate.append(" ");
        currentDate.append(monthDay);
        return currentDate.toString();
    }

}
