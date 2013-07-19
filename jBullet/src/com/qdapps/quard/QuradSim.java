/*
 * Java port of Bullet (c) 2008 Martin Dvorak <jezek2@advel.cz>
 *
 * Bullet Continuous Collision Detection and Physics Library
 * Copyright (c) 2003-2008 Erwin Coumans  http://www.bulletphysics.com/
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from
 * the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose, 
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package com.qdapps.quard;

import static com.bulletphysics.demos.opengl.IGL.GL_COLOR_BUFFER_BIT;
import static com.bulletphysics.demos.opengl.IGL.GL_DEPTH_BUFFER_BIT;

import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import org.lwjgl.LWJGLException;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.demos.opengl.DemoApplication;
import com.bulletphysics.demos.opengl.GLDebugDrawer;
import com.bulletphysics.demos.opengl.IGL;
import com.bulletphysics.demos.opengl.LWJGL;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.InternalTickCallback;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DebugDrawModes;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;

/**
 * BasicDemo is good starting point for learning the code base and porting.
 * 
 * @author jezek2
 */
public class QuradSim extends DemoApplication {

	// create 125 (5x5x5) dynamic object
	private static final int ARRAY_SIZE_X = 5;
	private static final int ARRAY_SIZE_Y = 5;
	private static final int ARRAY_SIZE_Z = 5;

	// maximum number of objects (and allow user to shoot additional boxes)
	private static final int MAX_PROXIES = (ARRAY_SIZE_X * ARRAY_SIZE_Y
			* ARRAY_SIZE_Z + 1024);

	private static final int START_POS_X = -5;
	private static final int START_POS_Y = -5;
	private static final int START_POS_Z = -3;

	// keep the collision shapes, for deletion/cleanup
	private ObjectArrayList<CollisionShape> collisionShapes = new ObjectArrayList<CollisionShape>();
	private BroadphaseInterface broadphase;
	private CollisionDispatcher dispatcher;
	private ConstraintSolver solver;
	private DefaultCollisionConfiguration collisionConfiguration;

	public QuradSim(IGL gl) {
		super(gl);
		//this.debugMode=this.debugMode|DebugDrawModes.DRAW_WIREFRAME;
		
	}

	@Override
	public void clientMoveAndDisplay() {
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// simple dynamics world doesn't handle fixed-time-stepping
		float ms = getDeltaTimeMicroseconds();

		// step the simulation
		if (dynamicsWorld != null) {
			dynamicsWorld.stepSimulation(ms / 1000000f);
			// optional but useful: debug drawing
			dynamicsWorld.debugDrawWorld();
		}

		renderme();

		// glFlush();
		// glutSwapBuffers();
	}

	@Override
	public void displayCallback() {
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		renderme();

		// optional but useful: debug drawing to detect problems
		if (dynamicsWorld != null) {
			dynamicsWorld.debugDrawWorld();
		}

		// glFlush();
		// glutSwapBuffers();
	}

	public void initPhysics() {
		setCameraDistance(1.8f);

		// collision configuration contains default setup for memory, collision
		// setup
		collisionConfiguration = new DefaultCollisionConfiguration();

		// use the default collision dispatcher. For parallel processing you can
		// use a diffent dispatcher (see Extras/BulletMultiThreaded)
		dispatcher = new CollisionDispatcher(collisionConfiguration);

		broadphase = new DbvtBroadphase();

		// the default constraint solver. For parallel processing you can use a
		// different solver (see Extras/BulletMultiThreaded)
		SequentialImpulseConstraintSolver sol = new SequentialImpulseConstraintSolver();
		solver = sol;

		// TODO: needed for SimpleDynamicsWorld
		// sol.setSolverMode(sol.getSolverMode() &
		// ~SolverMode.SOLVER_CACHE_FRIENDLY.getMask());

		dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase,
				solver, collisionConfiguration);

		dynamicsWorld.setGravity(new Vector3f(0f, -10f, 0f));

