/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1360.robot;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.HumanInputProvider;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.auto.AutonControl;
import org.usfirst.frc.team1360.robot.subsystem.Arm;
import org.usfirst.frc.team1360.robot.subsystem.ArmProvider;
import org.usfirst.frc.team1360.robot.subsystem.Drive;
import org.usfirst.frc.team1360.robot.subsystem.Elevator;
import org.usfirst.frc.team1360.robot.subsystem.ElevatorProvider;
import org.usfirst.frc.team1360.robot.subsystem.Intake;
import org.usfirst.frc.team1360.robot.teleop.TeleopArm;
import org.usfirst.frc.team1360.robot.teleop.TeleopControl;
import org.usfirst.frc.team1360.robot.teleop.TeleopDrive;
import org.usfirst.frc.team1360.robot.teleop.TeleopElevator;
import org.usfirst.frc.team1360.robot.teleop.TeleopIntake;
import org.usfirst.frc.team1360.robot.util.OrbitCamera;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonStatic;
import org.usfirst.frc.team1360.robot.util.log.LogProvider;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;
import org.usfirst.frc.team1360.robot.util.log.MatchLogger;
import org.usfirst.frc.team1360.robot.util.log.TempFileLog;
import org.usfirst.frc.team1360.robot.util.position.DriveEncoderPositionProvider;
import org.usfirst.frc.team1360.robot.util.position.OrbitPositionProvider;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
@SingletonStatic
public class Robot extends TimedRobot {
	private LogProvider log;
	private ElevatorProvider elevator;
	private ArmProvider arm;
	private MatchLogProvider matchLog;
	private HumanInputProvider humanInput;
	private SensorInputProvider sensorInput;
	private RobotOutputProvider robotOutput;
	private OrbitPositionProvider position;
	private TeleopControl teleopControl;
	
	private OrbitCamera camera;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		log = Singleton.configure(TempFileLog.class);
		matchLog = Singleton.configure(MatchLogger.class);
		humanInput = Singleton.configure(HumanInput.class);
		sensorInput = Singleton.configure(SensorInput.class);
		robotOutput = Singleton.configure(RobotOutput.class);
		position = Singleton.configure(DriveEncoderPositionProvider.class);
		Singleton.configure(Drive.class);
		Singleton.configure(Intake.class);
		elevator = Singleton.configure(Elevator.class);
		arm = Singleton.configure(Arm.class);
		Singleton.configure(TeleopDrive.class);
		Singleton.configure(TeleopIntake.class);
		Singleton.configure(TeleopElevator.class);
		Singleton.configure(TeleopArm.class);
		teleopControl = Singleton.configure(TeleopControl.class);
		
		camera = new OrbitCamera();
		

		
		matchLog.writeHead();
		
		robotOutput.clearStickyFaults();
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
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		matchLog.write("----------STARTING AUTO PERIOD----------");
		matchLog.startVideoCache();
		
		position.start();
		AutonControl.start();
		
		sensorInput.reset();
		position.start();
		position.reset(0,0,0);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		camera.updateCamera();
		
		matchLog.write(String.format("X Pos = %f inches, Y Pos = %f inches,  Left Enc = %d ticks, Right Enc = %d ticks", 
				position.getX(), position.getY(), sensorInput.getLeftDriveEncoder(), sensorInput.getRightDriveEncoder()));
		
		SmartDashboard.putNumber("Left", sensorInput.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right", sensorInput.getRightDriveEncoder());
		SmartDashboard.putNumber("X", position.getX());
		SmartDashboard.putNumber("Y", position.getY());
		SmartDashboard.putNumber("A", position.getA() * 180 / Math.PI);
		SmartDashboard.putNumber("Elevator Encoder", sensorInput.getElevatorEncoder());
		SmartDashboard.putNumber("Arm Encoder", sensorInput.getArmEncoder());
		SmartDashboard.putBoolean("Arm Switch", sensorInput.getArmSwitch());
		SmartDashboard.putBoolean("Top Switch", sensorInput.getTopSwitch());
		SmartDashboard.putBoolean("BottomSwitch", sensorInput.getBottomSwitch());
	}

	@Override
	public void teleopInit() {
		camera.updateCamera();
		
		matchLog.write("----------STARTING TELEOP PERIOD----------");
		matchLog.startVideoCache();
		
		AutonControl.stop();
	}
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		matchLog.write(String.format("Elevator Enc = %d, Arm Enc = %d", 
				sensorInput.getElevatorEncoder(), sensorInput.getArmEncoder()));
		
		SmartDashboard.putNumber("Left", sensorInput.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right", sensorInput.getRightDriveEncoder());
		SmartDashboard.putNumber("X", position.getX());
		SmartDashboard.putNumber("Y", position.getY());
		SmartDashboard.putNumber("A", position.getA() * 180 / Math.PI);
		SmartDashboard.putNumber("Elevator Encoder", sensorInput.getElevatorEncoder());
		SmartDashboard.putNumber("Arm Encoder", sensorInput.getArmEncoder());
		SmartDashboard.putBoolean("Arm Switch", sensorInput.getArmSwitch());
		SmartDashboard.putBoolean("Top Switch", sensorInput.getTopSwitch());
		SmartDashboard.putBoolean("BottomSwitch", sensorInput.getBottomSwitch());
		
		teleopControl.runCycle();
	}
	
	@Override
	public void disabledInit() {
		matchLog.write("----------ROBOT DISABLED LOG ENDING----------");
		matchLog.stopVideoCache();
		
		elevator.stop();
		arm.stop();
		AutonControl.stop();
		position.stop();
	}
	
	@Override
	public void disabledPeriodic() {
		camera.updateCamera();
		
		SmartDashboard.putNumber("Left", sensorInput.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right", sensorInput.getRightDriveEncoder());
		SmartDashboard.putNumber("X", position.getX());
		SmartDashboard.putNumber("Y", position.getY());
		SmartDashboard.putNumber("A", position.getA() * 180 / Math.PI);
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
