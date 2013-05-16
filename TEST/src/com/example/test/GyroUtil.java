package com.example.test;

import android.hardware.SensorManager;

public class GyroUtil {

	private static final float EPSILON = 0.01f;

	public static void getRotationMatrix(float[] gyroOrg, float dT, float [] result) {
		float[] deltaRotationVector = new float[4];
		// This timestep's delta rotation to be multiplied by the current
		// rotation
		// after computing it from the gyro sample data.

		// Axis of the rotation sample, not normalized yet.
		float axisX = gyroOrg[0];
		float axisY = gyroOrg[1];
		float axisZ = gyroOrg[2];

		// Calculate the angular speed of the sample
		float omegaMagnitude = (float) Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ);

		// Normalize the rotation vector if it's big enough to get the axis
		// (that is, EPSILON should represent your maximum allowable margin of
		// error)
		if (omegaMagnitude > EPSILON) {
			axisX /= omegaMagnitude;
			axisY /= omegaMagnitude;
			axisZ /= omegaMagnitude;
		}

		// Integrate around this axis with the angular speed by the timestep
		// in order to get a delta rotation from this sample over the timestep
		// We will convert this axis-angle representation of the delta rotation
		// into a quaternion before turning it into the rotation matrix.
		float thetaOverTwo = omegaMagnitude * dT / 2.0f;
		float sinThetaOverTwo = (float)Math.sin(thetaOverTwo);
		float cosThetaOverTwo = (float)Math.cos(thetaOverTwo);
		deltaRotationVector[0] = sinThetaOverTwo * axisX;
		deltaRotationVector[1] = sinThetaOverTwo * axisY;
		deltaRotationVector[2] = sinThetaOverTwo * axisZ;
		deltaRotationVector[3] = cosThetaOverTwo;

		float[] deltaRotationMatrix = result;
		SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
		
		// User code should concatenate the delta rotation we computed with the
		// current rotation
		// in order to get the updated rotation.
		// rotationCurrent = rotationCurrent * deltaRotationMatrix;

	}

}
