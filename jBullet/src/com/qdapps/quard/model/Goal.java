package com.qdapps.quard.model;

import java.util.LinkedList;
import java.util.List;

import com.qdapps.quard.model.slicer.Slicer;


public abstract class Goal {
	
	private List<Goal> subGoal; //If the goal is already sliced;
	
	private long startTime; 
	private long endTime;
	
	private Status targetStatus;
	
	private Goal parentGoal;
	
	private String command; 

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

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
	
	/**
	 * Test if the status is too far from the goal; if yes, usually this get abandoned;
	 * @param s
	 * @return
	 */
	public abstract boolean tooFar(Status s);
	
	
	/**
	 * if this goal is done;
	 * @param s
	 * @return
	 */
	public abstract boolean acchived (Status s);

	
	/**self slice;
	 * @param s
	 * @return
	 */
	public abstract LinkedList<Goal> slice(Status s) ;

	/**
	 * generate the command try to achive the next goal;
	 * @param status
	 * @return
	 */
	public abstract Command gnerateCommand(Status status);

}
