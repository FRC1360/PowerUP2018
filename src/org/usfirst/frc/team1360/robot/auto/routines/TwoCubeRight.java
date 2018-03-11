package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.ElevatorToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;

public class TwoCubeRight extends AutonRoutine{

	public TwoCubeRight() {
		super("Two Cube Right", 0);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	protected void runCore() throws InterruptedException 
	{
		int offset;
		double TICK_INCH = 5.30516;
		new Calibrate().runNow("Calibrate");
		
		if(fms.plateLeft(1)) {
			new DriveToInch(5000, 185, 0, 0, false).runUntilFinish();
			
			new SweepTurn(2000, 90, 36, true, false).runUntilFinish();
			
			//new DriveToDistance(5000, -201, 221, -90, 0, true).runUntilFinish();
			new DriveToInch(5000, 180, -90, 0, false).runUntilFinish();

			
			new SweepTurn(2000, 137, 26, false, false).runUntilFinish();
			
			new ElevatorToTarget(1500, elevator.POS_TOP-50).runUntilFinish();
			
			waitFor("Calibrate", 0);
			
			arm.goToPosition(-35);
			Thread.sleep(500);
			
			intake.setClamp(intake.FREE);
			intake.setIntake(0.5);
			Thread.sleep(1000);
			intake.setIntake(0);
			arm.goToTop();
			Thread.sleep(500);
			/**/
			
			if(fms.plateLeft(0))
			{
				
				new ElevatorToTarget(2000, elevator.POS_BOTTOM).runUntilFinish();
				//new SweepTurn(2000, -97, 22, true, false).runUntilFinish();

				arm.goToPosition(arm.POS_BOTTOM);
				
				intake.setIntake(-1);
				intake.setClamp(intake.FREE);
				
				new DriveToInch(2000, 70, 159, 0, false).runUntilFinish();
				
				arm.goToTop();
				intake.setIntake(0);
				intake.setClamp(intake.CLOSED);
				new ElevatorToTarget(750, elevator.ONE_FOOT*2).runUntilFinish();
				arm.goToPosition(-30);
				Thread.sleep(500);
				intake.setIntake(0.75);
				intake.setClamp(intake.FREE);
				robotOutput.tankDrive(0, 0);
				Thread.sleep(10000);
				
				
				/*
				elevator.upToTarget(elevator.SWITCH_HEIGHT);
				while(elevator.isMovingToTarget()) Thread.sleep(10);
				intake.setIntake(0.75);
				intake.setClamp(intake.FREE);
				Thread.sleep(3000);
				intake.setIntake(0);
				intake.setClamp(intake.CLOSED);
				*/
			}
			else
			{
				return;
				/*
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
				*/
				
			}
			 /**/
			
			
		}
		else
		{
//			//Tuned
//			elevator.goToTarget(elevator.ONE_FOOT*4);
//			new DriveToDistance(5000, position.getX(), 200, 0, 20, true).runUntilFinish();
//
//			new SweepTurn(2000, 45, 48, true).runUntilFinish();
//
//			arm.goToPosition(-40);
//			
//			new ElevatorToTarget(2500, elevator.POS_TOP-50).runUntilFinish();
//			
//			new DriveToDistance(2000, position.getX() + 20, position.getY() + 20, -45, 2, false).runUntilFinish();
//			intake.setClamp(intake.FREE);
//			robotOutput.setIntake(0.5);
//			Thread.sleep(500);
//			robotOutput.setIntake(0);
//
//			new SweepTurn(1000, 45, -48, false).runUntilFinish();
//			
//			elevator.goToBottom();
			
			
			if(fms.plateLeft(0)) {
				return;
				/*new DriveBackwardsToDistance(10000, position.getX()-10, position.getY()-10, -135, 5, false).runUntilFinish();
				new SweepTurn45(10000, 40, true, false).runUntilFinish();
				elevator.goToTarget(elevator.SWITCH_HEIGHT);
				new DriveToDistance(10000, -100, position.getY(), -90, 20, false).runUntilFinish();
				new SweepTurn45(10000, 40, true, false).runUntilFinish();
				intake.setClamp(intake.FREE);
				intake.setIntake(0.5);
				Thread.sleep(1000);
				intake.setClamp(intake.CLOSED);
				intake.setIntake(0);
				*/
				
			}
			else
			{
				
				return;
				//intake.setIntake(-1);
				//new SweepTurn(1000, 45, 48, true, false).runUntilFinish();
				
				
				/*
				elevator.goToTarget(elevator.SWITCH_HEIGHT);
				new DriveBackwardsToDistance(10000, position.getX()-10, position.getY()-10, -135, 5, false).runUntilFinish();
				
				intake.setClamp(intake.FREE);
				intake.setIntake(0.5);
				Thread.sleep(1000);
				intake.setClamp(intake.CLOSED);
				intake.setIntake(0);
				*/
				
			}
		}
		
	}

}
