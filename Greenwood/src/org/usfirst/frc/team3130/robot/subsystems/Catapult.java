package org.usfirst.frc.team3130.robot.subsystems;

import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.commands.ControlCatapult;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Catapult extends Subsystem {
    
	//Define requisite Constants
    static final double SHOOTER_MAXCURRENT = 40.0;
    public static final double TOP_ZONE = 26;
    public static final double SLOW_ZONE = 3;
    
    //Instance handling
    private static Catapult m_pInstance;
    public static Catapult GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new Catapult();
    	return m_pInstance;
    }
    
    //Define Objects
    private static CANTalon m_shooterController;
    private static Timer m_currentTimer;
    private static Timer m_voltageTimer;
    
    //Define basic memory types
    private static boolean m_bOnPID;
    private static boolean m_bResetStepOneDone;

    
    private Catapult(){
    	//Initiallize Basic data types
    	m_bResetStepOneDone = false;
    	m_bOnPID = false;
    	
    	//Set up Talon
    	m_shooterController = new CANTalon(RobotMap.CAN_SHOOTERMOTOR);
    	m_shooterController.enableForwardSoftLimit(false);
    	m_shooterController.enableReverseSoftLimit(false);
    	m_shooterController.enableBrakeMode(true);
    	m_shooterController.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	m_shooterController.changeControlMode(TalonControlMode.PercentVbus);
    	m_shooterController.setAllowableClosedLoopErr(2);
    	
    	m_shooterController.configEncoderCodesPerRev(RobotMap.RATIO_WINCHCODESPERREV);
    	
    	LiveWindow.addActuator("Catapult", "Winch Talon", m_shooterController);
    	
    	//Set up Timers
    	m_currentTimer = new Timer();
    	m_currentTimer.reset();
    	m_currentTimer.start();
    	m_voltageTimer = new Timer();
    	m_voltageTimer.reset();
    	m_voltageTimer.start();
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ControlCatapult());
    }
    
    //Getters
    public static boolean isBottomHit()
    {
    	return m_shooterController.isRevLimitSwitchClosed();
    }
    
    public static double GetSpeed() 
    {
    	return m_shooterController.getSpeed();
    }
    
    public static double GetPositon()
    {
    	// 0.965 is drum diameter.
    	return m_shooterController.getPosition() * Math.PI * 0.965;
    }
    
    //Cat movement and PID methods
    public static int GetPIDError()
    {
    	return m_shooterController.getClosedLoopError();
    }
    
    public static boolean OnTarget()
    {
    	return Math.abs(GetPIDError()) < Preferences.getInstance().getInt("WinchTolerance", 45);
    }
    
    public static void toSetpoint(float goal)
    {
    	if(goal > 3 && goal < 21.0){
    		if(!m_bOnPID){
    			m_bOnPID = true;
    			double termP = Preferences.getInstance().getDouble("Catapult P Value", 1);
    			double termI = Preferences.getInstance().getDouble("Catapult I Value", 0);
    			double termD = Preferences.getInstance().getDouble("Catapult D Value", 0);
    			
    			m_shooterController.changeControlMode(TalonControlMode.Position);
    			m_shooterController.setPID(termP, termI, termD);
    			m_shooterController.enableControl();
    		}
    		m_shooterController.set(goal / (Math.PI * 0.965));
    	}
    }
    
    public static void moveCatapult(float speed)
    {
    	m_bOnPID = false;
    	m_shooterController.changeControlMode(TalonControlMode.PercentVbus);
    	if(speed > 0){
    		if(GetPositon() < TOP_ZONE){
    			m_shooterController.set(speed);
    		}else{
    			m_shooterController.set(0);
    		}
    	}else if(!CheckZero()) {
    		if(GetPositon() < SLOW_ZONE){
    			m_shooterController.set(0.5 * speed);
    		}else{
    			m_shooterController.set(speed);
    		}
    	}else{
    		m_shooterController.set(0);
    	}
    }
    
    public static boolean CheckZero()
    {
    	if(isBottomHit()){
    		m_shooterController.setPosition(0);
    		return true;
    	}else return false;
    }
    
    public static boolean WatchCurrent()
    {
    	if(Math.abs(m_shooterController.getOutputVoltage()) < 3.0) m_voltageTimer.reset();
    	if(m_shooterController.getOutputCurrent() > Preferences.getInstance().getDouble("CatapultMaxCurrent", SHOOTER_MAXCURRENT)) {
    		if(m_currentTimer.get() > Preferences.getInstance().getDouble("CatapultCurrentTime", 0.5)) {
    			if(m_voltageTimer.get() > Preferences.getInstance().getDouble("CatapultStartTime", 0.3)) {
    				return true;
    			}
    		}
    	}else{
    		m_currentTimer.reset();
    	}
    	return false;
    }
}

