package com.qdapps.quard.model.goal;

import java.util.LinkedList;

import com.qdapps.quard.model.Command;
import com.qdapps.quard.model.Goal;
import com.qdapps.quard.model.Status;

public class Hover extends Goal {

	@Override
	public boolean tooFar(Status s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean acchived(Status s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<Goal> slice(Status s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command gnerateCommand(Status status) {
		// TODO Auto-generated method stub
		return null;
	}

}
