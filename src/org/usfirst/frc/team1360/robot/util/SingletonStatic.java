package org.usfirst.frc.team1360.robot.util;

/**
 * Indicates that a singleton request for a type should be resolved by a static method of the type
 * @author nickmertin
 */
public @interface SingletonStatic {
	/**
	 * The name of the static method
	 */
	String value() default "getInstance";
}
