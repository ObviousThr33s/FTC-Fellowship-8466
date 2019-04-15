package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.SamwiseVision;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseColor;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrainIMU;
import org.firstinspires.ftc.teamcode.Samwise.Hanger.SamwiseHanger;

@Autonomous(name = "SamwiseAutoDrive v1", group = "Samwise")
@Disabled
public class SamwiseAutoDrive extends LinearOpMode {

    // this is set before the robot is initialized
    protected boolean isCrater = false;

    //ideal right most mineral middle point position
    protected double idealPos = 689;

    /* Declare OpMode members. */
    //SamwiseRobot sr = new SamwiseRobot(hardwareMap, telemetry);
    SamwiseDriveTrainIMU robot = new SamwiseDriveTrainIMU();   // Use a drivetrain's hardware
    SamwiseVision vis = new SamwiseVision();
    SamwiseMarkerDeposit md = new SamwiseMarkerDeposit();
    SamwiseHanger hanger = new SamwiseHanger();//sr.hanger();
    SamwiseColor color = new SamwiseColor();
    // SampleAndDeposit sampleAndDeposit = null;

    DigitalChannel touchFrontSide;  // side touch sensor
    DigitalChannel touchBackSide;  // side touch sensor
    DigitalChannel touchFront;  // front touch sensor
    DigitalChannel touchfront2;  // front touch sensor
    DigitalChannel touchfront3;

    DcMotor motorE1;
    DcMotor motorJ1;
    DcMotor motorJ3;

    DcMotor motor2J2;
    DcMotor motor1J2;

    CRServo servoE2;

    boolean runWithArm = true;

    static final int E1_MAX_COUNT = 4600;

