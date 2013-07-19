package com.qdapps.quard.model;

import com.qdapps.quard.model.controller.Controller;

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
		
		this.setController(controller);
		controller.init();
		
	}
	
	
	
	
}
