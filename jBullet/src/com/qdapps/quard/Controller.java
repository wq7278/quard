package com.qdapps.quard;

import java.util.LinkedList;
import java.util.List;

public abstract class Controller {
	abstract public void init();
	abstract public Command nextCommand(Status status, long time);
	abstract public void setGoad(Goal[] g);
	abstract public Goal [] getRootGoal();	
	
	private LinkedList<Goal> maingoal = new LinkedList<>();
	private LinkedList<Goal> goalList = new LinkedList<>();
	private Slicer slicer;
	
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
