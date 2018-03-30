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

public class SwitchThreeCube extends AutonRoutine {
	
	public SwitchThreeCube() {
	    super("Switch Three Cube", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
		//new Calibrate().runNow("Calibrate");
		
		if(!Robot.csvLoaded) return;
		
		if(fms.plateLeft(0)) {
			PathfindFromFile path = new PathfindFromFile(10000, "switchL");
			path.runNow("To Left Switch");
			path.setWaypoint(7, "Start Elevator");
			
			//waitFor("Calibrate", 0);

			
			new ElevatorToTarget(1500, elevator.ONE_FOOT*2).runAfter("Start Elevator", "Elevator Switch");
            arm.goToPosition(arm.POS_BOTTOM);

			waitFor("To Left Switch", 0);
			robotOutput.tankDrive(0, 0);
			
			intake.setClamp(intake.FREE);
			robotOutput.setIntake(0.75);
			Thread.sleep(500);
			robotOutput.setIntake(0);
			elevator.goToBottom();

            //Second Cube
			PathfindFromFile path2 = new PathfindFromFile(10000, "switchL2");
			path2.setReverse();
			path2.runUntilFinish();

            intake.setIntake(-1);

            PathfindFromFile path3 = new PathfindFromFile(10000, "switchL3");
            path3.runUntilFinish();

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);

            new ElevatorToTarget(1500, elevator.ONE_FOOT*2).runNow("Elevator Switch2");

            PathfindFromFile path4 = new PathfindFromFile(10000, "switchL4");
            path4.setReverse();
            path4.runUntilFinish();
            waitFor("Elevator Switch2", 0);

            PathfindFromFile path5 = new PathfindFromFile(10000, "switchL5");
            path5.runUntilFinish();

            intake.setIntake(0.75);
            intake.setClamp(intake.FREE);
			Thread.sleep(500);
			intake.setIntake(0);

			//Third Cube
            PathfindFromFile path6 = new PathfindFromFile(10000, "switchL6");
            path6.setReverse();
            elevator.goToBottom();
            path6.runUntilFinish();

            PathfindFromFile path7 = new PathfindFromFile(10000, "switchL7");
            intake.setIntake(-1);
            path7.runUntilFinish();
            intake.setClamp(intake.CLOSED);
            intake.setIntake(0);

            PathfindFromFile path8 = new PathfindFromFile(10000, "switchL8");
            path8.setReverse();
            new ElevatorToTarget(1500, elevator.ONE_FOOT*2).runNow("Elevator Switch3");
            path8.runUntilFinish();

            PathfindFromFile path9 = new PathfindFromFile(10000, "switchL9");
            path9.runUntilFinish();
            waitFor("Elevator Switch3", 0);

            intake.setClamp(intake.FREE);
            intake.setIntake(0.75);

		} else {
			
			PathfindFromFile path = new PathfindFromFile(10000, "switchR");
			path.runNow("To Right Switch");
			
			//waitFor("Calibrate", 0);

			
			new ElevatorToTarget(1500, elevator.ONE_FOOT*2).runNow("Elevator");
			arm.goToPosition(arm.POS_BOTTOM);

			waitFor("Elevator", 0);
			waitFor("To Right Switch", 0);
			robotOutput.tankDrive(0, 0);
	
			intake.setClamp(intake.FREE);
			robotOutput.setIntake(0.75);
			Thread.sleep(500);
			intake.setIntake(0);

			elevator.goToBottom();

            //Second cube
            PathfindFromFile path2 = new PathfindFromFile(10000, "switchR2");
            path2.setReverse();
            path2.runUntilFinish();

            arm.goToPosition(arm.POS_BOTTOM);

            intake.setIntake(-1);
            intake.setClamp(intake.FREE);
            PathfindFromFile path3 = new PathfindFromFile(10000, "switchR3");
            path3.runUntilFinish();

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);

            PathfindFromFile path4 = new PathfindFromFile(10000, "switchR4");
            path4.setReverse();

            new ElevatorToTarget(1500, Elevator.ONE_FOOT*2).runNow("Elevator Switch 2");

            path4.runUntilFinish();

            PathfindFromFile path5 = new PathfindFromFile(10000, "switchR5");
            path5.runUntilFinish();

            waitFor("Elevator Switch 2", 0);

            intake.setIntake(0.75);
            intake.setClamp(intake.FREE);
            Thread.sleep(500);
            intake.setIntake(0);

            /*
            //Third cube
            PathfindFromFile path6 = new PathfindFromFile(10000, "switchR6");
            path6.setReverse();
            elevator.goToBottom();
            path6.runUntilFinish();


            PathfindFromFile path7 = new PathfindFromFile(10000, "switchR7");
            intake.setIntake(-1);
            path7.runUntilFinish();

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);

            PathfindFromFile path8 = new PathfindFromFile(10000, "switchR8");
            path8.setReverse();
            new ElevatorToTarget(1500, Elevator.ONE_FOOT*2).runNow("Elevator Switch 3");
            path8.runUntilFinish();

            PathfindFromFile path9 = new PathfindFromFile(10000, "switchR9");
            path8.setReverse();
            path8.runUntilFinish();
            waitFor("Elevator Switch 3", 0);

            intake.setClamp(intake.FREE);
            intake.setIntake(0.75);
            */

		}
	}
}
