package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class CatapultFire extends Subsystem {
    
	//Instance Handling
	private static CatapultFire m_pInstance;
	public static CatapultFire GetInstance(){
		if(m_pInstance == null) m_pInstance = new CatapultFire();
		return m_pInstance;
	}
	
	//Define Objects
	private static Solenoid m_actuatorFire;
	
	private CatapultFire()
	{
		//Instantiate Objects
		m_actuatorFire = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_SHOOTERLOCK);
		LiveWindow.addActuator("Catapult", "Catapult Fire Solenoid", m_actuatorFire);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void Actuate(boolean release)
    {
    	m_actuatorFire.set(release);
    }
    
    public boolean GetState(){
    	return m_actuatorFire.get();
    }
}

