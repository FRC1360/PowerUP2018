package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveBackwardsToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn45;

public class TwoCubeRight extends AutonRoutine{

	public TwoCubeRight() {
		super("Two Cube Right", 0);
		// TODO Auto-generated constructor stub
	}
	
	//SWITCH		SCALE
	//Right		Right	switch then scale
	//Right		Left		switch then scale
	//Left		Right	
	//Left		Left

	@Override
	protected void runCore() throws InterruptedException 
	{
		int offset;
		double TICK_INCH = 5.30516;
		new Calibrate().runNow("Calibration");
		
		if(fms.plateLeft(1)) {
			new DriveToDistance(10000, position.getX(), 140, 0, 20, true).runUntilFinish();
			
			new SweepTurn(10000, 48, true, false).runUntilFinish();
			new DriveToDistance(10000, -135, position.getY(), -90, 20, true).runUntilFinish();
			new SweepTurn(10000, 110, 40, false, false).runUntilFinish();
			
			waitFor("Calibrate", 0);
			elevator.goToTarget(elevator.POS_TOP - 100);
			
			Thread.sleep(20);
			while(elevator.isMovingToTarget()) Thread.sleep(10);
			arm.goToPosition(-25);
			
			new DriveToDistance(1000, position.getX(), position.getY()+13, 0, 2, false).runUntilFinish();
			
			intake.setClamp(intake.FREE);
			intake.setIntake(1);
			Thread.sleep(1000);
			intake.setIntake(0);
			arm.goToTop();
			Thread.sleep(500);
			elevator.goToBottom();
			
			//-------------Grab Cube-------------
			
			robotOutput.tankDrive(-0.5, -0.5);
			
			offset = sensorInput.getLeftDriveEncoder();
			while(Math.abs(sensorInput.getLeftDriveEncoder() - offset) < TICK_INCH*12) Thread.sleep(10);
			
			robotOutput.tankDrive(0.5, -0.5);
			while(Math.abs(sensorInput.getAHRSYaw()) < 90) Thread.sleep(10);
			robotOutput.tankDrive(0, 0);
			
			
			intake.setIntake(-1);
			intake.setClamp(intake.FREE);
			robotOutput.tankDrive(0.5, 0.5);
			Thread.sleep(1000);
			intake.setClamp(intake.CLOSED);
			intake.setIntake(0);
			
			
			if(fms.plateLeft(0))
			{
				elevator.goToTarget(elevator.SWITCH_HEIGHT);
				while(elevator.isMovingToTarget())
				
				intake.setClamp(intake.FREE);
				intake.setIntake(0.5);
				Thread.sleep(1000);
				intake.setClamp(intake.CLOSED);
				intake.setIntake(0);
			}
			else
			{
				robotOutput.tankDrive(-0.5, -0.5);
				offset = sensorInput.getLeftDriveEncoder();
				while(Math.abs(sensorInput.getLeftDriveEncoder() - offset) < TICK_INCH*24) Thread.sleep(10);
				robotOutput.tankDrive(0, 0);
				
				new SweepTurn45(10000, 40, false, false).runUntilFinish();
				elevator.goToTarget(elevator.SWITCH_HEIGHT);
				new DriveToDistance(10000, -20, position.getY(), 90, 20, false).runUntilFinish();
				new SweepTurn45(10000, 40, false, false).runUntilFinish();
				intake.setClamp(intake.FREE);
				intake.setIntake(0.5);
				Thread.sleep(1000);
				intake.setClamp(intake.CLOSED);
				intake.setIntake(0);
			}
			
		}
		else
		{
			elevator.goToTarget(elevator.ONE_FOOT*4);
			new DriveToDistance(10000, position.getX(), 200, 0, 20, true).runUntilFinish();

			new SweepTurn45(10000, 48, true, false).runUntilFinish();

			arm.goToPosition(-40);
			elevator.goToTarget(elevator.POS_TOP);
			while(elevator.isMovingToTarget() && arm.movingToPosition()) Thread.sleep(10);
			new DriveToDistance(10000, position.getX() + 20, position.getY() + 20, -45, 20, false).runUntilFinish();
			intake.setClamp(intake.FREE);
			robotOutput.setIntake(0.5);
			Thread.sleep(1000);
			robotOutput.setIntake(0);
			
			arm.goToPosition(-40);
			elevator.goToBottom();
			
			//-------------Grab Cube-------------
			new DriveBackwardsToDistance(10000, position.getX()+36, position.getY()-36, -45, 20, false).runUntilFinish();
			new SweepTurn(10000, 50, true, false).runUntilFinish();
			
			arm.goToPosition(arm.POS_BOTTOM);
			intake.setClamp(intake.FREE);
			intake.setIntake(-1);

			new DriveToDistance(1000, position.getX()-10, position.getY()+10, -135, 5, false).runUntilFinish();
			
			intake.setClamp(intake.CLOSED);
			intake.setIntake(0);
			
			
			
			
			if(fms.plateLeft(0)) {
				new DriveBackwardsToDistance(10000, position.getX()-10, position.getY()-10, -135, 5, false).runUntilFinish();
				new SweepTurn45(10000, 40, true, false).runUntilFinish();
				elevator.goToTarget(elevator.SWITCH_HEIGHT);
				new DriveToDistance(10000, -100, position.getY(), -90, 20, false).runUntilFinish();
				new SweepTurn45(10000, 40, true, false).runUntilFinish();
				intake.setClamp(intake.FREE);
				intake.setIntake(0.5);
				Thread.sleep(1000);
				intake.setClamp(intake.CLOSED);
				intake.setIntake(0);
				
				
			}
			else
			{
				elevator.goToTarget(elevator.SWITCH_HEIGHT);
				new DriveBackwardsToDistance(10000, position.getX()-10, position.getY()-10, -135, 5, false).runUntilFinish();
				
				intake.setClamp(intake.FREE);
				intake.setIntake(0.5);
				Thread.sleep(1000);
				intake.setClamp(intake.CLOSED);
				intake.setIntake(0);
				
			}
		}
		
	}

}
