package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;

public final class SCurveToTarget extends AutonRoutine {
	private double xs;
	private double ys;
	private double x;
	private double y;
	private double startAngle;
	private double epsilon;

	public SCurveToTarget(long timeout, double xs, double ys, double x, double y, double startAngle, double epsilon) {
		super("SCurveToTarget", timeout);
		this.xs = xs;
		this.ys = ys;
		this.x = x;
		this.y = y;
		this.startAngle = startAngle * Math.PI / 180;
		this.epsilon = epsilon * epsilon;
	}

	@Override
	protected void runCore() throws InterruptedException {
		double xCenter = (xs + x) / 2;
		double yCenter = (ys + y) / 2;
		double dx = x - xs;
		double dy = y - ys;
		double lineAngle = Math.atan2(dx, dy);
		double startAngle2 = startAngle + 2 * (lineAngle - startAngle);
		new ArcToTarget(0, x, y, xCenter, yCenter, startAngle, epsilon).runUntilFinish();
		new ArcToTarget(0, xCenter, yCenter, x, y, startAngle2, epsilon).runUntilFinish();
	}
}
