package org.firstinspires.ftc.teamcode.Samwise.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AbstractPhysical.DriveTrain;

public class SamwiseDriveTrain_refactored extends DriveTrain {

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;

    public SamwiseDriveTrain_refactored(){
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwm) {

        // Define and Initialize MotorsAndServos
        leftDrive  = hwm.get(DcMotor.class, "left_drive");
        rightDrive = hwm.get(DcMotor.class, "right_drive");

        leftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightDrive.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void drive(double power){
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }

}

