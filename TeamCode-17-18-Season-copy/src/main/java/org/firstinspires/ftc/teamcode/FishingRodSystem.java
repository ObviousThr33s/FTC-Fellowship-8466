package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by 28761 on 1/6/2018.
 */

public class FishingRodSystem extends RelicArmSubsystem {
    Servo lowerReel;
    Servo upperReel;
    Servo claw;
    DcMotor rodMotor;
    Telemetry tl;
    static final double MIN_SERVO_VALUE = 0.09;
    static final double MAX_SERVO_VALUE = 0.91;
    double clawOpenPosition = 0.3;
    double clawClosedPosition = 0.62;

    //position in range [0,1]->[0,90]
    public void setClawPosition(double position) {
        claw.setPosition(position);
    }

    //power in range [-1,1]
    public void setLowerReelPower(double power) {
        double servoPower = 0.5 + power / 2;
        if (servoPower < MIN_SERVO_VALUE) {
            servoPower = MIN_SERVO_VALUE;
        }
        if (servoPower > MAX_SERVO_VALUE) {
            servoPower = MAX_SERVO_VALUE;
        }
        lowerReel.setPosition(servoPower);
    }

    //power in range [-1,1]
    public void setUpperReelPower(double power) {
        double servoPower = 0.5 + power / 2;
        if (servoPower < MIN_SERVO_VALUE) {
            servoPower = MIN_SERVO_VALUE;
        }
        if (servoPower > MAX_SERVO_VALUE) {
            servoPower = MAX_SERVO_VALUE;
        }
        upperReel.setPosition(servoPower);
    }

    //power in range [-1,1]
    public void setRodMotorPower(double power) {
        rodMotor.setPower(power);
    }

    public FishingRodSystem(HardwareMap hwMap, Telemetry telemetry, String lowerReelLabel, String upperReelLabel, String clawLabel, String rodMotorLabel) {
        tl = telemetry;
        lowerReel = hwMap.get(Servo.class, lowerReelLabel);
        upperReel = hwMap.get(Servo.class, upperReelLabel);
        setLowerReelPower(0.0);
        setUpperReelPower(0.0);
        this.claw = hwMap.get(Servo.class, clawLabel);
        setClawPosition(clawOpenPosition);
        rodMotor = hwMap.get(DcMotor.class, rodMotorLabel);
        rodMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rodMotor.setPower(0.0);
    }

//    static final double RATIO = 1.0;
//
//    public void setPower(double power) {
//        rodMotor.setPower(power);
//        lowerReel.setPosition(0.5 + power * RATIO);
//        upperReel.setPosition(0.5 + power * RATIO);
//    }
//
//    @Override
//    public void testMotor() {
//        rodMotor.setPower(0.5);
//        long time = System.currentTimeMillis();
//        while (System.currentTimeMillis() - time < 2000) {
//            rodMotor.setPower(0.5);
//        }
//        rodMotor.setPower(0.0);
//    }
//
//    @Override
//    public void testCRServo() {
//        lowerReel.setPosition(0.25);
//        upperReel.setPosition(0.25);
//        tl.addLine("set 0.25 to CR servo");
//        tl.update();
//        long time = System.currentTimeMillis();
//        while (System.currentTimeMillis() - time < 2000) {
//            lowerReel.setPosition(0.25);
//            upperReel.setPosition(0.25);
//        }
//        tl.addLine("end to CR servo");
//        tl.update();
//        lowerReel.setPosition(0.5);
//        upperReel.setPosition(0.5);
//    }
//
//    @Override
//    public void testDBServo() {
//        claw.setPosition(0.75);
//    }

    static int state = 1;
    static int toggleTime = 900;
    boolean tiltActive = false;
    double tiltStart = 0;

    public void tiltToggleStart() {
        setLowerReelPower(state);
        setUpperReelPower(-state);
        tiltActive = true;
        tiltStart = System.currentTimeMillis();
    }

    public boolean tiltToggleVerify() {
        if (System.currentTimeMillis() - tiltStart >= toggleTime) {
            tiltActive = false;
            setLowerReelPower(0);
            setUpperReelPower(0);
            state = -state;
            return true;
        }
        return false;
    }

    public void tiltToggle() {
        setLowerReelPower(state);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        setUpperReelPower(-state);
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time < toggleTime) {
            //wait until finish toggling
        }
        setLowerReelPower(0.0);
        setUpperReelPower(0.0);
        state = -state;
    }

    public void gripperToggle() {
        setClawPosition(claw.getPosition() <= clawOpenPosition ? clawClosedPosition : clawOpenPosition);
    }

    public double getRatio () { return ratio; }

    public void incRatio(double inc) {
        ratio += inc;
        if (ratio < 0) {
            ratio = 0;
        }
        if (ratio > 1) {
            ratio = 1;
        }
    }

    static double ratio = 0.25;//TBDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD

    public void extendOn(double dir) {
        setLowerReelPower(dir);
        setUpperReelPower(dir);
        setRodMotorPower(dir * ratio);
    }

    public void extendOff() {
        setLowerReelPower(0.0);
        setUpperReelPower(0.0);
        setRodMotorPower(0.0);
    }

    public void gripperMoveOn(double dir) {
        setLowerReelPower(dir);
        setUpperReelPower(dir);
    }

    public void gripperMoveOff() {
        setLowerReelPower(0.0);
        setUpperReelPower(0.0);
    }
}
