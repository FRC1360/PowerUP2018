package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
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
	
	@Override
	public void disable() {
		// TODO Auto-generated method stub
		elevator.set
	}

	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		double speed = humanInput.deadzone(humanInput.getElevator());
		
		if (speed < 0) 
		{
			elevator.setrising(speed);
			
		}
		else if(speed > 0)
		{
			elevator.setdescending(speed);
		}
		else 
		{
			elevator.Sethold(sensorInput.getElevatorTick());
		}
		
	}

	
	}
	
