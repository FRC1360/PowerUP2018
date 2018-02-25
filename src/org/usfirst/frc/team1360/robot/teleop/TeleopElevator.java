package org.usfirst.frc.team1360.robot.teleop;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class TeleopElevator implements TeleopComponent {

	RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
	ElevatorProvider elevator = Singleton.get(ElevatorProvider.class);
	ArmProvider arm = Singleton.get(ArmProvider.class);
	IntakeProvider intake = Singleton.get(IntakeProvider.class);
	SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	
	
	private double lastSpeed = 0;
	private boolean heldLastLoop = false;
	private int position = 0;
	//0 = 4 foot
	//1 = 5 foot
	//2 = 6 foot
	
	@Override
	public void disable() {
		elevator.setIdle();
		lastSpeed = 0;
	}
	

	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		double speed = humanInput.getElevator();
		boolean scaleMax = humanInput.getScaleMax();
		boolean scaleLow = humanInput.getScaleLow();
		boolean switchPreset = humanInput.getSwitch();
		boolean intakePreset = humanInput.getIntake();
		
		if (speed == 0)
		{
			if(scaleMax && !arm.movingToPosition())
			{
				elevator.goToTarget(elevator.SCALE_HIGH);
				arm.goToTop();
			}
			else if(scaleLow && !arm.movingToPosition())
			{
				elevator.goToTarget(elevator.SCALE_LOW);
				arm.goToPosition(arm.POS_BOTTOM);
			}
			else if(switchPreset && !arm.movingToPosition())
			{
				elevator.goToTarget(elevator.SWITCH_HEIGHT);
				arm.goToPosition(arm.POS_BOTTOM);
			}
			else if(intakePreset)
			{
				elevator.goToTarget(elevator.INTAKE_HEIGHT);
			}
			if (!elevator.isMovingToTarget())
			{
				//elevator.hold();
				//dampening stuff
//				
//				if(lastSpeed > 0)
//					elevator.goToTarget(sensorInput.getElevatorEncoder() + 300);
//				else if(lastSpeed < 0)
//					elevator.goToTarget(sensorInput.getElevatorEncoder() - 300);
//				else
				if(!elevator.isHolding())
					elevator.hold();
			}
			
		}
		else
		{
			if (lastSpeed == 0)
				elevator.startManual();
			elevator.setManualSpeed((speed > 0) ? speed * speed : (speed * speed) * -1);
		}
		lastSpeed = speed;
	}	
}
