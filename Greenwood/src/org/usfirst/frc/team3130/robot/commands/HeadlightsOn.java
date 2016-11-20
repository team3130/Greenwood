package org.usfirst.frc.team3130.robot.commands;

import org.usfirst.frc.team3130.robot.subsystems.Headlights;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HeadlightsOn extends Command {

    public HeadlightsOn() {
        // Use requires() here to declare subsystem dependencies
        requires(Headlights.GetInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Headlights.Activate(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
