package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.OrbitPID;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public enum DriverConfig {
	RACING 
	{
		@Override
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput, SensorInputProvider sensorInput)
		{
			double speed = humanInput.getRacingThrottle();
			double turn = humanInput.getRacingTurn();
			boolean change = humanInput.getRacingDampen();
			
			if(change)
			{
				speed = speed / 2;
				turn = turn / 2;
			}
			
			double elevatorHeight = sensorInput.getElevatorEncoder();
			matchLogger.write("Elevator Enc == " + elevatorHeight / 50);
			double multiplier = Math.cos((1/33.165) * (elevatorHeight / 50));
			matchLogger.write("Multiplier == " + multiplier);
			
			speed = speed * multiplier;
			
			robotOutput.arcadeDrive(speed, turn);
			robotOutput.shiftGear(humanInput.getRacingShift());
		}
	},
	RACING_CURRENT_LIMIT
	{
		double voltageMax = 1.0;
		
		@Override
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput, SensorInputProvider sensorInput)
		{
			double speed = humanInput.getRacingThrottle();
			double turn = humanInput.getRacingTurn();
			boolean change = humanInput.getRacingDampen();
			
			if(change)
			{
				speed = speed / 2;
				turn = turn / 2;
			}
			
			double elevatorHeight = sensorInput.getElevatorEncoder();
			matchLogger.write("Elevator Enc == " + elevatorHeight / 100);
			double multiplier = Math.cos((1/33.165) * (elevatorHeight / 100));
			matchLogger.write("Multiplier == " + multiplier);
			
			speed = speed * multiplier;
			
			robotOutput.arcadeDrive(speed, turn);
			robotOutput.shiftGear(humanInput.getRacingShift());
		}
	},
	HALO 
	{
		@Override
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput, SensorInputProvider sensorInput)
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
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput, SensorInputProvider sensorInput)
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
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput, SensorInputProvider sensorInput) 
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
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput, SensorInputProvider sensorInput)
		{
			
			robotOutput.tankDrive(humanInput.getLeftJoystickThrottle(), humanInput.getRightJoystickThrottle());
			robotOutput.shiftGear(humanInput.getJoystickShift());
			

		}
	},
	
	CHEESYDRIVE
	{
		@Override
		public void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput, SensorInputProvider sensorInput)
		{
			robotOutput.cheesyDrive(humanInput.getCheesyThrottle(),
					humanInput.getCheesyTurn(),
					humanInput.getCheesyQuickTurn(),
					humanInput.getCheesyShift());
			robotOutput.shiftGear(humanInput.getCheesyShift());
		}
		
	};
	
	protected MatchLogProvider matchLogger = Singleton.get(MatchLogProvider.class);
	public abstract void calculate(RobotOutputProvider robotOutput, HumanInputProvider humanInput, SensorInputProvider sensorInput);
}
