package org.firstinspires.ftc.teamcode.Samwise.Conceptual;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AbstractPhysical.DriveTrain;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;
import org.firstinspires.ftc.teamcode.Samwise.Hanger.SamwiseHanger;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseArm;

public class SamwiseRobot {

    //Utils
    HardwareMap hw;
    Telemetry tel;

    //Subsystems
    SamwiseArm arm;
    SamwiseHanger hanger;
    SamwiseDriveTrain driveTrain;

    public SamwiseRobot(HardwareMap h, Telemetry t){
        SamwiseArm        arm        = new SamwiseArm(h);
        SamwiseHanger     hanger     = new SamwiseHanger(h, t, "hangermotor1", "hangerservo1", "hangerservo2");
        SamwiseDriveTrain driveTrain = new SamwiseDriveTrain();

        driveTrain.init(h);
    }


    public SamwiseArm arm() { return arm; }
    public SamwiseHanger hanger() {return hanger;}
    public SamwiseDriveTrain driveTrain() {
        return driveTrain;
    }
}
