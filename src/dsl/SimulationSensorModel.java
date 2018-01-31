package dsl;

import java.util.*;

import groovy.lang.Binding;
import model.Simulation;
import model.Capteur;
import model.CapteurRandom;
import model.CapteurNeperien;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;



public class SimulationSensorModel {
	
	
	private Binding binding;
	private List<Capteur> sensors;
	
	public SimulationSensorModel(Binding binding) {		
		this.binding = binding;
		this.sensors=new ArrayList<Capteur>();
	}

	public void createSensor(String name) {
		Capteur sensor = new CapteurRandom();
		//sensor.setName(name);
		//sensor.setType(type);
		//this.sensors.add(sensor);
		//this.binding.setVariable(name, sensor);

		for(int i =0;i<(int)this.binding.getVariable("time");i++){
			System.out.println("sensor " + name + " value : "+ sensor.law(i));
		}

	}

	public void setTime(int ticks){
		this.binding.setVariable("time",ticks);
	}
		
	public void createZone(String name, int nbRandom, int nbNeperien) throws IOException{
		List<Capteur> l = new ArrayList<Capteur>();
		File file = new File("result/"+name+".csv");
		file.getParentFile().mkdirs();
		int nb = nbRandom + nbNeperien;

		for(int i=0;i<nbRandom;i++){
			l.add(new CapteurRandom());
		}
		for(int i=0;i<nbNeperien;i++){
			l.add(new CapteurNeperien());
		}

		PrintWriter writer = new PrintWriter(file);

		for(int i =0;i<nb;i++){
			if(i == nb-1){
				//System.out.printf("sensor"+i+"\n");
				writer.printf("sensor"+i+"\n");
			} else {
				//System.out.printf("sensor"+i+",");
				writer.printf("sensor"+i+",");
			}
		}
		for(int i =0;i<(int)this.binding.getVariable("time");i++){
			for(int j=0;j<nb;j++){
				if(j==nb-1){
					//System.out.printf(l.get(j).law()+"\n");
					writer.printf(l.get(j).law(i)+"\n");
				} else {
					//System.out.printf(l.get(j).law()+",");
					writer.printf(l.get(j).law(i)+",");
				}
			}
		}
		writer.close();
	}
    
}
