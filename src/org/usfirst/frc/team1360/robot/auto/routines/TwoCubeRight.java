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
		new Calibrate().runNow("Calibration");
		
		if(fms.plateLeft(1)) {
			
		}
		else
		{
			elevator.goToTarget(elevator.ONE_FOOT*4);
			
			new DriveToDistance(10000, position.getX(), 175, 0, 20, true).runUntilFinish();
			
			new SweepTurn45(10000, 48, true, false).runUntilFinish();

			arm.goToPosition(-15);
			elevator.goToTarget(elevator.POS_TOP);
			new DriveToDistance(10000, position.getX() + 20, position.getY() + 20, -45, 20, true).runUntilFinish();
			
			while(elevator.isMovingToTarget()) Thread.sleep(10);
			intake.setClamp(intake.FREE);
			intake.setIntake(0.5);
			Thread.sleep(500);
			
			arm.goToPosition(-40);
			elevator.goToBottom();
			
			if(fms.plateLeft(0)) {
				
			}
			else
			{
				new DriveBackwardsToDistance(10000, position.getX()+36, position.getY()-36, -45, 20, false).runUntilFinish();;
				new SweepTurn(10000, 50, true, false).runUntilFinish();
				
				arm.goToPosition(arm.POS_BOTTOM);
				intake.setClamp(intake.FREE);
				intake.setIntake(-1);
				
				new DriveToDistance(10000, position.getX()+10, position.getY()+10, -135, 5, false).runUntilFinish();
				elevator.goToTarget(elevator.SWITCH_HEIGHT);
				new DriveBackwardsToDistance(10000, position.getX()-10, position.getY()-10, -135, 5, false).runUntilFinish();
				
				intake.setClamp(intake.FREE);
				intake.setIntake(-0.5);
				
			}
		}
		
	}

}
