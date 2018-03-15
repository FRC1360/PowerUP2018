package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.ElevatorToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.FaceAngle;
import org.usfirst.frc.team1360.robot.auto.drive.PathfindFromFile;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TwoCubeRight extends AutonRoutine{

	public TwoCubeRight() {
		super("Two Cube Right", 0);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	protected void runCore() throws InterruptedException 
	{
		new Calibrate().runNow("Calibrate");
		
		if(fms.plateLeft(0) && fms.plateLeft(1)) { //LL
			PathfindFromFile path = new PathfindFromFile(10000, Robot.trajectorySwitchLScaleL);
			path.runNow("To Scale");
			waitFor("To Scale", 0);
			
			new FaceAngle(1000, 20).runNow("spin");
			
			new ElevatorToTarget(2000, ElevatorProvider.SCALE_HIGH-50).runNow("Elevator Scale");
			
			waitFor("spin", 0);
			arm.goToPosition(-30);
			waitFor("Elevator Scale", 0);
			
			
			intake.setClamp(IntakeProvider.FREE);
			intake.setIntake(1);
			Thread.sleep(500);
			intake.setIntake(0);
			arm.goToPosition(ArmProvider.POS_TOP);
			
			new ElevatorToTarget(1000, ElevatorProvider.POS_BOTTOM).runUntilFinish();
			arm.goToPosition(ArmProvider.POS_BOTTOM);
			new FaceAngle(1000, 150).runUntilFinish();
			
			intake.setIntake(-1);
			new DriveToInch(1500, 50, 150, 6,  2, true, false).runUntilFinish();
			intake.setClamp(IntakeProvider.CLOSED);
			new ElevatorToTarget(1000, ElevatorProvider.ONE_FOOT*3).runUntilFinish();
			
			intake.setClamp(IntakeProvider.FREE);
			intake.setIntake(1);
			Thread.sleep(1000);
		}
		else if(fms.plateLeft(0) && !fms.plateLeft(1)) {
			//Start of first scale
			PathfindFromFile scalePath = new PathfindFromFile(4800, Robot.trajectorySwitchLScaleR1);
			PathfindFromFile switchPath = new PathfindFromFile(4500, Robot.trajectorySwitchLScaleR2);
			scalePath.runNow("To Scale");
			
//			new ElevatorToTarget(2000, ElevatorProvider.ONE_FOOT*2).runNow("Elevator Scale");
			elevator.safety(0.15, false);
			
			waitFor("Calibrate", 0);
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
			
			new FaceAngle(1000, -150).runUntilFinish();
			
			//Start of switch
			switchPath.runNow("To Switch");
			arm.goToPosition(-45);
			
			waitFor("To Switch", 0);

			intake.setIntake(-1);
			intake.setClamp(intake.FREE);
			new FaceAngle(1500, -180).runUntilFinish();
			robotOutput.tankDrive(0, 0);
			
			intake.setIntake(0);
			intake.setClamp(intake.CLOSED);
			new ElevatorToTarget(2000, ElevatorProvider.ONE_FOOT*3).runUntilFinish();
			new DriveToInch(2000, 4, -150, 5, true, false).runUntilFinish();
			robotOutput.tankDrive(0, 0);
			intake.setIntake(1);
			intake.setClamp(intake.FREE);
			
			Thread.sleep(1000);
			intake.setIntake(0);
			
		}
		else if(!fms.plateLeft(0) && !fms.plateLeft(0)) {
			//Start of first scale
			PathfindFromFile scalePath = new PathfindFromFile(10000, Robot.trajectorySwitchRScaleR);
			scalePath.runNow("To Scale");
			
			waitFor("To Scale", 0);
			new ElevatorToTarget(2000, ElevatorProvider.SCALE_HIGH-50).runNow("Elevator Scale");
			robotOutput.tankDrive(0, 0);
			
			waitFor("Elevator Scale", 0);
			matchLogger.writeClean("SCALE ELEVATOR DONE");
			
			waitFor("Calibrate", 0);
			arm.goToPosition(-20);
			Thread.sleep(500);
			
			matchLogger.writeClean("SCALE ELEVATOR AND PATH DONE");
			
			intake.setClamp(intake.FREE);
			intake.setIntake(1);
			Thread.sleep(750);
			intake.setIntake(0);
			intake.setClamp(intake.CLOSED);
			
			arm.goToTop();
			new ElevatorToTarget(2000, ElevatorProvider.POS_BOTTOM).runUntilFinish();
			
			new FaceAngle(1000, -162).runUntilFinish();
			arm.goToPosition(-45);
			
			//Start of switch
			intake.setIntake(-1);
			intake.setClamp(intake.FREE);
			new DriveToInch(10000, 25, -162, 3, true, false).runUntilFinish();
			robotOutput.tankDrive(0, 0);
			matchLogger.writeClean("SCALE DONE DRIVING");
			
			Thread.sleep(500);
			intake.setIntake(0);
			intake.setClamp(intake.CLOSED);
			
			new ElevatorToTarget(2000, ElevatorProvider.ONE_FOOT*3).runUntilFinish();
			
			intake.setIntake(1);
			intake.setClamp(intake.FREE);
			
			Thread.sleep(1000);
			intake.setIntake(0);
		}
		else if(!fms.plateLeft(0) && fms.plateLeft(1)) {
			//Run Switch
			PathfindFromFile switchPath = new PathfindFromFile(5000, "lmao.csv");
			PathfindFromFile cubePath = new PathfindFromFile(5000, "lmao.csv");
			switchPath.runNow("To Switch");
			
			new ElevatorToTarget(2000, ElevatorProvider.ONE_FOOT*3).runUntilFinish();
			waitFor("Calibrate", 0);
			arm.goToPosition(-30);
			
			waitFor("To Switch", 0);
			intake.setIntake(1);
			intake.setClamp(intake.FREE);
			Thread.sleep(500);
			
			intake.setIntake(0);
			arm.goToTop();
			elevator.goToBottom();
			
			new FaceAngle(1000, 0).runUntilFinish();
			
			//Run Scale
			cubePath.runNow("To Cube");
			Thread.sleep(1000);
			
			arm.goToPosition(-45);
			
			waitFor("To Cube", 0);
			intake.setIntake(-1);
			intake.setClamp(intake.FREE);
			
			new FaceAngle(1000, -180).runUntilFinish();
			new DriveToInch(1000, 6, -180, 6, false, false).runUntilFinish();
			intake.setIntake(0);
			intake.setClamp(intake.CLOSED);
			
			new FaceAngle(1000, 0).runUntilFinish();
			elevator.goToTop();
			new DriveToInch(1000, 30, 0, 6, false, false).runUntilFinish();
			
			intake.setClamp(intake.FREE);
			intake.setIntake(0.75);
			Thread.sleep(1000);
			intake.setIntake(0);
			intake.setClamp(intake.CLOSED);
		}
	}

}
