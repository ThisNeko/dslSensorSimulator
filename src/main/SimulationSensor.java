package main;

import java.io.File;

import dsl.SimulationSensorDSL;

public class SimulationSensor {
	public static void main(String[] args) {
		SimulationSensorDSL dsl = new SimulationSensorDSL();
		if(args.length > 0) {
			dsl.eval(new File(args[0]));
		} else {
			System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
		}
	}
}