package com.example.test;

public class VecUtil {
	public static float dot(float[] v1, float[] v2) {
		float res = 0;
		for (int i = 0; i < v1.length; i++)
			res += v1[i] * v2[i];
		return res;
	}
	
	public static float [] cloneFloatArray(float[] v) {
		if (v == null) return null;
		float [] res = new float [v.length];
		for (int i = 0; i < v.length; i++)
			res[i] = v[i];
		return res;
	}
	
	public static void copyFloatArray(float[] from, float [] to) {
		if (from == null) return;
		for (int i = 0; i < to.length; i++)
			to[i] = from[i];
		
	}
	
	
	public static void add(float[] v1, float[] v2) {
		
		for (int i = 0; i < v1.length; i++)
			v1[i] += v2[i];
		
	}

	
	public static float magnitudeSq(float[] vector) {
		return (float) (vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);
	}

	public static float magnitude(float[] vector) {
		return (float) Math.sqrt(magnitudeSq(vector));
	}
	
	public static void mul(float fct, float[] vector) {
		vector[0] = fct * vector[0];
		vector[1] = fct * vector[1];
		vector[2] = fct * vector[2];
	}

	public static void cross(float[] u, float[] v, float[] result) {
		result[0] = u[1] * v[2] - v[1] * u[2];
		result[1] = u[2] * v[0] - v[2] * u[0];
		result[2] = u[0] * v[1] - v[0] * u[1];
	}

	public static void normalize(float[] _vector) {
		float magnitude;
		magnitude = (float) (Math.sqrt(_vector[0] * _vector[0] + _vector[1] * _vector[1] + _vector[2] * _vector[2]));
		_vector[0] = _vector[0] / magnitude;
		_vector[1] = _vector[1] / magnitude;
		_vector[2] = _vector[2] / magnitude;

	}

}
