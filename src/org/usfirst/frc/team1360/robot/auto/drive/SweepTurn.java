package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SweepTurn extends AutonRoutine{
	
	private double radius;
	private int leftOffset;
	private int rightOffset;
	private double sweepAngle;
	
	private boolean reverse;
	private boolean chain;
	private boolean left;
	
	private final double DRIVE_WIDTH = 24;//inches
	private final double TARGET_SPEED = 5;//ft/sec
	
	public SweepTurn(long timeout, double sweepAngle, double r, boolean leftTurn, boolean chain) {
		super("SweepTurn", timeout);
		
		reverse = r < 0;
		
		this.sweepAngle = sweepAngle;
		this.left = leftTurn;
		this.chain = chain;
		this.leftOffset = sensorInput.getLeftDriveEncoder();
		this.rightOffset = sensorInput.getRightDriveEncoder();
		
		this.radius = Math.abs(r);
	}

	public SweepTurn(long timeout, double r, boolean leftTurn, boolean chain) {
		this(timeout, 90, r, leftTurn, chain);
	}

	@Override
	protected void runCore() throws InterruptedException {
		OrbitPID pidInner = new OrbitPID(0.003, 0.0, 0.04);
		OrbitPID pidOutter = new OrbitPID(0.008, 0.0, 0.0);
		
		int innerEncTicks = (int) ((((radius - (DRIVE_WIDTH / 2)) * 2 * Math.PI * sweepAngle / 360)) * 5.30516);
		int outterEncTicks = (int) ((((radius + (DRIVE_WIDTH / 2)) * 2 * Math.PI * sweepAngle / 360)) * 5.30516);
		
		
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
					leftSpeed = pidInner.calculate(innerEncTicks, Math.abs(sensorInput.getLeftDriveEncoder() - leftOffset));
					rightSpeed = pidOutter.calculate(outterEncTicks, Math.abs(sensorInput.getRightDriveEncoder() - rightOffset));
					
					if(reverse)
						robotOutput.tankDrive(-leftSpeed, -rightSpeed);
					else
						robotOutput.tankDrive(leftSpeed, rightSpeed);
				}
				Thread.sleep(10);
			}
		}
		else
		{
			while(sensorInput.getLeftDriveEncoder() - leftOffset < outterEncTicks) {
				if(chain)
				{
					robotOutput.tankDrive(ratio * 0.5, 0.5);
				}else {
					leftSpeed = pidOutter.calculate(outterEncTicks, Math.abs(sensorInput.getLeftDriveEncoder() - leftOffset));
					rightSpeed = pidInner.calculate(innerEncTicks, Math.abs(sensorInput.getRightDriveEncoder() - rightOffset))	;
					
					if(reverse)
						robotOutput.tankDrive(-leftSpeed, -rightSpeed);
					else
						robotOutput.tankDrive(leftSpeed, rightSpeed);
				}
				Thread.sleep(10);
			}
		}
	}
	

}
