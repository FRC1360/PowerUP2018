package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {

		new DriveToInch(5000, 100, 0, 0, true).runUntilFinish();
		
		//new SweepTurn(2000, 90, 36, true, false).runUntilFinish();
		
		
		//new SweepTurn(10000, 90, 40, true, false).runUntilFinish();
		
		//new DriveToInch(10000, -100, 0, 0, false).runUntilFinish();
		
		//robotOutput.tankDrive(0, 0);
		
		
		//robotOutput.tankDrive(1, 1);
		/*
		while(true)
		{
			robotOutput.tankDrive(1, 1);
			SmartDashboard.putNumber("Outer Velocity", sensorInput.getRightEncoderVelocity());
			SmartDashboard.putNumber("Inner Veloctiy", sensorInput.getLeftEncoderVelocity());
		}
		*/
		
		robotOutput.tankDrive(0, 0);
		
		intake.setIntake(-0.5);
		Thread.sleep(1000);


	}
}
