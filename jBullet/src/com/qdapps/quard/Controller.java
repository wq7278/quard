package com.qdapps.quard;

public abstract class Controller {
	abstract public void init();
	abstract public Command nextCommand(Status status, long time);
	abstract public void setGoad(Goal[] g);
	abstract public Goal [] getRootGoal();	
	
	protected Goal [] goal;
	private Goal currentGoal;
	
	public Goal getCurrentGoal() {
		return currentGoal;
	}
	public void setCurrentGoal(Goal currentGoal) {
		this.currentGoal = currentGoal;
	}
}
