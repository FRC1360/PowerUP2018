package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public final class MoveToTarget extends AutonRoutine {
	double xs, ys, x, y, epsilon;

	public MoveToTarget(long timeout, double xs, double ys, double x, double y, double epsilon) {
		super("MoveToTarget", timeout);
		this.xs = xs;
		this.ys = ys;
		this.x = x;
		this.y = y;
		this.epsilon = epsilon * epsilon;
	}

	@Override
	protected void runCore() throws InterruptedException {
		OrbitPID pidY = new OrbitPID(0.25, 0.0, 0.0);
		double dx = x - xs;
		double dy = y - ys;
		double lineAngle = Math.atan2(dx, dy);
		double length = Math.sqrt(dx * dx + dy * dy);
		double xl, yl;
		do {
			double _x = position.getX() - xs;
			double _y = position.getY() - ys;
			double a = position.getA();
			double sin = -Math.sin(a);
			double cos = Math.cos(a);
			xl = cos * x - sin * y;
			yl = sin * x + cos * y;
			double al = a - lineAngle;
			robotOutput.arcadeDrivePID(pidY.calculate(length, yl), 1.0 * (Math.atan(xl / 12) - al));
			Thread.sleep(10);
		} while (xl * xl + (length - yl) * (length - yl) > epsilon);
		robotOutput.arcadeDrivePID(0, 0);
	}

	@Override
	protected void overrideCore() {
		robotOutput.arcadeDrivePID(0, 0);
	}
}
