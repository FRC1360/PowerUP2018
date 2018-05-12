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
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;
import org.usfirst.frc.team1360.robot.util.log.MatchLogger;

public class TeleopElevator implements TeleopComponent {

	RobotOutputProvider robotOutput = Singleton.get(RobotOutputProvider.class);
	HumanInputProvider humanInput = Singleton.get(HumanInputProvider.class);
	MatchLogProvider matchLog = Singleton.get(MatchLogger.class);
	ElevatorProvider elevator = Singleton.get(ElevatorProvider.class);
	IntakeProvider intake = Singleton.get(IntakeProvider.class);
	SensorInputProvider sensorInput = Singleton.get(SensorInputProvider.class);
	
	
	private double lastSpeed = 0;
	private boolean overrideToggle = false;
	private boolean overrideHeld = false;
	
	
	//0 = 4 foot
	//1 = 5 foot
	//2 = 6 foot
	
	@Override
	public void disable() {
		elevator.setIdle();
		overrideHeld = false;
		overrideToggle = false;
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
		boolean override = humanInput.getDriverOverride();

        matchLog.writeClean("CY: " + speed + ":" + elevator.isClimbing() + ":" + elevator.isMovingToTarget()+ ":" + overrideToggle);
		
		if(override && !overrideHeld) {
			overrideToggle = !overrideToggle;
			overrideHeld = true;
		}
		
		if(!override) {
			overrideHeld = false;
		}

        if (speed == 0)
		{
            matchLog.writeClean("ELEVATOR: Speed is Zero");
			if(scaleMax )
			{
				elevator.goToTarget(elevator.SCALE_HIGH);
			}
			else if(scaleLow)
			{
				elevator.goToTarget(elevator.SCALE_LOW);
			}
			else if(switchPreset)
			{
				elevator.goToTarget(elevator.SWITCH_HEIGHT);
			}
			else if(intakePreset)
			{
				elevator.goToTarget(elevator.INTAKE_HEIGHT);
			}

			if (!elevator.isMovingToTarget() && !elevator.isClimbing())
			{
			    matchLog.writeClean("CY: holding");
				elevator.hold();
			}
			
		}
		else
		{
			if (lastSpeed == 0) {
                matchLog.writeClean("CY: starting manual");

                elevator.startManual();
			}

			elevator.setManualSpeed((speed > 0) ? speed * speed * speed : speed * speed * speed, overrideToggle);
		}
		lastSpeed = speed;
	}	
}
