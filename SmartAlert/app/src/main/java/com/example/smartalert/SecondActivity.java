package com.example.smartalert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SecondActivity extends AppCompatActivity {

    Button fire;
    Button move;
    DatabaseHelper db = new DatabaseHelper(this);
    private GpsTracker gpsTracker;
    Boolean message;
    DatabaseReference reff;
    Detections detections;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        detections = new Detections();
        reff= FirebaseDatabase.getInstance().getReference().child("Detections");

        double latitude = 0;
        double longitude = 0;

        final double finalLatitude = latitude;
        final double finalLongitude = longitude;


        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        gpsTracker = new GpsTracker(SecondActivity.this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();

        } else {
            gpsTracker.showSettingsAlert();
        }


        fire = findViewById(R.id.fire);
        move = findViewById(R.id.move);

        move.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                detections.setTypeOfEmergency("Fall Detection");
                detections.setLatitude(finalLatitude);
                detections.setLongitude(finalLongitude);
                detections.setTimestamp(timestamp);

                reff.push().setValue(detections);

                Intent i = new Intent(SecondActivity.this, MoveDetection.class);
                startActivity(i);
            }
        });



        fire.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String smsText = "“Βρίσκομαι στην τοποθεσία με γεωγραφικό μήκος :" + finalLatitude + " και γεωγραφικό\n" +
                        "πλάτος :" + finalLongitude + " και παρατηρώ μια πυρκαγιά";

                Uri uri = Uri.parse("smsto:" + "6987339078");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", smsText);
                startActivity(intent);


                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                detections.setTypeOfEmergency("Fire");
                detections.setLatitude(finalLatitude);
                detections.setLongitude(finalLongitude);
                detections.setTimestamp(timestamp);

                reff.push().setValue(detections);

                Toast.makeText(SecondActivity.this, "Το SMS εστάλει!!!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
