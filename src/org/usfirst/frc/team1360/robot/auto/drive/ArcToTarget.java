package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public final class ArcToTarget extends AutonRoutine {
	private double xs;
	private double ys;
	private double x;
	private double y;
	private double startAngle;
	private double epsilon;
	
	private final double DRIVE_WIDTH = 30.5;
	
	public ArcToTarget(long timeout, double xs, double ys, double x, double y, double startAngle, double epsilon) {
		super("ArcToTarget", timeout);
		this.xs = xs;
		this.ys = ys;
		this.x = x;
		this.y = y;
		this.startAngle = startAngle * Math.PI / 180;
		this.epsilon = epsilon * epsilon;
	}

	@Override
	protected void runCore() throws InterruptedException {
		OrbitPID pid = new OrbitPID(1.0, 0.0, 0.0);
		double dx = x - xs;
		double dy = y - ys;
		double lineAngle = Math.atan2(dx, dy);
		double da = 2 * (startAngle - lineAngle) % (Math.PI * 2);
		if (da == 0) { // we're pointing straight at target
			new MoveToTarget(0, xs, ys, x, y, 0).runUntilFinish();
			return;
		}
		if (da > Math.PI)
			da -= Math.PI * 2; // change angles >180 to negative angles >-180
		if (da == Math.PI) // we're pointing straight away from target
			da -= 0.00001;
		double r = Math.sqrt(dx * dx + dy * dy) / Math.sin(Math.abs(da) / 2);
		double r2 = r * r;
		double angleYSO = startAngle + Math.copySign(Math.PI / 2, da); // angle between +y axis, start, origin
		double xOrigin = xs + r * Math.sin(angleYSO);
		double yOrigin = ys + r * Math.cos(angleYSO);
		double neutral = Math.copySign(Math.log((2 * r + DRIVE_WIDTH) / (2 * r - DRIVE_WIDTH)), da);
		double rl2, rl, al;
		int lLast = sensorInput.getLeftDriveEncoder();
		int rLast = sensorInput.getRightDriveEncoder();
		do {
			Thread.sleep(10);
			double _x = position.getX() - xOrigin;
			double _y = position.getY() - yOrigin;
			rl2 = _x * _x + _y * _y;
			rl = Math.sqrt(rl2);
			al = Math.atan2(_x, _y) - startAngle + Math.PI / 2;
			int dl = sensorInput.getLeftDriveEncoder() - lLast;
			int dr = sensorInput.getRightDriveEncoder() - rLast;
			if (dl == 0 || dr == 0)
				continue;
			double output = neutral + pid.calculate(neutral + Math.copySign((r - rl) / r, da), Math.log((double) dl / dr));
			lLast += dl;
			rLast += rl;
			if (output > 0)
				robotOutput.tankDrive(1.0, Math.exp(-output));
			else
				robotOutput.tankDrive(Math.exp(output), 1.0);
			log.write(String.format("ArcToTarget %f,%f | %f", rl, al * 180 / Math.PI, output));
		} while (rl2 + r2 - 2 * rl * r * Math.cos(da - al) > epsilon);
		robotOutput.tankDrive(0, 0);
	}
	
	@Override
	public void overrideCore() {
		robotOutput.tankDrive(0, 0);
	}
}
