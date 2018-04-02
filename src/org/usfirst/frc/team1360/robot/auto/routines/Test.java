package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.Robot;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.Calibrate;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToInch;
import org.usfirst.frc.team1360.robot.auto.drive.ElevatorToTarget;
import org.usfirst.frc.team1360.robot.auto.drive.FaceAngle;
import org.usfirst.frc.team1360.robot.auto.drive.PathfindFromFile;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.Elevator;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;



public final class Test extends AutonRoutine {

	public Test() {
		super("Test", 0);
	}

	@Override
	protected void runCore() throws InterruptedException {
//		new FaceAngle(1500, 90).runUntilFinish();
		
//		/*
		robotOutput.shiftGear(true);
		PathfindFromFile path = new PathfindFromFile(10000, "switchR");
		path.runNow("To Right Switch");
		
		path.setWaypoint(7, "Start Intake");
		
		new ElevatorToTarget(1500, elevator.ONE_FOOT*3).runNow("Elevator");
		arm.goToPosition(arm.POS_BOTTOM);

		waitFor("Elevator", 0);
		waitFor("Start Intake", 0);
		
		intake.setClamp(intake.FREE);
		robotOutput.setIntake(0.75);
		Thread.sleep(300);
		
		waitFor("To Right Switch", 0);
		intake.setIntake(0);
		robotOutput.tankDrive(-0.1, -0.1);


        //Second cube
        PathfindFromFile path2 = new PathfindFromFile(10000, "switchR2");
        path2.setReverse();
        path2.setWaypoint(1, "Start elevator");
        
        Thread.sleep(200);
        path2.runNow("switchR2");
        waitFor("Start elevator", 0);
        elevator.goToTarget(elevator.POS_BOTTOM);
        arm.goToPosition(arm.POS_BOTTOM);
        waitFor("switchR2", 0);
        
        
		new FaceAngle(800, -30, 2, true).runNow("spin");

        intake.setIntake(-1);
        intake.setClamp(intake.FREE);
        
		waitFor("spin", 0);
        
        PathfindFromFile path3 = new PathfindFromFile(10000, "switchR3");
        path3.runUntilFinish();

        Thread.sleep(100);
        intake.setIntake(0);
        intake.setClamp(intake.CLOSED);

        PathfindFromFile path4 = new PathfindFromFile(10000, "switchR4");
        path4.setReverse();

        path4.runUntilFinish();
        
		new FaceAngle(1200, 30, 5, true).runNow("spin2");
		waitFor("spin2", 0);

        new ElevatorToTarget(1500, Elevator.ONE_FOOT*3).runNow("Elevator Switch 2");

        PathfindFromFile path5 = new PathfindFromFile(10000, "switchR5");
        path5.setWaypoint(3, "Early outtake 2");
        path5.runNow("switchR5");

        waitFor("Elevator Switch 2", 0);
        waitFor("Early outtake 2", 0);

        intake.setIntake(0.75);
        intake.setClamp(intake.FREE);
        Thread.sleep(200);
        waitFor("switchR5", 0);
        intake.setIntake(0);


        //Third cube
        PathfindFromFile path6 = new PathfindFromFile(10000, "switchR6");
        path6.setReverse();
        path6.runUntilFinish();

        new ElevatorToTarget(1500, Elevator.POS_BOTTOM).runNow("Elevator cube 2");
        arm.goToPosition(arm.POS_BOTTOM);
        
        new FaceAngle(800, -45, 2, true).runNow("spin3");
		waitFor("spin3", 0);
		waitFor("Elevator cube 2", 0);

        PathfindFromFile path7 = new PathfindFromFile(10000, "switchR7");
        intake.setIntake(-1);
        path7.runUntilFinish();

        Thread.sleep(100);
        intake.setIntake(0);
        intake.setClamp(intake.CLOSED);

        PathfindFromFile path8 = new PathfindFromFile(10000, "switchR8");
        path8.setReverse();
        new ElevatorToTarget(1500, Elevator.ONE_FOOT*2).runNow("Elevator Switch 3");
        path8.runUntilFinish();
        
        new FaceAngle(1200, 25, 5, true).runNow("spin4");
		waitFor("spin4", 0);

        PathfindFromFile path9 = new PathfindFromFile(10000, "switchR9");
        path9.runUntilFinish();
        waitFor("Elevator Switch 3", 0);

        intake.setClamp(intake.FREE);
        intake.setIntake(0.75);

        Thread.sleep(1000);
//        */
	}
}
