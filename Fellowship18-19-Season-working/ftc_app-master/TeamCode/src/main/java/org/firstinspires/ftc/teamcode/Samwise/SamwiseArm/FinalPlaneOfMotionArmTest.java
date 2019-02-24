package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


import com.qualcomm.robotcore.hardware.HardwareMap;


@TeleOp (name = "PlaneOfMotion")


// COPY OF ArmTest3___________________________________________________________




public class FinalPlaneOfMotionArmTest extends OpMode {
    private boolean manualmode = false;

    //private DcMotor ArmMotor1 = null;
    private DcMotor ArmMotor2 = null;
    private DcMotor ArmMotor2_2 = null;
    private DcMotor ArmMotor3 = null;
    //J1 static
    static final double BigGearCount = 120.0;
    static final double SmallGearCount = 24.0;
    static final double EncoderCountJ1 = 1680.0;
    static final double MaxOrMinDegrees = 45.0;
    private double J1GearRatio = BigGearCount / SmallGearCount;
    private double TickPerDegreeJ1 = EncoderCountJ1 / 360.0;
    private double BigToSmallRatio = MaxOrMinDegrees * J1GearRatio;
    private double SmallDegreeToTicks = BigToSmallRatio * TickPerDegreeJ1;

    //J2, J3 Static
    static final double EncoderCountJ2 = 1993.6; //number of ticks per motor round
    static final double EncoderCountJ3 = 1993.6; //number of ticks per motor round
    static final double HeightOfPlane = 6.0; //height of plane of motion
    static final double LengthJ2toJ3 = 13.0; //distance between J2 and J3
    static final double LengthJ3toJ4 = 15.0; //distance between J3 and J4

    private double H = HeightOfPlane;
    private double L1 = LengthJ2toJ3; //length between J2 and J3
    private double L2 = LengthJ3toJ4; //length between J3 and J4
    private double TickPerDegreeJ3 = EncoderCountJ3 / 360.0;
    private double TickPerDegreeJ2 = EncoderCountJ2 / 360.0;
    double J2MaxPos = 90.0 * TickPerDegreeJ2;
    double J2MinPos = 0.0 * TickPerDegreeJ2;

    double J2FirsttoLast = 1;
    double J3FirsttoLast = 1;

    int HoldPosONOFF = 0;
    double power_level = 0.2;
    private boolean firsttime = false;
    double J2initialposition;
    double J3initialposition;


    public void init() {

        ArmMotor2 = hardwareMap.dcMotor.get("1J2");
        ArmMotor3 = hardwareMap.dcMotor.get("J3");
        ArmMotor2_2 = hardwareMap.dcMotor.get("2J2");
        ArmMotor2.setPower(0);
        ArmMotor2_2.setPower(0);
        ArmMotor3.setPower(0);
        // ArmMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // ArmMotor2_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // ArmMotor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ArmMotor2_2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ArmMotor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void init_loop() {

    /*
        int motor2currentpos;
        int motor3currentpos;
        if (gamepad1.x) {
            motor2currentpos =  ArmMotor2.getCurrentPosition() +40;
            ArmMotor2.setTargetPosition(motor2currentpos);
            ArmMotor2.setPower(0.1);
        }
        if (gamepad1.b) {
            motor2currentpos = ArmMotor2.getCurrentPosition() -40;
            ArmMotor2.setTargetPosition(motor2currentpos);
            ArmMotor2.setPower(0.1);
        }
        ArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (gamepad1.y) {
            ArmMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ArmMotor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ArmMotor3.setPower(0);
            ArmMotor2.setPower(0);
        }
        if(gamepad1.dpad_left) {
            motor3currentpos = ArmMotor3.getCurrentPosition() +40;
            ArmMotor3.setTargetPosition(motor3currentpos);
            ArmMotor3.setPower(0.1);
        }
        if (gamepad1.dpad_right) {
            motor3currentpos = ArmMotor3.getCurrentPosition() - 40;
            ArmMotor3.setTargetPosition(motor3currentpos);
            ArmMotor3.setPower(0.1);
        }
        ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */
        ArmMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmMotor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmMotor2_2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //manually adjust
        if (gamepad1.left_stick_y > 0.1) {
            ArmMotor2.setPower(power_level);
            ArmMotor2_2.setPower(power_level);
        } else if (gamepad1.left_stick_y < -0.1) {
            ArmMotor2.setPower(-power_level);
            ArmMotor2_2.setPower(-power_level);
        } else {
            ArmMotor2.setPower(0);
            ArmMotor2_2.setPower(0);
        }
        if (gamepad1.right_stick_y > 0.1) {
            ArmMotor3.setPower(0.5);
        } else if (gamepad1.right_stick_y < -0.1) {
            ArmMotor3.setPower(-0.5);
        } else {
            ArmMotor3.setPower(0.0);
        }
        //initial position fixed

        J2initialposition = ArmMotor2.getCurrentPosition();
        J3initialposition = ArmMotor3.getCurrentPosition();

      /*  if (gamepad1.right_bumper) {
            firsttime = true;
        }

        if (firsttime = true) {
            firsttime = false;
            ArmMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ArmMotor2_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ArmMotor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //set initial plane of motion position
            //matthhh
            double J2_init = 180.0;
            double J3_init = 90.0;
            double J2_adjust = 30.0;
            double J2_PoM_init = J2_init - 30.0 - 90.0;
            double J3_height = L1 * Math.sin(Math.toRadians(J2_PoM_init));
            double J3_angel = 90.0 - J2_PoM_init + Math.toDegrees(Math.acos((J3_height - H) / L2));

            double J3_adjust = J3_angel - J3_init;

            ArmMotor2.setTargetPosition((int) J2_adjust * (int) TickPerDegreeJ2);   //check motor rotate direction
            ArmMotor2_2.setTargetPosition((int) J2_adjust* (int) TickPerDegreeJ2);
            ArmMotor3.setTargetPosition(-1*(int) J3_adjust * (int) TickPerDegreeJ3);
            ArmMotor2.setPower(0.2);
            ArmMotor3.setPower(0.2);
            ArmMotor2_2.setPower(0.2);
            ArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmMotor2_2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } */
    }

