package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.hardware.Sensor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
/**
 * Created by KAEGAN on 1/28/2018.
 */

public class KeithColorSensor{

	ColorSensor KnockerCS = null;
	private Telemetry tel;

	KeithColorSensor(HardwareMap hwMap, Telemetry t) {
		tel = t;
		KnockerCS = hwMap.get(ColorSensor.class, "JewlColorSensor");
	}

	public void getColor(){
		int colorBlue = KnockerCS.blue();
		int colorRed = KnockerCS.red();
		int colorGreen = KnockerCS.green();
	}




}
