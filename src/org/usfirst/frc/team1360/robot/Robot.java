/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1360.robot;

import java.io.File;

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
import org.usfirst.frc.team1360.robot.util.OrbitCamera;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.SingletonStatic;
import org.usfirst.frc.team1360.robot.util.log.MatchLogProvider;
import org.usfirst.frc.team1360.robot.util.log.MatchLogger;
import org.usfirst.frc.team1360.robot.util.position.DriveEncoderPositionProvider;
import org.usfirst.frc.team1360.robot.util.position.OrbitPositionProvider;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

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
	private OrbitPositionProvider position;
	private TeleopControl teleopControl;
	
	

	public static Trajectory trajectorySwitchLScaleL;
	public static Trajectory trajectorySwitchRScaleR;
	public static Trajectory trajectorySwitchRScaleL1;
	public static Trajectory trajectorySwitchRScaleL2;
	public static Trajectory trajectorySwitchLScaleR;
	public static Trajectory trajectorySwitchL;
	public static Trajectory trajectorySwitchR;

	
	private boolean IN_JAR = true;
	private String FILE_ROOT = "~/";

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
		position = Singleton.configure(DriveEncoderPositionProvider.class);
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
		
		CameraServer.getInstance().startAutomaticCapture();
		
		robotOutput.clearStickyFaults();
		sensorInput.reset();
		
		arm.start();
		elevator.start();
		
		/**Auto Path Naming System
		 * 
		 * waypoint "point+name"
		 * Trajectory "trajectory+name"
		 * Config "config+name"
		 * File "file+name"
		 * 
		 * Auto Routines 2018 root names
		 * 
		 * Two Cubes Switch Scale
		 * SwitchLScaleL - Two cube
		 * SwitchRScaleR - Two cube
		 * SwitchLScaleR - Single Switch
		 * SwitchRScaleL - Double Switch
		 * 
		 * Single Cube Switch
		 * SwitchL
		 * SwitchR
		 * 
		 * 
		 */
		
		//UNUSED
		/*
		//Switch Left Scale Right score on scale
		Waypoint[] pointsSwitchLScaleR1 = new Waypoint[] {
				new Waypoint(1.63, 4, 0),
				new Waypoint(23, 5.5, Pathfinder.d2r(35))
		};
		
		//Switch left scale right score on switch
		Waypoint[] pointsSwitchLScaleR2 = new Waypoint[] {
				new Waypoint(23, 5.5, Pathfinder.d2r(150)),
				new Waypoint(19.5, 10, Pathfinder.d2r(90)),
				new Waypoint(19.5, 15.5, Pathfinder.d2r(90))
		};
		*/
		//UNUSED
		
		//Two Cube Switch Scale
		//Switch Left Scale Left two cube
		Waypoint[] pointsSwitchLScaleL = new Waypoint[] {
				new Waypoint(1.63, 4, 0),
				new Waypoint(12, 2, 0),
				new Waypoint(20, 9, Pathfinder.d2r(90)),
				new Waypoint(19.5, 19, Pathfinder.d2r(90)),
				new Waypoint(23, 22, 0)
		};
		
		//Switch Right Scale Right two cube
		Waypoint[] pointsSwitchRScaleR = new Waypoint[] {
				new Waypoint(1.63, 4, 0),
				new Waypoint(23, 5.5, Pathfinder.d2r(35))
		};
		
		
		//Score first cube on switch right (Two cube)
		Waypoint[] pointsSwitchRScaleL1 = new Waypoint[] {
				new Waypoint(1.63, 4, 0),
				new Waypoint(10, 2.5, 0),
				new Waypoint(14, 6.5, Pathfinder.d2r(90))
		};
		
		//Go to score second cube on switch right (Two cube)
		Waypoint[] pointsSwitchRScaleL2 = new Waypoint[] {
				new Waypoint(13, 5.5, 0),
				new Waypoint(17, 4.5, 0),
				new Waypoint(21, 7, Pathfinder.d2r(90))
		};
		
		
		Waypoint[] pointsSwitchLScaleR = new Waypoint[] {
				new Waypoint(1.63, 4, 0),
				new Waypoint(12, 2, 0),
				new Waypoint(20, 9, Pathfinder.d2r(90)),
				new Waypoint(19.5, 19, Pathfinder.d2r(90)),
				new Waypoint(16.5, 23.5, Pathfinder.d2r(180)),
				new Waypoint(14, 21.5, Pathfinder.d2r(270))
		};
		
		
		//SWITCH AUTO PATHS
		Waypoint[] pointsSwitchR = new Waypoint[] {
				new Waypoint(1.63, 8.5, 0),
				new Waypoint(10, 8.5, 0)
		};
		
		Waypoint[] pointsSwitchL = new Waypoint[] {
				new Waypoint(1.63, 8.5, 0),
				new Waypoint(5.5, 13, Pathfinder.d2r(90)),
				new Waypoint(10, 18.25, 0)
		};
		

		//CONFIGS
		//Two Cube
		Trajectory.Config configSwitchRScaleR = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 4, 100);
		Trajectory.Config configSwitchLScaleL = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 7, 100);
		Trajectory.Config configSwitchRScaleL1 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 4, 100);
		Trajectory.Config configSwitchRScaleL2 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 5, 100);
		Trajectory.Config configSwitchLScaleR = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 7, 100);
		
		//Switch Only
		Trajectory.Config configSwitchL = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);
		Trajectory.Config configSwitchR = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
		



		//FILES
		//TwoCubes
		File fileSwitchLScaleL = new File(FILE_ROOT + "switchLScaleL.csv");
		File fileSwitchRScaleR = new File(FILE_ROOT + "switchRScaleR.csv");
		File fileSwitchRScaleL1 = new File(FILE_ROOT + "switchRScaleL1.csv");
		File fileSwitchRScaleL2 = new File(FILE_ROOT + "switchRScaleL2.csv");
		File fileSwitchLScaleR = new File(FILE_ROOT + "switchLScaleR.csv");
		
		//Switch Only
		File fileSwitchL = new File(FILE_ROOT + "switchL.csv");
		File fileSwitchR = new File(FILE_ROOT + "switchR.csv");

		
		if(IN_JAR) {
			//TRAJECTORY GENERATION
			//Two Cubes
			ClassLoader classLoader = getClass().getClassLoader();
			fileSwitchLScaleL = new File(classLoader.getResource("1360AutoPaths/switchLScaleL.csv").getFile());
			fileSwitchRScaleR = new File(classLoader.getResource("1360AutoPaths/switchRScaleR.csv").getFile());
			fileSwitchRScaleL1 = new File(classLoader.getResource("1360AutoPaths/switchRScaleL1.csv").getFile());
			fileSwitchRScaleL2 = new File(classLoader.getResource("1360AutoPaths/switchRScaleL2.csv").getFile());
			fileSwitchLScaleR = new File(classLoader.getResource("1360AutoPaths/switchLScaleR.csv").getFile());

			fileSwitchL = new File(classLoader.getResource("1360AutoPaths/switchL.csv").getFile());
			fileSwitchR = new File(classLoader.getResource("1360AutoPaths/switchR.csv").getFile());

			
		}

		trajectorySwitchLScaleL = Pathfinder.readFromCSV(fileSwitchLScaleL);
		trajectorySwitchRScaleL1 = Pathfinder.readFromCSV(fileSwitchRScaleL1);
		trajectorySwitchRScaleL2 = Pathfinder.readFromCSV(fileSwitchRScaleL2);
		trajectorySwitchLScaleR = Pathfinder.readFromCSV(fileSwitchLScaleR);
		trajectorySwitchRScaleR = Pathfinder.readFromCSV(fileSwitchRScaleR);
			
		//Switch Profiles
		trajectorySwitchL = Pathfinder.readFromCSV(fileSwitchL);
		trajectorySwitchR = Pathfinder.readFromCSV(fileSwitchR);

		

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
		matchLog.startVideoCache();
		
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
		
		matchLog.writeClean(String.format("X Pos = %f inches, Y Pos = %f inches,  Left Enc = %d ticks, Right Enc = %d ticks", 
				position.getX(), position.getY(), sensorInput.getLeftDriveEncoder(), sensorInput.getRightDriveEncoder()));
		
		SmartDashboard.putNumber("Left Velocity", Math.abs(sensorInput.getLeftEncoderVelocity()));
		SmartDashboard.putNumber("Right Velocity", Math.abs(sensorInput.getRightEncoderVelocity()));
		
		SmartDashboard.putNumber("Left", sensorInput.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right", sensorInput.getRightDriveEncoder());
		SmartDashboard.putNumber("X", position.getX());
		SmartDashboard.putNumber("Y", position.getY());
		SmartDashboard.putNumber("A", position.getA() * 180 / Math.PI);
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
		matchLog.startVideoCache();
		
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
		
		SmartDashboard.putNumber("Arm Velocity", sensorInput.getArmEncoderVelocity());
		
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
		SmartDashboard.putNumber("Elevator current", sensorInput.getElevatorCurrent());
		matchLog.write("Elevator current:" + sensorInput.getElevatorCurrent());
	}
	
	@Override
	public void disabledInit() {
		matchLog.writeClean("----------ROBOT DISABLED LOG ENDING----------");
		matchLog.stopVideoCache();

		try {
			AutonControl.stop();
		} catch (Throwable t) {
			matchLog.write(t.toString());
		}
		
		elevator.stop();
		arm.stop();
		position.stop();
		
		teleopControl.disable();
	}
	
	@Override
	public void disabledPeriodic() {
		
		AutonControl.select();
		SmartDashboard.putNumber("Left", sensorInput.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right", sensorInput.getRightDriveEncoder());
		
		SmartDashboard.putNumber("VEL RIGHT", sensorInput.getRightEncoderVelocity());
		
		SmartDashboard.putNumber("Left", sensorInput.getLeftDriveEncoder());
		SmartDashboard.putNumber("Right", sensorInput.getRightDriveEncoder());
		SmartDashboard.putNumber("X", position.getX());
		SmartDashboard.putNumber("Y", position.getY());
		SmartDashboard.putNumber("A", position.getA() * 180 / Math.PI);

		SmartDashboard.putNumber("Elevator Encoder", sensorInput.getElevatorEncoder());
		SmartDashboard.putNumber("Arm Encoder", sensorInput.getArmEncoder());
		SmartDashboard.putNumber("Arm Velocity", sensorInput.getArmEncoderVelocity());
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
	
	@Override
	public void testInit() {
		int armEps = 1;
		int elevatorEps = 10;
		int driveEps = 20;
		
		boolean armNominal = false;
		boolean elevatorNominal = false;
		
		
		sensorInput.resetArmEncoder();
		
		matchLog.writeHead();
		
		matchLog.writeClean("STARTING ARM SELF CHECK");
		
		arm.safety(0.5, false);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Check the arm encoder
		if(Math.abs(sensorInput.getArmEncoder()) <= armEps) {
			matchLog.writeClean("Arm Encoder: FAILURE");
		}
		else {
			matchLog.writeClean("Arm Encoder: Ready for Orbit");
			armNominal = true;
		}
		
		if(armNominal) {
			arm.safety(0.1, false);
			while(sensorInput.getArmEncoderVelocity() > 0 && !sensorInput.getArmSwitch()) {
				arm.safety(0.1, false);
			}
			
			if(!sensorInput.getArmSwitch()) {
				matchLog.writeClean("Arm Switch: FAILURE");
			}
			else
			{
				matchLog.writeClean("Arm Switch: Ready for Orbit");
			}
			
			arm.safety(0, false);
			
		}
		
		matchLog.writeClean("STARTING ELEVATOR SELF CHECK");
		
		sensorInput.resetElevatorEncoder();
		elevator.safety(0.25, false);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(Math.abs(sensorInput.getElevatorEncoder()) <= elevatorEps) {
			matchLog.writeClean("Elevator Encoder: FAILURE");
		}
		else {
			matchLog.writeClean("Elevator Encoder: Ready for Orbit");
			elevatorNominal = true;
		}
		
		if(elevatorNominal) {
			elevator.safety(-0.1, false);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			while(sensorInput.getElevatorVelocity() > 0 && !sensorInput.getBottomSwitch()) {
				elevator.safety(-0.1, false);
			}
			
			if(!sensorInput.getBottomSwitch()) {
				matchLog.writeClean("Elevator Bottom Switch: FAILURE");
			} else {
				matchLog.writeClean("Elevator Bottom Switch: Ready for Orbit");
			}
		}
		
		matchLog.writeClean("STARTING DRIVE SELF CHECK");
		
		sensorInput.resetLeftEncoder();
		
		robotOutput.tankDrive(0.5, 0);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		robotOutput.tankDrive(0, 0);
		
		if(Math.abs(sensorInput.getLeftDriveEncoder()) > driveEps) {
			matchLog.writeClean("Left Drive: Ready for Orbit");
		}
		else {
			matchLog.writeClean("Left Drive: FAILURE");
		}
		
		robotOutput.tankDrive(0, 0.5);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		robotOutput.tankDrive(0, 0);
		
		if(Math.abs(sensorInput.getRightDriveEncoder()) > driveEps) {
			matchLog.writeClean("Right Drive: Ready for Orbit");
		}
		else {
			matchLog.writeClean("Right Drive: FAILURE");
		}

		matchLog.writeClean("SELF TEST COMPLETE LOG ENDING");
		
		
		
		
	}
}
