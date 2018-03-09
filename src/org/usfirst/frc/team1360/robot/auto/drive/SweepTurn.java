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
	
	private final double DRIVE_WIDTH = 24.7;//inches
	private final double TARGET_SPEED = 5;//ft/sec
	private final double TICKS_PER_INCH = 5.30516;//Ticks
	
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
		double leftSpeed;
		double rightSpeed;
		
		OrbitPID pidInner = new OrbitPID(0.003, 0.0, 0.04);
		OrbitPID pidOuter = new OrbitPID(0.008, 0.0, 0.0);
		
		//middle ticks if there was a wheel there
		int middleTicks = (int) (((radius * 2 * Math.PI * sweepAngle / 360)) * TICKS_PER_INCH);
		
		//Inner and outer ticks
		int innerTicks = (int) ((((radius - (DRIVE_WIDTH / 2)) * 2 * Math.PI * sweepAngle / 360)) * TICKS_PER_INCH);
		int outerTicks = (int) ((((radius + (DRIVE_WIDTH / 2)) * 2 * Math.PI * sweepAngle / 360)) * TICKS_PER_INCH);
		
		//convert ft/s to ticks/s
		double ticksPerSec = TICKS_PER_INCH * (TARGET_SPEED * 12);
		
		//Calculate total time to do the sweep assuming our ticks/s is the outter speed
		double timeForMovement = middleTicks / ticksPerSec;
		
		//calculate inner velocity based off of the time
		double innerVelocity = innerTicks / timeForMovement;
		
		//calculate outer veloctiy based off of the time
		double outerVelocity = outerTicks / timeForMovement;
		
		if(left)
		{
			while(sensorInput.getRightDriveEncoder() - rightOffset < outerTicks) {
				
				leftSpeed = pidInner.calculate(innerVelocity, Math.abs(sensorInput.getLeftEncoderVelocity()));
				rightSpeed = pidOuter.calculate(outerVelocity, Math.abs(sensorInput.getRightEncoderVelocity()));
				
				robotOutput.tankDrive(leftSpeed, rightSpeed);
				
				Thread.sleep(5);
			}
		}
		else
		{
			while(sensorInput.getLeftDriveEncoder() - leftOffset < outerTicks) {
				
				leftSpeed = pidOuter.calculate(outerVelocity, Math.abs(sensorInput.getLeftEncoderVelocity()));
				rightSpeed = pidInner.calculate(innerVelocity, Math.abs(sensorInput.getRightEncoderVelocity()));
				
				robotOutput.tankDrive(leftSpeed, rightSpeed);
				
				Thread.sleep(5);
			}
		}
	}
	

}
