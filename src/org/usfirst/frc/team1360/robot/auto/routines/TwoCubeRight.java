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
		SmartDashboard.putNumber("Auto time", 0);
		int offset;
		double TICK_INCH = 5.30516;
		new Calibrate().runNow("Calibrate");
		
		if(fms.plateLeft(1) && fms.plateLeft(0)) {
			matchLogger.writeClean("AUTO DEBUG LOG 1: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			new DriveToInch(5000, 125, 0, 10, 8, true, false).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 2: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
			sensorInput.getAHRSYaw());
			
			
			new SweepTurn(10000, -90, 72, 10, true, false).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 3: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			new DriveToInch(5000, 120, -90, 10, 8, true, false).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 4: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());

			new SweepTurn(3000, 0, 30, 10, false, false).runUntilFinish();//36
			new FaceAngle(2000, 30).runUntilFinish();
			
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
			
//				new SweepTurn(2000, 160, 20, 7.5, false, false).runUntilFinish();//100
			new FaceAngle(3000, 160).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 7: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			new DriveToInch(2000, 90, 160, 7.5, false, false).runUntilFinish();
			
			matchLogger.writeClean("AUTO DEBUG LOG 8: " + "Drive Encoders = " + sensorInput.getLeftDriveEncoder() + " " + sensorInput.getRightDriveEncoder() + ", NAVX Angle = " +
					sensorInput.getAHRSYaw());
			
			
			intake.setIntake(0);
			intake.setClamp(intake.CLOSED);
			arm.goToPosition(arm.POS_BOTTOM+5);
			new ElevatorToTarget(750, elevator.ONE_FOOT*2).runUntilFinish();
			intake.setIntake(1);
			intake.setClamp(intake.FREE);
			new DriveToInch(1000, 6, 160, 10, true, false).runUntilFinish(); //159
			robotOutput.tankDrive(0, 0);
			intake.setIntake(0);
			SmartDashboard.putNumber("Auto time", (System.currentTimeMillis() - start) / 1000.0);
		}
		else if(!fms.plateLeft(0) && fms.plateLeft(1))
		{
			//FIRST CUBE
			 new DriveToInch(5000, 120, 0, 10, true, false).runUntilFinish();
			 
			 new SweepTurn(2000, -90, 24, 10, true, false).runUntilFinish();
			 
			 //SECOND CUBE
			 new SweepTurn(5000, -270, 36, -7, true, true).runUntilFinish();
			 
			 new DriveToInch(5000, 185, -270, 10, 1, true, false).runUntilFinish();
			 
			 new FaceAngle(3000, -238.39).runUntilFinish();
			 
			 new DriveToInch(2000, 12.41, -238.39, 4, false, false).runUntilFinish();
			 
			 new FaceAngle(2000, -356).runUntilFinish();
			 
			 new DriveToInch(5000, 72.32, -356, 6, true, false).runUntilFinish();
		}
		else if(!fms.plateLeft(0) && !fms.plateLeft(1))
		{
			new SweepTurn(5000, 11, 2700, 10, true, false).runUntilFinish();
			
			new FaceAngle(5000, -177).runUntilFinish();
			
			new DriveToInch(5000, 65.42, -177, 10, 1, true, false).runUntilFinish();
		}
		else if(fms.plateLeft(0) && !fms.plateLeft(1))
		{
			
		}
		
	}

}
