package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveBackwardsToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn45;

public class ScaleRightStart extends AutonRoutine{

	public ScaleRightStart() {
		super("ScaleRightStart", 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void runCore() throws InterruptedException {
		new Calibrate().runNow("Calibrate");
		
		if(fms.plateLeft(1))
		{
			//Not tuned
			elevator.goToTarget(elevator.ONE_FOOT*4);
			new DriveToDistance(10000, position.getX(), 140, 0, 20, true).runUntilFinish();
			/*new SweepTurn(10000, 48, true, false).runUntilFinish();
			new SweepTurn(10000, 40, false, false).runUntilFinish();*/
			new SweepTurn(10000, 48, true, false).runUntilFinish();
			new DriveToDistance(10000, position.getX() + 145, position.getY(), -90, 20, true).runUntilFinish();
			new SweepTurn(10000, 36, false, false).runUntilFinish();
			//new SweepTurn45(10000, 48, false, false).runUntilFinish();

			arm.goToPosition(-15);
			elevator.goToTarget(elevator.POS_TOP);
			//while(elevator.isMovingToTarget()) Thread.sleep(10);
			Thread.sleep(500);
			new DriveToDistance(10000, position.getX() + 20, position.getY() + 20, 45, 20, true).runUntilFinish();
			intake.setClamp(intake.FREE);
			intake.setIntake(-0.5);
			Thread.sleep(1000);
			intake.setIntake(0);
			arm.goToTop();
			new DriveBackwardsToDistance(10000, position.getX() + 10, position.getY() + 10, 45, 20, true).runUntilFinish();
			elevator.goToBottom();
			Thread.sleep(10000);
		}
		else
		{
			//Tuned
			elevator.goToTarget(elevator.ONE_FOOT*4);
			new DriveToDistance(10000, position.getX(), 175, 0, 20, true).runUntilFinish();
			/*new SweepTurn(10000, 48, true, false).runUntilFinish();
			new SweepTurn(10000, 40, false, false).runUntilFinish();*/
			new SweepTurn45(10000, 48, true, false).runUntilFinish();

			arm.goToPosition(-15);
			elevator.goToTarget(elevator.POS_TOP);
			//while(elevator.isMovingToTarget()) Thread.sleep(10);
			Thread.sleep(500);
			new DriveToDistance(10000, position.getX() + 20, position.getY() + 20, -45, 20, true).runUntilFinish();
			intake.setClamp(intake.FREE);
			intake.setIntake(-0.5);
			Thread.sleep(1000);
			intake.setIntake(0);
			arm.goToTop();
			new DriveBackwardsToDistance(10000, position.getX() + 10, position.getY() + 10, -45, 20, true).runUntilFinish();
			elevator.goToBottom();
			Thread.sleep(10000);
		}
	}

}
