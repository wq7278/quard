package com.qdapps.quard.model;

public class Command {
	
	public double [] cmdForMotors;
	public String [] cmd;

	public String toString (){
		String s = "";
		if (cmd!=null){
			for (int i = 0; i < cmd.length; i++) {
				String cmdString = cmd[i];
				s+= (" | " +cmdString);
				
			}
		}
		return s;
	}
	
}
