package com.qdapps.quard.model;

import java.util.List;


public class Goal {
	
	private List<Goal> subGoal; //If the goal is already sliced;
	
	private long startTime; 
	private long endTime;
	
	private Status targetStatus;
	
	private Goal parentGoal;

	public List<Goal> getSubGoal() {
		return subGoal;
	}

	public void setSubGoal(List<Goal> subGoal) {
		this.subGoal = subGoal;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Status getTargetStatus() {
		return targetStatus;
	}

	public void setTargetStatus(Status targetStatus) {
		this.targetStatus = targetStatus;
	}

	public Goal getParentGoal() {
		return parentGoal;
	}

	public void setParentGoal(Goal parentGoal) {
		this.parentGoal = parentGoal;
	}
}
