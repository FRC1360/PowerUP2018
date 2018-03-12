package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.ElevatorToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.FaceAngle;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TwoCubeRight extends AutonRoutine{

	public TwoCubeRight() {
		super("Two Cube Right", 0);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	protected void runCore() throws InterruptedException 
	{
		long start = System.currentTimeMillis();
		int offset;
		double TICK_INCH = 5.30516;
		new Calibrate().runNow("Calibrate");
		
		if(fms.plateLeft(1)) {
			matchLogger.writeClean("AUTO DEBUG LOG 1: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			new DriveToInch(5000, 128, 0, 10, true).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 2: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
			sensorInput.getAHRSYaw());
			
			
			new SweepTurn(10000, -90, 72, 10, true, false).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 3: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			new DriveToInch(5000, 126, -90, 7.5, true).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 4: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());

			new SweepTurn(2000, 40, 30, 7.5, false, false).runUntilFinish();//36
			
			matchLogger.writeClean("AUTO DEBUG LOG 5: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			//BUG STARTS HERE
			//elevator.startManual();
			//elevator.setManualSpeed(1.0, false);
			//while(sensorInput.getElevatorEncoder() < elevator.ONE_FOOT*3) Thread.sleep(10);
			
			new ElevatorToTarget(1500, (int) (elevator.ONE_FOOT*5)).runUntilFinish();
			arm.goToPosition(-20);
			Thread.sleep(1000);
			
			intake.setClamp(intake.FREE);
			intake.setIntake(1);
			Thread.sleep(250);
			arm.goToTop();
			/**/
			
			if(fms.plateLeft(0))
			{
				
				new ElevatorToTarget(2000, elevator.POS_BOTTOM).runUntilFinish();
				intake.setIntake(0);

				arm.goToPosition(arm.POS_BOTTOM);
				
				intake.setIntake(-1);
				intake.setClamp(intake.FREE);
				
				//BUG DOESN'T REACH
				matchLogger.writeClean("AUTO DEBUG LOG 6: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
						sensorInput.getAHRSYaw());
				
				new SweepTurn(2000, 160, 20, 7.5, false, false).runUntilFinish();//100
				
				matchLogger.writeClean("AUTO DEBUG LOG 7: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
						sensorInput.getAHRSYaw());
				
				new DriveToInch(2000, 90, 160, 7.5, false).runUntilFinish();
				
				matchLogger.writeClean("AUTO DEBUG LOG 8: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
						sensorInput.getAHRSYaw());
				
				
				intake.setIntake(0);
				intake.setClamp(intake.CLOSED);
				arm.goToPosition(arm.POS_BOTTOM+5);
				new ElevatorToTarget(750, elevator.ONE_FOOT*2).runUntilFinish();
				intake.setIntake(1);
				intake.setClamp(intake.FREE);
				new DriveToInch(1000, 6, 160, 10, true).runUntilFinish(); //159
				robotOutput.tankDrive(0, 0);
				SmartDashboard.putNumber("Auto time", (System.currentTimeMillis() - start) / 1000.0);
				
				
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
