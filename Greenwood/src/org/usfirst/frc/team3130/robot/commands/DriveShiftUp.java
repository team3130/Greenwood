package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.OI;
import org.usfirst.frc.team3130.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class DriveShiftUp extends Command {
	
	private Timer timer;
	private double initThrottle;
	private double startThrottle;
	private boolean throttleNeg;
	private double throttleConst;
	private double moveSpeed;
	DefaultDrive drive = new DefaultDrive();
	
    public DriveShiftUp() {
    	timer = new Timer();
        requires(Chassis.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    	Chassis.Shift(false);
    	initThrottle = -OI.stickL.getY();
    	startThrottle = initThrottle / 2.0;
    	throttleNeg = startThrottle < 0;
    	throttleConst = -0.1 / Math.log(startThrottle/2.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*
    	if (throttleNeg) {
    		moveSpeed = startThrottle - Math.exp(-timer.get()/throttleConst);
    	}
    	else {
    		moveSpeed = startThrottle + Math.exp(-timer.get()/throttleConst);
    	}
		double turnSpeed = -OI.stickR.getX();
		double turnThrottle = (-0.5 * OI.stickR.getRawAxis(3)) + 0.5;
		
		//Explicitly turning on Quadratic inputs for drivers, as all other systems will use nonQuadratic
		Chassis.DriveArcade(moveSpeed, turnSpeed * turnThrottle, true);
		if (Math.abs(-OI.stickL.getY() - moveSpeed) < 0.05){
			drive.start();
		}*/
    	drive.start();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
