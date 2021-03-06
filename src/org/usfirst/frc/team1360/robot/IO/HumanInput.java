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

	@Override
	public boolean getFileOverride(){
		return driver.getStartButtonPressed() && driver.getBackButtonPressed();
	}
	
	//---------Racing--------------
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getRacingThrottle()
	 */
	@Override
	public double getRacingThrottle()
	{
		return deadzone(driver.getTriggerAxis(Hand.kRight), 0.1) - deadzone(driver.getTriggerAxis(Hand.kLeft), 0.1); 
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team1360.robot.IO.HumanInputProvider#getRacingTurn()
	 */
	@Override
	public double getRacingTurn()
	{
		return deadzone(driver.getX(Hand.kLeft), 0.2);
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
		return this.driver.getYButton(); //inversed for default high gear
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
		return -this.driver.getY(Hand.kLeft);
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
	
	//----------Cheesy Drive Controls----------

	@Override
	public double getCheesyThrottle() {
		// TODO Auto-generated method stub
		return -driver.getY(Hand.kLeft);
	}

	@Override
	public double getCheesyTurn() {
		// TODO Auto-generated method stub
		return driver.getX(Hand.kRight);
	}

	@Override
	public boolean getCheesyShift() {
		// TODO Auto-generated method stub
		return driver.getBumper(Hand.kLeft);
	}

	@Override
	public boolean getCheesyQuickTurn() {
		// TODO Auto-generated method stub
		return driver.getBumper(Hand.kRight);
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

	@Override
	public double getArm() {
		return deadzone(-operator.getY(Hand.kLeft), 0.2);
	}
	
	@Override
	public boolean getOperatorClamp() {
		return operator.getBumper(Hand.kLeft);
	}
	
	@Override
	public boolean getScaleMax() {
		return operator.getYButton();
	}
	
	@Override
	public boolean getSwitch() {
		return operator.getXButton();
	}
	
	@Override
	public boolean getScaleLow() {
		return operator.getBButton();
	}
	
	@Override
	public boolean getIntake() {
		return operator.getAButton();
	}
	
	@Override
	public boolean getClimb() {
		return operator.getPOV(0) == 0;
	}
	
	@Override
	public boolean getBar() {
		return operator.getPOV(0) == 90;
	}
	
	@Override
	public boolean getDriverOverride() {
		return driver.getBumper(Hand.kLeft) && driver.getBumper(Hand.kRight);
	}

	//-----------Auto Selection-----------
	@Override
	public boolean getAutoInc() {
		return operator.getPOV(0) == 0;
	}

	@Override
	public boolean getAutoDec() {
		return operator.getPOV(0) == 180;
	}
	
	

	//TODO Operator Controls
	// Operator Controls
	//returns left joystick of elevator of operator controller
	public double getElevator()
	{
		return deadzone(-operator.getY(Hand.kRight), 0.2);
	}
	
	//returns input after comparing to deadzone
	public double deadzone(double Input, double deadzone) {
		if (Math.abs(Input) > deadzone) {
			return Input;
		}
		return 0;
	}

	
}
