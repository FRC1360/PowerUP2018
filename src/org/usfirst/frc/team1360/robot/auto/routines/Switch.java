package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;
import org.usfirst.frc.team1360.robot.util.GetFMS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Switch extends AutonRoutine{

	public Switch() {
		super("Switch", 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void runCore() throws InterruptedException {
		//new Calibrate().run();

		if(fms.plateLeft(0)) {
			elevator.goToTarget(500);
			new SweepTurn(10000, 141/4, true).runUntilFinish();
			new DriveToDistance(10000, -90, position.getY(), -90, 10).runUntilFinish();
			new SweepTurn(10000, 141/4, false).runUntilFinish();
			new DriveToDistance(10000, position.getX(), 90, 0, 10).runUntilFinish();
			
			
		} else {

			elevator.goToTarget(700);
			arm.goToPosition(-30);

			new DriveToDistance(10000, 0, 100, 0, 10).runUntilFinish();//96 inches
	
			intake.setClamp(intake.FREE);
			intake.setIntake(-1);
			
			Thread.sleep(500);
			intake.setIntake(0);
			arm.goToTop();
			
			Thread.sleep(1000);
			
		}
	
	}

}
