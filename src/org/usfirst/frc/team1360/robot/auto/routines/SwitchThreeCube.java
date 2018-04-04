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

    private final PathfindFromFile path;
    private final PathfindFromFile path2;
    private final PathfindFromFile path3;
    private final PathfindFromFile path4;
    private final PathfindFromFile path5;
    private final PathfindFromFile path6;
    private final PathfindFromFile path7;
    private final PathfindFromFile path8;
    private final PathfindFromFile path9;

    public SwitchThreeCube() {
	    super("Switch Three Cube", 0);

        path = new PathfindFromFile(10000, "switchR").cutOffFeet(0.1);
        path2 = new PathfindFromFile(10000, "switchR2").cutOffFeet(0.1).setReverse();
        path3 = new PathfindFromFile(10000, "switchR3").cutOffFeet(0.1);
        path4 = new PathfindFromFile(10000, "switchR4").cutOffFeet(0.1).setReverse();
        path5 = new PathfindFromFile(10000, "switchR5").cutOffFeet(0.1);
        path6 = new PathfindFromFile(10000, "switchR6").cutOffFeet(0.1).setReverse();
        path7 = new PathfindFromFile(10000, "switchR7").cutOffFeet(0.1);
        path8 = new PathfindFromFile(10000, "switchR8").cutOffFeet(0.1).setReverse();
        path9 = new PathfindFromFile(10000, "switchR9").cutOffFeet(0.1);
	}

	@Override
	protected void runCore() throws InterruptedException {
		//new Calibrate().runNow("Calibrate");
		
		if(!Robot.csvLoaded) return;
		
		if(fms.plateLeft(0)) {


		} else {
            robotOutput.shiftGear(true);

            path.setWaypoint(7, "Start Intake");
            path.runNow("To Right Switch");

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
            path2.setWaypoint(0.5, "Start elevator");

            path2.runNow("switchR2");
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

            path3.runUntilFinish();

            path4.runUntilFinish();

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);
            arm.goToPosition(arm.POS_BOTTOM);

            new FaceAngle(1200, 30, 5)
                    .setLowGear()
                    .runNow("spin2");
            waitFor("spin2", 0);

            new ElevatorToTarget(1500, Elevator.ONE_FOOT*3).runNow("Elevator Switch 2");


            path5.setWaypoint(2, "Early outtake 2");
            path5.runNow("switchR5");

            waitFor("Elevator Switch 2", 0);
            waitFor("Early outtake 2", 0);

            intake.setIntake(0.75);
            intake.setClamp(intake.FREE);


            waitFor("switchR5", 0);
            intake.setIntake(0);


            //Third cube
            path6.setWaypoint(0.5, "Start Elevator cube 2");
            path6.runNow("path6R");

            waitFor("Start Elevator cube 2", 0);
            elevator.goToTarget(elevator.POS_BOTTOM);
            arm.goToPosition(arm.POS_BOTTOM);
            waitFor("path6R", 0);

            new FaceAngle(800, -45, 2)
                    .setLowGear()
                    .runUntilFinish();

            intake.setIntake(-1);
            arm.goToPosition(arm.POS_BOTTOM);
            path7.runUntilFinish();

            path8.runUntilFinish();

            intake.setIntake(0);
            intake.setClamp(intake.CLOSED);

            new FaceAngle(1200, 25, 5).setLowGear().runNow("spin4");
            waitFor("spin4", 0);

            new ElevatorToTarget(1500, Elevator.ONE_FOOT*2).runNow("Elevator Switch 3");

            path9.runUntilFinish();
            waitFor("Elevator Switch 3", 0);

            intake.setClamp(intake.FREE);
            intake.setIntake(0.75);

            Thread.sleep(1000);
		}
	}
}
