
package org.usfirst.frc.team1360.robot;

import java.io.IOException;
import java.util.Arrays;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.teleop.TeleopControl;
import org.usfirst.frc.team1360.robot.util.OrbitCamera;
import org.usfirst.frc.team1360.server.Connection;
import org.usfirst.frc.team1360.navx.*;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {	
	private static Robot instance;
	
	private RobotOutput robotOutput;
	private HumanInput humanInput;
	private SensorInput sensorInput;
	private TeleopControl teleopControl;
	private OrbitCamera camera = new OrbitCamera();
	
	public Robot()
	{
		instance = this;
	}
	
    public void robotInit()
    {
    		this.robotOutput = RobotOutput.getInstance();
    		this.humanInput = HumanInput.getInstance();
    		this.teleopControl = TeleopControl.getInstance();
    		this.sensorInput = SensorInput.getInstance();
    		
    		this.sensorInput.reset();
    	
    		//TODO Add Camera Init
    }
    
    	public static Robot getInstance()
    	{
    		return instance;
    	}	
    

    public void autonomousInit() 
    {
    		this.sensorInput.reset();
    		this.sensorInput.resetAHRS();
    }

    public void disabledInit()
    {
    		this.robotOutput.stopAll();
    		this.teleopControl.disable();
    }
    
    public void disabledPeriodic()
    {

    }

    public void autonomousPeriodic()
    {
    		this.camera.updateCamera();
    	
		SmartDashboard.putNumber("NavX Yaw", this.sensorInput.getAHRSYaw());
    }


    public void teleopPeriodic()
    {
        this.teleopControl.runCycle();
        this.camera.updateCamera();
    }
 
}
