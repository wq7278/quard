package com.qdapps.quard.model.controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.qdapps.quard.model.Command;
import com.qdapps.quard.model.Goal;
import com.qdapps.quard.model.Status;

public abstract class Controller {
	abstract public void init();
	abstract public void shutdown();
	abstract public Command nextCommand(Status status, long time);
	//abstract public void setGoad(Goal[] g);
	//abstract public Goal [] getRootGoal();	
	
	private List<Goal> maingoal = Collections.synchronizedList(new LinkedList<Goal>());
	private List<Goal> goalList = Collections.synchronizedList(new LinkedList<Goal>());
	//private Slicer slicer; //no need for slicer anymore, the goal slic it self;
	
	
	/**the goal is being working on;
	 * @return
	 */
	public Goal getCurrentGoal() {
		if (this.getGoalList().size()>0)
		{
			Goal currentGoal = this.getGoalList().get(0);
		 	return currentGoal;
		}else return null;
	}
	
	/**
	 * find out what the next goal should be;
	 * @return
	 */
	public Goal getNextGoal() {
		Goal g = this.getGoalList().remove(0);
		return g;
	}
	
//	public Slicer getSlicer() {
//		return slicer;
//	}
//	public void setSlicer(Slicer slicer) {
//		this.slicer = slicer;
//	}
	public List<Goal> getMaingoal() {
		return maingoal;
	}
//	public void setMaingoal(List<Goal> maingoal) {
//		this.maingoal = maingoal;
//	}
	public List<Goal> getGoalList() {
		return goalList;
	}
//	public void setGoalList(List<Goal> goalList) {
//		this.goalList = goalList;
//	}
	private boolean shutdown = false;
	public boolean getShutDown() {
		// TODO Auto-generated method stub
		return shutdown;
	}
	

	
	
}
