package com.qdapps.quard.model;

import com.qdapps.quard.model.controller.Controller;
import com.qdapps.quard.model.controller.SimulationController;

public class Quard {

	private Controller controller;
	private Status status;
	
	
	public Controller getController() {
		return controller;
	}
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public void startUp(){
		
		//Start up the quard, init status; etc..
		
		Controller simcontroller = new SimulationController();
		
		this.setController(simcontroller );
		controller.init();
		
	}
	
	public void executeComand(Command cmd) {
		//after a command is back ,excute it;
		//this should be assigned to the controller, the controller shoudl know how to do this;
	}
	
	
	
	
}
