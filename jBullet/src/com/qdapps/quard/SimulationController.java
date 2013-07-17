package com.qdapps.quard;

/**
 * This is run in the sofware simulated evn;
 * @author qwang
 *
 */
public class SimulationController extends Controller {

	@Override
	public void init() {
		//Check every thing;
		//set a current goal;
	}

	@Override
	public Command nextCommand(Status status, long time) {
		Command cmd = null;
		Goal currentGoal = this.getCurrentGoal();
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
		
		return null;
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
		this.goal = g;
		return;
	}

	@Override
	public Goal[] getRootGoal() {
		
		return this.goal;
	}

}
