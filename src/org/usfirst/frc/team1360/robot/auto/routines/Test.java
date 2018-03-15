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
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.IntakeProvider;

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

		
		Waypoint[] points = new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(4, 4, Pathfinder.d2r(90))
				//new Waypoint(11, 18, 0)
		};

		PathfindFromFile path = new PathfindFromFile(10000, Robot.trajectory);
		path.runNow("To Scale");
		path.setWaypoint(38, "Start elevator");
		new ElevatorToTarget(1000, ElevatorProvider.SCALE_HIGH).runAfter("Start elevator", "Elevator scale");
		new Calibrate().runUntilFinish();
		waitFor("To Scale", 0);
		new FaceAngle(1000, 20).runUntilFinish();
		waitFor("Elevator scale", 0);
		arm.goToPosition(ArmProvider.POS_BOTTOM);
		new DriveToInch(1000, 6, 20, 6, false, false).runUntilFinish();
		intake.setClamp(IntakeProvider.FREE);
		intake.setIntake(-1);
		Thread.sleep(1000);
		intake.setIntake(0);
		arm.goToPosition(ArmProvider.POS_TOP);
		new ElevatorToTarget(1000, ElevatorProvider.POS_BOTTOM).runUntilFinish();
		arm.goToPosition(ArmProvider.POS_BOTTOM);
		new FaceAngle(1000, 165).runUntilFinish();
		intake.setIntake(1);
		new DriveToInch(1500, 60, 165, 6,  2, true, false).runUntilFinish();
		intake.setClamp(IntakeProvider.CLOSED);
		new ElevatorToTarget(1000, ElevatorProvider.SWITCH_HEIGHT).runUntilFinish();
		intake.setClamp(IntakeProvider.FREE);
		intake.setIntake(-1);
	}
}
