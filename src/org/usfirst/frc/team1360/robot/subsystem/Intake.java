package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
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
	
	private OrbitStateMachine<IntakeState> machine = new OrbitStateMachine<IntakeState>(IntakeState.IDLE);
	
	@Override
	public void setPosition(double position) {
		// TODO Auto-generated method stub
		
	    if (position==INTAKE) {
	    	
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
	public int getPosition(){
		return intakePosition;
	}
}
