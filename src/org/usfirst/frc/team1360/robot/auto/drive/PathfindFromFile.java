package org.usfirst.frc.team1360.robot.auto.drive;

import java.io.File;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public class PathfindFromFile extends AutonRoutine{

	final double DT_WIDTH = 2.05;
	final int TICKS_PER_REV = 250/3;
	final double WHEEL_SIZE = 0.4166;
	final double TIME_STEP = 0.025;
	final double MAX_FPS = 7;

	private double totalLength;

	private Trajectory trajectory;

	private EncoderFollower left;
	private EncoderFollower right;

	private int direction = 1; //1=forward; -1=backward;
	private double subtractFromNavx = 0;

	public PathfindFromFile(long timeout, String file) {
		super("Pathfind From File", timeout);
		File profile;
		File rightProfile;

		profile = new File("/home/lvuser/" + file + ".csv");

		if (!profile.exists())
			return;

		this.trajectory = Pathfinder.readFromCSV(profile);

		TankModifier modifier = new TankModifier(trajectory).modify(DT_WIDTH);

		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());

		this.totalLength = trajectory.segments[trajectory.length()-1].position;
	}

	public PathfindFromFile cutOffFeet(double feet){
		this.totalLength -= feet;
		return this;
	}
	
	public double getPosition() {
		try {
			return left == null ? 0 : left.getSegment().position;
		} catch (Throwable t) {
			return Double.NaN;
		}
	}

	public PathfindFromFile setReverse() {
		this.direction = -1;
		return this;
	}

	public PathfindFromFile startAndGoReverse() {
		this.direction = -1;
		this.subtractFromNavx = 180;
		return this;
	}

	public PathfindFromFile startReverse() {
		this.subtractFromNavx = 180;
		return this;
	}

	public void setWaypoint(double position, String name) {
		new WaypointComponent(position).runNow(name);
	}

	private double nearAngle(double angle, double reference) {
		return Math.round((reference - angle) / 360) * 360 + angle;
	}

	@Override
	protected void runCore() throws InterruptedException {
		if(trajectory == null) {
			return;
		}

		sensorInput.resetLeftEncoder();
		sensorInput.resetRightEncoder();

		left.configureEncoder(sensorInput.getLeftDriveEncoder(), TICKS_PER_REV, WHEEL_SIZE);
		right.configureEncoder(sensorInput.getRightDriveEncoder(), TICKS_PER_REV, WHEEL_SIZE);

		left.configurePIDVA(2.5, 0.0, 0.3, 1/MAX_FPS, 0);//D = 0.5 P = 4
		right.configurePIDVA(2.5, 0.0, 0.3, 1/MAX_FPS, 0);

		double l, r, turn, angleDifference;

		long time = System.currentTimeMillis();

		matchLogger.write("PATHFINDER STARTING");

		OrbitPID turnPID = new OrbitPID(0.075, 0.03, 0.0);

		while((!left.isFinished() || !right.isFinished()) && isLessThanTargetFeet()) {

			if(System.currentTimeMillis() - time >= TIME_STEP * 1000)
			{
				time = System.currentTimeMillis();
				l = left.calculate((direction>0?sensorInput.getLeftDriveEncoder():sensorInput.getRightDriveEncoder())*direction);
				r = right.calculate((direction>0?sensorInput.getRightDriveEncoder():sensorInput.getLeftDriveEncoder())*direction);

				//angleDifference = Pathfinder.boundHalfDegrees(Pathfinder.r2d(left.getHeading()) - sensorInput.getAHRSYaw());

				//turn = 0.0375 * angleDifference + (0.0025 * (angleDifference / 0.05));

				double yaw = direction > 0 ? -sensorInput.getAHRSYaw()+subtractFromNavx : -(sensorInput.getAHRSYaw()+(180-subtractFromNavx));
				turn = turnPID.calculate(nearAngle(direction > 0 ? Pathfinder.r2d(left.getHeading()) : Pathfinder.r2d(left.getHeading()), yaw), yaw);
				//turn = 0;

				matchLogger.write(String.format("PATHFINDER heading = %f3, actual = %f3, turn = %f3, l = %f3, r = %f3, pos = %f3", Pathfinder.r2d(left.getHeading()), -sensorInput.getAHRSYaw(), turn/10, l, r, getPosition()));

				l -= turn;
				r += turn;

				if (Math.abs(l) > 1 || Math.abs(r) > 1) {
					if (Math.abs(l) > Math.abs(r)) {
						r /= Math.abs(l);
						l = Math.signum(l);
					} else {
						l /= Math.abs(r);
						r = Math.signum(r);
					}
				}

				robotOutput.tankDrive((direction>0?l:r)*direction, (direction>0?r:l)*direction);
			}

			Thread.sleep(5);
		}

		robotOutput.tankDrive(0, 0);
	}

	private boolean isLessThanTargetFeet() {
		return (left.getSegment().position + right.getSegment().position) / 2 < totalLength;
	}

	private final class WaypointComponent extends AutonRoutine {
		private double position;

		public WaypointComponent(double position) {
			super(null, 0);
			this.position = position;
		}

		@Override
		protected void runCore() throws InterruptedException {
			System.out.printf("Waiting for position %f; %f\n", position, getPosition());
			while (getPosition() < position) {
				matchLogger.write(String.format("Position: %f (%f)", getPosition(), position));
				Thread.sleep(25);
			}
			System.out.printf("Reached position %f\n", position);
		}
	}
}