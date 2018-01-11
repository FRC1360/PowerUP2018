package org.usfirst.frc.team1360.robot.util;

import java.util.HashMap;

public final class Singleton {
	private static final HashMap<Class<?>, Object> objects = new HashMap<>();
	
	private Singleton() {}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz) {
		return (T) objects.computeIfAbsent(clazz, c -> {
			try {
				while (true) {
					SingletonType type = c.getAnnotation(SingletonType.class);
					if (type != null) {
						c = type.type();
						continue;
					}
					SingletonStatic _static = c.getAnnotation(SingletonStatic.class);
					if (_static != null)
						return c.getMethod(_static.name()).invoke(null);
					break;
				}
				return c.getConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
}
