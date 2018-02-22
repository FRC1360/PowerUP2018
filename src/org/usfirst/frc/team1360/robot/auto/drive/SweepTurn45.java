package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SweepTurn45 extends AutonRoutine{
	
	private double radius;
	private int leftOffset;
	private int rightOffset;
	
	private boolean chain;
	private boolean left;
	
	private final double DRIVE_WIDTH = 24;
	
	public SweepTurn45(long timeout, double r, boolean leftTurn, boolean chain) {
		super("SweepTurn45", timeout);
		
		this.left = leftTurn;
		this.chain = chain;
		this.leftOffset = sensorInput.getLeftDriveEncoder();
		this.rightOffset = sensorInput.getRightDriveEncoder();
		
		this.radius = r;
	}

	@Override
	protected void runCore() throws InterruptedException {
		OrbitPID pidInner = new OrbitPID(0.003, 0.0, 0.04);
		OrbitPID pidOutter = new OrbitPID(0.008, 0.0, 0.0);
		
		int innerEncTicks = (int) ((((radius - (DRIVE_WIDTH / 2)) * 2 * Math.PI) / 8) * 5.30516);
		int outterEncTicks = (int) ((((radius + (DRIVE_WIDTH / 2)) * 2 * Math.PI) / 8) * 5.30516);
		
		double leftSpeed = 0;
		double rightSpeed = 0;
		
		double innerCircle = radius - 12;
		double outerCircle = radius + 12;
		
		double ratio = outerCircle / innerCircle;
		
		if(left)
		{
			while(sensorInput.getRightDriveEncoder() - rightOffset < outterEncTicks) {
				
				if(chain)
				{
					robotOutput.tankDrive(0.5, ratio * 0.5 * 1.275);
				}else {
					leftSpeed = pidInner.calculate(innerEncTicks, sensorInput.getLeftDriveEncoder() - leftOffset);
					rightSpeed = pidOutter.calculate(outterEncTicks, sensorInput.getRightDriveEncoder() - rightOffset);
					
					robotOutput.tankDrive(leftSpeed, rightSpeed);
				}
					
			}
		}
		else
		{
			while(sensorInput.getLeftDriveEncoder() - leftOffset < outterEncTicks) {
				if(chain)
				{
					robotOutput.tankDrive(ratio * 0.5, 0.5);
				}else {
					leftSpeed = pidOutter.calculate(outterEncTicks, sensorInput.getLeftDriveEncoder() - leftOffset);
					rightSpeed = pidInner.calculate(innerEncTicks, sensorInput.getRightDriveEncoder() - rightOffset);
					
					robotOutput.tankDrive(leftSpeed, rightSpeed);
				}
			}
		}
	}
	

}
