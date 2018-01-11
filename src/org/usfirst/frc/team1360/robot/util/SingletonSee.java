package org.usfirst.frc.team1360.robot.util;

/**
 * Indicates that a singleton request on a type should redirect to another type
 * @author nickmertin
 */
public @interface SingletonSee {
	/**
	 * The type to redirect to
	 */
	Class<?> value();
}
