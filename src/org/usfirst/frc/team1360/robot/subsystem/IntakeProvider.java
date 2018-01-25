package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.util.SingletonSee;
import org.usfirst.frc.team1360.robot.util.SingletonType;

@SingletonType(Intake.class)
public interface IntakeProvider{    // setting up intake interface	
    final int FREE = 0;
	final int OPEN = 1;
	final int CLOSED = 2;
	
	void setClamp(int clamp);
	void setIntake(double speed);
}