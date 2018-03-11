package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SweepTurn extends AutonRoutine{
	
	private double radius;
	private int leftOffset;
	private int rightOffset;
	private double angleOffset;
	private double sweepAngle;
	
	private boolean reverse;
	private boolean left;
	private boolean dampen;
	private boolean chain;
	
	private final double DRIVE_WIDTH = 24.7;//inches
	private final double TARGET_SPEED = 7;//ft/sec
	private final double TICKS_PER_INCH = 5.30516;//Ticks
	
	public SweepTurn(long timeout, double sweepAngle, double r, boolean leftTurn, boolean chain) {
		super("SweepTurn", timeout);
		
		reverse = sweepAngle < 0;
		
		//this.dampen = dampen;
		this.chain = chain;
		this.sweepAngle = Math.abs(sweepAngle);
		this.left = leftTurn;
		this.leftOffset = sensorInput.getLeftDriveEncoder();
		this.rightOffset = sensorInput.getRightDriveEncoder();
		this.angleOffset = sensorInput.getAHRSYaw();
		
		this.radius = r-12.35;
	}
	
	public SweepTurn(long timeout, double sweepAngle, double r, boolean leftTurn, boolean dampen, boolean chain) {
		this(timeout, sweepAngle, r, leftTurn, chain);
		this.dampen = dampen;
	}

	public SweepTurn(long timeout, double r, boolean leftTurn, boolean dampen, boolean chain) {
		this(timeout, 90, r, leftTurn, chain);
		this.dampen = dampen;
	}
	
	public SweepTurn(long timeout, double r, boolean leftTurn, boolean chain) {
		this(timeout, 90, r, leftTurn, chain);
	}

	@Override
	protected void runCore() throws InterruptedException {
		double leftSpeed;
		double rightSpeed;
		
		OrbitPID pidInner = new OrbitPID(0.01, 0.0, 0.0);
		OrbitPID pidOuter = new OrbitPID(0.01, 0.0, 0.0);
		
		OrbitPID dampen = new OrbitPID(0.02, 0.0, 0.0);
		
		//middle ticks if there was a wheel there
		int middleTicks = (int) (((radius * 2 * Math.PI * sweepAngle / 360)) * TICKS_PER_INCH);
		
		//Inner and outer ticks
		int innerTicks = (int) ((((radius - (DRIVE_WIDTH / 2)) * 2 * Math.PI * sweepAngle / 360)) * TICKS_PER_INCH);
		int outerTicks = (int) ((((radius + (DRIVE_WIDTH / 2)) * 2 * Math.PI * sweepAngle / 360)) * TICKS_PER_INCH);
		
		//convert ft/s to ticks/s
		double ticksPerSec = TICKS_PER_INCH * (TARGET_SPEED * 12);
		
		//Calculate total time to do the sweep assuming our ticks/s is the outter speed
		double timeForMovement = outerTicks / ticksPerSec;
		
		//calculate inner velocity based off of the time
		double innerVelocity = innerTicks / timeForMovement;
		
		//calculate outer veloctiy based off of the time
		double outerVelocity = ticksPerSec;
		
		double dampenAmt;
		
		if(left)
		{
			while(Math.abs(angleOffset - sensorInput.getAHRSYaw()) < sweepAngle) {			
				
				leftSpeed = pidInner.calculate(innerVelocity, Math.abs(sensorInput.getLeftEncoderVelocity()));
				rightSpeed = pidOuter.calculate(outerVelocity, Math.abs(sensorInput.getRightEncoderVelocity()));
				
				SmartDashboard.putNumber("Inner Velocity", Math.abs(sensorInput.getLeftEncoderVelocity()));
				SmartDashboard.putNumber("Outer Velocity", Math.abs(sensorInput.getRightEncoderVelocity()));
				
				SmartDashboard.putNumber("Target Inner Velocity", innerVelocity);
				SmartDashboard.putNumber("Target Outer Velocity", outerVelocity);
				
				SmartDashboard.putNumber("Target Angle", sweepAngle);
				SmartDashboard.putNumber("Current Angle", Math.abs(angleOffset - sensorInput.getAHRSYaw()));
				SmartDashboard.putNumber("Raw Sensor", sensorInput.getAHRSYaw());
				
				
				if(!chain)
				{	
					dampenAmt = dampen.calculate(sweepAngle, Math.abs(angleOffset - sensorInput.getAHRSYaw()));
					if(dampenAmt > 1) 
						dampenAmt = 1;
					
					SmartDashboard.putNumber("Dampen Amt", dampenAmt);
					
					leftSpeed = leftSpeed * dampenAmt;
					rightSpeed = rightSpeed * dampenAmt;
				}

				
				if(reverse)
					robotOutput.tankDrive(-leftSpeed, -rightSpeed);
				else
					robotOutput.tankDrive(leftSpeed, rightSpeed);
				
				Thread.sleep(5);
			}
		}
		else
		{
			while(Math.abs(angleOffset - sensorInput.getAHRSYaw()) < sweepAngle) {
				
				leftSpeed = pidOuter.calculate(outerVelocity, Math.abs(sensorInput.getLeftEncoderVelocity()));
				rightSpeed = pidInner.calculate(innerVelocity, Math.abs(sensorInput.getRightEncoderVelocity()));
				
				SmartDashboard.putNumber("Target Angle", sweepAngle);
				SmartDashboard.putNumber("Current Angle", Math.abs(Math.abs(angleOffset) - sensorInput.getAHRSYaw()));
				SmartDashboard.putNumber("Raw Sensor", sensorInput.getAHRSYaw());
				
				if(!chain)
				{
					dampenAmt = dampen.calculate(sweepAngle, Math.abs(angleOffset - sensorInput.getAHRSYaw()));
					if(dampenAmt > 1) 
						dampenAmt = 1;
					
					SmartDashboard.putNumber("Dampen Amt", dampenAmt);
					
					leftSpeed = leftSpeed * dampenAmt;
					rightSpeed = rightSpeed * dampenAmt;
				}
				
				if(reverse)
					robotOutput.tankDrive(-leftSpeed, -rightSpeed);
				else
					robotOutput.tankDrive(leftSpeed, rightSpeed);
				
				Thread.sleep(5);
			} 
		}
		
		robotOutput.tankDrive(0, 0);
		
	}
	

}
