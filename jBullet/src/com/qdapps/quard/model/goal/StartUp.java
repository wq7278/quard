package com.qdapps.quard.model.goal;

import java.util.LinkedList;

import com.qdapps.quard.model.Command;
import com.qdapps.quard.model.Goal;
import com.qdapps.quard.model.Status;

public class StartUp extends Goal {
	
	public StartUp(long waitTime){
		Status targetStatus = new Status();
		this.setTargetStatus(targetStatus );
		this.setStartTime(System.currentTimeMillis());
		this.setEndTime(this.getStartTime()+waitTime);
		
		//put the target in status Map;
		//All motors need to be "StandBy"
		targetStatus.statusMap.put("Motor1", "READY");
		targetStatus.statusMap.put("Motor2", "READY");
		targetStatus.statusMap.put("Motor3", "READY");
		targetStatus.statusMap.put("Motor4", "READY");
	}
	
	public boolean tooFar(Status s){
	
		if (System.currentTimeMillis() > this.getEndTime()){
			return true;
		}
		return false;
	
	}
	
	
	public boolean acchived (Status s){
		if (  "READY".equals( s.statusMap.get("Motor2") )
				&&  "READY".equals( s.statusMap.get("Motor1")) 
				&& "READY".equals( s.statusMap.get("Motor3"))
				&& "READY".equals( s.statusMap.get("Motor4"))
			){
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public LinkedList<Goal> slice(Status s) {
		// if the g is Startup; check status to see if it is started up. no slic needed; 
		//just return the goal as self;
		LinkedList<Goal> gs = new LinkedList<>();
		gs.add(this);
		return gs;
		
	}

	boolean alreadySendStartUp = false;
	@Override
	public Command gnerateCommand(Status status) {
		Command cmd = null;
		
		if (!alreadySendStartUp ){
			cmd = sendStartUpToMachine();
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
