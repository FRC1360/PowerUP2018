package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.ElevatorToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.FaceAngle;
import org.usfirst.frc.team1360.robot.auto.drive.PathfindFromFile;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;

public class TwoCubeRight extends AutonRoutine{

	public TwoCubeRight() {
		super("Two Cube Right", 0);
	}
	

	@Override
	protected void runCore() throws InterruptedException 
	{
		//new Calibrate().runNow("Calibrate");
		
		if(fms.plateLeft(0) && fms.plateLeft(1)) { //LL
			PathfindFromFile path = new PathfindFromFile(10000, "switchLScaleL");
			path.runNow("To Scale");
			waitFor("To Scale", 0);
			
			new FaceAngle(2000, 20).runNow("spin");
			
			new ElevatorToTarget(2000, ElevatorProvider.SCALE_HIGH-50).runUntilFinish();
			
			waitFor("spin", 0);
			arm.goToPosition(-30);
			
			intake.setClamp(IntakeProvider.FREE);
			intake.setIntake(1);
			Thread.sleep(500);
			intake.setIntake(0);
			arm.goToPosition(ArmProvider.POS_TOP);
			
			new ElevatorToTarget(1000, ElevatorProvider.POS_BOTTOM).runNow("Elevator down");
			while (sensorInput.getElevatorEncoder() > elevator.ONE_FOOT * 2) Thread.sleep(10);
			arm.goToPosition(ArmProvider.POS_BOTTOM);
			new FaceAngle(1000, 153).runUntilFinish();


			

		}
		else if(fms.plateLeft(0) && !fms.plateLeft(1)) { //LR
			
			PathfindFromFile switch1 = new PathfindFromFile(10000, "switchLScaleR");
			switch1.runNow("Switch 1");
			waitFor("Switch 1", 0);
			
			new ElevatorToTarget(2000, (int) (elevator.ONE_FOOT*2)).runUntilFinish();
			robotOutput.setClamp(intake.FREE);
			robotOutput.setIntake(0.5);
			Thread.sleep(1000);
			robotOutput.setClamp(intake.FREE);
			robotOutput.setIntake(0);
		}
		else if(!fms.plateLeft(0) && !fms.plateLeft(1)) { //RR
			//Start of first scale
			PathfindFromFile scalePath = new PathfindFromFile(10000, "switchRScaleR");
			scalePath.runNow("To Scale");
			
//			new ElevatorToTarget(2000, ElevatorProvider.ONE_FOOT*2).runNow("Elevator Scale");
			elevator.safety(0.15, false);


			arm.goToPosition(-20);
			
			waitFor("To Scale", 0);
			robotOutput.tankDrive(0, 0);
			
			//waitFor("Elevator Scale", 0);
			matchLogger.writeClean("SCALE ELEVATOR DONE");
			
			intake.setIntake(1);
			intake.setClamp(intake.FREE);
			Thread.sleep(500);
			intake.setIntake(0);
			intake.setClamp(intake.CLOSED);
			
			arm.goToTop();
			new ElevatorToTarget(2000, ElevatorProvider.POS_BOTTOM).runUntilFinish();

			arm.goToPosition(arm.POS_BOTTOM);
			new FaceAngle(2000, -162).runUntilFinish();
			
			//Start of switch
			intake.setIntake(-1);
			intake.setClamp(intake.FREE);
			new DriveToInch(10000, 40, -162, 10, 3, true, false).runUntilFinish();
			robotOutput.tankDrive(0, 0);
			matchLogger.writeClean("SCALE DONE DRIVING");
			
			Thread.sleep(500);
			intake.setIntake(0);
			intake.setClamp(intake.CLOSED);
			
			new ElevatorToTarget(2000, ElevatorProvider.ONE_FOOT*3).runNow("Elevator");
			Thread.sleep(750);
			new DriveToInch(1000, 3, -162, 4, true, false).runNow("Approach");
			
			intake.setIntake(1);
			intake.setClamp(intake.FREE);
			waitFor("Approach", 0);
			Thread.sleep(1000);
			intake.setIntake(0);
		}
		else if(!fms.plateLeft(0) && fms.plateLeft(1)) { //RL

			PathfindFromFile switch1 = new PathfindFromFile(10000, "switchRScaleL1");
			PathfindFromFile switch2 = new PathfindFromFile(10000, "switchRScaleL2");
			switch1.runNow("Switch 1");
			new ElevatorToTarget(2000, elevator.ONE_FOOT * 3).runUntilFinish();
			arm.goToPosition(arm.POS_BOTTOM);
			waitFor("Switch 1", 0);
			new FaceAngle(2000, -90).runUntilFinish();
			intake.setIntake(1);
			intake.setClamp(intake.FREE);
			Thread.sleep(1000);
			intake.setIntake(0);
			arm.goToTop();
			new ElevatorToTarget(2000, elevator.POS_BOTTOM).runUntilFinish();
			new FaceAngle(2000, 0).runUntilFinish();
			arm.goToPosition(arm.POS_BOTTOM);
			switch2.runUntilFinish();
			new FaceAngle(2000, -160).runUntilFinish();
			intake.setIntake(-1);
			new DriveToInch(2000, 24, -160, 5, true, false).runUntilFinish();
			Thread.sleep(200);
			intake.setClamp(intake.CLOSED);
			intake.setIntake(0);
			new ElevatorToTarget(2000, elevator.ONE_FOOT * 3).runUntilFinish();
			intake.setClamp(intake.FREE);
			intake.setIntake(1);
			Thread.sleep(1000);
			intake.setIntake(0);
		}
	}

}
