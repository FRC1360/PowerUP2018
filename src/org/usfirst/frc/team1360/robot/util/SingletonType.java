package org.usfirst.frc.team1360.robot.util;

/**
 * Indicates that an instance of another type should be created in place of this one in a first-time singleton request
 * @author nickmertin
 */
public @interface SingletonType {
	/**
	 * The type to create an instance of
	 */
	Class<?> value();
}
