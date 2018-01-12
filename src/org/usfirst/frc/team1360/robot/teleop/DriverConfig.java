package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitPID;
import org.usfirst.frc.team1360.robot.util.Singleton;

public enum DriverConfig {
	RACING 
	{
		@Override
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput)
		{
			
			boolean deadzone = Math.abs(humanInput.getRacingTurn()) < 0.2;
			if (deadzone)
			{
				double speed = humanInput.getRacingThrottle();
				
				if (!lastDeadzone || Math.abs(speed) < 0.01)
				{
					driveController.SetSetpoint(Singleton.get(SensorInputProvider.class).getAHRSYaw());
				}
				

				driveController.SetInput(Singleton.get(SensorInputProvider.class).getAHRSYaw());
				driveController.CalculateError();
					
				robotOutput.arcadeDrivePID(speed, Math.abs(speed) * driveController.GetOutput());
			}
			else
			{
				robotOutput.arcadeDrive(humanInput.getRacingThrottle(), humanInput.getRacingTurn());
			}
			lastDeadzone = deadzone;
			
			double turn = humanInput.getRacingTurn();
			boolean change = humanInput.getRacingDampen();
			
			robotOutput.shiftGear(humanInput.getRacingShift());
			
			if(Math.abs(turn) < 0.2)
				turn = 0;
			
			if(change)
			{
				robotOutput.arcadeDrive(humanInput.getRacingThrottle() / 2, turn / 2);	
			}
			else
			{
				robotOutput.arcadeDrive(humanInput.getRacingThrottle(), turn);
			}
			

		}
	},
	
	HALO 
	{
		@Override
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput)
		{
			double turn = humanInput.getHaloTurn();
			
			if(Math.abs(turn) < 0.2)
				turn = 0;
			
			robotOutput.arcadeDrive(-humanInput.getHaloThrottle(), turn);
			robotOutput.shiftGear(humanInput.getHaloShift());
		}
	},
	
	TANK 
	{

		@Override
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput)
		{
			double left = humanInput.getTankLeft();
			double right = humanInput.getTankRight();
			
			if(Math.abs(left) < 0.2)
				left = 0;
			
			if(Math.abs(right) < 0.2)
				right = 0;
			
			robotOutput.tankDrive(left, right);
			robotOutput.shiftGear(humanInput.getTankShift());
		}
		
	},
	
	ARCADE
	{

		@Override
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput) 
		{
			double turn = humanInput.getArcadeTurn();
			
			if(Math.abs(turn) < 0.2)
				turn = 0;
			
			robotOutput.arcadeDrive(humanInput.getArcadeThrottle(), turn);
			robotOutput.shiftGear(humanInput.getArcadeShift());
		}
		
	},
	
	JOYSTICKTANK
	{
		@Override
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput)
		{
			double left = humanInput.getLeftJoystickThrottle();
			double right = humanInput.getRightJoystickThrottle();
			
			
			robotOutput.tankDrive(left, right);
			robotOutput.shiftGear(humanInput.getJoystickShift());
			

		}
	};
	
	private static OrbitPID driveController = new OrbitPID(0.1, 0.00005, 0.01, 0.5);
	private static boolean lastDeadzone = false;
	
	public abstract void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput);
}
