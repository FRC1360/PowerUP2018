package org.usfirst.frc.team1360.robot.auto.drive;

import java.io.File;
import java.io.IOException;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class PathfindFromFile extends AutonRoutine{
	
	final double DT_WIDTH = 2.05;
	final int TICKS_PER_REV = 250/3;
	final double WHEEL_SIZE = 0.4166;
	final double TIME_STEP = 0.025;
	final double FPS = 13;
	final double ACCELERATION = 13;
	final double MAX_FPS = 6;
	
	private Trajectory leftTraj;
	private Trajectory rightTraj;
	
	private EncoderFollower left;
	private EncoderFollower right;
	
	public PathfindFromFile(long timeout, String file) {
		super("Pathfind From File", timeout);
		File leftProfile;
		File rightProfile;
		
		try {
			leftProfile = new File("/U/L-" + file);	
			rightProfile = new File("/U/R-" + file);
		} catch(Throwable e) {
			matchLogger.writeClean(file + " is invalid");
			return;
		}
		
		this.leftTraj = Pathfinder.readFromCSV(leftProfile);
		this.rightTraj = Pathfinder.readFromCSV(rightProfile);
	}
	
	public PathfindFromFile(long timeout, Trajectory traj) {
		super("Pathfind From File", timeout);
		
		this.leftTraj = traj;
		this.rightTraj = traj;
	}
	
	public double getPosition() {
		return left.getSegment().position;
	}
	
	public void setWaypoint(double position, String name) {
		new WaypointComponent(position).runNow(name);
	}
	
	private double nearAngle(double angle, double reference) {
		return Math.round((reference - angle) / 360) * 360 + angle;
	}

	@Override
	protected void runCore() throws InterruptedException {
		
		if(leftTraj == null || rightTraj == null)
		{
			return;
		}
		
		TankModifier modifierLeft = new TankModifier(leftTraj).modify(DT_WIDTH);
		TankModifier modifierRight = new TankModifier(rightTraj).modify(DT_WIDTH);
		
		left = new EncoderFollower(modifierLeft.getLeftTrajectory());
		right = new EncoderFollower(modifierRight.getRightTrajectory());
		
		left.configureEncoder(sensorInput.getLeftDriveEncoder(), TICKS_PER_REV, WHEEL_SIZE);
		right.configureEncoder(sensorInput.getRightDriveEncoder(), TICKS_PER_REV, WHEEL_SIZE);
		
		left.configurePIDVA(1.0, 0.0, 0.5, 1/MAX_FPS, 0);//D = 0.5 P = 4
		right.configurePIDVA(1.0, 0.0, 0.5, 1/MAX_FPS, 0);
		
		double l, r, turn, angleDifference;
		
		long time = System.currentTimeMillis();
		
		matchLogger.write("PATHFINDER STARTING");
		
		OrbitPID turnPID = new OrbitPID(0.2, 0.0, 0.1);
		
		while(!left.isFinished() || !right.isFinished()) {
			
			if(System.currentTimeMillis() - time >= TIME_STEP * 1000)
			{
				time = System.currentTimeMillis();
				l = left.calculate(sensorInput.getLeftDriveEncoder());
				r = right.calculate(sensorInput.getRightDriveEncoder());
				
				//angleDifference = Pathfinder.boundHalfDegrees(Pathfinder.r2d(left.getHeading()) - sensorInput.getAHRSYaw());
				
				//turn = 0.0375 * angleDifference + (0.0025 * (angleDifference / 0.05));
				
				double yaw = -sensorInput.getAHRSYaw();
				turn = turnPID.calculate(nearAngle(Pathfinder.r2d(left.getHeading()), yaw), yaw);
				
				matchLogger.write(String.format("PATHFINDER heading = %f3, actual = %f3, turn = %f3, l = %f3, r = %f3", Pathfinder.r2d(left.getHeading()), -sensorInput.getAHRSYaw(), turn/10, l, r));
				
//				if (turn > 0)
//					l *= Math.exp(-turn);
//				else
//					r *= Math.exp(turn);
				l -= turn;
				r += turn;
				
//				if (turn < 0 && l < 0)
//					l = 0;
//				if (turn > 0 && r < 0)
//					r = 0;
				
				if (Math.abs(l) > 1 || Math.abs(r) > 1)
				{
					if (Math.abs(l) > Math.abs(r))
					{
						r /= Math.abs(l);
						l = Math.signum(l);
					}
					else
					{
						l /= Math.abs(r);
						r = Math.signum(r);
					}
				}
				
				robotOutput.tankDrive(l, r);
			}
			
			Thread.sleep(1);
		}
		
		robotOutput.tankDrive(0, 0);
	}
	
	private final class WaypointComponent extends AutonRoutine {
		private double position;
		
		public WaypointComponent(double position) {
			super(null, 0);
			this.position = position;
		}

		@Override
		protected void runCore() throws InterruptedException {
			while (getPosition() < position) Thread.sleep(1);
		}
	}
}
