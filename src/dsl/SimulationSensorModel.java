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
		this.binding.setVariable("noName",z);
		zonesList.add(z);	
		this.binding.setVariable("writeType","terminal");	

	
	}

	public void createSensor(String name, String behavior, String zone) {

		//Sensor sensor = new Sensor(name,new LawRandom());	
		Sensor sensor = new Sensor(name,(Behavior)this.binding.getVariable(behavior));			
		//sensorsList.add(sensor);
		((Zone)this.binding.getVariable(zone)).add(sensor);
		//z.add(sensor);

		//System.out.println(name);

	}

	public void createLawPolynomial(String name,ArrayList<Double> list){
		Behavior law = new LawPolynomial(list);
		law.setName(name);
		this.binding.setVariable(name,law);		

		//System.out.println(law.generateData(5));
	}

	public void createLawRandom(String name){
		Behavior law = new LawRandom();
		law.setName(name);
		this.binding.setVariable(name,law);
	}

	public void setTime(int ticks){
		this.binding.setVariable("time",ticks);
	}

	public void setDuration(int value){
		this.binding.setVariable("duration",value);
	}
		
	/*public void createZone(String name, int nbRandom, int nbNeperien) throws IOException{
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
	}*/

	public void createZone(String name, int nbSensor,String behavior) {
		Zone zone = new Zone(name);

		for(int i=0;i<nbSensor;i++){
			Sensor s = new Sensor("sensor"+name+i,(Behavior)this.binding.getVariable(behavior));
			zone.add(s);
		}


		zonesList.add(zone);
		this.binding.setVariable(name,zone);

	}

	public void addSensorToZone(int nbSensor,String behavior,String zone){
		int nbInZone = ((Zone)this.binding.getVariable(zone)).getSensors().size();
		Behavior b = (Behavior)this.binding.getVariable(behavior);
		for(int i=0;i<nbSensor;i++){
			Sensor s = new Sensor("sensor"+zone+(nbInZone+i),b);
			((Zone)this.binding.getVariable(zone)).add(s);
		}
	}

	public void writeType(String type){
		this.binding.setVariable("writeType",type);
	}


	public void run(String name) throws IOException{

		Simulation simu = new Simulation(name);
		simu.setZones(zonesList);

		String type = (String)this.binding.getVariable("writeType");
		if(type=="csv"){
			simu.execCSV((int)this.binding.getVariable("time"));
		} else if(type=="influxDB") {
			simu.execDB((int)this.binding.getVariable("time"));
		} else {
			simu.exec((int)this.binding.getVariable("time"));
		}
		

	}



    
}
