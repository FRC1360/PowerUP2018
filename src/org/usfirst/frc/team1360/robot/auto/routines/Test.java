package org.usfirst.frc.team1360.robot.auto.routines;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.auto.drive.DriveToDistance;
import org.usfirst.frc.team1360.robot.auto.drive.SweepTurn;

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

		Waypoint[] point = new Waypoint[] {
				new Waypoint(0,0,0),
				new Waypoint(0,0,0)
		};
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
		Trajectory trajectory = Pathfinder.generate(point, config);
		
		TankModifier modifier = new TankModifier(trajectory).modify(0.627);
		
		EncoderFollower left = new EncoderFollower(modifier.getLeftTrajectory());
		EncoderFollower right = new EncoderFollower(modifier.getRightTrajectory());
		
		left.configureEncoder(sensorInput.getLeftDriveEncoder(), 83, 0.127);
		right.configureEncoder(sensorInput.getRightDriveEncoder(), 83, 0.127);
		
		left.configurePIDVA(1.0, 0.0, 0.0, 1 / 1, 0);
		right.configurePIDVA(1.0, 0.0, 0.0, 1 / 1, 0);
		
		double l, r, turn;
		
		while(!left.isFinished() || !right.isFinished()) {
			l = left.calculate(sensorInput.getLeftDriveEncoder());
			r = right.calculate(sensorInput.getRightDriveEncoder());
			
			turn = 0.8 * (-1.0/80.0) * (Pathfinder.d2r(left.getHeading()) - sensorInput.getAHRSYaw());
			
			robotOutput.tankDrive(l + turn, r - turn);
		}
		
		robotOutput.setIntake(1.0);
		Thread.sleep(10000);
		
	}
}
