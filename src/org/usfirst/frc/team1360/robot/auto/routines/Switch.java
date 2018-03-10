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
		new Calibrate().runNow("Calibrate");
		
		if(fms.plateLeft(0)) {
			elevator.goToTarget(700);
			new SweepTurn(2500, 130/4, true, false).runUntilFinish();
			new DriveToDistance(2500, -80, position.getY(), -80, 10, false).runUntilFinish();
			
			waitFor("Calibrate", 0);
			arm.goToPosition(-40);
			new SweepTurn(2500, 130/4, false, false).runUntilFinish();
			new DriveToDistance(2500, position.getX(), 80, 0, 10, false).runUntilFinish();
			
			intake.setClamp(intake.FREE);
			//intake.setIntake(0.5);
			robotOutput.setIntake(0.5);
			Thread.sleep(500);
			//intake.setIntake(0);
			robotOutput.setIntake(0);
			arm.goToTop();
			elevator.goToBottom();
			Thread.sleep(1000);
			
			
		} else {
			elevator.goToTarget(700);
			
			waitFor("Calibrate", 0);
			arm.goToPosition(-40);

			new DriveToDistance(5000, 0, 90, 0, 10, false).runUntilFinish();//96 inches
	
			intake.setClamp(intake.FREE);
			//intake.setIntake(-1);
			robotOutput.setIntake(1);
			
			Thread.sleep(500);
			intake.setIntake(0);
			arm.goToTop();
			elevator.goToBottom();
			
			Thread.sleep(1000);
			
		}
		
		Thread.sleep(1000);
	
	}

}
