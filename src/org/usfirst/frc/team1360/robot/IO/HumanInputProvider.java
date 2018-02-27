package org.usfirst.frc.team1360.robot.IO;

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
	
	//----------Cheesy Drive Controls----------
	double getCheesyThrottle();
	
	double getCheesyTurn();
	
	boolean getCheesyShift();
	
	boolean getCheesyQuickTurn();

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

	double getElevator();
	
	double getArm();
	
	boolean getScaleMax();

	boolean getSwitch();

	boolean getScaleLow();

	boolean getIntake();

	boolean getClimb();
	
	//-----------Auto Selection-----------
	boolean getAutoInc();

	boolean getAutoDec();
	double deadzone(double Input, double deadzone);

	

}