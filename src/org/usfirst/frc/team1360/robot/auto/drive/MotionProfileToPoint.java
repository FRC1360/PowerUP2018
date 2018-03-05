package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotionProfileToPoint extends AutonRoutine{
	
	Waypoint point = new Waypoint();
	
	public MotionProfileToPoint(long timeout, double x, double y, double A) {
		super("DriveToDistance", timeout);

	}

	@Override
	protected void runCore() throws InterruptedException
	{
		
		
		
	}
}
