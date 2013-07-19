package com.qdapps.quard.model.slicer;

import java.util.LinkedList;

import com.qdapps.quard.model.Goal;
import com.qdapps.quard.model.Status;

public interface Slicer {
	public LinkedList<Goal> slice(Status s, Goal g);
}
