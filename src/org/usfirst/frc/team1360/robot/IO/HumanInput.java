package org.usfirst.frc.team1360.robot.IO;
import org.usfirst.frc.team1360.robot.util.LogitechAttack3Joystick;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;



public class HumanInput {
	private static HumanInput instance;

	// Joysticks
	private XboxController driver;
	private XboxController operator;
	private LogitechAttack3Joystick driverLeft;
	private LogitechAttack3Joystick driverRight;
		
	public static HumanInput getInstance()	//Return intance of HumanInput; create if it doesn't exist
	{
		if (instance == null)
		{
			instance = new HumanInput();
		}
		
		return instance;
	}
	
	private HumanInput()									//Constructor to initialize fields
	{
		this.driver = new XboxController(0);					//Driver Xbox on USB Port 0 on DS
		this.operator = new XboxController(1);					//Operator Xbox on USB Port 1 on DS
		this.driverLeft = new LogitechAttack3Joystick(2);
		this.driverRight = new LogitechAttack3Joystick(3);
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
	public boolean getRacingShift() {
		return this.driver.getYButton();
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
	public boolean getHaloShift() {
		return this.driver.getYButton();
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
	public boolean getArcadeShift() {
		return this.driver.getYButton();
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
	public boolean getTankShift() {
		return this.driver.getYButton();
	}
	
	//----------Logitech Controls------
	//TODO Change to WPILIB Logitech class
	public double getLeftJoystickThrottle()
	{
		return -driverLeft.getY();
	}
	
	public double getRightJoystickThrottle()
	{
		return -driverRight.getY();
	}
	
	public boolean getJoystickShift()
	{
		return driverLeft.getTrigger() || driverRight.getTrigger();
	}
	//---------operator controls-------
	
	
	// controls whether clamp is open or closed
    public boolean getOperatorClamp()
    {
    		return operator.getAButton();
    }
    
    //controls speed of intake wheels
    public double getOperatorSpeed() 
    {
    		return operator.getY(Hand.kLeft);
    }

	

}
