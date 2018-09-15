package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class KeithElevator {
	private DcMotor ElevatorMotor = null;
	private Servo ElevatorServo = null;
	private Telemetry t;
	private double motorPower;
	public boolean UP;
	public static double upPos = 0.3;

	public static double downPos = 0.95;

	KeithElevator(HardwareMap hwmap, Telemetry tel, String elevatorMotor, String elevatorServo) {
		ElevatorMotor = hwmap.get(DcMotor.class, elevatorMotor);
		ElevatorServo = hwmap.get(Servo.class, elevatorServo);
		t = tel;
		ElevatorServo.scaleRange(0.0, 1.0);
	}

	public void elevatorPower(double Pwr) {
		ElevatorMotor.setPower(Pwr);
	}

	public double getKickerPosition(){
		return ElevatorServo.getPosition();
	}

	public boolean isKickerUp(){
		if (getKickerPosition() == upPos){
			UP = true;
		}
		if (getKickerPosition() <= downPos && getKickerPosition() != upPos) {
			UP = false;
		}

		return UP;
	}


	public void kickerSetPosition(double Pos) {t.addLine("kicker servo position is being set"); ElevatorServo.setPosition(Pos); }

	public void kickerReset() {
		kickerSetPosition(downPos);
		while (ElevatorServo.getPosition() < downPos){ t.addLine("kicker reseting..."); }
		t.addLine("kicker reset");

	}

	public void kickerKick() {
		kickerSetPosition(upPos);
		while (ElevatorServo.getPosition() > upPos){ t.addLine("kicker kicking..."); }
		t.addLine("kicker kicked");
	}
}