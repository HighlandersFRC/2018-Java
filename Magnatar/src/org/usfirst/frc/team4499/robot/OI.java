/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static Joystick controllerZero=new Joystick (0);
	public static JoystickButton fireButton =new JoystickButton (controllerZero,8);
	public static JoystickButton rightshiftButton =new JoystickButton (controllerZero,6);
	public static JoystickButton leftshiftButton =new JoystickButton (controllerZero,5);
	public static JoystickButton waverUpButton =new JoystickButton (controllerZero,4);
	public static JoystickButton waverDownButton = new JoystickButton(controllerZero, 1);
	}