		// create a few basic rigid bodies
		CollisionShape groundShape = new BoxShape(new Vector3f(5f, 5f, 5f));
		//CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 1, 0), 5);

		collisionShapes.add(groundShape);

		Transform groundTransform = new Transform();
		groundTransform.setIdentity();
		groundTransform.origin.set(0, -5.3f, 0);

		// We can also use DemoApplication::localCreateRigidBody, but for
		// clarity it is provided here:
		{
			float mass = 0f;

			// rigidbody is dynamic if and only if mass is non zero, otherwise
			// static
			boolean isDynamic = (mass != 0f);

			Vector3f localInertia = new Vector3f(0, 0, 0);
			if (isDynamic) {
				groundShape.calculateLocalInertia(mass, localInertia);
			}

			// using motionstate is recommended, it provides interpolation
			// capabilities, and only synchronizes 'active' objects
			DefaultMotionState myMotionState = new DefaultMotionState(
					groundTransform);
			RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(
					mass, myMotionState, groundShape, localInertia);
			RigidBody body = new RigidBody(rbInfo);

			// add the body to the dynamics world
			dynamicsWorld.addRigidBody(body);
		}

		// {
		// // create a few dynamic rigidbodies
		// // Re-using the same collision is better for memory usage and
		// performance
		//
		// CollisionShape colShape = new BoxShape(new Vector3f(1, 1, 1));
		// //CollisionShape colShape = new SphereShape(1f);
		// collisionShapes.add(colShape);
		//
		// // Create Dynamic Objects
		// Transform startTransform = new Transform();
		// startTransform.setIdentity();
		//
		// float mass = 1f;
		//
		// // rigidbody is dynamic if and only if mass is non zero, otherwise
		// static
		// boolean isDynamic = (mass != 0f);
		//
		// Vector3f localInertia = new Vector3f(0, 0, 0);
		// if (isDynamic) {
		// colShape.calculateLocalInertia(mass, localInertia);
		// }
		//
		// float start_x = START_POS_X - ARRAY_SIZE_X / 2;
		// float start_y = START_POS_Y;
		// float start_z = START_POS_Z - ARRAY_SIZE_Z / 2;
		//
		// for (int k = 0; k < ARRAY_SIZE_Y; k++) {
		// for (int i = 0; i < ARRAY_SIZE_X; i++) {
		// for (int j = 0; j < ARRAY_SIZE_Z; j++) {
		// startTransform.origin.set(
		// 2f * i + start_x,
		// 10f + 2f * k + start_y,
		// 2f * j + start_z);
		//
		// // using motionstate is recommended, it provides interpolation
		// capabilities, and only synchronizes 'active' objects
		// DefaultMotionState myMotionState = new
		// DefaultMotionState(startTransform);
		// RigidBodyConstructionInfo rbInfo = new
		// RigidBodyConstructionInfo(mass, myMotionState, colShape,
		// localInertia);
		// RigidBody body = new RigidBody(rbInfo);
		// body.setActivationState(RigidBody.ISLAND_SLEEPING);
		//
		// dynamicsWorld.addRigidBody(body);
		// body.setActivationState(RigidBody.ISLAND_SLEEPING);
		// }
		// }
		// }
		// }

		// create a few dynamic rigidbodies
		// Re-using the same collision is better for memory usage and
		// performance

		CompoundShape colShape = createTheQuardShape(); 

		//CollisionShape colShape = new BoxShape(new Vector3f(1, 1, 1));
		// CollisionShape colShape = new SphereShape(1f);
		collisionShapes.add(colShape);

		// Create Dynamic Objects
		Transform startTransform = new Transform();
		startTransform.setIdentity();

		float mass = 1f;

		// rigidbody is dynamic if and only if mass is non zero, otherwise
		// static
		boolean isDynamic = (mass != 0f);

		Vector3f localInertia = new Vector3f(0, 0, 0);
		if (isDynamic) {
			colShape.calculateLocalInertia(mass, localInertia);
		}

