/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.test;

import java.io.File;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.util.FloatMath;
import android.view.MotionEvent;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class DisplayMessageActivity extends Activity implements SensorEventListener {

    private GLSurfaceView mGLView;
    private static Logger log = null;
    
    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;
    float [] deltaRotationMatrix = new float[9];
    
    float [] axisA = new float [3];
    float [] axisG = null;//new float [3];
    
    long ct =0;
    float [] oldPos  = new float [3];
    float [] newPos = new float [3];
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //confg the log;
      		LogConfigurator logConfigurator = new LogConfigurator();
              logConfigurator.setFileName(Environment.getExternalStorageDirectory()
                              + File.separator + "MyApp" + File.separator + "logs"
                              + File.separator + "TestGyro.txt");
              logConfigurator.setRootLevel(Level.INFO);
              logConfigurator.setLevel("org.apache", Level.ERROR);
             // logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
              logConfigurator.setFilePattern("%d %-5p  %m%n");
              logConfigurator.setMaxFileSize(1024 * 1024 * 5);
              // logConfigurator.set
              logConfigurator.setImmediateFlush(false);
              logConfigurator.setUseLogCatAppender(false);
              logConfigurator.configure();
              
              log = Logger.getLogger(DisplayMessageActivity.class);
              log.info("My Application Created");
        
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> typedSensors = sm.getSensorList(Sensor.TYPE_ALL); 
		System.out.println("List censor: ");
		for (Sensor s: typedSensors ) {
			System.out.println(s.getType() +" | " + s.getVendor()+ " | "+s.getVersion());
			if (Sensor.TYPE_ACCELEROMETER == s.getType() ||Sensor.TYPE_GYROSCOPE == s.getType()){
				sm.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST); // Rates: SENSOR_DELAY_FASTEST, SENSOR_DELAY_GAME, // SENSOR_DELAY_NORMAL, SENSOR_DELAY_UI
			}
		}
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        mGLView = new MyGLSurfaceView(this, newPos);
        
        setContentView(mGLView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
    public void onSensorChanged(SensorEvent event) {
    	
		axisG = new float [3];
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
            	 axisG[0] = axisX;
            	 axisG[1] = axisY;
            	 axisG[2] = axisZ;
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
        	 axisA[0] = event.values[0];
             axisA[1] = event.values[1];
             axisA[2] = event.values[2];
        }
        //if (ct%100 == 0)textView.setText(ct+"");
        
//       textView.setText("G: \n" + axisXg + ": " + axisYg + ": " + axisZg + "\n"  + 
//       			 		  "A: \n" + axisXa + ": " + axisYa + ": " + axisZa + "\n");
       // log.debug("G:" + axisG[0] + ": " + axisG[1] + ": " + axisG[2] + "" + "A:" + axisA[0] + ": " + axisA[1] + ": " + axisA[2] + "");
       	// if (textView.getText().length()>1000){
       	//	 textView.setTag(textView.getText().subSequence(0, 1000));
        
        //back up the old position vector;
        {
	        oldPos[0] = newPos[0];
	        oldPos[1] = newPos[1];
	        oldPos[2] = newPos[2];
        }
        
        //assign the new position
        {
        	fuse (newPos, oldPos, axisG, axisA);
        }
        
       }

	private void fuse(float[] newPos, float[] oldPos, float[] axisG,	float[] axisA) {
		//Accelo only;
		newPos[0] = axisA[0];
		newPos[1] = axisA[1];
		newPos[2] = axisA[2];
		
	}
}

class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context, float [] pos) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        mRenderer.pos = pos;
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                  dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                  dy = dy * -1 ;
                }

                mRenderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR;  // = 180.0f / 320
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
    
    
    
}
