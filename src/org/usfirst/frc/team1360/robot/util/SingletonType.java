package org.usfirst.frc.team1360.robot.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an instance of another type should be created in place of this one in a first-time singleton request
 * @author nickmertin
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SingletonType {
	/**
	 * The type to create an instance of
	 */
	Class<?> value();
}
