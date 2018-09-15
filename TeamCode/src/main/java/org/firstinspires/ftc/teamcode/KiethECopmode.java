package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "Keith - ElevatorCarriage", group = "TeleOp")

public class KiethECopmode extends OpMode {

   public double Pwr;
   public HardwareMap hwMap = null;
   public KeithElevator kE = null;

   public void init() {
	   hwMap = hardwareMap;
	   //kE = new KeithElevator(hwMap, 0.5);
   }

	public void loop(){

		boolean actionTaken = false;

		if (!gamepad1.left_bumper && !gamepad1.right_bumper || gamepad1.left_bumper && !gamepad1.right_bumper || !gamepad1.left_bumper && gamepad1.right_bumper){
			if (gamepad1.left_bumper && !gamepad1.right_bumper){
				actionTaken = true;
				kE.elevatorPower(0.5);
			}
			if (gamepad1.right_bumper && !gamepad1.left_bumper) {
				actionTaken = true;
				kE.elevatorPower(-0.5);
			}
			if (!actionTaken) {
				//kE.elevatorStop();
			}
		} else{
			//kE.elevatorStop();
		}

		if (gamepad1.dpad_down) {
			kE.kickerKick();
		}
	}
}
