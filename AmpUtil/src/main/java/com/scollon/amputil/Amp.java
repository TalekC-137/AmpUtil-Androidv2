package com.scollon.amputil;

import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

public class Amp {
    private static  MediaRecorder mRecorder;
    private static Thread runner;
    private static final Handler mHandler = new Handler();
    private static double mEMA = 0.0;
    static final private double EMA_FILTER = 0.6;


    public static final void startEverything(Runnable runnable,int refRate){

        if (runner == null)
        {
            runner = new Thread(){
                public void run()
                {
                    while (runner != null)
                    {
                        try
                        {
                            Thread.sleep(refRate);
                            Log.i("Noise", "Tock");
                        } catch (InterruptedException e) { };
                        mHandler.post(runnable);
                    }
                }
            };
            runner.start();
            Log.d("Noise", "start runner()");
        }
    }

    public static final void startRecorder(){
        if (mRecorder == null)
        {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            try
            {
                mRecorder.prepare();
            }catch (java.io.IOException ioe) {
                android.util.Log.e("[startErr]", "IOException: " + android.util.Log.getStackTraceString(ioe));

            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[startErr]", "SecurityException: " + android.util.Log.getStackTraceString(e));
            }
            try
            {
                mRecorder.start();
            }catch (java.lang.SecurityException e) {
                android.util.Log.e("[startErr]", "SecurityException: " + android.util.Log.getStackTraceString(e));
            }

        }

    }
    public static final void stopRecorder() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    /*
    you dont need a comment here, the name speaks for itself
    to get the perfect dB you'd need to calculate the rms input voltage
    btu every device is different so you'd also have to do a calibration sequence before the first run
    but by using 0.7 the calculations are close enough
     */
    public static final Double soundDb(){
        return  20 * Math.log10(getAmplitudeEMA() / 0.7);
    }

    //raw amplitude
    public static final int getAmplitude() {
        if (mRecorder != null)
            return  (mRecorder.getMaxAmplitude());
        else
            return 0;

    }
    //exponential moving average amplitude
    //outputs a clearer data
    public static double getAmplitudeEMA() {
        double amp =  getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }

}
