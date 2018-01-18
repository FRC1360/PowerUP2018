package org.usfirst.frc.team1360.robot.auto.drive;

import org.usfirst.frc.team1360.robot.auto.AutonRoutine;

public final class ArcToTarget extends AutonRoutine {
	private double xs;
	private double ys;
	private double x;
	private double y;
	private double startAngle;
	
	public ArcToTarget(long timeout, double xs, double ys, double x, double y, double startAngle) {
		super("ArcToTarget", timeout);
		this.xs = xs;
		this.ys = ys;
		this.x = x;
		this.y = y;
		this .startAngle = startAngle * Math.PI / 180;
	}

	@Override
	protected void runCore() throws InterruptedException {
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
		double angleYSO = startAngle + Math.copySign(Math.PI / 2, da); // angle between +y axis, start, origin
		double xOrigin = xs + r * Math.sin(angleYSO);
		double yOrigin = ys + r * Math.cos(angleYSO);
	}
}
