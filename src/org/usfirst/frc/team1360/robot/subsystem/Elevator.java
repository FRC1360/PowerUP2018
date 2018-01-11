package org.usfirst.frc.team1360.robot.subsystem;

import java.util.function.Consumer;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.teleop.ElevatorTeleop;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;

public class Elevator implements ElevatorProvider{

	
	
	public final static int MIDDLE_TARGET = 500;
	public final static int TOP_TARGET = 1000;
	
	
	@Override
	public void goToTarget(double target, Consumer<String> onError) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stayAtTarget(int target) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stayAtTop() {
		// TODO Auto-generated method stub
		
	}
	static OrbitStateMachine<ElevatorState> ElevatorStateMachine = new OrbitStateMachine<Elevator.ElevatorState>(ElevatorState.STATE_BOTTOM);
	
	
	private static enum ElevatorState implements OrbitStateMachineState<ElevatorState>{
		
		STATE_BOTTOM {

			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context, Object arg) throws InterruptedException {
				// TODO Auto-generated method stub
				RobotOutput.getInstance().setElevatorMotor(0);
				
			
				
			}
			
		},
		
		STATE_RISING {

			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				// TODO Auto-generated method stub
				int target = (int) context.getArg();
				if (SensorInput.getInstance().getElevatorTick() > target)
				{
					RobotOutput.getInstance().setElevatorMotor(0.5);
				}
				if (SensorInput.getInstance().getElevatorTick() < target)
				{
					ElevatorStateMachine.setState(STATE_HOLD, target);
				}
			}
			
		},
		STATE_HOLD{

			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				// TODO Auto-generated method stub
				int target = (int) context.getArg();
				if (SensorInput.getInstance().getElevatorTick() > target )
				{
					RobotOutput.getInstance().setElevatorMotor(-0.1);
				}
				else 
				{
					RobotOutput.getInstance().setElevatorMotor(0.1);
				}
			}
			
		},
		
	
		STATE_DESCENDING{

			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				// TODO Auto-generated method stub
				
			}
			
		};

		
}


	
	
}
