package org.firstinspires.ftc.teamcode.Samwise.Conceptual;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;
import org.firstinspires.ftc.teamcode.Samwise.Hanger.SamwiseHanger;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.TRexSamwiseArm;

public class SamwiseRobot {

    //Utils
    HardwareMap hw;
    Telemetry tel;

    //Subsystems
    TRexSamwiseArm arm;
    SamwiseHanger hanger;
    SamwiseDriveTrain driveTrain;

    public SamwiseRobot(HardwareMap h, Telemetry t){
        TRexSamwiseArm arm = new TRexSamwiseArm(h);
        //SamwiseHanger     hanger     = new SamwiseHanger(h, t, "hangermotor1", "hangerservo1", "hangerservo2");
        SamwiseHanger     hanger     = new SamwiseHanger();
        SamwiseDriveTrain driveTrain = new SamwiseDriveTrain();

        driveTrain.init(h);
    }


    public TRexSamwiseArm arm() { return arm; }
    public SamwiseHanger hanger() {return hanger;}
    public SamwiseDriveTrain driveTrain() {
        return driveTrain;
    }
}
