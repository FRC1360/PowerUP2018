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

    public ThreeCubeScale() {
    	super("Three Cube Scale", 0);

    	scalePathR1 = new PathfindFromFile(10000, "scaleRRR1").startAndGoReverse();
		scalePathR2 = new PathfindFromFile(10000, "scaleRRR2").startReverse();
		scalePathR3 = new PathfindFromFile(10000, "scaleRRR3").startAndGoReverse();
		scalePathR4 = new PathfindFromFile(10000, "scaleRRR4").startReverse();
		scalePathR5 = new PathfindFromFile(10000, "scaleRRR5").startAndGoReverse();
    }

    @Override
    protected void runCore() throws InterruptedException
    {
		arm.goToPosition(arm.POS_TOP-200);

        if(fms.plateLeft(1)) { //L
        	


        }
        else { //R
        	//Start of first cube
			scalePathR1.runUntilFinish();

			elevator.goToTarget(elevator.POS_TOP);

			arm.goToPosition(arm.POS_BEHIND);
			while (sensorInput.getArmEncoder() > arm.POS_BEHIND+50) Thread.sleep(10);

			intake.setClamp(intake.FREE);
			intake.setIntake(1);
			Thread.sleep(1000);

			arm.goToPosition(arm.POS_BOTTOM);
			Thread.sleep(500);
			elevator.goToBottom();


			//2nd Cube
			intake.setIntake(-1);
			scalePathR2.runUntilFinish();
			intake.setClamp(intake.CLOSED);
			Thread.sleep(500);
			intake.setIntake(0);


			scalePathR3.runNow("Cube 2");
			waitFor("Cube 2", 0);

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

        }

    }

}
