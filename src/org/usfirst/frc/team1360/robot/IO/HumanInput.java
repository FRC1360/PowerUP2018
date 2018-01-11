package org.usfirst.frc.team1360.robot.IO;
import org.usfirst.frc.team1360.robot.util.LogitechExtremeJoystick;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;



public class HumanInput {
	private static HumanInput instance;

	// Joysticks
	private XboxController driver;
	private XboxController operator;
	private LogitechExtremeJoystick driver1;
	private LogitechExtremeJoystick driver2;

	
	private HumanInput()									//Constructor to initialize fields
	{
		this.driver = new XboxController(0);					//Driver Xbox on USB Port 0 on DS
		this.operator = new XboxController(1);					//Operator Xbox on USB Port 1 on DS
		this.driver1 = new LogitechExtremeJoystick(2);
		this.driver2 = new LogitechExtremeJoystick(3);
	}
	
	public static HumanInput getInstance()					//Return intance of HumanInput; create if it doesn't exist
	{
		if (instance == null)
		{
			instance = new HumanInput();
		}
		
		return instance;
	}
	
	//Driver Controls
	
	//---------Racing--------------
	public double getRacingThrottle()
	{
		return this.driver.getTriggerAxis(Hand.kRight) - this.driver.getTriggerAxis(Hand.kLeft); 
	}
	
	public double getRacingTurn()
	{
		return this.driver.getX(Hand.kLeft);
	}
	
	public boolean getRacingDampen()
	{
		return this.driver.getXButton();
	}
	
	//------------Halo--------------
	public double getHaloThrottle()
	{
		return this.driver.getY(Hand.kLeft);
	}
	
	public double getHaloTurn()
	{
		return this.driver.getX(Hand.kRight);
	}
	
	//-----------Single-Stick Arcade------------
	public double getArcadeThrottle()
	{
		return this.driver.getY(Hand.kLeft);
	}
	
	public double getArcadeTurn()
	{
		return this.driver.getX(Hand.kLeft);
	}
	
	
	//----------Tank Controls---------
	public double getTankLeft()
	{
		return this.driver.getY(Hand.kLeft);
	}
	
	public double getTankRight()
	{
		return this.driver.getY(Hand.kRight);
	}
	
	
	//----------Logitech Controls------
	//TODO Change to WPILIB Logitech class
	public double getLeftJoystickThrottle()
	{
		return -driver1.getY();
	}
	
	public double getRightJoystickThrottle()
	{
		return -driver2.getY();
	}
	
	public boolean getJoystickShift()
	{
		return driver1.getMainTrigger() || driver2.getMainTrigger();
	}
	


	//TODO Operator Controls
	
	public double getElevator()
	{
		return this.operator.getY(Hand.kLeft);
	}

}
