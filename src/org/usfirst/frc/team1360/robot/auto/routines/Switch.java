package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;


public class Switch extends AutonRoutine{

	public Switch() {
		super("Switch", 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void runCore() throws InterruptedException {
		new Calibrate().runUntilFinish();
		
		if(fms.plateLeft(0)) {
			elevator.goToTarget(700);
			new SweepTurn(10000, 141/4, true, false).runUntilFinish();
			new DriveToDistance(10000, -70, position.getY(), -90, 10, false).runUntilFinish();
			arm.goToPosition(-40);
			new SweepTurn(10000, 141/4, false, false).runUntilFinish();
			new DriveToDistance(10000, position.getX(), 90, 0, 10, false).runUntilFinish();
			
			intake.setClamp(intake.FREE);
			intake.setIntake(-1);
			Thread.sleep(500);
			intake.setIntake(0);
			arm.goToTop();
			elevator.goToBottom();
			Thread.sleep(1000);
			
			
		} else {
			
			elevator.goToTarget(700);
			arm.goToPosition(-40);

			new DriveToDistance(10000, 0, 90, 0, 10, false).runUntilFinish();//96 inches
	
			intake.setClamp(intake.FREE);
			intake.setIntake(-1);
			
			Thread.sleep(500);
			intake.setIntake(0);
			arm.goToTop();
			elevator.goToBottom();
			
			Thread.sleep(1000);
			
		}
		
		Thread.sleep(1000);
	
	}

}
