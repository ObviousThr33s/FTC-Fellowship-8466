package org.firstinspires.ftc.teamcode.Samwise.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AbstractPhysical.DriveTrain;

public class SamwiseDriveTrain extends DriveTrain {

    DcMotor leftMotor = null;
    DcMotor rightMotor = null;

    public SamwiseDriveTrain(DcMotor leftMotor, DcMotor rightMotor, HardwareMap hw){
        leftMotor = hw.dcMotor.get("leftMotor");
    }

    public  SamwiseDriveTrain(){
        int addnumber=this.addNumbers(3, 4);
    }

    @Override
    public void drive(double power){
        leftMotor.setPower(power);
    }

    private int addNumbers(int number1, int number2) {
        int result = number1 + number2;
        return result;
    }
}

