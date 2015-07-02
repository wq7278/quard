package com.qdapps.quard.model.goal;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.qdapps.quard.model.Command;
import com.qdapps.quard.model.Goal;
import com.qdapps.quard.model.Status;

public class TakeOff extends Goal {
	
	private static Logger log = Logger.getLogger(TakeOff.class);
	
	private float hight ;
	private long duration;
	private float endSpeed;
	
	
	public TakeOff(float hight, long duration, float endSpeed){
		
		this.hight = hight;
		this.duration = duration;
		this.endSpeed = endSpeed;
		
		Status targetStatus = new Status();
		this.setTargetStatus(targetStatus );
		this.setStartTime(System.currentTimeMillis());
		this.setEndTime(this.getStartTime()+duration);
		
	}
	
	public boolean tooFar(Status s){
		
	
//		if (System.currentTimeMillis() > this.getEndTime()){
//			return true;
//		}
		return false;
	
	}
	
	
	public boolean acchived (Status s){
		//if the time is up. consider it done; this is for the motors that don't have a way to find out the status;
		boolean achived = false;
		if (System.currentTimeMillis() > this.getEndTime()){
			achived = true;
		}
		//test the status table if there is a way of finding out the motors status;
		else if (  "READY".equals( s.statusMap.get("Motor2") )
				&&  "READY".equals( s.statusMap.get("Motor1")) 
				&& "READY".equals( s.statusMap.get("Motor3"))
				&& "READY".equals( s.statusMap.get("Motor4"))
			){
			achived = true;
		}else {
			achived = false;
		}
		
		if (achived){
			log.info("Goad is achived: " + this.getClass().getName());
		}
		return achived;
		
	}

	@Override
	public LinkedList<Goal> slice(Status s) {
		// if the g is Startup; check status to see if it is started up. no slic needed; 
		//just return the goal as self;
		LinkedList<Goal> gs = new LinkedList<>();
		gs.add(this);
		//set the parent;
		this.setParentGoal(this);
		return gs;
		
	}

	boolean alreadySendStartUp = false;
	@Override
	public Command gnerateCommand(Status status) {
		Command cmd = null;
		
		if (!alreadySendStartUp ){
			cmd = sendStartUpToMachine();
			alreadySendStartUp = true;
		}else {
			cmd = null;
		}
		return cmd;
	}

	private Command sendStartUpToMachine() {
		Command cmd = new Command ();
		cmd.cmd = new String[]{
			"Start_Motors"
		};
		return cmd;
	}
	
	
}
