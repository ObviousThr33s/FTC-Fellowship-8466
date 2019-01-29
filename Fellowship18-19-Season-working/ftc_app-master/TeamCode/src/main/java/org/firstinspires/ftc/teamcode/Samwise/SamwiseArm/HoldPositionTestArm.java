package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class HoldPositionTestArm extends SamwiseArm
{
    private int hold_position_J2 = Integer.MAX_VALUE;
    private int hold_position_J3 = Integer.MAX_VALUE;

    static final double POWER_RANGE=0.2;

    public HoldPositionTestArm(HardwareMap hwm)
    {
        super(hwm);
    }


    public void mdriveJ1(boolean isLeft) {
        motorJ1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorJ1.setPower(isLeft?-0.7:0.7);
    }

    public void mstopJ1()
    {
        super.motorJ1.setPower(0);
    }

    public void mdriveJ2(float x)
    {
        if (Math.abs(x)>0.1)
        {
            System.out.println("J2 x: "+x);
            motorJ2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.hold_position_J2 = Integer.MAX_VALUE;
            super.motorJ2.setPower(Range.scale(x,-1, 1, -POWER_RANGE, POWER_RANGE));
        }
        else
        {
            if (this.hold_position_J2 == Integer.MAX_VALUE)
            {
                this.hold_position_J2 = super.motorJ2.getCurrentPosition();
            }
            motorJ2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            super.motorJ2.setTargetPosition(this.hold_position_J2);
            super.motorJ2.setPower(1);
        }
    }


    public void mdriveJ3(float x)
    {
        if (Math.abs(x)>0.1)
        {
            System.out.println("J3 x: "+x);
            motorJ3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.hold_position_J3 = Integer.MAX_VALUE;
            super.motorJ3.setPower(Range.scale(x,-1, 1, -POWER_RANGE, POWER_RANGE));
        }
        else
        {
            if (this.hold_position_J3 == Integer.MAX_VALUE)
            {
                this.hold_position_J3 = super.motorJ3.getCurrentPosition();
            }
            motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            super.motorJ3.setTargetPosition(this.hold_position_J3);
            super.motorJ3.setPower(1);
        }
    }

}

