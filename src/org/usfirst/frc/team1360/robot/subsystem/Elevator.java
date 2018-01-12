package org.usfirst.frc.team1360.robot.subsystem;

import java.util.function.Consumer;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.teleop.ElevatorTeleop;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;

public class Elevator implements ElevatorProvider{

SensorInput sensorInput = SensorInput.getInstance();
	
	static double ElevatorSpeed;
	
	private static enum ElevatorState implements OrbitStateMachineState<ElevatorState>{
		
		STATE_IDLE {

			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
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
				if (SensorInput.getInstance().getElevatorTick() <= target)
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
				int target = (int) context.getArg();
				
				if (SensorInput.getInstance().getElevatorTick() > target)
				{
					RobotOutput.getInstance().setElevatorMotor(-0.5);
				}
				if (SensorInput.getInstance().getElevatorTick() <= target)
				{
					ElevatorStateMachine.setState(STATE_HOLD, target);
				}
			}	
			}
			
		};

		


	static OrbitStateMachine<ElevatorState> ElevatorStateMachine = new OrbitStateMachine<Elevator.ElevatorState>(ElevatorState.STATE_IDLE);

	@Override
	public void goToTarget(double target, Consumer<String> onError) {
		// TODO Auto-generated method stub
		if (sensorInput.getElevatorTick() > target) 
		{
			try {
				ElevatorStateMachine.setState(ElevatorState.STATE_DESCENDING, target);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (sensorInput.getElevatorTick() <= target) {
			
			try {
				ElevatorStateMachine.setState(ElevatorState.STATE_RISING, target);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void goToBottom() {
		// TODO Auto-generated method stub
		if (ElevatorStateMachine.getState() != ElevatorState.STATE_IDLE) 
		{
			try {
				ElevatorStateMachine.setState(ElevatorState.STATE_DESCENDING, 5);
				if (sensorInput.getElevatorTick() < 10) 
				{
					ElevatorStateMachine.setState(ElevatorState.STATE_IDLE);
				}
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}


	@Override
	public void goToTop() {
		// TODO Auto-generated method stub
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_RISING, 1000);
		} catch (InterruptedException e) {e.printStackTrace();}
		if ( sensorInput.getTopSwitch() == true ) {
			RobotOutput.getInstance().setElevatorMotor(-0.05);
		}
		
		
		
	}

	@Override
	public void setspeed(double speed) {
		// TODO Auto-generated method stub
		ElevatorSpeed = speed;
	}


	
	
}

