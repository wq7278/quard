package com.qdapps.quard.model.controller;

import java.util.LinkedList;

import com.qdapps.quard.model.Command;
import com.qdapps.quard.model.Goal;
import com.qdapps.quard.model.Status;
import com.qdapps.quard.model.slicer.Slicer;

/**
 * This is run in the sofware simulated evn;
 * @author qwang
 *
 */
public class SimulationController extends Controller {

	@Override
	public void init() {
		//Check every thing;
		
		//Get current status;
		Status current = new Status();
		
		//set a current goal;
		Goal g = new Goal();
		this.getMaingoal().add(g);
		
		//slic the goal to subGoals;
		
		Slicer slicer = null;
		this.setSlicer(slicer );
		
		LinkedList<Goal> goals = slicer.slice (current, g);
		this.getGoalList().addAll(goals);
		
	}

	@Override
	public Command nextCommand(Status status, long time) {
		Command cmd = null;
		Goal currentGoal = this.getNextGoal();
		Status expectedStatus = currentGoal.getTargetStatus();
		// is the status to far? abandon; reslice the parent goal;
		if (tooFar(status, expectedStatus)){
			
		}else{
			Goal nextGoal = getNextGoal();
			if (nextGoal == null){
				//find next goal in goal list;
				Goal next = this.getMaingoal().poll();
				if (next == null){
					//If at the end of goal list; just use this current goal;
					next = currentGoal.getParentGoal();
					
				}
				
				LinkedList<Goal> goalList = getSlicer().slice(status, nextGoal);
				
				this.getGoalList().addAll(goalList);
				nextGoal = getNextGoal();
			}
			cmd = gnerateCommand(status, nextGoal);
		}
		return cmd;
	}

	/**
	 * find out what the next goal should be;
	 * @return
	 */
	private Goal getNextGoal() {
		Goal g = this.getGoalList().poll();
		return g;
	}

	/**
	 * what is the difference between this goal and next? get a command;
	 * @param status
	 * @param nextGoal
	 * @return
	 */
	private Command gnerateCommand(Status status, Goal nextGoal) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * too far from goal, something is wrong, need to adjust;
	 * @param status
	 * @param expectedStatus
	 * @return
	 */
	private boolean tooFar(Status status, Status expectedStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGoad(Goal[] g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Goal[] getRootGoal() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
