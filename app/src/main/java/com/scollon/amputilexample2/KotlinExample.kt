package com.scollon.amputilexample2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.scollon.amputil.Amp

class KotlinExample: AppCompatActivity()  {


    val runnable = Runnable {
        updateTv()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // default refresh rate is 100ms aka you'll get an update every 100millis
        Amp.startEverything(runnable, 100)

    }
    @Override
    override fun onResume(){
        super.onResume()
        Amp.startRecorder()

    }
    @Override
    override fun onPause(){
        super.onPause()
        Amp.stopRecorder()
    }
    fun updateTv(){
        val dBlvl = Amp.soundDb().toInt()

        //tv_dB.text = dBlvl.toString() + " dB"
//dont comment it like i did, you won't have to ], i do
        val ampLvl = Amp.getAmplitudeEMA().toInt()



        //dont comment it like i did, you won't have to ], i do
      // tv_amp.text = "AmplitudeEMA: " + ampLvl.toString()

    }



}