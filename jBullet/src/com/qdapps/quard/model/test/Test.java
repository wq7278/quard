package com.qdapps.quard.model.test;

import org.apache.log4j.Logger;

public class Test {

	public static Logger log = Logger.getLogger(Test.class);
	public static void main(String[] args) {
		
		// time
		double T = 10;
		double S = 100;
		
		for (double x = 0; x < T ; x += .01){
			double dt = x/T * Math.PI * 2;
			double sFactor = S/(Math.PI * 2);
			double a = sFactor *  Math.sin(dt);
			//v is the integration of a;
			double v = sFactor  * (1 - Math.cos(dt));
			//s is the integration of v 
			double s = sFactor *  (dt - Math.sin(dt));
			
			log.info(a + " " + v +" "  + s );
		}
		
		//System.out.println(Math.cos(Math.PI));
		
	}

}
