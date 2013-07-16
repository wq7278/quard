package com.qdapps.quard;

import java.util.List;

public class Goal {
	
	private List<Goal> subGoal; //If the goal is already sliced;
	
	private long startTime; 
	private long endTime;
	
	private Status targetStatus;
	
	private Goal parentGoal;
}
