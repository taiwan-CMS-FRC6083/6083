
package org.usfirst.frc.team6083.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.*;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;


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
    
    //VictorSP
    VictorSP motor_left = new VictorSP(0);
    VictorSP motor_right = new VictorSP(1);
    
    
    Joystick joy = new Joystick(0);
    
    //JoystickButton
    JoystickButton left = new JoystickButton(joy,9);
    JoystickButton right = new JoystickButton(joy,10);
    JoystickButton LB = new JoystickButton(joy,5);
    JoystickButton RB = new JoystickButton(joy,6);
    
    //SmartDashboard
    Preferences pref;
    
    //Device
    PowerDistributionPanel pdp = new PowerDistributionPanel(1);
    Compressor comp = new Compressor(0);
    
    //Double
    Double LY;
    Double RY;
    
    //Camera
    int session;
    Image frame;
    
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
        
        //Camera
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        session = NIVision.IMAQdxOpenCamera
        		  ("cam0",NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);      
    }
    
	
    public void autonomousInit() {
    
    }
    /**
     * This function is called periodically during operator control
     */

    public void teleopPeriodic() {
    	//double
    	Double SpeedControal = 2.0;
    	
    	//Camera
        NIVision.IMAQdxStartAcquisition(session);
    	NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
    	
    	while (isOperatorControl() && isEnabled()) {
    		
            NIVision.IMAQdxGrab(session, frame, 1);
            NIVision.imaqDrawShapeOnImage
                             (frame, frame, rect,DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
            CameraServer.getInstance().setImage(frame);

            if(joy.getRawAxis(1)>0.1 || joy.getRawAxis(1)<-0.1){		
                LY = joy.getRawAxis(1);
             }	
             else{
                LY = 0.0 ;	
             }	
         
             if(joy.getRawAxis(5)>0.1 || joy.getRawAxis(5)<-0.1){		
                RY = joy.getRawAxis(5);
             }	
             else{
                RY = 0.0 ;	
             }	
         	 
             if(LB.get()){
               motor_left.set(-LY/(SpeedControal*2));                     	
             }	
             else {
         	   motor_left.set(-LY/SpeedControal);
             }
         	
             if(RB.get()){
               motor_right.set(RY/(SpeedControal*2));                     	
             }	
             else {
         	   motor_right.set(RY/SpeedControal);
        	    }
         	
             //SmartDashboard
         	 SmartDashboard.putNumber("Left Motor Encoder Value", -motor_left.get());
         	 SmartDashboard.putNumber("Right Motor Encoder Value", motor_right.get());
         	 SmartDashboard.putNumber("speed", (-motor_left.get()+ motor_right.get())/2);
         	 SmartDashboard.putNumber("speed plot", (-motor_left.get()+ motor_right.get())/2);
         	 SmartDashboard.putNumber("LY value", joy.getRawAxis(1));
         	 SmartDashboard.putNumber("RY value", joy.getRawAxis(5));
         	 SmartDashboard.putNumber("PDP Voltage", pdp.getVoltage());
         	 /** put robot code under here! **/
            
         	//Timer
         	 Timer.delay(0.0005);	// wait for a motor update time
        }
    	
       
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testInit() {
    }
    public void testPeriodic() {
    	
    }
    
}