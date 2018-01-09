package org.usfirst.frc.team1360.robot.util;

import edu.wpi.first.wpilibj.Joystick;

public class LogitechAttack3Joystick extends Joystick {

	public LogitechAttack3Joystick(int port) {
		super(port);
	}
	
	public double getXAxis()
	{
		return this.getRawAxis(0);
	}
	
	public double getYAxis()
	{
		return this.getRawAxis(1);
	}
	
	public double getSlider()
	{
		return this.getRawAxis(2);
	}
	
	public boolean getTrigger()
	{
		return this.getRawButton(0);
	}
	
	public boolean getButton2()
	{
		return this.getRawButton(1);
	}
	
	public boolean getButton3()
	{
		return this.getRawButton(2);
	}
	
	public boolean getButton4()
	{
		return this.getRawButton(3);
	}
	
	public boolean getButton5()
	{
		return this.getRawButton(4);
	}
	
	public boolean getButton6()
	{
		return this.getRawButton(5);
	}
	
	public boolean getButton7()
	{
		return this.getRawButton(6);
	}
	
	public boolean getButton8()
	{
		return this.getRawButton(7);
	}
	
	public boolean getButton9()
	{
		return this.getRawButton(8);
	}
	
	public boolean getButton10()
	{
		return this.getRawButton(9);
	}
	
	public boolean getButton11()
	{
		return this.getRawButton(10);
	}

}
