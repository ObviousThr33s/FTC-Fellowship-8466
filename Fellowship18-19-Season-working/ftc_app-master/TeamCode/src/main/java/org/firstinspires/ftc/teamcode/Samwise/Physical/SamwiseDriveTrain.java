package org.firstinspires.ftc.teamcode.Samwise.Physical;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AbstractPhysical.DriveTrain;

public class SamwiseDriveTrain extends DriveTrain {

    DcMotor leftMotor = null;

    public SamwiseDriveTrain(DcMotor leftMotor, DcMotor rightMotor, HardwareMap hw){
        leftMotor = hw.dcMotor.get("leftMotor");
    }

    @Override
    public void drive(double power){
        leftMotor.setPower(power);
    }

}
