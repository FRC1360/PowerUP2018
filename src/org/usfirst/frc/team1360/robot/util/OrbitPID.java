package org.usfirst.frc.team1360.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OrbitPID {
	private double kP, kI, kD, kF, kIInner, kIOuter, minOut, maxOut;
	private double integral;
	private double lastInput;
	private long lastTime;
	private double lastOutput;
	
	public OrbitPID(double kP, double kI, double kD) {
		this(kP, kI, kD, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
	}
	
	public OrbitPID(double kP, double kI, double kD, double kIInner, double kIOuter, double minOut, double maxOut, double kF) {
		configure(kP, kI, kD, kIInner, kIOuter, minOut, maxOut, kF);
	}
	
	public OrbitPID(double kP, double kI, double kD, double kF) {
		this(kP, kI, kD, Double.NaN, Double.NaN, Double.NaN, Double.NaN, kF);
	}
	
	public void configure(double kP, double kI, double kD, double kIInner, double kIOuter, double minOut, double maxOut, double kF) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
		this.kIInner = kIInner;
		this.kIOuter = kIOuter;
		this.minOut = minOut;
		this.maxOut = maxOut;
		this.lastInput = Double.NaN;
		this.lastTime = -1;
	}
	
	public double calculate(double target, double input) {
		double error = target - input;
		long time = System.nanoTime();
		
		double out = kP * error;
		
		/*
		if(kF != Double.NaN) {
			out = (kP * error) + (kF * lastOutput);
		}
		else {
			
		}*/
		
		if (lastTime != -1)
		{
			long dt = time - lastTime;
			if (kI != 0 && (kIInner == Double.NaN || Math.abs(error) >= kIInner) && (kIOuter == Double.NaN || Math.abs(error) <= kIOuter)) {
				integral += error * dt;
				out += kI * integral;
			}
			out += kD * (input - lastInput) / dt;
		}
		
		lastInput = input;
		lastTime = time;
		
		if (Math.abs(out) < minOut)
			out = 0;
		else if (Math.abs(out) > maxOut)
			out = Math.copySign(maxOut, out);
		
		lastOutput = out;
		return out;
	}
}
 