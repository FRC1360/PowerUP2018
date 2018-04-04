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

    private final PathfindFromFile pathR;
    private final PathfindFromFile pathR2;
    private final PathfindFromFile pathR3;
    private final PathfindFromFile pathR4;
    private final PathfindFromFile pathR5;
    private final PathfindFromFile pathR6;
    private final PathfindFromFile pathR7;
    private final PathfindFromFile pathR8;
    private final PathfindFromFile pathR9;

    private final PathfindFromFile pathL;
    private final PathfindFromFile pathL2;
    private final PathfindFromFile pathL3;
    private final PathfindFromFile pathL4;
    private final PathfindFromFile pathL5;
    private final PathfindFromFile pathL6;
    private final PathfindFromFile pathL7;
    private final PathfindFromFile pathL8;
    private final PathfindFromFile pathL9;

    public SwitchThreeCube() {
	    super("Switch Three Cube", 0);

        pathR = new PathfindFromFile(10000, "switchR").cutOffFeet(0.1);
        pathR2 = new PathfindFromFile(10000, "switchR2").cutOffFeet(0.1).setReverse();
        pathR3 = new PathfindFromFile(10000, "switchR3")/*.cutOffFeet(0.1)*/;
        pathR4 = new PathfindFromFile(10000, "switchR4").cutOffFeet(0.1).setReverse();
        pathR5 = new PathfindFromFile(10000, "switchR5").cutOffFeet(0.1);
        pathR6 = new PathfindFromFile(10000, "switchR6").cutOffFeet(0.1).setReverse();
        pathR7 = new PathfindFromFile(10000, "switchR7")/*.cutOffFeet(0.1)*/;
        pathR8 = new PathfindFromFile(10000, "switchR8").cutOffFeet(0.1).setReverse();
        pathR9 = new PathfindFromFile(10000, "switchR9").cutOffFeet(0.1);

        pathL = new PathfindFromFile(10000, "switchL").cutOffFeet(0.1);
        pathL2 = new PathfindFromFile(10000, "switchL2").cutOffFeet(0.1).setReverse();
        pathL3 = new PathfindFromFile(10000, "switchL3")/*.cutOffFeet(0.1)*/;
        pathL4 = new PathfindFromFile(10000, "switchL4").cutOffFeet(0.1).setReverse();
        pathL5 = new PathfindFromFile(10000, "switchL5").cutOffFeet(0.1);
        pathL6 = new PathfindFromFile(10000, "switchL6").cutOffFeet(0.1).setReverse();
        pathL7 = new PathfindFromFile(10000, "switchL7")/*.cutOffFeet(0.1)*/;
        pathL8 = new PathfindFromFile(10000, "switchL8").cutOffFeet(0.1).setReverse();
        pathL9 = new PathfindFromFile(10000, "switchL9").cutOffFeet(0.1);
	}

	@Override
	protected void runCore() throws InterruptedException {
		//new Calibrate().runNow("Calibrate");
		
		if(!Robot.csvLoaded) return;
		
		if(fms.plateLeft(0)) {
            robotOutput.shiftGear(true);

            pathL.setWaypoint(7, "Start Intake");
            pathL.runNow("To Right Switch");

            new ElevatorToTarget(1500, elevator.ONE_FOOT*3).runNow("Elevator");
            arm.goToPosition(arm.POS_BOTTOM);

            waitFor("Elevator", 0);
            waitFor("Start Intake", 0);

            intake.setClamp(intake.FREE);
            robotOutput.setIntake(0.75);

            waitFor("To Right Switch", 0);

            intake.setIntake(0);
            robotOutput.tankDrive(-0.1, -0.1);


            //Second cube
            pathL2.setWaypoint(0.5, "Start elevator");

            pathL2.runNow("switchR2");
            waitFor("Start elevator", 0);
            elevator.goToTarget(elevator.POS_BOTTOM);
            arm.goToPosition(arm.POS_BOTTOM);
            waitFor("switchR2", 0);

            new FaceAngle(800, 30, 2)
                    .setLowGear()
                    .runNow("spin");

            intake.setIntake(-1);
            intake.setClamp(intake.FREE);
            arm.goToPosition(arm.POS_BOTTOM);

            waitFor("spin", 0);

            pathL3.runUntilFinish();

            pathL4.runUntilFinish();

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);
            arm.goToPosition(arm.POS_BOTTOM);

            new FaceAngle(1200, -30, 5)
                    .setLowGear()
                    .runNow("spin2");
            waitFor("spin2", 0);

            new ElevatorToTarget(1500, Elevator.ONE_FOOT*3).runNow("Elevator Switch 2");


            pathL5.setWaypoint(2, "Early outtake 2");
            pathL5.runNow("switchR5");

            waitFor("Elevator Switch 2", 0);
            waitFor("Early outtake 2", 0);

            intake.setIntake(0.75);
            intake.setClamp(intake.FREE);


            waitFor("switchR5", 0);
            intake.setIntake(0);


            //Third cube
            pathL6.setWaypoint(0.5, "Start Elevator cube 2");
            pathL6.runNow("path6R");

            waitFor("Start Elevator cube 2", 0);
            elevator.goToTarget(elevator.POS_BOTTOM);
            arm.goToPosition(arm.POS_BOTTOM);
            waitFor("path6R", 0);

            new FaceAngle(800, 45, 2)
                    .setLowGear()
                    .runUntilFinish();

            intake.setIntake(-1);
            arm.goToPosition(arm.POS_BOTTOM);
            pathL7.runUntilFinish();

            pathL8.runUntilFinish();

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);

            new FaceAngle(1200, -25, 5).setLowGear().runNow("spin4");
            waitFor("spin4", 0);

            new ElevatorToTarget(1500, Elevator.ONE_FOOT*2).runNow("Elevator Switch 3");

            pathL9.runUntilFinish();
            waitFor("Elevator Switch 3", 0);

            intake.setClamp(intake.FREE);
            intake.setIntake(0.75);

            Thread.sleep(1000);

		} else {
            robotOutput.shiftGear(true);

            pathR.setWaypoint(7, "Start Intake");
            pathR.runNow("To Right Switch");

            new ElevatorToTarget(1500, elevator.ONE_FOOT*3).runNow("Elevator");
            arm.goToPosition(arm.POS_BOTTOM);

            waitFor("Elevator", 0);
            waitFor("Start Intake", 0);

            intake.setClamp(intake.FREE);
            robotOutput.setIntake(0.75);

            waitFor("To Right Switch", 0);

            intake.setIntake(0);
            robotOutput.tankDrive(-0.1, -0.1);


            //Second cube
            pathR2.setWaypoint(0.5, "Start elevator");

            pathR2.runNow("switchR2");
            waitFor("Start elevator", 0);
            elevator.goToTarget(elevator.POS_BOTTOM);
            arm.goToPosition(arm.POS_BOTTOM);
            waitFor("switchR2", 0);

            new FaceAngle(800, -30, 2)
                    .setLowGear()
                    .runNow("spin");

            intake.setIntake(-1);
            intake.setClamp(intake.FREE);
            arm.goToPosition(arm.POS_BOTTOM);

            waitFor("spin", 0);

            pathR3.runUntilFinish();

            pathR4.runUntilFinish();

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);
            arm.goToPosition(arm.POS_BOTTOM);

            new FaceAngle(1200, 30, 5)
                    .setLowGear()
                    .runNow("spin2");
            waitFor("spin2", 0);

            new ElevatorToTarget(1500, Elevator.ONE_FOOT*3).runNow("Elevator Switch 2");


            pathR5.setWaypoint(2, "Early outtake 2");
            pathR5.runNow("switchR5");

            waitFor("Elevator Switch 2", 0);
            waitFor("Early outtake 2", 0);

            intake.setIntake(0.75);
            intake.setClamp(intake.FREE);


            waitFor("switchR5", 0);
            intake.setIntake(0);


            //Third cube
            pathR6.setWaypoint(0.5, "Start Elevator cube 2");
            pathR6.runNow("path6R");

            waitFor("Start Elevator cube 2", 0);
            elevator.goToTarget(elevator.POS_BOTTOM);
            arm.goToPosition(arm.POS_BOTTOM);
            waitFor("path6R", 0);

            new FaceAngle(800, -45, 2)
                    .setLowGear()
                    .runUntilFinish();

            intake.setIntake(-1);
            arm.goToPosition(arm.POS_BOTTOM);
            pathR7.runUntilFinish();

            pathR8.runUntilFinish();

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);

            new FaceAngle(1200, 25, 5).setLowGear().runNow("spin4");
            waitFor("spin4", 0);

            new ElevatorToTarget(1500, Elevator.ONE_FOOT*2).runNow("Elevator Switch 3");

            pathR9.runUntilFinish();
            waitFor("Elevator Switch 3", 0);

            intake.setClamp(intake.FREE);
            intake.setIntake(0.75);

            Thread.sleep(1000);
		}
	}
}
