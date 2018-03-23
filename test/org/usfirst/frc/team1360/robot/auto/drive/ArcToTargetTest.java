package org.usfirst.frc.team1360.robot.auto.drive;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doAnswer;

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
import org.usfirst.frc.team1360.robot.util.position.OrbitPositionProvider;

@RunWith(MockitoJUnitRunner.class)
public class ArcToTargetTest {

    private final double startX = 0, startY = 0;
    private double destX, destY;

    @Mock
    protected RobotOutputProvider robotOutput;
    @Mock
    protected SensorInputProvider sensorInput;

    protected DriveEncoderPositionProvider position;

    private DriveTrain driveTrain;

    @Before
    public void setUp() throws Exception {
        Singleton.configure(Riolog.class);

        //register mocked inout and output
        Singleton.configure(RobotOutputProvider.class, robotOutput);
        Singleton.configure(SensorInputProvider.class, sensorInput);
        
        position = Singleton.configure(DriveEncoderPositionProvider.class);

        driveTrain = new DriveTrain(position.getInchesPerTick());

        //this records output values in order to convert into encoder rotations
        doAnswer(driveTrain.tankDrive()).when(robotOutput).tankDrive(anyDouble(), anyDouble());
        doAnswer(driveTrain.arcadeDrivePID()).when(robotOutput).arcadeDrivePID(anyDouble(), anyDouble());

        doAnswer(driveTrain.getLeftDriveEncoder()).when(sensorInput).getLeftDriveEncoder();
        doAnswer(driveTrain.getRightDriveEncoder()).when(sensorInput).getRightDriveEncoder();

        position.start();
    }

    @After
    public void tearDown() throws Exception {
        position.stop();
        position.reset(0, 0, 0);
    }

    @Test
    public void straightNoAngle() throws InterruptedException {
        destX = 0;
        destY = 50;

        //new ArcToTarget(0, startX, startY, destX, destY, 0, 2).runUntilFinish();

        checkTarget(2);
    }

    @Test
    public void curveToRight() throws InterruptedException {
        destX = 50;
        destY = 50;

        //new ArcToTarget(0, startX, startY, destX, destY, 0, 2).runUntilFinish();

        checkTarget(2);
    }

    @Test
    public void curveToLeft() throws InterruptedException {
        destX = -50;
        destY = 50;

        //new ArcToTarget(0, startX, startY, destX, destY, 0, 2).runUntilFinish();

        checkTarget(2);
    }
    
    private void checkTarget(double epsilon) {
    	double dx = destX - position.getX();
    	double dy = destY - position.getY();
    	double d = Math.sqrt(dx * dx + dy * dy);
    	
    	if (d > epsilon)
    		fail(String.format("Expected: <%f\"; Actual: %f\"", epsilon, d));
    }

    private static class DriveTrain {
        private static final double MAX_SPEED_LOW = 102; //inches per second
        private static final double MAX_SPEED_TOP = 192; //inches per second

        private double inchesPerTick;

        private double leftEncoder = 0;
        private double rightEncoder = 0;

        private double leftMotor;
        private double rightMotor;

        private long lastUpdateTimestamp;

        DriveTrain(double inchesPerTick) {
            this.inchesPerTick = inchesPerTick;
            this.lastUpdateTimestamp = System.currentTimeMillis();
        }

        Answer<Void> tankDrive() {
            return invocation -> {
                //these are values passed to robotOutput.tankDrive(left, right);
                leftMotor = invocation.getArgument(0);
                rightMotor = invocation.getArgument(1);

                updateEncoders();
                
                System.out.printf("tankDRIVE %1.4f->%1.4f %1.4f->%1.4f\n", leftMotor, leftEncoder, rightMotor, rightEncoder);

                return null;
            };
        }
        
        Answer<Void> arcadeDrivePID() {
        	return invocation -> {
        		//these are values passed to robotOutput.arcadeDrivePID(throttle, turn);
        		double throttle = invocation.getArgument(0);
        		double turn = invocation.getArgument(1);
        		
        		leftMotor = throttle + turn/2;
        		rightMotor = throttle - turn/2;

                updateEncoders();

                System.out.printf("arcadeDRIVE %1.4f %1.4f\n", leftMotor, rightMotor);
                
                return null;
        	};
        }

        Answer<Integer> getLeftDriveEncoder() {
            return invocation -> (int) leftEncoder;
        }

        Answer<Integer> getRightDriveEncoder() {
            return invocation -> (int) rightEncoder;
        }

        private void updateEncoders() {
            long now = System.currentTimeMillis();
            long travelTime = (now - lastUpdateTimestamp); //in seconds
            lastUpdateTimestamp = now;

            leftEncoder += (leftMotor * MAX_SPEED_LOW) * travelTime * inchesPerTick;
            rightEncoder += (rightMotor * MAX_SPEED_LOW) * travelTime * inchesPerTick;
        }
    }
}