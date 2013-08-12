package com.qdapps.quard.model;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Vector3f;

public class Status {
	
	private Vector3f u; //up
	private Vector3f n; //North
	
	private Vector3f w; //rotate speed;
	private Vector3f v; //linear speed;
	
	private Vector3f pos; //glob position;

	public Map<String, Object> statusMap = new HashMap<>();
	
	public Vector3f getU() {
		return u;
	}

	public void setU(Vector3f u) {
		this.u = u;
	}

	public Vector3f getN() {
		return n;
	}

	public void setN(Vector3f n) {
		this.n = n;
	}

	public Vector3f getW() {
		return w;
	}

	public void setW(Vector3f w) {
		this.w = w;
	}

	public Vector3f getV() {
		return v;
	}

	public void setV(Vector3f v) {
		this.v = v;
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

}
