package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;
import org.usfirst.frc.team1360.robot.util.Singleton;

public class Calibrate extends AutonRoutine{
	
	public Calibrate() {
		super("Calibrate", 5000);
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		arm.safety(-1);
		Thread.sleep(500);
		arm.safety(1);
		while(!sensorInput.getArmSwitch()) arm.safety(1);
		
		
	}

}
