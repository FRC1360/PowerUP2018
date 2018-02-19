package org.usfirst.frc.team1360.robot.util.position;

import java.util.Objects;

public final class OrbitPosition implements Cloneable {
	private double x; // X-coordinate (inches, right from center of driver station = positive)
	private double y; // Y-coordinate (inches, forward from driver station wall = positive)
	private double a; // Angle (radians, clockwise from forward = positive)
	
	public OrbitPosition(double x, double y, double a) {
		this.x = x;
		this.y = y;
		this.a = a;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getA() {
		return a;
	}
	
	public void offset(double x, double y, double a) {
		this.x += x;
		this.y += y;
		this.a += a;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrbitPosition that = (OrbitPosition) o;
		return Double.compare(that.x, x) == 0 &&
				Double.compare(that.y, y) == 0 &&
				Double.compare(that.a, a) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, a);
	}

	@Override
	public String toString() {
		return "OrbitPosition{" +
				"x=" + x +
				", y=" + y +
				", a=" + a +
				'}';
	}
}
