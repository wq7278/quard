package com.qdapps.quard.model.controller;

import java.util.LinkedList;
import java.util.List;

import com.qdapps.quard.model.Command;
import com.qdapps.quard.model.Goal;
import com.qdapps.quard.model.Status;
import com.qdapps.quard.model.slicer.Slicer;

public abstract class Controller {
	abstract public void init();
	abstract public Command nextCommand(Status status, long time);
	abstract public void setGoad(Goal[] g);
	abstract public Goal [] getRootGoal();	
	
	private LinkedList<Goal> maingoal = new LinkedList<>();
	private LinkedList<Goal> goalList = new LinkedList<>();
	private Slicer slicer;
	
	
	/**the goal is being working on;
	 * @return
	 */
	public Goal getCurrentGoal() {
		Goal currentGoal = this.getGoalList().peek();
		return currentGoal;
	}
	
	/**
	 * find out what the next goal should be;
	 * @return
	 */
	public Goal getNextGoal() {
		Goal g = this.getGoalList().poll();
		return g;
	}
	
	public Slicer getSlicer() {
		return slicer;
	}
	public void setSlicer(Slicer slicer) {
		this.slicer = slicer;
	}
	public LinkedList<Goal> getMaingoal() {
		return maingoal;
	}
//	public void setMaingoal(List<Goal> maingoal) {
//		this.maingoal = maingoal;
//	}
	public LinkedList<Goal> getGoalList() {
		return goalList;
	}
//	public void setGoalList(List<Goal> goalList) {
//		this.goalList = goalList;
//	}
	

	
	
}
