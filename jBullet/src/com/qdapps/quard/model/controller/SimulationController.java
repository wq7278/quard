package com.qdapps.quard.model.controller;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.qdapps.quard.model.Command;
import com.qdapps.quard.model.Goal;
import com.qdapps.quard.model.Status;
import com.qdapps.quard.model.goal.StartUp;
import com.qdapps.quard.model.slicer.Slicer;
import com.qdapps.quard.model.slicer.SlicerImpl;

/**
 * This is run in the sofware simulated evn;
 * @author qwang
 *
 */
public class SimulationController extends Controller {

	private Logger log = Logger.getLogger(SimulationController.class);
	@Override
	public void init() {
		//Check every thing;
		//Get current status;
		Status current = new Status();
		
		//set a current goal; start all motors in 15 sec;
		Goal g = new StartUp(5000);
		//this.getMaingoal().add(g); //don't add this, the firs main goal should be pop out after it is sliced;
		LinkedList<Goal> goals = g.slice (current);
		this.getGoalList().addAll(goals);
		
	}

	@Override
	public Command nextCommand(Status status, long time) {
		Command cmd = null;
		Goal currentGoal = this.getCurrentGoal();
		
		//If there is no goal comming back; don't do anything;
		if (currentGoal == null ) return null;
		
		Status expectedStatus = currentGoal.getTargetStatus();
		// is the status to far? abandon; reslice the parent goal;
		if (currentGoal.tooFar(status)){
			// this is where the goal should be abandoned. after that, see the main goal list, try next thing. 
			// abandon, re-adjust;
		} 
		boolean achieved = currentGoal.acchived(status);
		
		long currentTime = System.currentTimeMillis(); 
		if (achieved || currentTime > currentGoal.getEndTime()){
			//need a new goal as current Goal;
			//firt pop un the current goal;
			getNextGoal();
			Goal nextGoal = getCurrentGoal();
			if (nextGoal == null){
				//find next goal in goal list; if null, look at the parent list to find the next goal to achieve;
				Goal next = this.getMaingoal().poll();
				if (next == null){
					//If at the end of goal list; just use this current goal; 
					//If this happened, go to a waiting stage;
					log.error("Can not find the next main goal..");
					next = currentGoal.getParentGoal();
				}
				next.setStartTime(currentTime);
				LinkedList<Goal> goalList = next.slice(status);
				this.getGoalList().addAll(goalList);
				nextGoal = getNextGoal();
			}
			currentGoal = nextGoal;
		}
		
		//Generate the command should be run;
		cmd = currentGoal.gnerateCommand(status);
		return cmd;
	}

}
