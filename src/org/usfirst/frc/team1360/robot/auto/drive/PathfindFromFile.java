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
	final double TIME_STEP = 0.05;
	final double FPS = 5;
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

	@Override
	protected void runCore() throws InterruptedException {
		

		
		TankModifier modifierLeft = new TankModifier(leftTraj).modify(DT_WIDTH);
		TankModifier modifierRight = new TankModifier(rightTraj).modify(DT_WIDTH);
		
		left = new EncoderFollower(modifierLeft.getLeftTrajectory());
		right = new EncoderFollower(modifierRight.getRightTrajectory());
		
		left.configureEncoder(sensorInput.getLeftDriveEncoder(), TICKS_PER_REV, WHEEL_SIZE);
		right.configureEncoder(sensorInput.getRightDriveEncoder(), TICKS_PER_REV, WHEEL_SIZE);
		
		left.configurePIDVA(3.0, 0.0, 0.5, 1/MAX_FPS, 0);//D = 0.5 P = 4
		right.configurePIDVA(3.0, 0.0, 0.5, 1/MAX_FPS, 0);
		
		double l, r, turn, angleDifference;
		
		long time = System.currentTimeMillis();
		
		matchLogger.writeClean("PATHFINDER STARTING");
		
		OrbitPID turnPID = new OrbitPID(1.3, 0.05, 0.5);
		
		while(!left.isFinished() || !right.isFinished()) {
			
			if(System.currentTimeMillis() - time >= 50)
			{
				time = System.currentTimeMillis();
				l = left.calculate(sensorInput.getLeftDriveEncoder());
				r = right.calculate(sensorInput.getRightDriveEncoder());
				
				//angleDifference = Pathfinder.boundHalfDegrees(Pathfinder.r2d(left.getHeading()) - sensorInput.getAHRSYaw());
				
				//turn = 0.0375 * angleDifference + (0.0025 * (angleDifference / 0.05));
				
				turn = turnPID.calculate(Pathfinder.r2d(left.getHeading()), -sensorInput.getAHRSYaw());
				
				matchLogger.writeClean(String.format("PATHFINDER heading = %f3, actual = %f3, turn = %f3", Pathfinder.r2d(left.getHeading()), -sensorInput.getAHRSYaw(), turn/10));
				
				robotOutput.tankDrive(l - (turn/10), r + (turn/10));
			}
			
			Thread.sleep(1);
		}
		
		robotOutput.tankDrive(0, 0);
	}
}