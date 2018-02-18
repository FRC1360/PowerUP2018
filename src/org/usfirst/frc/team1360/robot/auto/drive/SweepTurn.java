package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SweepTurn extends AutonRoutine{
	
	private double radius;
	private int leftOffset;
	private int rightOffset;
	
	private boolean left;
	
	private final double DRIVE_WIDTH = 24;
	
	public SweepTurn(long timeout, double r, boolean leftTurn) {
		super("SweepTurn", timeout);
		
		this.left = leftTurn;
		
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
			while(sensorInput.getLeftDriveEncoder() - leftOffset < innerEncTicks || sensorInput.getRightDriveEncoder() - rightOffset < outterEncTicks) {
				leftSpeed = pidInner.calculate(innerEncTicks, sensorInput.getLeftDriveEncoder() - leftOffset);
				rightSpeed = pidOutter.calculate(outterEncTicks, sensorInput.getRightDriveEncoder() - rightOffset);
				
				robotOutput.tankDrive(leftSpeed, rightSpeed);
				
				SmartDashboard.putNumber("Left Wheel Speed = ", leftSpeed);
				SmartDashboard.putNumber("Right Wheel Speed = ", rightSpeed);
				
				SmartDashboard.putNumber("Left Enc", sensorInput.getLeftDriveEncoder() - leftOffset);
				SmartDashboard.putNumber("Right Enc", sensorInput.getRightDriveEncoder() - rightOffset);
				
				SmartDashboard.putNumber("Left Target", innerEncTicks);
				SmartDashboard.putNumber("Right Target", outterEncTicks);
				
				
				//log.write("LEFT: " + leftSpeed + " RIGHT: " + rightSpeed);
				//log.write("Left Enc: " + (sensorInput.getLeftDriveEncoder() - leftOffset) + " Right Enc: " + (sensorInput.getRightDriveEncoder() - rightOffset));
			
			}
		}
		else
		{
			while(sensorInput.getLeftDriveEncoder() - leftOffset < outterEncTicks || sensorInput.getRightDriveEncoder() - rightOffset < innerEncTicks) {
				leftSpeed = pidOutter.calculate(outterEncTicks, sensorInput.getLeftDriveEncoder() - leftOffset);
				rightSpeed = pidInner.calculate(innerEncTicks, sensorInput.getRightDriveEncoder() - rightOffset);
				
				robotOutput.tankDrive(leftSpeed, rightSpeed);
				
				SmartDashboard.putNumber("Left Wheel Speed = ", leftSpeed);
				SmartDashboard.putNumber("Right Wheel Speed = ", rightSpeed);
				
				SmartDashboard.putNumber("Left Enc", sensorInput.getLeftDriveEncoder() - leftOffset);
				SmartDashboard.putNumber("Right Enc", sensorInput.getRightDriveEncoder() - rightOffset);
				
				SmartDashboard.putNumber("Left Target", outterEncTicks);
				SmartDashboard.putNumber("Right Target", innerEncTicks);
				
				
				//SmartDashboard.putData();
				
				//log.write("LEFT: " + leftSpeed + " RIGHT: " + rightSpeed);
				//log.write("Left Enc: " + (sensorInput.getLeftDriveEncoder() - leftOffset) + " Right Enc: " + (sensorInput.getRightDriveEncoder() - rightOffset));
			}
		}
	}
	
	@Override
	public void overrideCore() {
		robotOutput.tankDrive(0, 0);
	}

}
