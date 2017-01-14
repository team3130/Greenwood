package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.Chassis;
import org.usfirst.frc.team3130.robot.OI;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveShiftDown extends Command {

	private Timer timer;
	private boolean m_bShifted;
	
    public DriveShiftDown() {
        // Use requires() here to declare subsystem dependencies
        requires(Chassis.GetInstance());
        timer = new Timer();
        m_bShifted = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    	Chassis.TalonsToCoast(true);
    	Chassis.DriveTank(0, 0); 		//Cut all power to the motors so they aren't running during the shift
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Execute the shift only once, and only at a certain delay after the motors have been stopped
    	if(!m_bShifted && timer.get() > Preferences.getInstance().getDouble("Shift Dead Time STart", 0.125)){
    		Chassis.Shift(true);
    		m_bShifted = true;
    		//Reset the timer so that the ending dead time is from shifting rather than from the start.
    		timer.reset();
    		timer.start();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//End the command a configurable length of time after the robot has shifted gears
        return (m_bShifted && timer.get()> Preferences.getInstance().getDouble("Shift Dead Time End", 0.125));
    }

    // Called once after isFinished returns true
    protected void end() {
    	//System for readding power to the motors the same as the standard drive system would
    	double moveSpeed = -OI.stickL.getY();
    	double turnSpeed = -OI.stickR.getX();
    	double turnThrottle = (-0.5 * OI.stickR.getRawAxis(3)) + 0.5;
    	//Explicitly turning on Quadratic inputs for drivers, as all other systems will use nonQuadratic
    	Chassis.DriveArcade(moveSpeed, turnSpeed * turnThrottle, true);
    	
    	Chassis.TalonsToCoast(false);	//Back to brake mode for normal robot operations
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
