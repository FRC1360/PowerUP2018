package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.*;

public class DCMPSF extends AutonRoutine {
    private PathfindFromFile scalePathR1;
    private PathfindFromFile scalePathR2;
    private PathfindFromFile scalePathR3;
    private PathfindFromFile scalePathR4;
    private PathfindFromFile scalePathR5;

    public DCMPSF() {
        super("DCMP Semifinals", 0);

        scalePathR1 = new PathfindFromFile(10000, "scaleRRR1").cutOffFeet(0.1).startAndGoReverse();
        scalePathR2 = new PathfindFromFile(10000, "scaleRRR2").startReverse();
        scalePathR3 = new PathfindFromFile(10000, "scaleRRR3").startAndGoReverse();
        scalePathR4 = new PathfindFromFile(10000, "scaleRRR4").startReverse();
        scalePathR5 = new PathfindFromFile(10000, "scaleRRR5").startAndGoReverse();
    }

    @Override
    protected void runCore() throws InterruptedException {
        if (!Robot.csvLoaded) return;

        if (!fms.plateLeft(1)) { //LR+RR
            //Start of first cube
            robotOutput.shiftGear(false);

            scalePathR1.setWaypoint(10, "Elevator Up");
            scalePathR1.runNow("Scale Path 1");

            waitFor("Elevator Up");
            elevator.goToTarget(elevator.POS_TOP);
            arm.goToPosition(arm.POS_BEHIND);

            while (sensorInput.getArmEncoder() > arm.POS_BEHIND+50) Thread.sleep(10);

            waitFor("Scale Path 1");

            intake.setClamp(intake.FREE);
            intake.setIntake(0.5);
            Thread.sleep(200);


            arm.goToPosition(arm.POS_BOTTOM);
            while (sensorInput.getArmEncoder() < arm.POS_TOP) Thread.sleep(10);
            elevator.goToBottom();

            robotOutput.shiftGear(true);


            while (sensorInput.getElevatorEncoder() > elevator.POS_BOTTOM+100) Thread.sleep(10);


            //2nd Cube
            intake.setIntake(-1);
            scalePathR2.runUntilFinish();
            intake.setClamp(intake.CLOSED);
            Thread.sleep(250);
            intake.setIntake(0);


            scalePathR3.runNow("Cube 2");
            waitFor("Cube 2");

            elevator.goToTarget(elevator.POS_TOP);
            arm.goToPosition(arm.POS_BEHIND);
            while (sensorInput.getArmEncoder() > arm.POS_BEHIND+50) Thread.sleep(10);

            intake.setClamp(intake.FREE);
            intake.setIntake(0.7);

            arm.goToPosition(arm.POS_BOTTOM);
            while (sensorInput.getArmEncoder() < arm.POS_TOP) Thread.sleep(10);
            elevator.goToBottom();
            Thread.sleep(1300);
			/*

			/*
			//3rd Cube
			new FaceAngle(5000, 20, 2).runUntilFinish();

			intake.setIntake(-1);
			scalePathR4.runUntilFinish();
			intake.setClamp(intake.CLOSED);
			Thread.sleep(500);
			intake.setIntake(0);

			new ElevatorToTarget(1500, elevator.POS_TOP).runNow("Elevator Up2");
			scalePathR5.runNow("Cube 3");
			Thread.sleep(500);
			arm.goToPosition(arm.POS_BEHIND);
			waitFor("Cube 3", 0);

			new FaceAngle(5000, -10, 2).runUntilFinish();
			waitFor("Elevator Up2", 0);

			intake.setClamp(intake.FREE);
			intake.setIntake(0.5);

			arm.goToPosition(arm.POS_BOTTOM);
			Thread.sleep(500);
			elevator.goToBottom();
			*/
            Thread.sleep(2000);
        } else if (fms.plateLeft(0)) { //LL
            new DriveToInch(10000, -100, 0, 10, true, false).runUntilFinish();
        } else { //RL
            PathfindFromFile switch1 = new PathfindFromFile(10000, "dcmpSfRL").startAndGoReverse();
            switch1.runNow("Switch");
            elevator.goToTarget(elevator.ONE_FOOT*3);
            arm.goToPosition(arm.POS_BOTTOM);
            waitFor("Switch");
            new FaceAngle(1000, 90, 2).runUntilFinish();
            robotOutput.tankDrive(0.3, 0.3);
            Thread.sleep(500);
            robotOutput.tankDrive(0, 0);
            intake.setIntake(0.5);
            intake.setClamp(intake.FREE);
            Thread.sleep(1000);
        }
    }
}
