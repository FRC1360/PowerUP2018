package org.usfirst.frc.team1360.robot.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a singleton request on a type should redirect to another type
 * @author nickmertin
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SingletonSee {
	/**
	 * The type to redirect to
	 */
	Class<?> value();
}
