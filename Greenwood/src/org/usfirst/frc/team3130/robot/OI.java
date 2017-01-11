package org.usfirst.frc.team3130.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.buttons.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import org.usfirst.frc.team3130.robot.commands.*;

import org.usfirst.frc.team3130.robot.RobotMap;

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
	public static Joystick stickL;
	public static Joystick stickR;
	public static Joystick gamepad;
	
	//Create Buttons
	public static JoystickButton preset1;
	public static JoystickButton preset2;
	public static JoystickButton intakePin;
	public static JoystickButton aimLeft;
	public static JoystickButton aimRight;
	public static JoystickButton fire;
	public static JoystickButton shiftDown;
	public static JoystickButton shiftUp;
	public static JoystickButton streight;
	public static JoystickButton toPoint;
	public static JoystickButton headlightOn;
	public static JoystickButton headlightOff;
	public static POVTrigger CDFIntake;
	public static POVTrigger inIntake;
	public static POVTrigger intakeOut;
	public static POVTrigger portcullisIntake;	

	//Create Objects

	
	//Create Auton Inputs
	public static enum Position{
		kLowBar, kPos2, kPos3, kPos4, kPos5
	}
	
	public static enum Defense{
		kLowBar, kMoat, kRamparts, kRoughTerrain, kRockWall
	}
	
	public static SendableChooser<Position> positionChooser;
	public static SendableChooser<Defense> defenseChooser;
	
	private OI()
	{
		stickL = new Joystick(0);
		stickR = new Joystick(1);
		gamepad = new Joystick(2);
		
		preset1				= new JoystickButton(gamepad, RobotMap.BTN_PRESET_1);
		preset2				= new JoystickButton(gamepad, RobotMap.BTN_PRESET_2);
		intakePin			= new JoystickButton(gamepad, RobotMap.BTN_INTAKEPIN);
		aimLeft				= new JoystickButton(stickR, RobotMap.BTN_AIMLEFT);
		aimRight			= new JoystickButton(stickR, RobotMap.BTN_AIMRIGHT);
		fire				= new JoystickButton(gamepad, RobotMap.BTN_SHOOT);
		shiftDown			= new JoystickButton(stickL, RobotMap.BTN_SHIFT);
		shiftUp				= new JoystickButton(stickR, RobotMap.BTN_SHIFT);
		streight			= new JoystickButton(stickR, 10);	//Secret Magic Buttons
		toPoint				= new JoystickButton(stickL, 10);	//Secret Magic Buttons
		headlightOn			= new JoystickButton(stickR, RobotMap.BTN_HEADLIGHT);
		headlightOff		= new JoystickButton(stickL, RobotMap.BTN_HEADLIGHT);
		CDFIntake			= new POVTrigger(gamepad, RobotMap.POV_CDFMODE);
		inIntake			= new POVTrigger(gamepad, RobotMap.POV_INTAKEIN);
		intakeOut			= new POVTrigger(gamepad, RobotMap.POV_INTAKEOUT);
		portcullisIntake	= new POVTrigger(gamepad, RobotMap.POV_PORTCULLISMODE);
	
		//Map Buttons to Objects
		shiftDown.whenPressed(new DriveShiftDown());
		shiftUp.whenPressed(new DriveShiftUp());
		headlightOn.whenPressed(new HeadlightsOn());
		headlightOff.whenPressed(new HeadlightsOff());
	
		//Set up Auton Choosers
		positionChooser = new SendableChooser<Position>();
		positionChooser.addDefault("Low Bar", Position.kLowBar);
		positionChooser.addObject("Position 2", Position.kPos2);
		positionChooser.addObject("Position 3", Position.kPos3);
		positionChooser.addObject("Position 4", Position.kPos4);
		positionChooser.addObject("Position 5", Position.kPos5);
		SmartDashboard.putData("Auton Position", positionChooser);
		
		defenseChooser = new SendableChooser<Defense>();
		defenseChooser.addDefault("Low Bar", Defense.kLowBar);
		defenseChooser.addObject("Moat", Defense.kMoat);
		defenseChooser.addObject("Ramparts", Defense.kRamparts);
		defenseChooser.addObject("Rough Terrain", Defense.kRoughTerrain);
		defenseChooser.addObject("Rock Wall", Defense.kRockWall);
		SmartDashboard.putData("Auton Defense", defenseChooser);
	}

	
	//Selection of Auton Positions
	public static double ReturnAutonAngle()
	{
		double angle = 0;
		switch((Position)positionChooser.getSelected())
		{
			case kLowBar:
				angle = Preferences.getInstance().getDouble("Auton Turn Angle LowBar", -45);
				break;
			case kPos2:
				angle = Preferences.getInstance().getDouble("Auton Turn Angle Position2",-28);
				break;
			case kPos3:
				angle = Preferences.getInstance().getDouble("Auton Turn Angle Position3",-15);
				break;
			case kPos4:
				angle = Preferences.getInstance().getDouble("Auton Turn Angle Position4",0);
				break;
			case kPos5:
				angle = Preferences.getInstance().getDouble("Auton Turn Angle Position5",15);
				break;
		}
		return angle;
	}
	
	public static double ReturnAutonDistance()
	{
		switch((Defense)defenseChooser.getSelected()){
			case kLowBar:
				return Preferences.getInstance().getDouble("Low Bar Drive Distance", 140);
			case kMoat:
				return Preferences.getInstance().getDouble("Moat Drive Distance", 150);
			case kRamparts:
				return Preferences.getInstance().getDouble("Ramparts Drive Distance", 140);
			case kRoughTerrain:
				return Preferences.getInstance().getDouble("Rough Terrain Drive Distance", 140);
			case kRockWall:
				return Preferences.getInstance().getDouble("Rock Wall Drive Distance", 140);
			default:
				return 140;	
		}
	}
	
	public static double ReturnAutonDistHorizontal()
	{
		switch((Position)positionChooser.getSelected()){
			case kLowBar:
				return 0;
			case kPos2:
				return Preferences.getInstance().getDouble("Stop2Ball Position 2",218);
			case kPos3:
				return Preferences.getInstance().getDouble("Stop2Ball Position 3",112);
			case kPos4:
				return Preferences.getInstance().getDouble("Stop2Ball Position 4",160);
			case kPos5:
				return Preferences.getInstance().getDouble("Stop2Ball Position 5",70);
			default:
				return 0;
		}
	}
}

