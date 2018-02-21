package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;

public class ScaleRightStart extends AutonRoutine{

	public ScaleRightStart() {
		super("ScaleRightStart", 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void runCore() throws InterruptedException {
		new Calibrate.runUntilFinish();
		
		if(fms.plateLeft(1))
		{
			elevator.goToTarget(elevator.FOUR_FOOT);
			new DriveToDistance(10000, position.getX(), 250, 0, 20, true);
			new SweepTurn(10000, 36, true, false);
			new SweepTurn(10000, 36, false, false);

			arm.goToPosition(-40);
			elevator.goToTarget(elevator.SIX_FOOT);
			while(elevator.isMovingToTarget()) Thread.sleep(10);
			
			intake.setClamp(intake.FREE);
			intake.setIntake(-1);
			Thread.sleep(500);
			intake.setIntake(0);
			arm.goToTop();
			elevator.goToBottom();
			Thread.sleep(10000);
		}
		else
		{
			
		}
	}

}
