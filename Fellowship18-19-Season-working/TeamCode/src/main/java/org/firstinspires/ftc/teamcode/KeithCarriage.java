package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by KAEGAN on 1/11/2018.
 *
 * I don't know who KAEGAN is.
 * But all works are done by me.
 * --Charlie :)
 */

public class KeithCarriage extends Carriage {

    DcMotor slideMotor = null;
    DcMotor flipMotor = null;
    Servo lServo = null;
    Servo rServo = null;
    Telemetry tl;

    public KeithCarriage(HardwareMap hwMap, Telemetry telemetry, String SMLabel, String FMLabel, String LSLabel, String RSLabel) {
        tl = telemetry;
        slideMotor = hwMap.get(DcMotor.class, SMLabel);
        flipMotor = hwMap.get(DcMotor.class, FMLabel);
        flipMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        lServo = hwMap.get(Servo.class, LSLabel);
        rServo = hwMap.get(Servo.class, RSLabel);
    }

    //carriage constant
    public static final int LEFT = 1000;
    public static final int CENTER = 0;
    public static final int RIGHT = -1000;
    public static final double MAIN_POWER = 0.4;
    public static final double ADJUST_POWER = 0.08;

    //carriage data
    private int carriageState = 0;
    public boolean slideActive = false;
    public int destination;
    public int moveDir;
    public long carriageMotionStartTime;

    //threshold of velocity(ticks/ms)
    //public static final double THRESHOLD_VL = 0.8;

    public void powerSlideMotor(double pwr) {
        slideMotor.setPower(pwr);
    }

    public void slideStart(int dest) {
        if (slideActive) {
            tl.addLine("carriage is moving");
            tl.addLine("another move shall not be executed");
            tl.update();
            //ignore command
            return;
        }
        if (carriageState == dest && inRange(carriageState, slideMotor.getCurrentPosition())) {
            //already in position
            //sometimes carriageState isn't really current position
            //so at this time we will keep executing command
            tl.addLine(String.format("target:%d, tick:%d", dest, slideMotor.getCurrentPosition()));
            tl.addLine("in position");
            tl.update();
            //ignore command
            return;
        }
        //execute command
        slideActive = true;
        destination = dest;
        moveDir = Integer.signum(dest - slideMotor.getCurrentPosition());
        tl.addLine("Carriage GO");
        tl.update();
        slideMotor.setPower(MAIN_POWER * Integer.signum(dest - slideMotor.getCurrentPosition()));
        carriageMotionStartTime = System.currentTimeMillis();
    }

    public boolean slideVerify() {
        if (!slideActive) {
            tl.addLine("verifying motor position: " + slideMotor.getCurrentPosition());
            tl.update();
            //carriage isn't active
            return true;
        }
        //overshoot: reached desired position
        boolean overshoot;
        if (moveDir == 1) {
            overshoot = slideMotor.getCurrentPosition() >= destination - 10;
        } else {
            overshoot = slideMotor.getCurrentPosition() <= destination + 10;
        }
        //Math.abs(destination - slideMotor.getCurrentPosition()) <= 20
        if (overshoot || System.currentTimeMillis() - carriageMotionStartTime > 2000) {
            slideMotor.setPower(0.0);
            slideActive = false;
            carriageState = destination;
            tl.addLine("coarse adjust finished");
            tl.addLine("current slider position " + slideMotor.getCurrentPosition());
            tl.addLine("starting fine adjustment...");
            tl.update();
            autoAdjust();
            return true;
        }
        return false;
    }

    @Deprecated
    public void slideTo(int target) {
        if (carriageState == target) {
            //already in position
            tl.addLine(String.format("target:%d, tick:%d", target, slideMotor.getCurrentPosition()));
            tl.addLine("in position");
            tl.update();
            sleep(500);
            return;
        }
        tl.addLine("Carriage GO");
        tl.update();
        slideMotor.setPower(MAIN_POWER * Integer.signum(target - slideMotor.getCurrentPosition()));
        long startTime = System.currentTimeMillis();
        while (Math.abs(target - slideMotor.getCurrentPosition()) > 10) {
            //wait until finish
            sleep(5);
            if (System.currentTimeMillis() - startTime > 2000) {
                //timeout
                break;
            }
        }
        slideMotor.setPower(0.0);
        carriageState = target;
        tl.addLine("coarse adjust finished");
        tl.addLine("current slider position " + slideMotor.getCurrentPosition());
        tl.addLine("starting fine adjustment...");
        tl.update();
        sleep(500);
        autoAdjust();
    }

