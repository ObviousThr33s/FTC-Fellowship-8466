package org.firstinspires.ftc.teamcode.Samwise;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AbstractPhysical.DriveTrain;

public class SamwiseDriveTrain extends DriveTrain {

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;

    public SamwiseDriveTrain(){
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwm) {

        // Define and Initialize MotorsAndServos
        leftDrive  = hwm.get(DcMotor.class, "left_drive");
        rightDrive = hwm.get(DcMotor.class, "right_drive");
        //leftArm    = hwMap.get(DcMotor.class, "left_arm");
        leftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightDrive.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        //leftArm.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //leftArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        //leftClaw  = hwMap.get(Servo.class, "left_hand");
        //rightClaw = hwMap.get(Servo.class, "right_hand");
        //leftClaw.setPosition(MID_SERVO);
        //rightClaw.setPosition(MID_SERVO);
    }

    @Override
    public void drive(double power){
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }

}

