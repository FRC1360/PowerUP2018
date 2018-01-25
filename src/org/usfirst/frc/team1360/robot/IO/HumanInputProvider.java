package org.usfirst.frc.team1360.robot.IO;

import org.usfirst.frc.team1360.robot.util.SingletonType;

@SingletonType(HumanInput.class)
public interface HumanInputProvider {

	//---------Racing--------------
	double getRacingThrottle();

	double getRacingTurn();

	boolean getRacingDampen();

	boolean getRacingShift();

	//------------Halo--------------
	double getHaloThrottle();

	double getHaloTurn();

	boolean getHaloShift();

	//-----------Single-Stick Arcade------------
	double getArcadeThrottle();

	double getArcadeTurn();

	boolean getArcadeShift();

	//----------Tank Controls---------
	double getTankLeft();

	double getTankRight();

	boolean getTankShift();

	//----------Logitech Controls------
	//TODO Change to WPILIB Logitech class
	double getLeftJoystickThrottle();

	double getRightJoystickThrottle();

	boolean getJoystickShift();
	//---------operator controls-------

	// controls whether intake is on or off
	double getOperatorIntake();
	
	// controls whether intake is clamping or not
	boolean getOperatorClamp();

	//controls speed of intake wheels
	double getOperatorSpeed();

	//-----------Auto Selection-----------
	boolean getAutoInc();

	boolean getAutoDec();

	double getElevator();

	double deadzone(double Input, double deadzone);

	

}