    public void loop() {
        ArmMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmMotor2_2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmMotor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double J2CurrentPos_deg;

        // double J3TargetPos_deg;
        double J3TargetPos_Ticks;
        //  double a, b, c, c_1, d;

        if (gamepad1.right_bumper) {
            manualmode = false;
        }
        if (gamepad1.left_bumper) {
            manualmode = true;
        }

        if (manualmode = true) {


        } else {

            J2initialposition = ArmMotor2.getCurrentPosition();
            J3initialposition = ArmMotor3.getCurrentPosition();
            if (Math.abs(gamepad1.left_stick_y) > 0.1) {

                if (gamepad1.left_stick_y <= -0.05) { //push forward
                    J2MaxPos = J2initialposition + EncoderCountJ2 / 50.0;
                    ArmMotor2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));
                    ArmMotor2_2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));
                    //    ArmMotor2.setPower(gamepad1.left_stick_y / 5.0);
                    //    ArmMotor2_2.setPower(gamepad1.left_stick_y / 5.0);
                    ArmMotor2.setPower(0.5);
                    ArmMotor2_2.setPower(0.5);
                    ArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    ArmMotor2_2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    J3TargetPos_Ticks = J3initialposition + (-1.47 * (EncoderCountJ2 / 50.0));
                    ArmMotor3.setTargetPosition((int) (J3TargetPos_Ticks * J3FirsttoLast));
                    ArmMotor3.setPower(0.5); //test Power LAtEr yeet
                    ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                } else if (gamepad1.left_stick_y >= 0.05) { //backwards
                    J2MaxPos = J2initialposition - EncoderCountJ2 / 50.0;
                    ArmMotor2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));
                    ArmMotor2_2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));
                    ArmMotor2.setPower(0.5);
                    ArmMotor2_2.setPower(0.5);
                    ArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    ArmMotor2_2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    J3TargetPos_Ticks = J3initialposition - (-1.47 * (EncoderCountJ2 / 50.0));
                    ArmMotor3.setTargetPosition((int) (J3TargetPos_Ticks * J3FirsttoLast));
                    ArmMotor3.setPower(0.5); //test Power LAtEr yeet
                    ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }


        /*        double J2CurrentPos_deg;

                double J3TargetPos_deg;
                double J3TargetPos_Ticks;
                double a, b, c, c_1, d;
                J2CurrentPos_deg = (ArmMotor2.getCurrentPosition() / TickPerDegreeJ2);
                a = Math.cos(Math.toRadians(180.0 - J2CurrentPos_deg));
                b = (L1 * a - H) / L2;
                c = Math.acos(b);
                c_1 = Math.toDegrees(c);
                d = c_1 + 180.0 - J2CurrentPos_deg;
                J3TargetPos_deg = d;
                J3TargetPos_Ticks = J3TargetPos_deg * TickPerDegreeJ3;
//maatttthhhhh
                ArmMotor3.setTargetPosition((int) (J3TargetPos_Ticks * 3 * J3FirsttoLast));
                ArmMotor3.setPower(0); //test Power LAtEr yeet
                ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //J1
                //HoldPosONOFF = 1;
            }*/
                else {

                    stop();

               /* if (HoldPosONOFF == 1) {
                    ArmMotor2.setTargetPosition(ArmMotor2.getCurrentPosition());
                    ArmMotor2_2.setTargetPosition(ArmMotor2.getCurrentPosition());
                    ArmMotor3.setTargetPosition(ArmMotor3.getCurrentPosition());
                    ArmMotor2.setPower(1);
                    ArmMotor2_2.setPower(1);
                    ArmMotor3.setPower(1);
                    ArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    ArmMotor2_2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                }
                */

                }
            }
        }



    }
    public void stop(){
        ArmMotor2.setPower(0);
        ArmMotor2_2.setPower(0);
        ArmMotor3.setPower(0);
    }
}
