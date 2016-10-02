package org.usfirst.frc.team3130.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
class POVTrigger extends Trigger
{
	private int m_dir;
	Joystick m_stick;
	
	POVTrigger(Joystick stick, int dir)
	{
		m_dir = dir;
		m_stick = stick;
	}

	@Override
	public boolean get() {
		return m_stick.getPOV(0) == m_dir;
	}

	
}

public class OI {
	
	//Instance Handling
    private static OI m_pInstance;
    public static OI GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new OI();
    	return m_pInstance;
    }
	
	//Create Joysticks
	public static Joystick stickL = new Joystick(0);
	public static Joystick stickR = new Joystick(1);
	public static Joystick gamepad = new Joystick(2);
	
	//Create Buttons
	JoystickButton preset1		= new JoystickButton(gamepad, RobotMap.BTN_PRESET_1);
	JoystickButton preset2		= new JoystickButton(gamepad, RobotMap.BTN_PRESET_2);
	JoystickButton intakePin	= new JoystickButton(gamepad, RobotMap.BTN_INTAKEPIN);
	JoystickButton aimLeft		= new JoystickButton(stickR, RobotMap.BTN_AIMLEFT);
	JoystickButton aimRight		= new JoystickButton(stickR, RobotMap.BTN_AIMRIGHT);
	JoystickButton fire			= new JoystickButton(gamepad, RobotMap.BTN_SHOOT);
	JoystickButton shiftDown	= new JoystickButton(stickL, RobotMap.BTN_SHIFT);
	JoystickButton shiftUp		= new JoystickButton(stickR, RobotMap.BTN_SHIFT);
	JoystickButton streight		= new JoystickButton(stickR, 10);	//Secret Magic Buttons
	JoystickButton toPoint		= new JoystickButton(stickL, 10);	//Secret Magic Buttons
	JoystickButton headlight	= new JoystickButton(stickR, RobotMap.BTN_HEADLIGHT);
	POVTrigger CDFIntake		= new POVTrigger(gamepad, RobotMap.POV_CDFMODE);
	POVTrigger inIntake			= new POVTrigger(gamepad, RobotMap.POV_INTAKEIN);
	POVTrigger intakeOut		= new POVTrigger(gamepad, RobotMap.POV_INTAKEOUT);
	POVTrigger portcullisIntake	= new POVTrigger(gamepad, RobotMap.POV_PORTCULLISMODE);

	//Create Objects
	
	//Map Buttons to Objects
	
	//Create SMD Choosers for Auton
	//Selection of Auton Positions
}

