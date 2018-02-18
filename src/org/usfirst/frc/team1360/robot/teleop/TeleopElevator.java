package org.usfirst.frc.team1360.robot.teleop;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class TeleopElevator implements TeleopComponent {

	RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
	ElevatorProvider elevator = Singleton.get(ElevatorProvider.class);
	SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	
	private Map<Integer, Integer> positions = new HashMap<Integer, Integer>();
	{
		positions.put(0, elevator.POS_BOTTOM);
		positions.put(1, elevator.FOUR_FOOT);
		positions.put(2, elevator.FIVE_FOOT);
		positions.put(3, elevator.SIX_FOOT);
	}
	
	private double lastSpeed = 0;
	private boolean heldLastLoop = false;
	private int position = 0;
	//0 = 4 foot
	//1 = 5 foot
	//2 = 6 foot
	
	@Override
	public void disable() {
		// TODO Auto-generated method stub
		elevator.setIdle();
		lastSpeed = 0;
	}
	

	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		double speed = humanInput.getElevator();
		int preset = humanInput.getOperatorPOV(); 
		
		
		
		if (speed == 0)
		{
			if(preset == 0 && !heldLastLoop)
			{
				if(position < 3) position += 1;
				elevator.goToTarget(positions.get(position));
				heldLastLoop = true;
			}
			else if(preset == 180 && !heldLastLoop)
			{
				if(position > 0) position -= 1;
				elevator.goToTarget(positions.get(position));
				heldLastLoop = true;
			}
			else if(preset != 180 && preset != 0)
			{
				heldLastLoop = false;
			}
			if (!elevator.isHolding() && !elevator.isMovingToTarget())
			{
				elevator.hold();
			}
			
		}
		else
		{
			if (lastSpeed == 0)
				elevator.startManual();
			elevator.setManualSpeed(speed);
		}
		lastSpeed = speed;
	}	
}