    /**
     * init with and without tensorflow
     *
     * @param tf
     */
    protected void init(boolean tf) {
        /*
         * Initialize the driveToCrater system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        md.init(hardwareMap);
        hanger.init(hardwareMap, telemetry);

        //init J1 and E2 servo
        motorJ1 = hardwareMap.dcMotor.get("J1");
        motorJ1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //motorJ1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorJ3 = hardwareMap.dcMotor.get("J3");
        motorJ3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorE1 = hardwareMap.dcMotor.get("E1");
        motorE1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor1J2 = hardwareMap.dcMotor.get("1J2");
        motor2J2 = hardwareMap.dcMotor.get("2J2");

        //motorE1.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        //motorE1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        servoE2 = hardwareMap.crservo.get("E2");

        // get a reference to our digitalTouch object.
        touchFrontSide = hardwareMap.get(DigitalChannel.class, "touch_front_side");
        // set the digital channel to input.
        touchFrontSide.setMode(DigitalChannel.Mode.INPUT);

        touchBackSide = hardwareMap.get(DigitalChannel.class, "touch_back_side");
        // set the digital channel to input.
        touchBackSide.setMode(DigitalChannel.Mode.INPUT);

        touchFront = hardwareMap.get(DigitalChannel.class, "touch_front");
        // set the digital channel to input.
        touchFront.setMode(DigitalChannel.Mode.INPUT);

        touchfront2 = hardwareMap.get(DigitalChannel.class, "touch_front2");
        // set the digital channel to input.
        touchfront2.setMode(DigitalChannel.Mode.INPUT);

        touchfront3 = hardwareMap.get(DigitalChannel.class, "touch_front3");
        // set the digital channel to input.
        touchfront3.setMode(DigitalChannel.Mode.INPUT);

        color.init(hardwareMap, telemetry);

        if (tf) {
            vis.init(hardwareMap);
        }

    }


    @Override
    public void runOpMode() {
        this.init(true);

        // Wait for the game to start (driver presses PLAY)
        //waitForStart();
        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("status", "waiting for start command...");
            telemetry.update();
        }

        //Unhinging Robot
        //hanger.encoderDrive(this,1,0.9,1);
        //System.out.println("==> hanger encoder1 : "+hanger.hangermotor1.getCurrentPosition());

        //hanger.hangermotor1.setPower(-1);
        //sleep(200);
        //hanger.hangermotor1.setPower(0);

        //System.out.println("==> hanger encoder2 : "+hanger.hangermotor1.getCurrentPosition());

        //landing the Robot
        //hanger.encoderDrive(this, 1, 3000);
        //sleep(2000);
        hanger.encoderDrive(this, 1, 12700, 5.5);

        //sleep(1000);

        //Unhooking
        hanger.unHook();
        sleep(400); //wait until the hook fully opens
        while (hanger.hangerservo1.getPosition()<0.4){
            hanger.unHook();
            sleep(200);
        }

        // stop J3
        //J3.setPower(0);

        //Lowering the Slide
        //hanger.encoderDrive(this, 0.9, 46, 4);
        //sleep(500);

        SamwiseVision.GoldPosition position = SamwiseVision.GoldPosition.UNKNOWN;

        //Activate object detector to get gold position, then shut it down
        if (opModeIsActive()) {
            vis.activate();

            //this.adjustPosition();

            position = vis.getGoldPosition();  //Find gold mineral position

            //System.out.println("==>The Gold Position: " + position);

            if (position == SamwiseVision.GoldPosition.UNKNOWN) {
                // it is more likely to be UNKNOWN when two silver minerals in the view.
                position = SamwiseVision.GoldPosition.LEFT;
            }
            // System.out.println("==>Is this crater: " + vis.isCrater());
            vis.deactivate();
            vis.shutdown();
        }

        //System.out.println("This is the " + (isCrater ? "Crater" : "Depot"));

        //Sampling
        ISamwiseDriveRoute driveRoute = samplingRoute(position);

        // extending E1 and E2
        if(runWithArm) {
            servoE2.setDirection(DcMotorSimple.Direction.FORWARD);
            servoE2.setPower(.61);

            this.extendL1Auto();
        }

        //retracting the hanger without blocking the drive
        hanger.encoderDriveNoWait(this, .6, 1800);

//        sleep(4000);

        //driveToCrater the specific route
        driveRoute.drive();

        this.md.move(SamwiseMarkerDeposit.initPosition); //back to init position


        if(runWithArm) {
            //this.driveJ2up(500);
            //sleep(1500);

            //this.turnJ1Auto(1100);

            //this.driveJ3up(800);

            //this.driveJ2up(1600);

            //retracting the hanger
            // hanger.encoderDrive(this, 0.6, 53.5, 3);
            while (opModeIsActive()) {
                idle(); // keep L2
            }
        }

    }

    private void adjustPosition() {

        double ratio;

        do {
            Recognition reference = vis.getReference();

            if (reference == null) {
                return;
            }

            double curPos = (reference.getBottom() + reference.getTop()) / 2;
            ratio = idealPos / curPos;

            System.out.println("==>Current Ratio (before correction): " + ratio);

            if (ratio > 1) {
                // turn left
                System.out.println("==>Current Ratio: " + ratio + ". Turn Left 2 Degrees: ");
                this.robot.turnDrive(this, 2, 2);
            } else {
                // turn right
                System.out.println("==>Current Ratio: " + ratio + ". Turn Right 2 Degrees.");
                this.robot.turnDrive(this, -2, 2);
            }
        }
        while (ratio > 1.05 || ratio < .97);

        System.out.println("==>Current Ratio (after correction): " + ratio);
    }

    /**
     * select sampling driveToCrater route
     *
     * @param position
     * @return
     */
    protected ISamwiseDriveRoute samplingRoute(SamwiseVision.GoldPosition position) {

        ISamwiseDriveRoute driveRoute = null;

        /**
         * todo: externalize the routes
         */
        if (isCrater) {
            switch (position) {
                case RIGHT: //right
                    driveRoute = SamwiseDriveRouteFactory.createCraterRight2(this);
                    break;
                case LEFT: //left
                    driveRoute = SamwiseDriveRouteFactory.createCraterLeft2(this);
                    break;
                case CENTER:  //center
                default:
                    driveRoute = SamwiseDriveRouteFactory.createCraterCenter2(this);
            }
        } else {
            switch (position) {
                case RIGHT: //right
                    driveRoute = SamwiseDriveRouteFactory.createDepotRight(this);
                    break;
                case LEFT: //left
                    driveRoute = SamwiseDriveRouteFactory.createDepotLeft(this);
                    break;
                case CENTER:  //center
                default:
                    driveRoute = SamwiseDriveRouteFactory.createDepotCenter(this);
            }
        }
        return driveRoute;
    }

    public void extendL1Auto() {
        motorE1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorE1.setDirection(DcMotorSimple.Direction.REVERSE);
        motorE1.setPower(0.6);
        motorE1.setTargetPosition(E1_MAX_COUNT);
    }

    public void turnJ1Auto(int count) {
        motorJ1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setDirection(DcMotorSimple.Direction.REVERSE);
        motorJ1.setTargetPosition(count);
        motorJ1.setPower(0.5);
    }

    public void driveJ2up(int count) {

        motor1J2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2J2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor1J2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2J2.setDirection(DcMotorSimple.Direction.REVERSE);

        motor1J2.setTargetPosition(count);
        motor2J2.setTargetPosition(count);

        motor1J2.setPower(0.3);
        motor2J2.setPower(0.3);
    }

    public void driveJ3up(int count) {

        motorJ3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorJ3.setDirection(DcMotorSimple.Direction.REVERSE);

        motorJ3.setTargetPosition(count);

        motorJ3.setPower(1);
    }

}

