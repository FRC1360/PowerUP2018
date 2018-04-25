package org.usfirst.frc.team1360.robot.subsystem;

public interface IntakeProvider{    // setting up intake interface	
    final int FREE = 0;
	final int OPEN = 1;
	final int CLOSED = 2;
	
	void setClamp(int clamp);
	void setIntake(double speed);
	void setIntake(double speed, double spin);
}