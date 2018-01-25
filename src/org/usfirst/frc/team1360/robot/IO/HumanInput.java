package org.usfirst.frc.team1360.robot.IO;
import org.usfirst.frc.team1360.robot.util.LogitechAttack3Joystick;
import org.usfirst.frc.team1360.robot.util.SingletonSee;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;

@SingletonSee(HumanInputProvider.class)
public class HumanInput implements HumanInputProvider {
	// Joysticks
	private XboxController driver;
	private XboxController operator;
	private LogitechAttack3Joystick driverLeft;
	private LogitechAttack3Joystick driverRight;
	
	public HumanInput()									//Constructor to initialize fields
	{
		this.driver = new XboxController(0);					//Driver Xbox on USB Port 0 on DS
		this.operator = new XboxController(1);					//Operator Xbox on USB Port 1 on DS
		this.driverLeft = new LogitechAttack3Joystick(2);
		this.driverRight = new LogitechAttack3Joystick(3);
	}
	
	//Driver Controls
	
	//---------Racing--------------
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getRacingThrottle()
	 */
	@Override
	public double getRacingThrottle()
	{
		return this.driver.getTriggerAxis(Hand.kRight) - this.driver.getTriggerAxis(Hand.kLeft); 
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getRacingTurn()
	 */
	@Override
	public double getRacingTurn()
	{
		return this.driver.getX(Hand.kLeft);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getRacingDampen()
	 */
	@Override
	public boolean getRacingDampen()
	{
		return this.driver.getXButton();
	}
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getRacingShift()
	 */
	@Override
	public boolean getRacingShift() {
		return this.driver.getYButton();
	}
	//------------Halo--------------
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getHaloThrottle()
	 */
	@Override
	public double getHaloThrottle()
	{
		return this.driver.getY(Hand.kLeft);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getHaloTurn()
	 */
	@Override
	public double getHaloTurn()
	{
		return this.driver.getX(Hand.kRight);
	}
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getHaloShift()
	 */
	@Override
	public boolean getHaloShift() {
		return this.driver.getYButton();
	}
	
	//-----------Single-Stick Arcade------------
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getArcadeThrottle()
	 */
	@Override
	public double getArcadeThrottle()
	{
		return this.driver.getY(Hand.kLeft);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getArcadeTurn()
	 */
	@Override
	public double getArcadeTurn()
	{
		return this.driver.getX(Hand.kLeft);
	}
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getArcadeShift()
	 */
	@Override
	public boolean getArcadeShift() {
		return this.driver.getYButton();
	}
	
	
	//----------Tank Controls---------
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getTankLeft()
	 */
	@Override
	public double getTankLeft()
	{
		return this.driver.getY(Hand.kLeft);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getTankRight()
	 */
	@Override
	public double getTankRight()
	{
		return this.driver.getY(Hand.kRight);
	}
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getTankShift()
	 */
	@Override
	public boolean getTankShift() {
		return this.driver.getYButton();
	}
	
	//----------Logitech Controls------
	//TODO Change to WPILIB Logitech class
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getLeftJoystickThrottle()
	 */
	@Override
	public double getLeftJoystickThrottle()
	{
		return -driverLeft.getY();
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getRightJoystickThrottle()
	 */
	@Override
	public double getRightJoystickThrottle()
	{
		return -driverRight.getY();
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getJoystickShift()
	 */
	@Override
	public boolean getJoystickShift()
	{
		return driverLeft.getTrigger() || driverRight.getTrigger();
	}
	//---------operator controls-------
	
	
	// controls whether clamp is open or closed
    /* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getOperatorClamp()
	 */
    @Override
	public double getOperatorIntake()	// getter method for clamp open or closed
    {
    		return operator.getTriggerAxis(Hand.kRight);
    }
    
    //controls speed of intake wheels
    /* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getOperatorSpeed()
	 */
    @Override
	public double getOperatorSpeed() //getter method for intake roller speed
    {
    		return operator.getY(Hand.kLeft);
    }

	//-----------Auto Selection-----------
	@Override
	public boolean getAutoInc() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getAutoDec() {
		// TODO Auto-generated method stub
		return false;
	}

	//TODO Operator Controls
	// Operator Controls
	//returns left joystick of elevator of operator controller
	public double getElevator()
	{
		return this.operator.getY(Hand.kRight);
	}
	//returns input after comparing to deadzone
	public double deadzone(double Input, double deadzone) {
		if (Math.abs(Input) < deadzone) {
			return Input;
		}
		return 0;
	}
	
	@Override
	public boolean getOperatorClamp() {
		return operator.getBumper(Hand.kLeft);
	}

}
