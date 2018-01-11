package org.usfirst.frc.team1360.robot.util;

import java.util.HashMap;

/**
 * Utility class for resolving instances of singleton types
 * @author nickmertin
 *
 */
public final class Singleton {
	private static final HashMap<Class<?>, Object> objects = new HashMap<>();
	
	private Singleton() {}
	
	/**
	 * Gets the instance of a singleton type
	 * @param clazz The type of which to get an instance
	 * @return The instance
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz) {
		return (T) objects.computeIfAbsent(clazz, c -> {
			try {
				SingletonSee see = clazz.getAnnotation(SingletonSee.class); 
				if (see != null) {
					return get(see.value());
				}
				while (true) {
					SingletonType type = c.getAnnotation(SingletonType.class);
					if (type != null) {
						c = type.value();
						continue;
					}
					SingletonStatic _static = c.getAnnotation(SingletonStatic.class);
					if (_static != null)
						return c.getMethod(_static.value()).invoke(null);
					break;
				}
				return c.getConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
}
