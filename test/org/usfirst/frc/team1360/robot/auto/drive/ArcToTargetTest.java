package org.usfirst.frc.team1360.robot.auto.drive;

import static java.time.Instant.now;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doAnswer;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.usfirst.frc.team1360.robot.util.position.DriveEncoderPositionProvider;
import org.usfirst.frc.team1360.robot.util.position.OrbitPosition;
import org.usfirst.frc.team1360.robot.util.position.OrbitPositionProvider;

@RunWith(MockitoJUnitRunner.class)
public class ArcToTargetTest {

    private int startX=0, startY=0;
    private int destX, destY;

    @Mock
    protected RobotOutputProvider robotOutput;
    @Mock
    protected SensorInputProvider sensorInput;

    protected OrbitPositionProvider position;

    private Thread robot;
    private DriveTrain driveTrain;

    @Before
    public void setUp() throws Exception {
        Singleton.configure(RobotOutputProvider.class, robotOutput);
        Singleton.configure(SensorInputProvider.class, sensorInput);
        position = Singleton.configure(DriveEncoderPositionProvider.class);

        driveTrain = new DriveTrain(position);

        doAnswer(driveTrain.tankDrive()).when(robotOutput).tankDrive(anyDouble(), anyDouble());

        doAnswer(driveTrain.getLeftDriveEncoder()).when(sensorInput).getLeftDriveEncoder();
        doAnswer(driveTrain.getRightDriveEncoder()).when(sensorInput).getRightDriveEncoder();

        robot =  new Thread(driveTrain);
        robot.start();
    }

    @After
    public void tearDown() throws Exception {
        robot.interrupt();
    }

    @Test
    public void straightNoAngle() throws InterruptedException {
        destX = 0;
        destY = 50;

        new ArcToTarget(5_000, startX, startY, destX, destY, 0, 0).runUntilFinish();

        assertEquals(Arrays.asList(
                new OrbitPosition(10, 0, 0),
                new OrbitPosition(20, 0, 0),
                new OrbitPosition(30, 0, 0),
                new OrbitPosition(40, 0, 0),
                new OrbitPosition(50, 0, 0)
        ), driveTrain.getPath());
    }

    @Test
    public void curveToRight() throws InterruptedException {
        destX = 50;
        destY = 50;

        new ArcToTarget(5_000, startX, startY, destX, destY, 0, 0).runUntilFinish();

        assertEquals(Arrays.asList(
                new OrbitPosition(10, 10, 0),
                new OrbitPosition(20, 20, 0),
                new OrbitPosition(30, 30, 0),
                new OrbitPosition(40, 40, 0),
                new OrbitPosition(50, 50, 0)
        ), driveTrain.getPath());
    }

    @Test
    public void curveToLeft() throws InterruptedException {
        destX = -50;
        destY = 50;

        new ArcToTarget(5_000, startX, startY, destX, destY, 0, 0).runUntilFinish();

        assertEquals(Arrays.asList(
                new OrbitPosition(-10, 10, 0),
                new OrbitPosition(-20, 20, 0),
                new OrbitPosition(-30, 30, 0),
                new OrbitPosition(-40, 40, 0),
                new OrbitPosition(-50, 50, 0)
        ), driveTrain.getPath());
    }

    private static class DriveTrain implements Runnable {

        private int leftEncoder;
        private int rightEncoder;

        private double leftMotor;
        private double rightMotor;

        private OrbitPositionProvider position;

        private List<OrbitPosition> path = new ArrayList<>();

        DriveTrain(OrbitPositionProvider position) {
            this.position = position;
        }

        Answer<Void> tankDrive() {
            return invocation -> {
                //these are values passed to robotOutput.tankDrive(left, right);
                leftMotor = invocation.getArgument(0);
                rightMotor = invocation.getArgument(1);

                return null;
            };
        }

        Answer<Integer> getLeftDriveEncoder() {
            return invocation -> leftEncoder;
        }

        Answer<Integer> getRightDriveEncoder() {
            return invocation -> rightEncoder;
        }

        @Override
        public void run() {
            try {
                Instant start = now();
                while (true) {
                    updateEncoders();

                    if (Duration.between(start, now()).getSeconds()==1) {
                        start = now();
                        path.add(position.getPosition());
                    }

                    System.out.printf("x:%.2f, y:%.2f %n", position.getX(), position.getY());

                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                //exit
            }
        }

        public List<OrbitPosition> getPath() {
            return path;
        }

        private void updateEncoders() {
            leftEncoder += leftMotor * 10;
            rightEncoder += rightMotor * 10;
        }
    }
}