package com.qdapps.quard.model.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.qdapps.quard.model.Command;
import com.qdapps.quard.model.Goal;
import com.qdapps.quard.model.Status;
import com.qdapps.quard.model.goal.StartUp;
import com.qdapps.quard.model.goal.TakeOff;

/**
 * This is run in the sofware simulated evn;
 * @author qwang
 *
 */
public class SimulationController extends Controller {

	private Logger log = Logger.getLogger(SimulationController.class);
	
	private ExecutorService executor;
	private final int NTHREDS = 2;
	
	@Override
	public void init() {
		
		//start the goal listening thread; 
		this.executor  = Executors.newFixedThreadPool(NTHREDS);
	    Runnable worker = new TestCommandFetcher(this);
	    executor.execute(worker);
	    // This will make the executor accept no new threads
	    // and finish all existing threads in the queue
	    executor.shutdown();
	    //Get current status;
		Status current = new Status();
		
		
		//set a current goal; start all motors in 15 sec;
		Goal g = new StartUp(5000);
		//this.getMaingoal().add(g); //don't add this, the firs main goal should be pop out after it is sliced;
		LinkedList<Goal> goals = g.slice (current);
		this.getGoalList().addAll(goals);
		
		List<Goal> gs = new LinkedList<>();
		gs.add(new TakeOff(0.2f, 2000, 0));
		//gs.add(new Hover(2000));
	}

	@Override
	public Command nextCommand(Status status, long time) {
		Command cmd = null;
		Goal currentGoal = this.getCurrentGoal();
		
		//If there is no goal comming back; don't do anything;
		if (currentGoal == null ) return null;
		
		Status expectedStatus = currentGoal.getTargetStatus();
		// is the status to far? abandon; reslice the parent goal;
		if (currentGoal.tooFar(status)){
			// this is where the goal should be abandoned. after that, see the main goal list, try next thing. 
			// abandon, re-adjust;
		} 
		boolean achieved = currentGoal.acchived(status);
		
		long currentTime = System.currentTimeMillis(); 
		if (achieved || currentTime > currentGoal.getEndTime()){
			//need a new goal as current Goal;
			//firt pop un the current goal;
			getNextGoal();
			Goal nextGoal = getCurrentGoal();
			if (nextGoal == null){
				//find next goal in goal list; if null, look at the parent list to find the next goal to achieve;
				Goal next = null;
				if (this.getMaingoal().size()>0){
					next= this.getMaingoal().remove(0);
				}
				if (next == null){
					//If at the end of goal list; just use this current goal; 
					//If this happened, go to a waiting stage;
					log.error("Can not find the next main goal..");
					next = currentGoal.getParentGoal();
				}
				next.setStartTime(currentTime);
				LinkedList<Goal> goalList = next.slice(status);
				this.getGoalList().addAll(goalList);
				nextGoal = getNextGoal();
			}
			currentGoal = nextGoal;
		}
		
		//Generate the command should be run;
		cmd = currentGoal.gnerateCommand(status);
		return cmd;
	}

	@Override
	public void shutdown()  {
		// Wait until all threads are finish
		try {
	    executor.awaitTermination(5, TimeUnit.SECONDS);
		}catch(Exception e ){
			e.printStackTrace();
			log.error (e.getMessage());
		}
	    log.info("Finished all threads");
		
	}

}
