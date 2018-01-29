package org.usfirst.frc.team1360.robot.auto.drive;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.usfirst.frc.team1360.robot.IO.RobotOutputProvider;
import org.usfirst.frc.team1360.robot.IO.SensorInputProvider;
import org.usfirst.frc.team1360.robot.util.Singleton;
import org.usfirst.frc.team1360.robot.util.log.Riolog;
import org.usfirst.frc.team1360.robot.util.position.DriveEncoderPositionProvider;
import org.usfirst.frc.team1360.robot.util.position.OrbitPosition;
import org.usfirst.frc.team1360.robot.util.position.OrbitPositionProvider;

@RunWith(MockitoJUnitRunner.class)
public class ArcToTargetTest {

    private double startX = 0, startY = 0;
    private double destX, destY;

    @Mock
    protected RobotOutputProvider robotOutput;
    @Mock
    protected SensorInputProvider sensorInput;

    protected OrbitPositionProvider position;

    private Thread robot;
    private DriveTrain driveTrain;

    @Before
    public void setUp() throws Exception {
        Singleton.configure(Riolog.class);

        //register mocked inout and output
        Singleton.configure(RobotOutputProvider.class, robotOutput);
        Singleton.configure(SensorInputProvider.class, sensorInput);
        
        position = Singleton.configure(DriveEncoderPositionProvider.class);

        driveTrain = new DriveTrain();

        //this records output values in order to convert into encoder rotations
        doAnswer(driveTrain.tankDrive()).when(robotOutput).tankDrive(anyDouble(), anyDouble());

        doAnswer(driveTrain.getLeftDriveEncoder()).when(sensorInput).getLeftDriveEncoder();
        doAnswer(driveTrain.getRightDriveEncoder()).when(sensorInput).getRightDriveEncoder();

        robot = new Thread(driveTrain);
        robot.start();
        
        position.start();
    }

    @After
    public void tearDown() throws Exception {
        robot.interrupt();
    }

    @Test
    public void straightNoAngle() throws InterruptedException {
        destX = 0;
        destY = 50;

        new ArcToTarget(5_000, startX, startY, destX, destY, 0, 2).runUntilFinish();

        checkTarget(2);
    }

    @Test
    public void curveToRight() throws InterruptedException {
        destX = 50;
        destY = 50;

        new ArcToTarget(5_000, startX, startY, destX, destY, 0, 2).runUntilFinish();

        checkTarget(2);
    }

    @Test
    public void curveToLeft() throws InterruptedException {
        destX = -50;
        destY = 50;

        new ArcToTarget(5_000, startX, startY, destX, destY, 0, 2).runUntilFinish();

        checkTarget(2);
    }
    
    private void checkTarget(double epsilon) {
    	double dx = destX - position.getX();
    	double dy = destY - position.getY();
    	double d = Math.sqrt(dx * dx + dy * dy);
    	
    	if (d > epsilon)
    		fail(String.format("Expected: <%f\"; Actual: %f\"", epsilon, d));
    }

    private static class DriveTrain implements Runnable {

        private double leftEncoder;
        private double rightEncoder;

        private double leftMotor;
        private double rightMotor;

        Answer<Void> tankDrive() {
            return invocation -> {
                //these are values passed to robotOutput.tankDrive(left, right);
                leftMotor = invocation.getArgument(0);
                rightMotor = invocation.getArgument(1);

                return null;
            };
        }

        Answer<Integer> getLeftDriveEncoder() {
            return invocation -> (int) leftEncoder;
        }

        Answer<Integer> getRightDriveEncoder() {
            return invocation -> (int) rightEncoder;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    updateEncoders();
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                //exit
            }
        }

        private void updateEncoders() {
            leftEncoder += leftMotor * 50;
            rightEncoder += rightMotor * 50;
        }
    }
}