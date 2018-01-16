package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.subsystem.Elevator;
import org.usfirst.frc.team1360.robot.subsystem.Elevator.ElevatorState;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachine;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineContext;
import org.usfirst.frc.team1360.robot.util.OrbitStateMachineState;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class TeleopElevator implements TeleopComponent {

	RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
	ElevatorProvider elevator = Singleton.get(ElevatorProvider.class);
	SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	private double lastSpeed = 0;
	//sets state to idle and turns off motors
	@Override
	public void disable() {
		// TODO Auto-generated method stub
		elevator.setidle();
		lastSpeed = 0;
	}
/*checks the input of operator's right controller*with deadzone) and applies that to the motors.
 * It decides whether to set the elevator state to rising or decsending based on direction of joystick (negative or positive)
 * calls the hold state if joystick is 0(and joystick isn't already holding
*/
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		double speed = humanInput.deadzone(humanInput.getElevator(), 0.1);
		
		if (speed == 0)
		{
			if (elevator.getState() != ElevatorState.STATE_HOLD)
			{
				elevator.sethold(sensorInput.getElevatorTick());
			}
		}
		else
		{
			if (lastSpeed == 0)
				elevator.startManual();
			elevator.setspeed(speed);
		}
		lastSpeed = speed;
	}	
}
