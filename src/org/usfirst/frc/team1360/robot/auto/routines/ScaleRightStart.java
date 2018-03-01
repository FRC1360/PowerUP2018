package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveBackwardsToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;

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
//			elevator.goToTarget(elevator.ONE_FOOT*4);
			new DriveToDistance(10000, position.getX(), 140, 0, 20, true).runUntilFinish();
			
			new SweepTurn(10000, 48, true, false).runUntilFinish();
//			elevator.goToTarget(elevator.ONE_FOOT*6);
			new DriveToDistance(10000, -135, position.getY(), -90, 20, true).runUntilFinish();
			new SweepTurn(10000, 110, 40, false, false).runUntilFinish();
			
			waitFor("Calibrate", 0);
			elevator.goToTarget(elevator.POS_TOP);
			
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
			Thread.sleep(10000);
			/**/
		}
		else
		{
			//Tuned
			elevator.goToTarget(elevator.ONE_FOOT*4);
			new DriveToDistance(10000, position.getX(), 200, 0, 20, true).runUntilFinish();

			new SweepTurn(10000, 45, 48, true, false).runUntilFinish();

			arm.goToPosition(-40);
			elevator.goToTarget(elevator.POS_TOP);
			while(elevator.isMovingToTarget() && arm.movingToPosition()) Thread.sleep(10);
			new DriveToDistance(10000, position.getX() + 20, position.getY() + 20, -45, 20, false).runUntilFinish();
			intake.setClamp(intake.FREE);
			robotOutput.setIntake(0.5);
			Thread.sleep(1000);
			robotOutput.setIntake(0);
			arm.goToTop();
			Thread.sleep(500);
			elevator.goToTarget(elevator.ONE_FOOT);
			Thread.sleep(10000);
		}
	}

}
