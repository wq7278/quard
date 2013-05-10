package com.example.test;

import java.io.File;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.util.FloatMath;
import android.view.MenuItem;
import android.widget.TextView;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class DisplayMessageActivity extends Activity implements SensorEventListener {

	TextView textView = null;
	ScheduledThreadPoolExecutor stpe = null;
	SensorManager sm = null;
	Logger log = null;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		//confg the log;
		LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(Environment.getExternalStorageDirectory()
                        + File.separator + "MyApp" + File.separator + "logs"
                        + File.separator + "TestGyro.txt");
        logConfigurator.setRootLevel(Level.DEBUG);
        logConfigurator.setLevel("org.apache", Level.ERROR);
        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
        logConfigurator.setMaxFileSize(1024 * 1024 * 15);
        // logConfigurator.set
        logConfigurator.setImmediateFlush(false);
        logConfigurator.setUseLogCatAppender(false);
        logConfigurator.configure();
        
        log = Logger.getLogger(DisplayMessageActivity.class);
        log.info("My Application Created");

        
		
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		List<Sensor> typedSensors = sm.getSensorList(Sensor.TYPE_ALL); 
		System.out.println("List censor: ");
		for (Sensor s: typedSensors ) {
			System.out.println(s.getType() +" | " + s.getVendor()+ " | "+s.getVersion());
			if (Sensor.TYPE_ACCELEROMETER == s.getType() ||Sensor.TYPE_GYROSCOPE == s.getType()){
				sm.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST); // Rates: SENSOR_DELAY_FASTEST, SENSOR_DELAY_GAME, // SENSOR_DELAY_NORMAL, SENSOR_DELAY_UI
			}
		}
		
		

	
		// Get the message from the intent
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

		// Create the text view
		textView = new TextView(this);
		textView.setTextSize(12);
		textView.setText("ss" + "s" + "\nsdfgsfgsdfz\n");

		// Set the text view as the activity layout
		setContentView(textView);

		stpe = new ScheduledThreadPoolExecutor(3);
		
		//stpe.scheduleWithFixedDelay(this.new RunnableThread(this), 100, 100, TimeUnit.MILLISECONDS);
	}
	
	class RunnableThread implements Runnable {
       
		private Activity activity;
		public RunnableThread(Activity activity){
			this.activity = activity;
		}
        int cnt = 0;
        @Override
        public void run() {
            cnt ++;
//           
            activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					textView.setText("\n" + cnt + ": " + System.currentTimeMillis()	+ textView.getText());
					if (textView.getText().length()>1000){
						textView.setTag(textView.getText().subSequence(0, 1000));
					}
				}
			});
            
           // System.out.println(cnt);
        }
    }
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onDestroy(){//onDestroy
		super.onDestroy();
		System.out.println("Destroy is called.");
		if (stpe != null){
			stpe.shutdown();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}

	
	private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;
    float[] deltaRotationMatrix = new float[9];
    float axisXa = 0;
    float axisYa = 0;
    float axisZa = 0;
    long ct =0;
    @Override
    public void onSensorChanged(SensorEvent event) {
    	float axisXg = 0;
        float axisYg = 0;
        float axisZg = 0;
        
        ct ++;
        if (Sensor.TYPE_GYROSCOPE == event.sensor.getType()){  
    	
    	// This timestep's delta rotation to be multiplied by the current rotation
         // after computing it from the gyro sample data.
         if (timestamp != 0) {
             final float dT = (event.timestamp - timestamp) * NS2S;
             // Axis of the rotation sample, not normalized yet.
             float axisX = event.values[0];
             float axisY = event.values[1];
             float axisZ = event.values[2];

             // Calculate the angular speed of the sample
             float omegaMagnitude = FloatMath.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);
             if (omegaMagnitude > 0.03 ) {
            	 axisXg = axisX;
            	 axisYg = axisY;
            	 axisZg = axisZ;
             }
             // Normalize the rotation vector if it's big enough to get the axis
            
            // }
         }
         timestamp = event.timestamp;
         //SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
         // User code should concatenate the delta rotation we computed with the current rotation
         // in order to get the updated rotation.
         // rotationCurrent = rotationCurrent * deltaRotationMatrix;
     
        }else if (Sensor.TYPE_ACCELEROMETER == event.sensor.getType()){
        	 axisXa = event.values[0];
             axisYa = event.values[1];
             axisZa = event.values[2];
        }
        if (ct%100 == 0)textView.setText(ct+"");
        
//       textView.setText("G: \n" + axisXg + ": " + axisYg + ": " + axisZg + "\n"  + 
//       			 		  "A: \n" + axisXa + ": " + axisYa + ": " + axisZa + "\n");
        log.debug("G:" + axisXg + ": " + axisYg + ": " + axisZg + "" + "A:" + axisXa + ": " + axisYa + ": " + axisZa + "");
       	// if (textView.getText().length()>1000){
       	//	 textView.setTag(textView.getText().subSequence(0, 1000));
       }
    

}
