package pratap.weatherupdate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class MainApp extends AppCompatActivity {

    static TextView city;
    static TextView FD;
    static TextView CD;
    static TextView Status;
    static ImageView imageStatus;
    static TextView min;
    static TextView max;
    static TextView humidity;
    static TextView windspeed;
    static TextView clouds;
    static TextView sunrise;
    static TextView sunset;
    static TextView currentDate;
    static TextView message;
    static Context mycontext;
    static LocationUpdater loc = new LocationUpdater();
    static ParseJson parse = new ParseJson();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        mycontext = this;
        InitializeGUI();
        parse.execute(this);
        loc.LocationFinder(this);
    }


    public void InitializeGUI() {
        city = new TextView(this);
        FD = new TextView(this);
        CD = new TextView(this);
        Status = new TextView(this);
        min = new TextView(this);
        max = new TextView(this);
        humidity = new TextView(this);
        windspeed = new TextView(this);
        clouds = new TextView(this);
        sunrise = new TextView(this);
        sunset = new TextView(this);
        currentDate = new TextView(this);
        message = new TextView(this);
        imageStatus = new ImageView(this);
        city = (TextView) findViewById(R.id.city);
        city.setPaintFlags(city.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        FD = (TextView) findViewById(R.id.DegreeF);
        CD = (TextView) findViewById(R.id.degreeC);
        Status = (TextView) findViewById(R.id.status);
        imageStatus = (ImageView) findViewById(R.id.imageupdate);
        min = (TextView) findViewById(R.id.minF);
        max = (TextView) findViewById(R.id.maxF);
        humidity = (TextView) findViewById(R.id.humidity);
        windspeed = (TextView) findViewById(R.id.windspeed);
        clouds = (TextView) findViewById(R.id.clouds);
        sunrise = (TextView) findViewById(R.id.sunrise);
        sunset = (TextView) findViewById(R.id.sunset);
        message=(TextView)findViewById(R.id.Message);
        currentDate = (TextView) findViewById(R.id.currentDate);

    }

    public  void handleMainException() {
        new AlertDialog.Builder(MainApp.mycontext)
                .setMessage(ConstantVariables.Message)
                .setTitle("Application Will Exit")
                .setCancelable(false)
                .setPositiveButton("Check Setting", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}