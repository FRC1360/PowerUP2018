package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.subsystem.Intake;

public final class EncoderSwitch extends AutonRoutine {
	
	public EncoderSwitch() {
		super("Barebones Encoder Swtich Program", 0);
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		robotOutput.arcadeDrivePID(1.0, 0);
		while(sensorInput.getLeftDriveEncoder() < 10000) Thread.sleep(10);
		robotOutput.arcadeDrive(0.0, 0.0);
		Thread.sleep(100);
		
		robotOutput.tankDrive(-0.5, 0.5);
		while(Math.abs(sensorInput.getAHRSYaw()) < 45) Thread.sleep(10);
		robotOutput.tankDrive(0, 0);
		sensorInput.resetLeftEncoder();
		Thread.sleep(100);
		
		robotOutput.arcadeDrivePID(1.0, 0);
		while(sensorInput.getLeftDriveEncoder() < 5000) Thread.sleep(10);
		robotOutput.arcadeDrive(0.0, 0.0);
		Thread.sleep(100);
		
		robotOutput.tankDrive(0.5, -0.5);
		while(Math.abs(sensorInput.getAHRSYaw()) > 5) Thread.sleep(10);
		robotOutput.tankDrive(0, 0);
		sensorInput.resetLeftEncoder();
		Thread.sleep(100);
		
		robotOutput.arcadeDrivePID(1.0, 0);
		Thread.sleep(1000);
		robotOutput.arcadeDrive(0.0, 0.0);
		Thread.sleep(100);
		
		robotOutput.setClamp(Intake.OPEN);
		Thread.sleep(10000);
		
	}
}
