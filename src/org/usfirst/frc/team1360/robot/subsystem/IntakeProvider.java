package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.SingletonType;

@SingletonType(Intake.class)
public interface IntakeProvider{    // setting up intake interface
	public final int IDLE = 2;
	public final int INTAKE = 0;
	public final int RELEASE = 1;
	
    final int FREE = 0;
	final int OPEN = 1;
	final int CLOSED = 2;
	
	void setPosition(double position);    // sets the position the intake is in
	int getPosition();	// receives the position that the intake is in
	
	void setClamp(int clamp);
	void setIntake(double speed);
}