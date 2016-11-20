package org.usfirst.frc.team3130.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team3130.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

/**
 *
 */
public class Headlights extends Subsystem {
    
	private static Headlights m_pInstance;
	public static Headlights GetInstance()
	{
		if(m_pInstance == null) m_pInstance = new Headlights();
    	return m_pInstance;
	}
	
	
	//Create and define necessary Objects
	private static Relay headlight;
	
	private Headlights()
	{
		headlight = new Relay(RobotMap.RLY_HEADLIGHT);
	}
	
	public static void Activate(boolean on)
	{
		if(on){
			headlight.set(Value.kForward);
		}else{
			headlight.set(Value.kOff);
		}
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

