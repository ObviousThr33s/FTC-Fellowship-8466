package org.firstinspires.ftc.teamcode;

/**
 * Created by KAEGAN on 2/3/2018.
 */

public enum PowerLvl {

	LOW (0.2),
	MED (0.6),
	HIG (1.0);

	public final double constant;

	PowerLvl (double constant) {
		this.constant = constant;

	}
}
