package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.RobotMap;
import org.usfirst.frc.team3130.robot.subsystems.Catapult;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ControlCatapult extends Command {

	public enum WinchState { kSafe, kOverloadUp, kOverloadDown };
	
	private WinchState manualMode;
	private Timer timer;
	
    public ControlCatapult() {
        requires(Catapult.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	manualMode = WinchState.kSafe;
    	Catapult.moveCatapult(0);
    	timer.reset();
    	timer.start();
    }

    //Passes 1 to shooter when the button is pressed, and 0 when it isn't
    protected void execute() 
    {
    	double thumb = -OI.gamepad.getRawAxis(RobotMap.AXS_WINCH);	// Y-axis is positive down.
    	if (thumb < 0) Catapult.CheckZero();
    	
    	boolean danger = Catapult.WatchCurrent();
    	if(danger || timer.get() > 2.0){
    		timer.reset();
    		if(danger){
    			manualMode = thumb > 0 ? WinchState.kOverloadUp : WinchState.kOverloadDown;
    		}else{
    			manualMode = WinchState.kSafe;
    		}
    	}
    	if((thumb > 0.1 && manualMode != WinchState.kOverloadUp) || (thumb < 0.1 && manualMode != WinchState.kOverloadDown)) {
    		Catapult.moveCatapult((float) thumb);
    	}else{
    		Catapult.moveCatapult(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	Catapult.moveCatapult(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	end();
    }
}
