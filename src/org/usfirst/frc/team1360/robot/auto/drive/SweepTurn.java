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
	private double fps;//ft/sec
	
	private final double DRIVE_WIDTH = 24.7;//inches

	private final double TICKS_PER_INCH = 5.30516;//Ticks
	
	public SweepTurn(long timeout, double sweepAngle, double r, double fps, boolean leftTurn, boolean chain) {
		super("SweepTurn", timeout);
		
		reverse = fps < 0;
		
		//this.dampen = dampen;
		this.chain = chain;
		this.sweepAngle = sweepAngle;
		this.left = leftTurn;
		this.leftOffset = sensorInput.getLeftDriveEncoder();
		this.rightOffset = sensorInput.getRightDriveEncoder();
		this.angleOffset = sensorInput.getAHRSYaw(); /*Math.toDegrees(position.getA())*/;
		matchLogger.writeClean("NAVX ANGLE OFFSET " + this.angleOffset);
		this.fps = Math.abs(fps);
		
		this.radius = r-12.35;
	}
	
	public SweepTurn(long timeout, double sweepAngle, double r, boolean leftTurn, boolean dampen, boolean chain) {
		this(timeout, sweepAngle, r, 7.5, leftTurn, chain);
		this.dampen = dampen;
	}

	public SweepTurn(long timeout, double r, boolean leftTurn, boolean dampen, boolean chain) {
		this(timeout, 90, r, 7.5, leftTurn, chain);
		this.dampen = dampen;
	}
	
	public SweepTurn(long timeout, double r, boolean leftTurn, boolean chain) {
		this(timeout, 90, r, 7.5, leftTurn, chain);
	}

	@Override
	protected void runCore() throws InterruptedException {
		double leftSpeed;
		double rightSpeed;
		
		OrbitPID pidInner = new OrbitPID(0.005, 0.0, 0.1);
		OrbitPID pidOuter = new OrbitPID(0.01, 0.0, 0.0);
		
		OrbitPID dampen = new OrbitPID(0.045, 0.0, 0.0);
		
		double deltaA = Math.abs(sweepAngle - angleOffset);
		
		//middle ticks if there was a wheel there
		int middleTicks = (int) (((radius * 2 * Math.PI * deltaA / 360)) * TICKS_PER_INCH);
		
		//Inner and outer ticks
		int innerTicks = (int) ((((radius - (DRIVE_WIDTH / 2)) * 2 * Math.PI * deltaA / 360)) * TICKS_PER_INCH);
		int outerTicks = (int) ((((radius + (DRIVE_WIDTH / 2)) * 2 * Math.PI * deltaA / 360)) * TICKS_PER_INCH);
		
		//convert ft/s to ticks/s
		double ticksPerSec = TICKS_PER_INCH * (fps * 12);
		
		//Calculate total time to do the sweep assuming our ticks/s is the outer speed
		double timeForMovement = outerTicks / ticksPerSec;
		
		//calculate inner velocity based off of the time
		double innerVelocity = innerTicks / timeForMovement;
		
		//calculate outer velocity based off of the time
		double outerVelocity = ticksPerSec;
		
		double dampenAmt;
		
		if(left)
		{
			while(sensorInput.getAHRSYaw() > sweepAngle) {			
				
				//matchLogger.writeClean("AUTO DEBUG " + Double.toString(sensorInput.getAHRSYaw()));
				
				if(!chain)
				{	
					dampenAmt = -dampen.calculate(sweepAngle, sensorInput.getAHRSYaw());
					if(dampenAmt > 1) 
						dampenAmt = 1;
					
					if(dampenAmt < 0)
						dampenAmt = 0;
					
					leftSpeed = pidInner.calculate(innerVelocity*dampenAmt, Math.abs(sensorInput.getLeftEncoderVelocity()));
					rightSpeed = pidOuter.calculate(outerVelocity*dampenAmt, Math.abs(sensorInput.getRightEncoderVelocity()));
				}
				else {
					leftSpeed = pidInner.calculate(innerVelocity, Math.abs(sensorInput.getLeftEncoderVelocity()));
					rightSpeed = pidOuter.calculate(outerVelocity, Math.abs(sensorInput.getRightEncoderVelocity()));
				}
				
				if (leftSpeed < -0.1)
					leftSpeed = -0.1;
				if (rightSpeed < -0.1)
					rightSpeed = -0.1;
				
				if(reverse)
					robotOutput.tankDrive(-leftSpeed, -rightSpeed);
				else
					robotOutput.tankDrive(leftSpeed, rightSpeed);
				
				Thread.sleep(5);
			}
			robotOutput.tankDrive(0, 0);
		}
		else
		{			
			while(sensorInput.getAHRSYaw() < sweepAngle) {

				//matchLogger.writeClean("AUTO DEBUG " + Double.toString(sensorInput.getAHRSYaw()));
				
				
				if(!chain)
				{
					dampenAmt = dampen.calculate(sweepAngle, sensorInput.getAHRSYaw());
					

					if(dampenAmt > 1) 
						dampenAmt = 1;
					
					
					if(dampenAmt < 0)
						dampenAmt = 0;
					
					leftSpeed = pidOuter.calculate(outerVelocity*dampenAmt, Math.abs(sensorInput.getLeftEncoderVelocity()));
					rightSpeed = pidInner.calculate(innerVelocity*dampenAmt, Math.abs(sensorInput.getRightEncoderVelocity()));
				}
				else {
					leftSpeed = pidOuter.calculate(outerVelocity, Math.abs(sensorInput.getLeftEncoderVelocity()));
					rightSpeed = pidInner.calculate(innerVelocity, Math.abs(sensorInput.getRightEncoderVelocity()));
				}
				
				if (leftSpeed < -0.1)
					leftSpeed = -0.1;
				if (rightSpeed < -0.1)
					rightSpeed = -0.1;
				
				if(reverse)
					robotOutput.tankDrive(-leftSpeed, -rightSpeed);
				else
					robotOutput.tankDrive(leftSpeed, rightSpeed);
				
				Thread.sleep(5);
			} 
			robotOutput.tankDrive(0, 0);
		}
		
		robotOutput.tankDrive(0, 0);
		
	}
	

}
