package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveToPoint extends AutonRoutine{
	
	private double x;
	private double y;
	private double endA;

	
	public DriveToPoint(long timeout, double x, double y, double endA) {
		super("DriveToPoint", timeout);
		
		this.x = x;
		this.y = y;
		this.endA = endA;
	}

	@Override
	protected void runCore() throws InterruptedException
	{
		//Distance to the target
		double difX = Math.abs(position.getX() - x);
		double difY = Math.abs(position.getY() - y);
		
		
		
		
	}
}
