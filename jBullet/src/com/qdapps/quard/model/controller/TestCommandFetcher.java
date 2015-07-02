package com.qdapps.quard.model.controller;

import java.util.List;

import com.qdapps.quard.model.Goal;

public class TestCommandFetcher implements Runnable, CommandFetcher {

	private Controller controler;
	
	public TestCommandFetcher(Controller controller)
	{
		this.controler = controller;
	}
	
	
	@Override
	public void run() {
		//load the file name; 
		while (!this.controler.getShutDown()){
			List <Goal> goal = fetchCommands();
			if (goal!= null && goal.size() >0){
				this.controler.getMaingoal().retainAll(null);
				this.controler.getMaingoal().addAll(goal);
			}
			
			try {
				Thread.sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.print(".");
		}

	}


	@Override
	public List <Goal> fetchCommands() {
		String fileName = "command.txt";
		return null;
	}

}
