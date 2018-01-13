package org.usfirst.frc.team1360.robot.subsystem;

import org.usfirst.frc.team1360.robot.util.SingletonType;

@SingletonType(Intake.class)
public interface IntakeProvider{
	void setPosition(double position);
	int getPosition();	
}