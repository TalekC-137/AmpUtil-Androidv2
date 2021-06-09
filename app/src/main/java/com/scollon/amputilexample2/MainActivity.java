package com.scollon.amputilexample2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.scollon.amputil.Amp;

public class MainActivity extends AppCompatActivity {


    TextView tv_dB, tv_amp;
    private static final int PERMISSION_REQUEST_CODE = 200;
    final Runnable runnable = new Runnable(){

        public void run(){
            updateTv();
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_amp = findViewById(R.id.tv_amp);
        tv_dB = findViewById(R.id.tv_dB);



//asking user for permisson
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    PERMISSION_REQUEST_CODE);

        }


        Amp.startEverything(runnable, 100);


    }


    @Override
    public void onResume()
    {
        super.onResume();
        Amp.startRecorder();
    }
    @Override
    public void onPause()
    {
        super.onPause();
        Amp.stopRecorder();
    }
    public void updateTv(){

        Integer dBlvl = (int)Math.round(Amp.soundDb());
        tv_dB.setText(dBlvl.toString() + " dB");
        Integer ampLvl = (int)Math.round(Amp.getAmplitudeEMA());
        tv_amp.setText("AmplitudeEMA: " + ampLvl.toString());

    }



}