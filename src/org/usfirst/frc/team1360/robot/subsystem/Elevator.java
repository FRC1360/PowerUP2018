package org.usfirst.frc.team1360.robot.subsystem;

import java.util.function.Consumer;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
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
	
	
	public static enum ElevatorState implements OrbitStateMachineState<ElevatorState>{
		//sets motors to 0
		STATE_IDLE {
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				// TODO Auto-generated method stub
				robotOutput.setElevatorMotor(0);
			}


			
		},
		
		STATE_RISING {
/*
 * (non-Javadoc)
 * @see org.usfirst.frc.team1360.robot.util.OrbitStateMachineState#run(org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext)
 * 
 * used when rising to a target, in autor
 * can be passed a double for speed, in teleop.
 * runs while the state is unchanged, state can be changed outside of rising state,
 * state is also changed to hold when target is reached, with the target passed to hold
 */
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				// TODO Auto-generated method stub
				while (ElevatorStateMachine.getState() == ElevatorState.STATE_RISING) {
				if (context.getArg() instanceof Double) {
					
					double speed = (Double) context.getArg();
					
					robotOutput.setElevatorMotor(speed);
				
				}
				else {
					int target = (int) context.getArg();
				
					if (sensorInput.getElevatorTick() < target)
					{
						robotOutput.setElevatorMotor(0.5);
					}
					if (sensorInput.getElevatorTick() >= target)
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
				}
			
		},
		/* holds the elevator at a specific encoder tick
		 * runs while the joysticks are not moved, so that it runs during auto, and it breaks when the elvator should rise or fall
		 * can be stopped by changing the state via Elevator Methods
		 */
		STATE_HOLD{

			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				// TODO Auto-generated method stub
				while (true) {
				
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
			}
			
		},
		
	/*
	 * same as rising, only it descends the elevator to the target, or at the speed that is passed
	 * only one can be passed:speed or target
	 * sets state to hold when target is reached or idle if target was 0
	 */
		STATE_DESCENDING{
			@Override
			public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
				// TODO Auto-generated method stub
				
				if (context.getArg() instanceof Double) {
					
					double speed = (Double) context.getArg();
					
					robotOutput.setElevatorMotor(speed);
				
				}
				else {
				
					int target = (int) context.getArg();
				
					if (sensorInput.getElevatorTick() > target)
					{
						robotOutput.setElevatorMotor(-0.5);
					}
					if (sensorInput.getElevatorTick() <= target)
					{
						if(target == 0) {ElevatorStateMachine.setState(STATE_IDLE);}
						
						else {ElevatorStateMachine.setState(STATE_HOLD, target);}
					}
				}	
			}
			};
		
		protected RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
		protected SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
		protected Elevator elevatorclass = Singleton.get(Elevator.class);
			
		};

		


	static OrbitStateMachine<ElevatorState> ElevatorStateMachine = new OrbitStateMachine<Elevator.ElevatorState>(ElevatorState.STATE_IDLE);
//sends the elevator to a specific target by setting Rising or descending states which set the state to hold when target is reached
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
	//sets elevator to descending with the target of 0, Descending State sets the state to IDLE after 0 is reached 
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

//ends elevator to top and holds it there
	@Override
	public void goToTop() {
		// TODO Auto-generated method stub
		try {
			
			ElevatorStateMachine.setState(ElevatorState.STATE_RISING, 1000);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	//sets the speed of the elevator(not used at all currently)
	@Override
	public void setspeed(double speed) {
		// TODO Auto-generated method stub
		ElevatorSpeed = speed;
	}
//elevator hovers at (passed)target
	@Override
	public void sethold(int target) {
		// TODO Auto-generated method stub
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_HOLD, target);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	//elevator holds at  (passed) target for (passed)amount of time, then state is set to idle
	@Override
	public void sethold(int target, int millisec) {
		// TODO Auto-generated method stub
		int currenttime = 0;
		while(currenttime <= millisec) {
			
			try {
				ElevatorStateMachine.setState(ElevatorState.STATE_HOLD, target);
			} catch (InterruptedException e) {e.printStackTrace();}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {e.printStackTrace();}
			currenttime++;
		}
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_IDLE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//sets the elevator to rise to the (passed)target
	//elevator holds at target afterwards
	@Override
	public void setrising(int target) {
		// TODO Auto-generated method stub
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_RISING, target);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	//elevator rises at the speed that is passed
	@Override
	public void setrising(double speed) {
		// TODO Auto-generated method stub
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_RISING, speed);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	//elevator descends at (passed) speed
	@Override
	public void setdescending(double speed) {
		// TODO Auto-generated method stub
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_DESCENDING, speed);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	//elevator descends to (passed)target 
	//elevator holds at target afterwards
	@Override
	public void setdescending(int target) {
		// TODO Auto-generated method stub
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_DESCENDING, target);
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	//sets the elevator to idle, 
	//sets motors to 0
	@Override
	public void setidle()  {
		try {
			ElevatorStateMachine.setState(ElevatorState.STATE_IDLE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//returns current state of State Machine
	@Override
	public ElevatorState getState() {
		// TODO Auto-generated method stub
		return ElevatorStateMachine.getState();
	}



}

