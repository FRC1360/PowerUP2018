package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;

public class Intake implements IntakeProvider {
	
	private RobotOutput robotOutput = RobotOutput.getInstance();
	
	private static enum IntakeState implements OrbitStateMachineState<IntakeState>{
		
		INTAKE
		{
			@Override
			public void run(OrbitStateMachineContext<IntakeState> context) throws InterruptedException
			{
				RobotOutput.getInstance().setClamp(false);
		    	RobotOutput.getInstance().setIntake(1);
			}
			
		},
		CLOSED
		{
			@Override
			public void run(OrbitStateMachineContext<IntakeState> context) throws InterruptedException
			{
				RobotOutput.getInstance().setClamp(true);
		    	RobotOutput.getInstance().setIntake(0);
			}
		},
		IDLE
		{
			@Override
			public void run(OrbitStateMachineContext<IntakeState> context) throws InterruptedException
			{
				RobotOutput.getInstance().setClamp(false);
		    	RobotOutput.getInstance().setIntake(0);
			}
		};
		
		@Override
		public abstract void run(OrbitStateMachineContext<IntakeState> context) throws InterruptedException;
	}
	
	public final int IDLE = 2;
	public final int INTAKE = 0;
	public final int CLOSED = 1;
	
	private int intakePosition = 2;
	
	@Override
	public void setPosition(double position) {
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
