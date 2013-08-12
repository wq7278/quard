package com.qdapps.quard.model.slicer;

import java.util.LinkedList;

import com.qdapps.quard.model.Goal;
import com.qdapps.quard.model.Status;

public class SlicerImpl implements Slicer  {

	@Override
	public LinkedList<Goal> slice(Status s, Goal g) {
		
		 LinkedList<Goal> goals = g.slice(s);
		
		return goals;
	}

}
