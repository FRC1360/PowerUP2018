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
		synchronized (objects) {
			//System.out.printf("getting class: %s%n", clazz.getCanonicalName());
			SingletonSee see = clazz.getAnnotation(SingletonSee.class); 
			if (see != null) {
				//System.out.printf("getting: %s%n", see.value());
				return (T) get(see.value());
			}
			
			//System.out.printf("isCached: %s%n", "" +objects.containsKey(clazz));
			return (T) objects.computeIfAbsent(clazz, c -> {
				try {
					while (true) {
						//System.out.printf("getting type from: %s%n", c.getCanonicalName());
						SingletonType type = c.getAnnotation(SingletonType.class);
						if (type != null) {
							//System.out.printf("type: %s%n", type.value());
							c = type.value();
							continue;
						}
						SingletonStatic _static = c.getAnnotation(SingletonStatic.class);
						if (_static != null) {
							//System.out.printf("static: %s%n", _static.value());
							return c.getMethod(_static.value()).invoke(null);
						}
						break;
					}
					return c.getConstructor().newInstance();
				} catch (Exception e) {
					Throwable t = e.getCause();
					if (t != null)
						t.printStackTrace();
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			});	
		}
	}
}
