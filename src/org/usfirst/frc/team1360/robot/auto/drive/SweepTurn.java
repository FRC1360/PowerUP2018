package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SweepTurn extends AutonRoutine{
	
	private double radius;
	private int leftOffset;
	private int rightOffset;
	
	private boolean chain;
	private boolean left;
	
	private final double DRIVE_WIDTH = 24;
	
	public SweepTurn(long timeout, double r, boolean leftTurn, boolean chain) {
		super("SweepTurn", timeout);
		
		this.left = leftTurn;
		this.chain = chain;
		this.leftOffset = sensorInput.getLeftDriveEncoder();
		this.rightOffset = sensorInput.getRightDriveEncoder();
		
		this.radius = r;
	}

	@Override
	protected void runCore() throws InterruptedException {
		// TODO Auto-generated method stub
		OrbitPID pidInner = new OrbitPID(0.002, 0.0, 0.04);
		OrbitPID pidOutter = new OrbitPID(0.005, 0.0, 0.0);
		
		int innerEncTicks = (int) ((((radius - (DRIVE_WIDTH / 2)) * 2 * Math.PI) / 4) * 5.30516);
		int outterEncTicks = (int) ((((radius + (DRIVE_WIDTH / 2)) * 2 * Math.PI) / 4) * 5.30516);
		
		double leftSpeed = 0;
		double rightSpeed = 0;
		
		if(left)
		{
			while(/*sensorInput.getLeftDriveEncoder() - leftOffset < innerEncTicks ||*/ sensorInput.getRightDriveEncoder() - rightOffset < outterEncTicks) {
				
				if(chain)
				{
					
					
				}else {
					leftSpeed = pidInner.calculate(innerEncTicks, sensorInput.getLeftDriveEncoder() - leftOffset);
					rightSpeed = pidOutter.calculate(outterEncTicks, sensorInput.getRightDriveEncoder() - rightOffset);
					
					robotOutput.tankDrive(leftSpeed, rightSpeed);
				}
					
			}
		}
		else
		{
			while(sensorInput.getLeftDriveEncoder() - leftOffset < outterEncTicks /*|| sensorInput.getRightDriveEncoder() - rightOffset < innerEncTicks*/) {
				if(chain)
				{
					
					
				}else {
					leftSpeed = pidOutter.calculate(outterEncTicks, sensorInput.getLeftDriveEncoder() - leftOffset);
					rightSpeed = pidInner.calculate(innerEncTicks, sensorInput.getRightDriveEncoder() - rightOffset);
					
					robotOutput.tankDrive(leftSpeed, rightSpeed);
				}
			}
		}
	}
	

}
