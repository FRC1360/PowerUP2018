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
    	super("Two Cube Scale", 0);

    	scalePathR1 = new PathfindFromFile(10000, "ScaleRRR1").setReverse();
		scalePathR2 = new PathfindFromFile(10000, "ScaleRRR2");
		scalePathR3 = new PathfindFromFile(10000, "ScaleRRR3").setReverse();
		scalePathR4 = new PathfindFromFile(10000, "ScaleRRR4");
		scalePathR5 = new PathfindFromFile(10000, "ScaleRRR5").setReverse();


    }

    @Override
    protected void runCore() throws InterruptedException
    {

        if(fms.plateLeft(1)) { //L
        	


        }
        else { //R
        	//Start of first cube
			scalePathR1.setWaypoint(10, "Start Cube");
			scalePathR1.runNow("To Scale");

			waitFor("Start Cube", 0);
			new ElevatorToTarget(1500, elevator.POS_TOP).runUntilFinish();
			arm.goToPosition(arm.POS_BEHIND);
			waitFor("To Scale", 0);

			intake.setClamp(intake.FREE);
			intake.setIntake(0.5);

			arm.goToPosition(arm.POS_BOTTOM);
			Thread.sleep(500);
			elevator.goToBottom();

			new FaceAngle(5000, 20, 2).runUntilFinish();

			//2nd Cube
			intake.setIntake(-1);
			scalePathR2.runUntilFinish();
			intake.setClamp(intake.CLOSED);
			Thread.sleep(500);
			intake.setIntake(0);


			scalePathR3.runNow("Cube 2");
			waitFor("Cube 2", 0);

			new ElevatorToTarget(1500, elevator.POS_TOP).runNow("Elevator Up");
			new FaceAngle(5000, -10, 2).runUntilFinish();
			Thread.sleep(500);
			arm.goToPosition(arm.POS_BEHIND);
			waitFor("Elevator Up", 0);

			intake.setClamp(intake.FREE);
			intake.setIntake(0.5);

			arm.goToPosition(arm.POS_BOTTOM);
			Thread.sleep(500);
			elevator.goToBottom();

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

        }

    }

}
