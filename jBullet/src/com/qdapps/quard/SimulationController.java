package com.qdapps.quard;

import java.util.LinkedList;

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
		Slicer s = null;
		
		
		LinkedList<Goal> goals = s.slice (current, g);
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
			cmd = gnerateCommand(status, nextGoal);
		}
		return cmd;
	}

	/**
	 * find out what the next goal should be, if there is nothing, hovering;
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
