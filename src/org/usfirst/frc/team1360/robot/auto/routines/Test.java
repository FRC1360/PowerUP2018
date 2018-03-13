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

		final double DT_WIDTH = 2.05;
		final int TICKS_PER_REV = 83;
		final double WHEEL_SIZE = 0.4166;
		final double TIME_STEP = 0.05;
		final double FPS = 7;
		final double ACCELERATION = 5;
		
		
		Waypoint[] point = new Waypoint[] {
				new Waypoint(0,0,0),
				new Waypoint(4,4,90),
				//new Waypoint(4,8,90),
//				new Waypoint(4, 10, 90)
				
		};
		
		matchLogger.writeClean("PATHFINDER STARTING");
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, TIME_STEP, FPS, ACCELERATION, 30.0);
		Trajectory trajectory = Pathfinder.generate(point, config);
		
		TankModifier modifier = new TankModifier(trajectory).modify(DT_WIDTH);
		
		EncoderFollower left = new EncoderFollower(modifier.getLeftTrajectory());
		EncoderFollower right = new EncoderFollower(modifier.getRightTrajectory());
		
		left.configureEncoder(sensorInput.getLeftDriveEncoder(), TICKS_PER_REV, WHEEL_SIZE);
		right.configureEncoder(sensorInput.getRightDriveEncoder(), TICKS_PER_REV, WHEEL_SIZE);
		
		left.configurePIDVA(1.25, 0.0, 0.0, 1/FPS, 0);
		right.configurePIDVA(1.25, 0.0, 0.0, 1/FPS, 0);
		
		double l, r, turn;
		
		long time = System.currentTimeMillis();
		
		while(!left.isFinished() || !right.isFinished()) {
			
			if(System.currentTimeMillis() - time > 50)
			{
				time = System.currentTimeMillis();
				l = left.calculate(sensorInput.getLeftDriveEncoder());
				r = right.calculate(sensorInput.getRightDriveEncoder());
				
				turn = -(0.8 * (-1.0/80.0) * (Pathfinder.d2r(left.getHeading()) - sensorInput.getAHRSYaw()));
				
				matchLogger.writeClean("PATHFINDER left = " + l + " right = " + r + " turn = " + turn);
				
				robotOutput.tankDrive(l + turn, r - turn);
			}
		}
		robotOutput.tankDrive(0, 0);
		robotOutput.setIntake(1.0);
		Thread.sleep(10000);
		
	}
}