		// using motionstate is recommended, it provides interpolation
		// capabilities, and only synchronizes 'active' objects
		DefaultMotionState myMotionState = new DefaultMotionState(
				startTransform);
		RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(mass,myMotionState, colShape, localInertia);
		RigidBody body = new RigidBody(rbInfo);
		body.setActivationState(RigidBody.ISLAND_SLEEPING);

		dynamicsWorld.addRigidBody(body);
		body.setActivationState(RigidBody.ISLAND_SLEEPING);

		Map<String, Object> worldUserInfo = new Hashtable<String, Object>();
		worldUserInfo.put("Q",body);
		InternalTickCallback cb = new MyTickCallBack();
		dynamicsWorld.setInternalTickCallback(cb , worldUserInfo );
		clientResetScene();
	}

	private CompoundShape createTheQuardShape() {
		CompoundShape colShape =new CompoundShape();
		Transform lt = new Transform();
		lt.setIdentity();
		
		float armLength = .2f;
		float roterSize = 0.06f;
		float [][] transform = new float [][]{{1,0},{-1,0},{0,1},{0,-1}};
		for (int i = 0 ; i<4; i++){
			lt.origin.set(transform[i][0]* armLength, 0, transform[i][1]* armLength);
			colShape.addChildShape(lt, new BoxShape(new Vector3f(roterSize, roterSize, roterSize)));
		}
		
		lt.origin.set(0, 0, -0);
		colShape.addChildShape(lt, new BoxShape(new Vector3f(.1f, .02f, .1f)));
		
//		lt.origin.set(0, 0, -0);
//		colShape.addChildShape(lt, new BoxShape(new Vector3f(0.1f, 0.1f, 0.3f)));
		
		lt.origin.set(0, .03f, -0);
		colShape.addChildShape(lt, new BoxShape(new Vector3f(.05f, 0.01f, .05f)));
		return colShape;
	}

	public static void main(String[] args) throws LWJGLException {
		QuradSim ccdDemo = new QuradSim(LWJGL.getGL());
		ccdDemo.initPhysics();
		ccdDemo.getDynamicsWorld().setDebugDrawer(
				new GLDebugDrawer(LWJGL.getGL()));

		LWJGL.main(args, 1300, 800, "Bullet Physics Demo. http://bullet.sf.net",ccdDemo);
	}

	
	public void shootBox(Vector3f destination) {
		if (dynamicsWorld != null) {
			float mass = 0.01f;
			Transform startTransform = new Transform();
			startTransform.setIdentity();
			Vector3f camPos = new Vector3f(getCameraPosition());
			startTransform.origin.set(camPos);

			if (shootBoxShape == null) {
				//#define TEST_UNIFORM_SCALING_SHAPE 1
				//#ifdef TEST_UNIFORM_SCALING_SHAPE
				//btConvexShape* childShape = new btBoxShape(btVector3(1.f,1.f,1.f));
				//m_shootBoxShape = new btUniformScalingShape(childShape,0.5f);
				//#else
				shootBoxShape = new BoxShape(new Vector3f(.1f, .1f, .1f));
				//#endif//
			}

			RigidBody body = this.localCreateRigidBody(mass, startTransform, shootBoxShape);

			Vector3f linVel = new Vector3f(destination.x - camPos.x, destination.y - camPos.y, destination.z - camPos.z);
			linVel.normalize();
			linVel.scale(20);

			Transform worldTrans = body.getWorldTransform(new Transform());
			worldTrans.origin.set(camPos);
			worldTrans.setRotation(new Quat4f(0f, 0f, 0f, 1f));
			body.setWorldTransform(worldTrans);
			
			body.setLinearVelocity(linVel);
			body.setAngularVelocity(new Vector3f(0f, 0f, 0f));

			body.setCcdMotionThreshold(1f);
			body.setCcdSweptSphereRadius(0.2f);
		}
	}
}
