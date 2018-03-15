package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.ElevatorToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.PathfindFromFile;
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
			PathfindFromFile path = new PathfindFromFile(10000, Robot.trajectory);
			path.runNow("To Left Switch");
			path.setWaypoint(7, "Start Elevator");
			
			waitFor("Calibrate", 0);
			arm.goToPosition(-40);
			
			new ElevatorToTarget(2000, elevator.ONE_FOOT*3).runAfter("Start Elevator", "Elevator Switch");

			waitFor("To Left Switch", 0);
			robotOutput.tankDrive(0, 0);
			matchLogger.writeClean("SWITCH: STARTING OUTTAKE");
			
			new DriveToInch(750, 6, 0, 4, false, false).runUntilFinish();
			
			intake.setClamp(intake.FREE);
			robotOutput.setIntake(0.75);
			Thread.sleep(500);
			robotOutput.setIntake(0);
			arm.goToTop();
			elevator.goToBottom();
			
			Thread.sleep(2000);
			
		} else {
			
			PathfindFromFile path = new PathfindFromFile(10000, Robot.trajectory);
			path.runNow("To Right Switch");
			
			waitFor("Calibrate", 0);
			arm.goToPosition(-40);
			
			new ElevatorToTarget(2000, elevator.ONE_FOOT*2).runNow("Elevator");
			
			waitFor("Elevator", 0);
			waitFor("To Right Switch", 0);
			robotOutput.tankDrive(0, 0);
	
			intake.setClamp(intake.FREE);
			robotOutput.setIntake(0.75);
			Thread.sleep(500);
			intake.setIntake(0);
			arm.goToTop();
			elevator.goToBottom();
			
			Thread.sleep(2000);
		}
	}

}
