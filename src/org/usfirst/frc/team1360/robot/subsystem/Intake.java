package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;

@SingletonSee(IntakeProvider.class)
public class Intake implements IntakeProvider {
	
	private RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	
	private static enum IntakeState implements OrbitStateMachineState<IntakeState>{
		
	}
	
	public final int IDLE = 2;
	public final int INTAKE = 0;
	public final int CLOSED = 1;
	
	private int intakePosition = 2;
	
	@Override
	public void setPosition(int position) {
		// TODO Auto-generated method stub
		
	    if (position==INTAKE) {
	    	robotOutput.setClamp(false);
	    	robotOutput.setIntake(1);
	    	intakePosition = INTAKE;
	    	
	    }
	    else if(position==CLOSED) 
	    {
	    	robotOutput.setIntake(0);
	    	robotOutput.setClamp(true);
	    	intakePosition = CLOSED;
	    }
	    
	    else if (position==IDLE)
	    {
	    	robotOutput.setIntake(0);
	    	robotOutput.setClamp(false);
	    	intakePosition = IDLE;
	    }
	    
	}
	
	@Override 
	public int getPosition(){
		return intakePosition;
	}
}
