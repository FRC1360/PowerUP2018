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

public class ScaleLeft extends AutonRoutine{
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

    public ScaleLeft() {
        super("Left Scale", 0);

        scalePathL1 = new PathfindFromFile(10000, "scaleL1").cutOffFeet(0.1).startAndGoReverse();
        scalePathL2 = new PathfindFromFile(10000, "scaleL2").cutOffFeet(0.1).startReverse();
        scalePathL3 = new PathfindFromFile(10000, "scaleL3").cutOffFeet(0.1).startAndGoReverse();
        scalePathL4 = new PathfindFromFile(10000, "scaleL4").cutOffFeet(0.1).startAndGoReverse();
        scalePathL5 = new PathfindFromFile(10000, "scaleL5").cutOffFeet(0.1).startAndGoReverse();
    }

    @Override
    protected void runCore() throws InterruptedException
    {
        arm.goToPosition(arm.POS_TOP);

        if(fms.plateLeft(1)) { //L
            robotOutput.shiftGear(false);

            scalePathL1.setWaypoint(30, "Elevator Up");
            scalePathL1.runNow("Scale Path 1");

            waitFor("Elevator Up");
            elevator.goToTarget(elevator.POS_TOP, 0.5);
            arm.goToPosition(arm.POS_BEHIND);

            while (sensorInput.getArmEncoder() > arm.POS_BEHIND+50) Thread.sleep(10);

            waitFor("Scale Path 1");

            Thread.sleep(500);

            intake.setClamp(intake.FREE);
            intake.setIntake(0.5);
            Thread.sleep(1000);

            arm.goToPosition(arm.POS_BOTTOM);
            elevator.goToTarget(elevator.POS_BOTTOM);

            intake.setClamp(intake.FREE);
            intake.setIntake(-1);

            //2nd Cube
            scalePathL2.runUntilFinish();

			/*

			intake.setClamp(intake.CLOSED);
			intake.setIntake(0);

			arm.goToPosition(arm.POS_BEHIND);
			elevator.goToTarget(elevator.POS_TOP);
			scalePathL2.runUntilFinish();

            while (sensorInput.getArmEncoder() > arm.POS_BEHIND+50) Thread.sleep(10);

            intake.setClamp(intake.FREE);
            intake.setIntake(0.5);
            Thread.sleep(1000);

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);

            arm.goToPosition(arm.POS_BOTTOM);
            elevator.goToTarget(elevator.POS_BOTTOM);





			/**/

        }


    }

}
