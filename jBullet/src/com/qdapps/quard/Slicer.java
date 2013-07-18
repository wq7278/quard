package com.qdapps.quard;

import java.util.LinkedList;

public interface Slicer {
	public LinkedList<Goal> slice(Status s, Goal g);
}
