# AmpUtil-Androidv2
It's a library to simplify getting sound levels from phones microphone
you'll be able to get dB levels(gain) Amplitude and AmplitudeEMA(exponential moving average)

# Gradle
to use this library you must first add this to your root build.gradle
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
and this dependency to your build gradle
```
dependencies {
	        implementation 'com.github.TalekC-137:AmpUtil-Androidv2:1.0.0'
	}
```

# Setting up
to get sound from microphone youu need to add this to your manifest.xml
```
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```
then you'll have to ask user for permission for using microphone or if you're just doing it for yourself first install the app on a device and in setting grant microphone access

## MainActivity settup
in the activity of your chosing add this in your class above onCreate to make it global
#### JAVA
```
 final Runnable runnable = new Runnable(){

        public void run(){
            updateTv();
        };
    };
```
#### KOTLIN
```
val runnable = Runnable {
        updateTv()
    }
 ```
"updateTv()" is the function in which later you'll be setting up your textView and such (it's refresh rate will be explained later)

### Overrides
the next necessities are the overrides
#### JAVA
```
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
 ```
 #### KOTLIN
 ```
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
 ```
 
## updateTv()
the function in which the magic is happening
example of setting a TextView with dB gain level and AmplitudeEMA

#### JAVA
```
 public void updateTv(){

        Integer dBlvl = (int)Math.round(Amp.soundDb());
        tv_dB.setText(dBlvl.toString() + " dB");
        Integer ampLvl = (int)Math.round(Amp.getAmplitudeEMA());
        tv_amp.setText("AmplitudeEMA: " + ampLvl.toString());

    }
 ```
 #### KOTLIN
 ```
fun updateTv(){
   val dBlvl = Amp.soundDb().toInt()
    tv_dB.text = dBlvl.toString() + " dB"

    val ampLvl = Amp.getAmplitudeEMA().toInt()

    tv_amp.text = "AmplitudeEMA: " + ampLvl.toString()

}
```
# START IT 
to start the whole process call this function in your onCreate 
#### JAVA/KOTLIN
```
Amp.startEverything(runnable, 100);
```
runnable we created earlier and the "100" in this example is the refresh rate in milliseconds 
so given this example app the TextView will update every 100ms giving us the current dB gain and AmplitudeEMA

### ALL FUNCTIONS
#### Amp.soundDb()
returns the dB level
#### Amp.getAmplitude()
returns current max Amplitude not filtered
#### Amp.getAmplitudeEMA()
returns a clearer data by calculating the exponential moving average amplitude


or just check the example apps for more info
