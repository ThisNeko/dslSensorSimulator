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
	private List<Capteur> sensors;
	
	public SimulationSensorModel(Binding binding) {		
		this.binding = binding;
		this.sensors=new ArrayList<Capteur>();

		influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
		influxDB.createDatabase("yop");
		influxDB.createRetentionPolicy("defaultPolicy", "yop", "30d", 1, true);
		
	    batchPoints = BatchPoints
	    .database("yop")
        .retentionPolicy("defaultPolicy")
        .build();
	    influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);

	}

	public void createSensor(String name) {
		




 

		Capteur sensor = new CapteurRandom();
		//sensor.setName(name);
		//sensor.setType(type);
		//this.sensors.add(sensor);
		//this.binding.setVariable(name, sensor);

		/*Point point = Point.measurement("capteurs")		
		.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
		.addField("time", System.currentTimeMillis())
        .addField("capteur", name)
        .addField("value", sensor.low())
        .build();

        Point point = Point.measurement("capteurs")		
		.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
		.addField("time", System.currentTimeMillis())
        .addField("capteur", name)
        .addField("value", sensor.low())
        .build();*/

        


		for(int i =0;i<(int)this.binding.getVariable("time");i++){
			System.out.println("sensor " + name + " value : "+ sensor.law(i));
			Point point = Point.measurement("capteurs")		
		.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
		.addField("time", System.currentTimeMillis())
        .addField("capteur", name)
        .addField("value", sensor.law(i))
        .build();

        batchPoints.point(point);
        
			
		}

		
    //influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);
    influxDB.write(batchPoints);


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
