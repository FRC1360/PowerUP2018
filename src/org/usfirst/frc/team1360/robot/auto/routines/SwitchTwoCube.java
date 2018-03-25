package org.usfirst.frc.team1360.robot.auto.routines;

import jaci.pathfinder.Pathfinder;
import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.ElevatorToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.FaceAngle;
import org.usfirst.frc.team1360.robot.auto.drive.PathfindFromFile;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.Elevator;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;

public class SwitchTwoCube extends AutonRoutine {
	
	public SwitchTwoCube() {
	    super("Switch Two Cube", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
		// TODO Auto-generated method stub
		new Calibrate().runNow("Calibrate");
		
		if(!Robot.csvLoaded) return;
		
		if(fms.plateLeft(0)) {
			PathfindFromFile path = new PathfindFromFile(10000, Robot.trajectorySwitchL);
			path.runNow("To Left Switch");
			path.setWaypoint(7, "Start Elevator");
			
			waitFor("Calibrate", 0);
			arm.goToPosition(-40);
			
			new ElevatorToTarget(1500, elevator.ONE_FOOT*2).runAfter("Start Elevator", "Elevator Switch");

			waitFor("To Left Switch", 0);
			robotOutput.tankDrive(0, 0);
			
			new DriveToInch(750, 6, 0, 4, false, false).runUntilFinish();
			
			intake.setClamp(intake.FREE);
			robotOutput.setIntake(0.75);
			Thread.sleep(500);
			robotOutput.setIntake(0);
			elevator.goToBottom();


			PathfindFromFile path2 = new PathfindFromFile(10000, Robot.trajectorySwitchL2);
			path2.setReverse();
			path2.runUntilFinish();

            intake.setIntake(-1);

            PathfindFromFile path3 = new PathfindFromFile(10000, Robot.trajectorySwitchL3);
            path3.runUntilFinish();

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);

            new ElevatorToTarget(1500, elevator.ONE_FOOT*2).runNow("Elevator Switch2");

            PathfindFromFile path4 = new PathfindFromFile(10000, Robot.trajectorySwitchL4);
            path4.setReverse();
            path3.runUntilFinish();
            waitFor("Elevator Switch2", 0);

            PathfindFromFile path5 = new PathfindFromFile(10000, Robot.trajectorySwitchL5);
            path3.runUntilFinish();

            intake.setIntake(0.75);
            intake.setClamp(intake.FREE);

			Thread.sleep(2000);
			
		} else {
			
			PathfindFromFile path = new PathfindFromFile(10000, Robot.trajectorySwitchR);
			path.runNow("To Right Switch");
			
			waitFor("Calibrate", 0);
			arm.goToPosition(-40);
			
			new ElevatorToTarget(1500, elevator.ONE_FOOT*2).runNow("Elevator");
			
			waitFor("Elevator", 0);
			waitFor("To Right Switch", 0);
			robotOutput.tankDrive(0, 0);
	
			intake.setClamp(intake.FREE);
			robotOutput.setIntake(0.75);
			Thread.sleep(500);
			intake.setIntake(0);
			arm.goToTop();
			elevator.goToBottom();

            PathfindFromFile path2 = new PathfindFromFile(10000, Robot.trajectorySwitchR2);
            path2.setReverse();
            path2.runUntilFinish();

            intake.setIntake(-1);
            intake.setClamp(intake.FREE);
            PathfindFromFile path3 = new PathfindFromFile(10000, Robot.trajectorySwitchR3);
            path3.runUntilFinish();

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);

            PathfindFromFile path4 = new PathfindFromFile(10000, Robot.trajectorySwitchR4);
            path4.setReverse();
            new ElevatorToTarget(1500, Elevator.ONE_FOOT*2).runNow("Elevator Switch 2");
            path4.runUntilFinish();

            PathfindFromFile path5 = new PathfindFromFile(10000, Robot.trajectorySwitchR5);
            path5.runUntilFinish();

            waitFor("Elevator Switch 2", 0);

            intake.setIntake(0.75);
            intake.setClamp(intake.FREE);
            Thread.sleep(500);
            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);


		}
		
	}
}
