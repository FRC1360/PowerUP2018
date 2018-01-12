package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;

public class ElevatorTeleop implements TeleopComponent {
	SensorInput sensorInput;
	
	
private static enum ElevatorState implements OrbitStateMachineState<ElevatorState>{
	
	STATE_BOTTOM {

		@Override
		public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
			// TODO Auto-generated method stub
			/* Encoder is 0
			 * Bottom Sensor is true
			 * Joystick = 0 or button is not pressed*/
			
			
			
			
		}
		
	},
	
	STATE_RISING {

		@Override
		public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
			// TODO Auto-generated method stub
			/* Motors turning
			 * Watch for encoder to reach a specific tick(height)
			 * or top sensor */
			
			
		}
		
	},
	
	STATE_MIDDLE{

		@Override
		public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
			// TODO Auto-generated method stub
			/* Bang bang middle program
			 * Button to switch states*/
		}
		
	},
	
	STATE_TOP{

		@Override
		public void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException {
			// TODO Auto-generated method stub
			/* 
			 * stay at top
			 * reduce motor speed 
			 * use sensor input at top*/
			
		}
		
	};
	
	@Override
	public abstract void run(OrbitStateMachineContext<ElevatorState> context) throws InterruptedException;
	
}
	
	public ElevatorTeleop() {
		OrbitStateMachine<ElevatorState> Elevator = new OrbitStateMachine<ElevatorTeleop.ElevatorState>(ElevatorState.STATE_BOTTOM);
		sensorInput = SensorInput.getInstance();
		
		
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
