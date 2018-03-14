package org.usfirst.frc.team1360.robot.auto.drive;

import java.io.File;
import java.io.IOException;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class PathfindFromFile extends AutonRoutine{
	
	final double DT_WIDTH = 2.05;
	final int TICKS_PER_REV = 83;
	final double WHEEL_SIZE = 0.4166;
	final double TIME_STEP = 0.05;
	final double FPS = 7;
	final double ACCELERATION = 5;
	final double MAX_FPS = 15.5;
	
	private Trajectory leftTraj;
	private Trajectory rightTraj;
	
	private EncoderFollower left;
	private EncoderFollower right;
	
	public PathfindFromFile(long timeout, String file) throws IOException {
		super("Pathfind From File", timeout);
		
		File leftProfile = new File("L-" + file);
		File rightProfile = new File("R-" + file);
		
		this.leftTraj = Pathfinder.readFromCSV(leftProfile);
		this.rightTraj = Pathfinder.readFromCSV(rightProfile);
		
		matchLogger.writeClean(file + " is valid");
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

	@Override
	protected void runCore() throws InterruptedException {
		
		TankModifier modifierLeft = new TankModifier(leftTraj).modify(DT_WIDTH);
		TankModifier modifierRight = new TankModifier(rightTraj).modify(DT_WIDTH);
		
		left = new EncoderFollower(modifierLeft.getLeftTrajectory());
		right = new EncoderFollower(modifierRight.getRightTrajectory());
		
		left.configureEncoder(sensorInput.getLeftDriveEncoder(), TICKS_PER_REV, WHEEL_SIZE);
		right.configureEncoder(sensorInput.getRightDriveEncoder(), TICKS_PER_REV, WHEEL_SIZE);
		
		left.configurePIDVA(1.25, 0.0, 0.0, 1/MAX_FPS, 0);
		right.configurePIDVA(1.25, 0.0, 0.0, 1/MAX_FPS, 0);
		
		double l, r, turn;
		
		long time = System.currentTimeMillis();
		
		while(!left.isFinished() || !right.isFinished()) {
			
			if(System.currentTimeMillis() - time > 50)
			{
				time = System.currentTimeMillis();
				l = left.calculate(sensorInput.getLeftDriveEncoder());
				r = right.calculate(sensorInput.getRightDriveEncoder());
				
				turn = 0.8 * (-1.0/80.0) * (Pathfinder.d2r(left.getHeading()) - sensorInput.getAHRSYaw());
				
				matchLogger.writeClean("PATHFINDER left = " + l + " right = " + r + " turn = " + turn);
				
				robotOutput.tankDrive(l + turn, r - turn);
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
