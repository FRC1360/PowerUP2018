package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;


public class Calibrate extends AutonRoutine{
	
	public Calibrate() {
		super("Calibrate", 5000);
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		robotOutput.setArm(-1);
		while(sensorInput.getArmEncoder() < 500) Thread.sleep(10);
		robotOutput.setArm(0.75);
		while(!sensorInput.getArmSwitch()) robotOutput.setArm(0.75);
		sensorInput.resetArmEncoder();
		robotOutput.setArm(0);
		
		
	}

}
