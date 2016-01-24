
package org.usfirst.frc.team6083.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.vision.USBCamera;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    
    
    //motor
    VictorSP motor_left = new VictorSP(1);
    VictorSP motor_right = new VictorSP(0);
    
    //Joystick
    Joystick joy = new Joystick(0);
    JoystickButton left = new JoystickButton(joy,9);
    JoystickButton right = new JoystickButton(joy,10);
    
    //Device
    PowerDistributionPanel pdp = new PowerDistributionPanel(1);
    Compressor comp = new Compressor(0);
    
    //SmartDashboard
    Preferences pref;
    
    //camera
    USBCamera cam = new USBCamera("WEBCAMERA");
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        pref.getDouble("SpeedControal", 5.0);
        
        cam.openCamera();

    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

     	
    	
    	Double SpeedControal = 4.0;
    	if(left.get()){
    		motor_left.set(joy.getRawAxis(1)/SpeedControal);                     	
    	}	
    	else {
    		motor_left.set(joy.getRawAxis(1)/(SpeedControal*2));
    	}
    	
    	if(right.get()){
    		motor_right.set(-joy.getRawAxis(5)/SpeedControal);                     	
    	}	
    	else {
    		motor_right.set(-joy.getRawAxis(5)/(SpeedControal*2));
    	}
        //Timer.delay(500);

    	SmartDashboard.putNumber("Left Motor Encoder Value", -motor_left.get());
    	SmartDashboard.putNumber("Right Motor Encoder Value", motor_right.get());
    	SmartDashboard.putNumber("spSpeedControaleed", (-motor_left.get()+ motor_right.get())/2);
    	SmartDashboard.putNumber("Speed Plot", (-motor_left.get()+ motor_right.get())/2);
    	SmartDashboard.putNumber("LY value", joy.getRawAxis(1));
    	SmartDashboard.putNumber("RY value", joy.getRawAxis(5));
    	SmartDashboard.putNumber("PDP Voltage", pdp.getVoltage());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
