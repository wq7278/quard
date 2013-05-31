package com.qdapps.quard;

import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.InternalTickCallback;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;

public class MyTickCallBack extends InternalTickCallback {

	@Override
	public void internalTick(DynamicsWorld world, float timeStep) {
		//System.out.println("Call: " + timeStep);
		Map<String, Object> m = (Map<String, Object>)world.getWorldUserInfo();
		RigidBody quard = (RigidBody) m.get("Q");
		quard.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
		
		Vector3f agVelocity = new Vector3f();
		quard.getAngularVelocity(agVelocity);
		System.out.println("getAngularVelocity: " + agVelocity.x +" | " + agVelocity.y +" | " + agVelocity.z);
		
		Vector3f velocity = new Vector3f();
		quard.getVelocityInLocalPoint(new Vector3f(), velocity);
		
		Transform trans = new Transform();
		quard.getCenterOfMassTransform(trans);
		
//		Quat4f rotation = new Quat4f();
//		trans.getRotation(rotation );
		Vector3f force = new Vector3f(0, 10.01f, 0);
		
//		rotation.
		
		trans.transform(force);
		
		Vector3f centerOfmass = new Vector3f();
		quard.getCenterOfMassPosition(centerOfmass );
		
		force.sub(centerOfmass);
		float [] scale = new float[4];
		
		//caculate the scale: 
		calculate (scale, quard, timeStep); 
		
		Vector3f []  f = new Vector3f[4];
		for (int i = 0; i < f.length; i++) {
			f[i]= (Vector3f)force.clone();
			f[i].scale(scale[i]);
		}
		 
		//f[3].scale(1.1f);
		
		System.out.println("force: " + force.x +" | " + force.y +" | " + force.z);
		System.out.println("getAngularVelocity: " + agVelocity.x +" | " + agVelocity.y +" | " + agVelocity.z);
		System.out.println("getVelocity: " + velocity.x +" | " + velocity.y +" | " + velocity.z);
		
		float [][] transform = new float [][]{{1,0},{-1,0},{0,1},{0,-1}};
		float armLength = .2f;
		for (int i = 0 ; i<4; i++){
			quard.applyForce(f[i], new Vector3f(transform[i][0]* armLength, 0, transform[i][1]* armLength) );
		}
		
		
		
	}

	private void calculate(float[] scale, RigidBody quard, float timeStep) {
		//find out the position of the quard, the lower the motor, the biger the power it should send;
		Transform trans = new Transform();
		quard.getCenterOfMassTransform(trans);
		
//		Quat4f rotation = new Quat4f();
//		trans.getRotation(rotation );
		Vector3f [] motors = new Vector3f [4];
		float [][] transform = new float [][]{{1,0},{-1,0},{0,1},{0,-1}};
		
		float armLength = .2f;
		
		for (int i = 0 ; i<4; i++){
			motors[i] = new Vector3f(transform[i][0]* armLength, 0, transform[i][1]* armLength) ;
		}
		Map<Float, Integer> mtMap = new Hashtable<>();
		
		float max = -999999999f;
		for (int i = 0; i < motors.length; i++) {
			Vector3f motor = motors[i];
			trans.transform(motor);
			//now motor has a global position;Int
			
			if (motor.y>max){
				max = motor.y;
			}
		}
		float total = 0;
		float adjFactor = 0.1f;
		for (int i = 0; i < motors.length; i++) {
			total += (max - motors[i].y  + adjFactor);
		}
		
		for (int i = 0; i < motors.length; i++) {
			scale[i] = (max - motors[i].y  + adjFactor)/total;
		}
		//return scale;
	}

}
