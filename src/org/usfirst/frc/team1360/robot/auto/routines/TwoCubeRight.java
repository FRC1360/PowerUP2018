package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.ElevatorToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.FaceAngle;
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
		
		if(fms.plateLeft(1) && fms.plateLeft(0)) {
			matchLogger.writeClean("AUTO DEBUG LOG 1: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			new DriveToInch(5000, 128, 0, 10, true).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 2: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
			sensorInput.getAHRSYaw());
			
			
			new SweepTurn(10000, 90, 72, 10, true, false).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 3: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			new DriveToInch(5000, 126, -90, 7.5, true).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 4: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());

			new SweepTurn(2000, 130, 30, 7.5, false, false).runUntilFinish();//36
			
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
			
			new ElevatorToTarget(2000, elevator.POS_BOTTOM).runUntilFinish();
			intake.setIntake(0);

			arm.goToPosition(arm.POS_BOTTOM);
			
			intake.setIntake(-1);
			intake.setClamp(intake.FREE);
			
			//BUG DOESN'T REACH
			matchLogger.writeClean("AUTO DEBUG LOG 6: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			new SweepTurn(2000, 105, 24, 7.5, false, true).runUntilFinish();//100
			
			matchLogger.writeClean("AUTO DEBUG LOG 7: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			new DriveToInch(2000, 145, 170, 7.5, false).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 8: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			
			intake.setIntake(0);
			intake.setClamp(intake.CLOSED);
			arm.goToPosition(arm.POS_BOTTOM+5);
			new ElevatorToTarget(750, elevator.ONE_FOOT*2).runUntilFinish();
			intake.setIntake(1);
			intake.setClamp(intake.FREE);
			new DriveToInch(1000, 6, 190, 10, true).runUntilFinish(); //159
			robotOutput.tankDrive(0, 0);
			Thread.sleep(1000);
		}
		else if(!fms.plateLeft(0) && fms.plateLeft(1))
		{
			//FIRST CUBE
			 new DriveToInch(5000, 120, 0, 10, true).runUntilFinish();
			 
			 new SweepTurn(2000, 90, 24, 10, true, false).runUntilFinish();
			 
			 //SECOND CUBE
			 new SweepTurn(5000, 270, 36, 7, true, true).runUntilFinish();
			 
			 new DriveToInch(5000, 185, 270, 10, 1, true).runUntilFinish();
			 
			 
		}
		
	}

}
