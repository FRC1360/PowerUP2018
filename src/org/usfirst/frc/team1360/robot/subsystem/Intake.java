package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public class Intake implements IntakeProvider {
	
	private RobotOutput robotOutput = RobotOutput.getInstance();

	@Override
	public void setPosition(int position) {
		// TODO Auto-generated method stub
	    if (position==0) {
	    	robotOutput.setClamp(false);
	    	robotOutput.setIntake(1);
	    	
	    }
	    else if(position==1) 
	    {
	    	robotOutput.setIntake(0);
	    	robotOutput.setClamp(true);
	    }
	    
	    else if (position==2)
	    {
	    	robotOutput.setIntake(0);
	    	robotOutput.setClamp(false);
	    }
	}
}
