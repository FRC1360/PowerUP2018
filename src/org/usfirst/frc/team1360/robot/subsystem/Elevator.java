package org.usfirst.frc.team1360.robot.subsystem;

import java.util.function.Consumer;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.teleop.TeleopElevator;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class Elevator implements ElevatorProvider{

SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	
	static double ElevatorSpeed;
	
	
	private static enum ElevatorState implements OrbitStateMachineState<ElevatorState>{
		
		STATE_IDLE {
			RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
			
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				// TODO Auto-generated method stub
				robotOutput.setElevatorMotor(0);
			}


			
		},
		
		STATE_RISING {

			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				// TODO Auto-generated method stub
				RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
				
				if (context.getArg() instanceof Double) {
					
					double speed = (Double) context.getArg();
					
					robotOutput.setElevatorMotor(speed);
				
				}
				else {
					int target = (int) context.getArg();
				
					if (SensorInput.getInstance().getElevatorTick() < target)
					{
						robotOutput.setElevatorMotor(0.5);
					}
					if (SensorInput.getInstance().getElevatorTick() >= target)
					{
						if (target == 1000) {
							ElevatorStateMachine.setState(STATE_HOLD, new Boolean(true));
						}
						else {
							ElevatorStateMachine.setState(STATE_HOLD, target);
						}
					}
				}	
				}
			
		},
		STATE_HOLD{

			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				// TODO Auto-generated method stub
				RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
				SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
				int target = 0;
				boolean StayAtTop = false;
				
			
					if (context.getArg() instanceof Boolean) {
						StayAtTop = (Boolean) context.getArg();
					}
					
					else {target = (int) context.getArg();}
				
				
				
				
				if (StayAtTop ==  true) {
					if ( sensorInput.getTopSwitch() == true){
						robotOutput.setElevatorMotor(-0.05);
					}
					else 
					{
						robotOutput.setElevatorMotor(0.05);
					}
				}
				
			
				
				else {
					if (sensorInput.getElevatorTick() > target )
					{
						robotOutput.setElevatorMotor(-0.05);
					}
					else 
					{
						robotOutput.setElevatorMotor(0.05);
					}
				}
			}
			
		},
		
	
		STATE_DESCENDING{
			RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
			
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				// TODO Auto-generated method stub
				
				if (context.getArg() instanceof Double) {
					
					double speed = (Double) context.getArg();
					
					robotOutput.setElevatorMotor(speed);
				
				}
				else {
				
					int target = (int) context.getArg();
				
					if (SensorInput.getInstance().getElevatorTick() > target)
					{
						robotOutput.setElevatorMotor(-0.5);
					}
					if (SensorInput.getInstance().getElevatorTick() <= target)
					{
						if(target == 0) {ElevatorStateMachine.setState(STATE_IDLE);}
						
						else {ElevatorStateMachine.setState(STATE_HOLD, target);}
					}
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
				ElevatorStateMachine.setState(ElevatorState.STATE_DESCENDING, 0);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}


	@Override
	public void goToTop() {
		// TODO Auto-generated method stub
		try {
			
			ElevatorStateMachine.setState(ElevatorState.STATE_RISING, 1000);
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	@Override
	public void setspeed(double speed) {
		// TODO Auto-generated method stub
		ElevatorSpeed = speed;
	}

	@Override
	public void Sethold(int target) {
		// TODO Auto-generated method stub
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_HOLD, target);
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	@Override
	public void setrising(int target) {
		// TODO Auto-generated method stub
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_RISING, target);
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	@Override
	public void setrising(double speed) {
		// TODO Auto-generated method stub
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_RISING, speed);
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	@Override
	public void setdescending(double speed) {
		// TODO Auto-generated method stub
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_DESCENDING, speed);
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	@Override
	public void setdescending(int target) {
		// TODO Auto-generated method stub
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_DESCENDING, target);
		} catch (InterruptedException e) {e.printStackTrace();}
	}


}

