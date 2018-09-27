/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
// New comment on the brand new dorian branch
package org.usfirst.frc.team1360.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.auto.AutonControl;
import org.usfirst.frc.team1360.robot.subsystem.Arm;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.Climber;
import org.usfirst.frc.team1360.robot.subsystem.Drive;
import org.usfirst.frc.team1360.robot.subsystem.Elevator;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.Intake;
import org.usfirst.frc.team1360.robot.teleop.TeleopArm;
import org.usfirst.frc.team1360.robot.teleop.TeleopClimber;
import org.usfirst.frc.team1360.robot.teleop.TeleopControl;
import org.usfirst.frc.team1360.robot.teleop.TeleopDrive;
import org.usfirst.frc.team1360.robot.teleop.TeleopElevator;
import org.usfirst.frc.team1360.robot.teleop.TeleopIntake;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonStatic;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;
import org.usfirst.frc.team1360.robot.util.log.MatchLogger;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
@SingletonStatic
public class Robot extends TimedRobot {
    private ElevatorProvider elevator;
    private ArmProvider arm;
    private MatchLogProvider matchLog;
    private HumanInputProvider humanInput;
    private SensorInputProvider sensorInput;
    private RobotOutputProvider robotOutput;
    private TeleopControl teleopControl;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		matchLog = Singleton.configure(MatchLogger.class);
		humanInput = Singleton.configure(HumanInput.class);
		sensorInput = Singleton.configure(SensorInput.class);
		robotOutput = Singleton.configure(RobotOutput.class);
		Singleton.configure(Drive.class);
		Singleton.configure(Intake.class);
		arm = Singleton.configure(Arm.class);
		elevator = Singleton.configure(Elevator.class);
		Singleton.configure(Climber.class);
		Singleton.configure(TeleopDrive.class);
		Singleton.configure(TeleopIntake.class);
		Singleton.configure(TeleopElevator.class);
		Singleton.configure(TeleopArm.class);
		Singleton.configure(TeleopClimber.class);
		teleopControl = Singleton.configure(TeleopControl.class);
		
		//CameraServer.getInstance().startAutomaticCapture();
		
		//robotOutput.clearStickyFaults();
		sensorInput.reset();
		
		arm.start();
		elevator.start();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 * You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		matchLog.writeHead();
		
		matchLog.writeClean("----------STARTING AUTO PERIOD----------");
		
		sensorInput.reset();
		
		AutonControl.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		
		elevator.logState();
		arm.logState();
		
		matchLog.writeClean(String.format("Left Enc = %d ticks, Right Enc = %d ticks",
				sensorInput.getLeftDriveEncoder(), sensorInput.getRightDriveEncoder()));
		
		SmartDashboard.putNumber("Left Velocity", Math.abs(sensorInput.getLeftEncoderVelocity()));
		SmartDashboard.putNumber("Right Velocity", Math.abs(sensorInput.getRightEncoderVelocity()));
		
		SmartDashboard.putNumber("Left", sensorInput.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right", sensorInput.getRightDriveEncoder());
		SmartDashboard.putNumber("Angle NAVX", sensorInput.getAHRSYaw());
		SmartDashboard.putNumber("Elevator Encoder", sensorInput.getElevatorEncoder());
		SmartDashboard.putNumber("Arm Encoder", sensorInput.getArmEncoder());
		SmartDashboard.putBoolean("Arm Switch", sensorInput.getArmSwitch());
		SmartDashboard.putBoolean("Top Switch", sensorInput.getTopSwitch());
		SmartDashboard.putBoolean("BottomSwitch", sensorInput.getBottomSwitch());
	}

	@Override
	public void teleopInit() {
		
		disabledInit();
		
		matchLog.write("----------STARTING TELEOP PERIOD----------");
		
		arm.calibrate(false);
	}
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		teleopControl.runCycle();
		
		elevator.logState();
		arm.logState();

		matchLog.writeClean(String.format("Elevator Enc = %d, Arm Enc = %d",
				sensorInput.getElevatorEncoder(), sensorInput.getArmEncoder()));
		matchLog.writeClean("Bottom Switch: " + sensorInput.getBottomSwitch() +
						", Top Switch: " + sensorInput.getTopSwitch() +
						", Arm Switch: " + sensorInput.getArmSwitch());


		SmartDashboard.putNumber("Left", sensorInput.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right", sensorInput.getRightDriveEncoder());
		SmartDashboard.putNumber("Angle NAVX", sensorInput.getAHRSYaw());
		SmartDashboard.putNumber("Elevator Encoder", sensorInput.getElevatorEncoder());
		SmartDashboard.putNumber("Arm Encoder", sensorInput.getArmEncoder());
		SmartDashboard.putBoolean("Arm Switch", sensorInput.getArmSwitch());
		SmartDashboard.putBoolean("Top Switch", sensorInput.getTopSwitch());
		SmartDashboard.putBoolean("BottomSwitch", sensorInput.getBottomSwitch());

	}
	
	@Override
	public void disabledInit() {
		matchLog.writeClean("----------ROBOT DISABLED LOG ENDING----------");

		try {
			AutonControl.stop();
		} catch (Throwable t) {
			matchLog.write(t.toString());
		}
		
		elevator.stop();
		arm.stop();

		teleopControl.disable();
	}
	
	@Override
	public void disabledPeriodic() {
		
		AutonControl.select();
		SmartDashboard.putNumber("Left", sensorInput.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right", sensorInput.getRightDriveEncoder());
		
		SmartDashboard.putNumber("VEL RIGHT", sensorInput.getRightEncoderVelocity());

		SmartDashboard.putNumber("Angle NAVX", sensorInput.getAHRSYaw());
		
		SmartDashboard.putNumber("Left", sensorInput.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right", sensorInput.getRightDriveEncoder());

		SmartDashboard.putNumber("Elevator Encoder", sensorInput.getElevatorEncoder());
		SmartDashboard.putNumber("Arm Encoder", sensorInput.getArmEncoder());
		SmartDashboard.putBoolean("Arm Switch", sensorInput.getArmSwitch());
		SmartDashboard.putBoolean("Top Switch", sensorInput.getTopSwitch());
		SmartDashboard.putBoolean("BottomSwitch", sensorInput.getBottomSwitch());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
		
	}
}
