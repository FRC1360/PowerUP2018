package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitPID;
import org.usfirst.frc.team1360.robot.util.Singleton;

public enum DriverConfig {
	RACING 
	{
		@Override
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput)
		{
			double speed = humanInput.getRacingThrottle();
			double turn = humanInput.getRacingTurn();
			boolean change = humanInput.getRacingDampen();

			if(Math.abs(turn) < 0.2)
				turn = 0;
			
			if(change)
			{
				speed = speed / 2;
				turn = turn / 2;
			}
			
			robotOutput.arcadeDrive(speed, turn);
			robotOutput.shiftGear(humanInput.getRacingShift());
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
			
			robotOutput.tankDrive(humanInput.getLeftJoystickThrottle(), humanInput.getRightJoystickThrottle());
			robotOutput.shiftGear(humanInput.getJoystickShift());
			

		}
	},
	
	CHEESYDRIVE
	{
		@Override
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput)
		{
			robotOutput.cheesyDrive(humanInput.getCheesyThrottle(),
					humanInput.getCheesyTurn(),
					humanInput.getCheesyQuickTurn(),
					humanInput.getCheesyShift());
			robotOutput.shiftGear(humanInput.getCheesyShift());
		}
		
	};
	
	public abstract void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput);
}
