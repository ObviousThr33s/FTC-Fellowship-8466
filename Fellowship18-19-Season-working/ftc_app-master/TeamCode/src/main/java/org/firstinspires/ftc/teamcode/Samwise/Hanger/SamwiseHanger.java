package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SamwiseHanger {

    static final double COUNTS_PER_MOTOR_REV = 482;
    static final double DRIVE_GEAR_REDUCTION = 2.0;
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    private ElapsedTime runtime = new ElapsedTime();



    public Telemetry telemetry;

    public DcMotor hangermotor1 = null;
    public Servo hangerservo1 = null;

    public SamwiseHanger(HardwareMap hw, Telemetry t) {
        hangermotor1 = hw.dcMotor.get("hangermotor1");
        hangerservo1 = hw.servo.get("hangerservo1");

        telemetry = t;

        telemetry.addData("Mode", "Boron-Oxygen-Oxygen-Phosphorus Beryllium-Phosphorus init time");
        telemetry.update();

        //hangerservo1.setPosition(10);

        hangermotor1.setDirection(DcMotor.Direction.REVERSE);

        hangermotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hangermotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //hangermotor1.setTargetPosition(0);

        //hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //hangermotor1.setPower(1);
    }

    public void unHook() {

        telemetry.addData("Mode", "hook 2");
        hangerservo1.setPosition(0.5);
    }
    public void Hook() {
        telemetry.addData("Mode", "hook 2");
        hangerservo1.setPosition(0);
    }

    public void move(double power) {
        this.hangermotor1.setPower(power);
    }

    public void movedown() {
        telemetry.addData("Mode", "slide 1");

        //hangermotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //hangermotor1.setTargetPosition(0);

        //hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        hangermotor1.setPower(1);
    }
    public void moveup() {

        telemetry.addData("Mode", "slide 1");

        //hangermotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //hangermotor1.setTargetPosition(10);

        //hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        hangermotor1.setPower(-1);
    }


    public void encoderDrive(LinearOpMode linearopMode, double speed, double inches, double timeoutS) {

        if (linearopMode.opModeIsActive()) {
            int TargetPosition = this.hangermotor1.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            this.hangermotor1.setTargetPosition(TargetPosition);

            this.hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            runtime.reset();
            this.hangermotor1.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (linearopMode.opModeIsActive() && (runtime.seconds() < timeoutS) && (this.hangermotor1.isBusy())) {
                linearopMode.idle();

            }
        }

        this.hangermotor1.setPower(0);
        //hangermotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}