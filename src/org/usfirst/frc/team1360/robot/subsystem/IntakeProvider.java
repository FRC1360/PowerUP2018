package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.util.SingletonType;

@SingletonType(Intake.class)     
public interface IntakeProvider{    // setting up intake interface
	void setPosition(double position);    // sets the position the intake is in
	int getPosition();	// receives the position that the intake is in
}