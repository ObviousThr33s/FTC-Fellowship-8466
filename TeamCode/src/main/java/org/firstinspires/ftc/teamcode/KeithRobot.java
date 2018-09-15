package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Joshua on 10/28/2017.
 */

public class KeithRobot extends FTCRobot {
    BoschIMU imu;
    MecanumDS mds;
    FishingRodSystem frs;
    KeithJewlKnocker jks;
    KeithElevator ele;
    KeithCarriage car;
    JewlDetect jds;

    KeithRobot(HardwareMap hardwareMap, Telemetry telemetry) {
        imu = new BoschIMU(hardwareMap, "imu");
        mds = new MecanumDS(hardwareMap, telemetry, imu, "Front Left", "Front Right", "Back Left", "Back Right");
        frs = new FishingRodSystem(hardwareMap, telemetry, "lowerReel", "upperReel", "claw", "rodMotor");
        jks = new KeithJewlKnocker(hardwareMap, "JewlBase", "JewlKnocker","colorSensor", telemetry);
        ele = new KeithElevator(hardwareMap, telemetry, "HarvesterMain", "Kicker");
        car = new KeithCarriage(hardwareMap,telemetry, "slideMotor", "flipMotor", "lServo","rServo");
        jds = new JewlDetect();

        Servo carriageLeftServo = hardwareMap.servo.get("lServo");
        carriageLeftServo.setPosition(0.35);
        Servo carriageRightServo = hardwareMap.servo.get("rServo");
        carriageRightServo.setPosition(0.42);
        Servo kickerServo = hardwareMap.servo.get("Kicker");
        kickerServo.setPosition(0.52);
    }

    public IMUSystem GetIMUSystem() {
        return imu;
    }

    public DriveSystem GetDriveSystem() {
        return mds;
    }

    public RelicArmSubsystem GetRelicArmSubsystem() {
        return frs;
    }

    public KeithJewlKnocker GetJewelKnockerSubsystem() {
        return jks;
    }

    public KeithElevator GetKeithElevator() { return ele; }

    public KeithCarriage GetKeithCarriage() { return car; }

    public JewlDetect GetKeithJewlDetect() { return  jds; }
}
