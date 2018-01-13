package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonSee;

@SingletonSee(IntakeProvider.class)
public class Intake implements IntakeProvider {
	
	private RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	
	private static enum IntakeState implements OrbitStateMachineState<IntakeState>{
		
		INTAKE 	//clamp open and rollers running
		{
			RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
			@Override
			public void run(OrbitStateMachineContext<IntakeState> context) throws InterruptedException
			{
				robotOutput.setClamp(false);
				robotOutput.setIntake(1);
			}
			
		},
		CLOSED	//clamp closed and rollers not running
		{
			RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
			@Override
			public void run(OrbitStateMachineContext<IntakeState> context) throws InterruptedException
			{
				robotOutput.setClamp(true);
		    		robotOutput.setIntake(0);
			}
		},
		IDLE  //clamp open rollers not running
		{
			RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
			@Override
			public void run(OrbitStateMachineContext<IntakeState> context) throws InterruptedException
			{
				robotOutput.setClamp(false);
		    		robotOutput.setIntake(0);
			}
		};
		
		@Override
		public abstract void run(OrbitStateMachineContext<IntakeState> context) throws InterruptedException;
	}
	
	public final int IDLE = 2;
	public final int INTAKE = 0;
	public final int CLOSED = 1;
	
	private int intakePosition = 2;   //starts in idle
	
	private OrbitStateMachine<IntakeState> machine = new OrbitStateMachine<IntakeState>(IntakeState.IDLE);
	
	@Override
	public void setPosition(double position) {
		// TODO Auto-generated method stub
		
	    if (position==INTAKE) {  //keeps intake in given state (if already in idle the intake will stay in idle)
	    	
	    	intakePosition = INTAKE;
	    	try {
				machine.setState(IntakeState.INTAKE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    else if(position==CLOSED) 
	    {
	    	intakePosition = CLOSED;
	    	try {
				machine.setState(IntakeState.CLOSED);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    
	    else if (position==IDLE)
	    {
	    	intakePosition = IDLE;
	    	try {
				machine.setState(IntakeState.IDLE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    
	}
	
	@Override 
	public int getPosition(){	// getter method for the state machine
		return intakePosition;
	}
}
