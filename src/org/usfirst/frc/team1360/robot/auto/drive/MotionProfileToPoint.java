package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class MotionProfileToPoint extends AutonRoutine{
	
	private Waypoint[] point;

	private EncoderFollower left;
	private EncoderFollower right;
	
	private final double FPS = 10;
	private final int TICKS_PER_REV = 250; //TUNE
	private final int WHEEL_D = 5;
	private final double DT_WIDTH = 24.7;
	
	public MotionProfileToPoint(long timeout, double x, double y, double A) {
		super("Motion Profile", timeout);

		point = new Waypoint[] {
				new Waypoint(x, y, Pathfinder.d2r(A))
		};
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
		Trajectory trajectory = Pathfinder.generate(point, config);
		TankModifier modifier = new TankModifier(trajectory).modify(DT_WIDTH);
		
		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());
		
		left.configureEncoder(sensorInput.getLeftDriveEncoder(), TICKS_PER_REV, WHEEL_D); //current encoder pos, ticks per revolution, wheel diameter
		right.configureEncoder(sensorInput.getRightDriveEncoder(), TICKS_PER_REV, WHEEL_D); //current encoder pos, ticks per revolution, wheel diameter
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		left.configurePIDVA(1.0, 0.0, 0.0, 1 / FPS, 0);//THIS USES m/s so fix that tmrw
		left.configurePIDVA(1.0, 0.0, 0.0, 1 / FPS, 0);
		
		while(!left.isFinished() || !right.isFinished()) {
			double l = left.calculate(sensorInput.getLeftDriveEncoder());
			double r = right.calculate(sensorInput.getRightDriveEncoder());

			double gyro_heading = sensorInput.getAHRSYaw();
			double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

			double angleDifference = desired_heading - gyro_heading;
			double turn = 0.8 * (-1.0/80.0) * angleDifference;
			
			robotOutput.tankDrive(l + turn, r - turn);
		}
	}
}
