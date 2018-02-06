package dsl;

import java.util.*;

import groovy.lang.Binding;
import model.*;
import model.law.*;
import model.law.LawRandom;
import model.CapteurRandom;
import model.CapteurNeperien;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.InfluxDBIOException;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;



public class SimulationSensorModel {
	

	private InfluxDB influxDB;
	private BatchPoints batchPoints;
	
	private Binding binding;
	private List<Sensor> sensorsList;
	private List<Zone> zonesList;
	private Zone z;

	public SimulationSensorModel(Binding binding) {		
		this.binding = binding;
		this.sensorsList=new ArrayList<Sensor>();
		this.zonesList=new ArrayList<Zone>();
		this.z = new Zone("noName");
		zonesList.add(z);

	
	}

	public void createSensor(String name) {

		Sensor sensor = new Sensor(name,new LawRandom());
		//sensorsList.add(sensor);
		z.add(sensor);

		//System.out.println(name);

	}

	public void setTime(int ticks){
		this.binding.setVariable("time",ticks);
	}

	public void setDuration(int value){
		this.binding.setVariable("duration",value);
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


	public void run(String name){
		Simulation simu = new Simulation();
		simu.setZones(zonesList);
		simu.exec((int)this.binding.getVariable("time"));



		/*for(int j =0;j<(int)this.binding.getVariable("time");j++){
			for(int i=0;i<sensorsList.size();i++){
				System.out.println(j+","+sensorsList.get(i).getName()+","+sensorsList.get(i).getValue(j));				
			}
		}*/
	}



    
}
