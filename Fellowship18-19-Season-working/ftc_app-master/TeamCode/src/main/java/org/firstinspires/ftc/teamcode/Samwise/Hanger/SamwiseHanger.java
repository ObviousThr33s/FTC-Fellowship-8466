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
    static final double DRIVE_GEAR_REDUCTION = 2.0; //2.0
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    private ElapsedTime runtime = new ElapsedTime();


    public Telemetry telemetry;

    public DcMotor hangermotor1;
    public Servo hangerservo1;
    public Servo markerservo1;

    public SamwiseHanger() {

    }

    public void init(HardwareMap hw, Telemetry t) {
        hangermotor1 = hw.dcMotor.get("hangermotor1");
        hangerservo1 = hw.servo.get("hangerservo1");
        markerservo1 = hw.servo.get("marker");

        telemetry = t;

        //telemetry.addData("info", "Hanger Init");
        //telemetry.update();

        hangermotor1.setDirection(DcMotor.Direction.REVERSE);
        hangermotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hangermotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void Hook() {
            hangerservo1.setPosition(0);
    }

    public void unHook() {
        hangerservo1.setPosition(0.5);
    }

    public void hookUpdate() {

        if (Math.abs(hangerservo1.getPosition())<0.1)
        {
            hangerservo1.setPosition(0.5);
        }
        else
        {
            hangerservo1.setPosition(0);
        }
    }

    public void move(double power) {

        this.hangermotor1.setPower(power);
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

    public void encoderDriveNoWait(LinearOpMode linearopMode, double speed, double inches) {

        int TargetPosition = this.hangermotor1.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
        this.hangermotor1.setTargetPosition(TargetPosition);

        this.hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.hangermotor1.setPower(speed);

        //this.hangermotor1.setPower(0);
        //hangermotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}