package pratap.weatherupdate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by survya on 28-Mar-16.
 */
public class ParseJson extends AsyncTask<Context, Void, String> {
    HttpURLConnection connect;
    FetchWeatherUpdate fetch=new FetchWeatherUpdate();
    @Override
    protected String doInBackground(Context... params) {
        try
        {

            String Output;
            StringBuffer result=new StringBuffer();
            Output=fetch.makeConnection(MainApp.mycontext);

        if(Output!=null)
        {

            JSONObject js=new JSONObject(Output);
            JSONArray weather=js.getJSONArray(ConstantVariables.Weather_Object);
            JSONObject getWeather=weather.getJSONObject(0);
            ConstantVariables.Update_Status=getWeather.getString(ConstantVariables.Weather_Main);
            ConstantVariables.Update_Icon=getWeather.getString(ConstantVariables.Weather_Icon);
            ConstantVariables.Description=(getWeather.getString(ConstantVariables.Weather_Description)).toUpperCase();


            JSONObject getMain=js.getJSONObject(ConstantVariables.Main_Object);
            double temp=getMain.getDouble(ConstantVariables.Main_Temp);
            double tempMin=getMain.getDouble(ConstantVariables.Main_TempMin);
            double tempMax=getMain.getDouble(ConstantVariables.Main_TempMax);
            ConstantVariables.Humidity=getMain.getString(ConstantVariables.Main_Humidity);
            ConstantVariables.Temp_F=String.valueOf(fetch.converttoF(temp));
            ConstantVariables.Temp_C=String.valueOf(fetch.convertoC(temp));
            ConstantVariables.Temp_MinF=String.valueOf(fetch.converttoF(tempMin));
            ConstantVariables.Temp_MaxF=String.valueOf(fetch.converttoF(tempMax));
            ConstantVariables.Temp_MinC=String.valueOf(fetch.convertoC(tempMin));
            ConstantVariables.Temp_MaxC=String.valueOf(fetch.convertoC(tempMax));
            JSONObject getWind=js.getJSONObject(ConstantVariables.Wind_Object);
            JSONObject getClouds=js.getJSONObject((ConstantVariables.Clouds_Object));
            JSONObject getSys=js.getJSONObject(ConstantVariables.Sys_Object);
            ConstantVariables.wind_speed=Double.toString(getWind.getDouble(ConstantVariables.Wind_speed));
            ConstantVariables.clouds=getClouds.getString(ConstantVariables.Clouds_all);
            long sunrise=getSys.getLong(ConstantVariables.Sys_sunrise);
            ConstantVariables.sunrise=fetch.convertunix(sunrise);
            long sunset=getSys.getLong(ConstantVariables.Sys_sunset);
            ConstantVariables.sunset=fetch.convertunix(sunset);
            long currentDate=js.getLong(ConstantVariables.Object_dt);
            ConstantVariables.currentDate=fetch.currentDate(currentDate);
            ConstantVariables.currentLocation=js.getString(ConstantVariables.Object_name);






        }
        }catch(JSONException jj){

        }
        return null;
    }

    @Override
    protected void onPostExecute(String temp) {
        super.onPostExecute(temp);
        if(ConstantVariables.CITY.equals("NA")) {
            MainApp.city.setText(ConstantVariables.currentLocation);
            ConstantVariables.CITY=ConstantVariables.currentLocation;
            ConstantVariables.previous_City=ConstantVariables.currentLocation;

        }
        else{
            MainApp.city.setText(ConstantVariables.CITY);
        }
        MainApp.message.setText(ConstantVariables.Message);
        MainApp.Status.setText(ConstantVariables.Description);
        MainApp.FD.setText(ConstantVariables.Temp_F);
        MainApp.CD.setText(ConstantVariables.Temp_C);
        MainApp.min.setText(ConstantVariables.Temp_MinF);
        MainApp.max.setText(ConstantVariables.Temp_MaxF);
        MainApp.humidity.setText(ConstantVariables.Humidity);
        MainApp.windspeed.setText(ConstantVariables.wind_speed);
        MainApp.clouds.setText(ConstantVariables.clouds);
        MainApp.sunrise.setText(ConstantVariables.sunrise);
        MainApp.sunset.setText(ConstantVariables.sunset);
        MainApp.currentDate.setText(ConstantVariables.currentDate);
        if(ConstantVariables.Update_Icon.equals(ConstantVariables.CLEARDAY))
            MainApp.imageStatus.setImageResource(R.drawable.clearday);
        else if(ConstantVariables.Update_Icon.equals(ConstantVariables.CLEARNIGHT))
            MainApp.imageStatus.setImageResource(R.drawable.clearnight);
        else if(ConstantVariables.Update_Icon.equals(ConstantVariables.CLOUDDAY))
            MainApp.imageStatus.setImageResource(R.drawable.cloudday);
        else if(ConstantVariables.Update_Icon.equals(ConstantVariables.CLOUDNIGHT))
            MainApp.imageStatus.setImageResource(R.drawable.cloudnight);
        else if(ConstantVariables.Update_Icon.equals(ConstantVariables.CLOUDY_D_S)||ConstantVariables.Update_Icon.equals(ConstantVariables.CLOUDY_N_S)||ConstantVariables.Update_Icon.equals(ConstantVariables.CLOUDY_N)||ConstantVariables.Update_Icon.equals(ConstantVariables.CLOUDY_D))
            MainApp.imageStatus.setImageResource(R.drawable.clouds);
        else if(ConstantVariables.Update_Icon.equals(ConstantVariables.SNOW_DAY)||ConstantVariables.Update_Icon.equals(ConstantVariables.SNOW_NIGHT))
            MainApp.imageStatus.setImageResource(R.drawable.snowfall);
        else if(ConstantVariables.Update_Icon.equals(ConstantVariables.RAIN_DAY))
            MainApp.imageStatus.setImageResource(R.drawable.rainday);
        else if(ConstantVariables.Update_Icon.equals(ConstantVariables.RAIN_NIGHT))
            MainApp.imageStatus.setImageResource(R.drawable.rainnight);
        else if(ConstantVariables.Update_Icon.equals(ConstantVariables.MIST_DAY)||ConstantVariables.Update_Icon.equals(ConstantVariables.MIST_NIGHT))
            MainApp.imageStatus.setImageResource(R.drawable.mist);
        else if(ConstantVariables.Update_Icon.equals(ConstantVariables.SHOWERRAIN_D)||ConstantVariables.Update_Icon.equals(ConstantVariables.SHOWERRAIN_N))
            MainApp.imageStatus.setImageResource(R.drawable.showerrain);
        else if(ConstantVariables.Update_Icon.equals(ConstantVariables.THUNDER_DAY)||ConstantVariables.Update_Icon.equals(ConstantVariables.THUNDER_NIGHT))
            MainApp.imageStatus.setImageResource(R.drawable.thunderstrom);
        else
            MainApp.imageStatus.setImageResource(R.drawable.icon1);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConstantVariables.checkTime+=1;
                new ParseJson().execute(MainApp.mycontext);
            }
        }, 1000);

    }
}
