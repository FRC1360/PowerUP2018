package org.usfirst.frc.team1360.robot.auto.routines;

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

public class ThreeCubeScale extends AutonRoutine{
	private PathfindFromFile scalePathR1;
	private PathfindFromFile scalePathR2;
	private PathfindFromFile scalePathR3;
	private PathfindFromFile scalePathR4;
	private PathfindFromFile scalePathR5;

	private PathfindFromFile scalePathL1;
	private PathfindFromFile scalePathL2;
	private PathfindFromFile scalePathL3;
	private PathfindFromFile scalePathL4;
	private PathfindFromFile scalePathL5;

    public ThreeCubeScale() {
    	super("Three Cube Scale", 0);

    	scalePathR1 = new PathfindFromFile(10000, "scaleRRR1").cutOffFeet(0.1).startAndGoReverse();
		scalePathR2 = new PathfindFromFile(10000, "scaleRRR2").startReverse();
		scalePathR3 = new PathfindFromFile(10000, "scaleRRR3").startAndGoReverse();
		scalePathR4 = new PathfindFromFile(10000, "scaleRRR4").startReverse();
		scalePathR5 = new PathfindFromFile(10000, "scaleRRR5").startAndGoReverse();

		scalePathL1 = new PathfindFromFile(10000, "scaleLLL1").cutOffFeet(0.1).startAndGoReverse();
		scalePathL2 = new PathfindFromFile(10000, "scaleLLL2").cutOffFeet(0.1).startReverse();
		scalePathL3 = new PathfindFromFile(10000, "scaleLLL3").cutOffFeet(0.1).startAndGoReverse();
		scalePathL4 = new PathfindFromFile(10000, "scaleLLL4").cutOffFeet(0.1).startAndGoReverse();
		scalePathL5 = new PathfindFromFile(10000, "scaleLLL5").cutOffFeet(0.1).startAndGoReverse();
    }

    @Override
    protected void runCore() throws InterruptedException
    {
		arm.goToPosition(arm.POS_TOP);


        if(fms.plateLeft(1)) { //L
        	robotOutput.shiftGear(false);

        	scalePathL1.setWaypoint(20, "Elevator Up");
        	scalePathL1.runNow("Scale Path 1");

        	waitFor("Elevator Up");
			elevator.goToTarget(elevator.POS_TOP);
			arm.goToPosition(arm.POS_BEHIND);

			while (sensorInput.getArmEncoder() > arm.POS_BEHIND+50) Thread.sleep(10);

			waitFor("Scale Path 1");

			Thread.sleep(500);

			intake.setClamp(intake.FREE);
			intake.setIntake(1.0);
			Thread.sleep(1000);

			arm.goToPosition(arm.POS_BOTTOM);
			elevator.goToTarget(elevator.POS_BOTTOM);

			intake.setClamp(intake.FREE);
			intake.setIntake(-1);
			scalePathL2.runUntilFinish();

			/**/

        }
        else { //R
        	//Start of first cube
			robotOutput.shiftGear(false);

			scalePathR1.setWaypoint(10, "Elevator Up");
			scalePathR1.runNow("Scale Path 1");

			waitFor("Elevator Up");
			elevator.goToTarget(elevator.POS_TOP);
			arm.goToPosition(arm.POS_BEHIND);
			while (sensorInput.getArmEncoder() > arm.POS_BEHIND+50) Thread.sleep(10);

			waitFor("Scale Path 1");

			Thread.sleep(500);

			intake.setClamp(intake.FREE);
			intake.setIntake(0.5);
			Thread.sleep(1000);


			arm.goToPosition(arm.POS_BOTTOM);
			/*
			Thread.sleep(500);
			elevator.goToBottom();


			/*

			//2nd Cube
			intake.setIntake(-1);
			scalePathR2.runUntilFinish();
			intake.setClamp(intake.CLOSED);
			Thread.sleep(500);
			intake.setIntake(0);

			scalePathR3.runNow("Cube 2");
			waitFor("Cube 2");

			elevator.goToTarget(elevator.POS_TOP);
			arm.goToPosition(arm.POS_BEHIND);
			while (sensorInput.getArmEncoder() > arm.POS_BEHIND+50) Thread.sleep(10);

			intake.setClamp(intake.FREE);
			intake.setIntake(0.5);

			arm.goToPosition(arm.POS_BOTTOM);
			Thread.sleep(500);
			elevator.goToBottom();
			Thread.sleep(1300);

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

        }

    }

}
