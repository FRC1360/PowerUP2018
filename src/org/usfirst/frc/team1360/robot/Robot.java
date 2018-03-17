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

//	public static Trajectory trajectorySwitchLScaleR1;
//	public static Trajectory trajectorySwitchLScaleR2;
	public static Trajectory trajectorySwitchLScaleL;
	public static Trajectory trajectorySwitchRScaleR;
	public static Trajectory trajectorySwitchOneCubeRight;
	public static Trajectory trajectorySwitchOneCubeLeft;
	public static Trajectory trajectorySwitchFar2CubeR1;
	public static Trajectory trajectorySwitchFar2CubeR2;
	public static Trajectory trajectorySwitchFar2CubeL1;

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
		Singleton.configure(TeleopDrive.class);
		Singleton.configure(TeleopIntake.class);
		Singleton.configure(TeleopElevator.class);
		Singleton.configure(TeleopArm.class);
		teleopControl = Singleton.configure(TeleopControl.class);
		
		//CameraServer.getInstance().startAutomaticCapture();
		
		
		robotOutput.clearStickyFaults();
		sensorInput.reset();
		
		
		arm.start();
		elevator.start();
		
		
		Waypoint[] pointsSwitchLScaleL = new Waypoint[] {
				new Waypoint(1.63, 4, 0),
				new Waypoint(12, 2, 0),
				new Waypoint(20, 9, Pathfinder.d2r(90)),
				new Waypoint(19.5, 19, Pathfinder.d2r(90)),
				new Waypoint(23, 22, 0)
		};
		
		Waypoint[] pointsSwitchRScaleR = new Waypoint[] {
				new Waypoint(1.63, 4, 0),
				new Waypoint(23, 5.5, Pathfinder.d2r(35))
		};
		/*
		Waypoint[] pointsSwitchLScaleR1 = new Waypoint[] {
				new Waypoint(1.63, 4, 0),
				new Waypoint(23, 5.5, Pathfinder.d2r(35))
		};
		
		Waypoint[] pointsSwitchLScaleR2 = new Waypoint[] {
				new Waypoint(23, 5.5, Pathfinder.d2r(150)),
				new Waypoint(19.5, 10, Pathfinder.d2r(90)),
				new Waypoint(19.5, 15.5, Pathfinder.d2r(90))
		};
		*/
		Waypoint[] pointsSwitchOneCubeLeft = new Waypoint[] {
				new Waypoint(22, 5.5, Pathfinder.d2r(150)),
				new Waypoint(19.5, 10, Pathfinder.d2r(90)),
				new Waypoint(20, 15.5, Pathfinder.d2r(90))
		};
		
		Waypoint[] pointsSwitchOneCubeRight = new Waypoint[] {
				new Waypoint(22, 5.5, Pathfinder.d2r(150)),
				new Waypoint(19.5, 10, Pathfinder.d2r(90)),
				new Waypoint(20, 15.5, Pathfinder.d2r(90))
		};
		
		Waypoint[] pointsSwitchFar2CubeR1 = new Waypoint[] {
				new Waypoint(1.63, 4, 0),
				new Waypoint(10, 2.5, 0),
				new Waypoint(14, 6.5, Pathfinder.d2r(90))
		};
		
		Waypoint[] pointsSwitchFar2CubeR2 = new Waypoint[] {
				new Waypoint(13, 5.5, 0),
				new Waypoint(17, 4.5, 0),
				new Waypoint(21, 7, Pathfinder.d2r(90))
		};
		
		Waypoint[] pointsSwitchFar2CubeL1 = new Waypoint[] {
				new Waypoint(1.63, 4, 0),
				new Waypoint(5.5, 11, Pathfinder.d2r(90)),
				new Waypoint(5.5, 15, Pathfinder.d2r(90)),
				new Waypoint(10, 18, 0)
		};
			
		Trajectory.Config configRR = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 4, 100);
		Trajectory.Config configLL = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 7, 100);
//		Trajectory.Config configLR1 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 4, 100);
//		Trajectory.Config configLR2 = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 5, 100);
		Trajectory.Config configRL = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 8, 7, 100);
		Trajectory.Config configL = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);
		Trajectory.Config configR = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 7, 4, 100);//jerk was 180
		Trajectory.Config configFar2R = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 6, 6, 100);
		Trajectory.Config configFar2L = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.025, 6, 6, 100);
		
//		trajectorySwitchLScaleL = Pathfinder.generate(pointsSwitchLScaleL, configLL);//switchLscaleL
//		trajectorySwitchLScaleR1 = Pathfinder.generate(pointsSwitchLScaleR1, configLR1);//switchLscaleR - 1
//		trajectorySwitchLScaleR2 = Pathfinder.generate(pointsSwitchLScaleR2, configLR2);//switchLscaleR - 2
//		trajectorySwitchRScaleR = Pathfinder.generate(pointsSwitchRScaleR, configRR);//switchRscaleR
//		trajectorySwitchOneCubeRight = Pathfinder.generate(pointsSwitchOneCubeLeft, configL);
//		trajectorySwitchOneCubeLeft = Pathfinder.generate(pointsSwitchOneCubeLeft, configR);
		trajectorySwitchFar2CubeR1 = Pathfinder.generate(pointsSwitchFar2CubeR1, configFar2R);
		trajectorySwitchFar2CubeR2 = Pathfinder.generate(pointsSwitchFar2CubeR2, configFar2R);
		trajectorySwitchFar2CubeL1 = Pathfinder.generate(pointsSwitchFar2CubeL1, configFar2L);
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