    public void autoAdjust() {
        if (Math.abs(carriageState - slideMotor.getCurrentPosition()) < 5) {
            return;
        }
        slideMotor.setPower(ADJUST_POWER * Integer.signum(carriageState - slideMotor.getCurrentPosition()));
        long startTime = System.currentTimeMillis();
        while (Math.abs(carriageState - slideMotor.getCurrentPosition()) > 5) {
            //wait until finish
            if (System.currentTimeMillis() - startTime > 100) {
                //timeout
                break;
            }
        }
        slideMotor.setPower(0.0);
        tl.addLine("fine adjustment finished");
        tl.addLine(String.format("state:%d current position: %d", carriageState, slideMotor.getCurrentPosition()));
        tl.update();
        sleep(50);
    }

    private boolean inRange(int target, int value) {
        return Math.abs(target - value) < 20;
    }

    @Deprecated
    public void flipperToggleStart() {
        if (!flipperToggleActive) {
            return;
        }
        flipperToggleActive = true;
        flipMotor.setPower(currentState ? 1.0 : -1.0);
    }

    //return true iff flipper has reached destination or isn't active
    @Deprecated
    public boolean flipperToggleVerify() {
        if (!flipperToggleActive) {
            return true;
        }
        if ((currentState ? UP : DOWN) - flipMotor.getCurrentPosition() > 5) {
            flipMotor.setPower(0.0);
            currentState = !currentState;
            flipperToggleActive = false;
            return true;
        }
        return false;
    }

    //false: down true:up
    boolean currentState;
    static final int FlipperTimeOut = 1200;
    static final int UP = 450;
    static final int DOWN = 0;
    boolean flipperToggleActive = false;

    //test function
    public void flipperToggle() {

        tl.addLine(String.format("current state: %s", currentState ? "up" : "down"));
        tl.addLine(String.format("goto: %s", currentState ? "down" : "up"));
        tl.update();
        flipMotor.setPower(currentState == false ? 0.75 : -0.125);
        long startTime = System.currentTimeMillis();
        int target = currentState ? DOWN : UP;
        while (Math.abs(target - flipMotor.getCurrentPosition()) > 5) {
            //if (!KeithTeleOp.isIsStopped()) {
            //wait until finish
            tl.addLine(String.format("current position: %d", flipMotor.getCurrentPosition()));
            tl.update();
            if (System.currentTimeMillis() - startTime > FlipperTimeOut) {
                break;
            }
            if (target == DOWN && flipMotor.getCurrentPosition() < DOWN) {
                break;
            }
            if (target == UP && flipMotor.getCurrentPosition() > UP) {
                break;
            }
            //}else{
            //    break;
            //}
        }
        currentState = !currentState;
        flipMotor.setPower(0.0);
        tl.addLine("finish");
        tl.update();
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //gripper constants
    static final double holdL = 1.0;
    static final double releaseL = 0.2;
    static final double holdR = 0.7;
    static final double releaseR = 0.1;
    static final boolean LEFTS = true;
    static final boolean RIGHTS = false;

    public void holderToggle(boolean side) {
        if (side) {
            tl.addLine("LEFT " + String.format("to %s", lServo.getPosition() == holdL ? "release" : "hold"));
            tl.update();
            lServo.setPosition(lServo.getPosition() == holdL ? releaseL : holdL);
            sleep(500);
        } else {
            tl.addLine("RIGHT " + String.format("to %s", rServo.getPosition() == holdR ? "release" : "hold"));
            tl.update();
            rServo.setPosition(rServo.getPosition() == holdR ? releaseR : holdR);
            sleep(500);
        }
    }

//	public static final int DIS = 100;


//    public void flipToggle() {
//        flipMotor.setPower(currentState ? 1.0 : -1.0);
//        while ((currentState ? UP : DOWN) - flipMotor.getCurrentPosition() > 5) {
//            //wait until finish
//        }
//        currentState = !currentState;
//        flipMotor.setPower(0);
//    }

//    public void setState(int state) {
//		slideMotor.setPower(Integer.signum(state * DIS - slideMotor.getCurrentPosition()));
//		while (Math.abs(slideMotor.getCurrentPosition() - state * DIS) > 10) {
//			//wait until finish
//		}
//		slideMotor.setPower(0.0);
//	}
}
