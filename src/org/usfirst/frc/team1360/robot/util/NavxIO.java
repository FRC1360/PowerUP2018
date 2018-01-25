package org.usfirst.frc.team1360.robot.util;

public final class NavxIO {
    public static final int MAX_NAVX_MXP_DIGIO_PIN_NUMBER      = 9;
    public static final int MAX_NAVX_MXP_ANALOGIN_PIN_NUMBER   = 3;
    public static final int MAX_NAVX_MXP_ANALOGOUT_PIN_NUMBER  = 1;
    public static final int NUM_ROBORIO_ONBOARD_DIGIO_PINS     = 10;
    public static final int NUM_ROBORIO_ONBOARD_PWM_PINS       = 10;
    public static final int NUM_ROBORIO_ONBOARD_ANALOGIN_PINS  = 4;
    
	private NavxIO() {
	}
	
	public static int dio(int port) {
        if (port > MAX_NAVX_MXP_DIGIO_PIN_NUMBER)
            throw new IllegalArgumentException("Error:  Invalid navX-MXP Digital I/O Pin #");
		return NUM_ROBORIO_ONBOARD_DIGIO_PINS + port + (port > 3 ? 4 : 0);
	}

	public static int pwm(int port) {
        if (port > MAX_NAVX_MXP_DIGIO_PIN_NUMBER)
            throw new IllegalArgumentException("Error:  Invalid navX-MXP Digital I/O Pin #");
		return NUM_ROBORIO_ONBOARD_PWM_PINS + port;
	}
	
	public static int analogIn(int port) {
        if (port > MAX_NAVX_MXP_ANALOGIN_PIN_NUMBER)
            throw new IllegalArgumentException("Error:  Invalid navX-MXP Analog Input Pin #");
		return NUM_ROBORIO_ONBOARD_ANALOGIN_PINS + port;
	}
	
	public static int analogOut(int port) {
        if (port > MAX_NAVX_MXP_ANALOGOUT_PIN_NUMBER)
            throw new IllegalArgumentException("Error:  Invalid navX-MXP Analog Output Pin #");
		return port;
	}
}
