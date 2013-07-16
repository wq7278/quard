package com.qdapps.quard;

public abstract class Controller {
	abstract public void init();
	abstract public Command nextCommand(Status status, long time);
	abstract public Goal setGoad(Goal[] g);
	abstract public Goal [] getRootGoal();	
	
	private Goal [] rootGoal;
}
