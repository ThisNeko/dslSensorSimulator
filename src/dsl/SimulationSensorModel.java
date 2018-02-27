package dsl;

import java.util.*;
import model.dataset.*;
import groovy.lang.Binding;
import model.*;
import model.law.*;
import model.law.LawRandom;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import groovy.lang.Closure;

import java.util.concurrent.TimeUnit;




public class SimulationSensorModel {
	
	
	private Binding binding;
	private List<Sensor> sensorsList;
	private List<Zone> zonesList;
	private List<Zone> aggregateZones;
	private Zone z;

	public SimulationSensorModel(Binding binding) {		
		this.binding = binding;
		this.sensorsList=new ArrayList<Sensor>();
		this.zonesList=new ArrayList<Zone>();
		this.aggregateZones=new ArrayList<Zone>();
		this.z = new Zone("noName");
		this.binding.setVariable("noName",z);
		zonesList.add(z);
		
		this.binding.setVariable("writeType","terminal");	

	
	}

	public void createSensor(String name, String behavior, String zone) {

		//Sensor sensor = new Sensor(name,new LawRandom());	
		Sensor sensor = new Sensor(name,(Behavior)this.binding.getVariable(behavior));	

		if(!this.binding.hasVariable(zone)){
			createZone(zone);
		}		
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

	public void createLawRandom(String name,int n, int m){
		Behavior law = new LawRandom(n,m);
		law.setName(name);
		this.binding.setVariable(name,law);
	}

	public void setTime(int ticks){
		this.binding.setVariable("time",ticks);
	}

	public void setDuration(int value){
		this.binding.setVariable("duration",value);
	}
			
	public void createZone(String name, int nbSensor,String behavior) {
		Zone zone = new Zone(name);

		for(int i=0;i<nbSensor;i++){
			Sensor s = new Sensor("sensor"+name+i,(Behavior)this.binding.getVariable(behavior));
			zone.add(s);
		}


		zonesList.add(zone);
		this.binding.setVariable(name,zone);

	}

	public void createZone(String name) {
		Zone zone = new Zone(name);	


		zonesList.add(zone);
		this.binding.setVariable(name,zone);

	}

	public void addSensorToZone(int nbSensor,String behavior,String zone){
		if(!this.binding.hasVariable(zone)){
			createZone(zone);
		}

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
		simu.setAggregates(aggregateZones);

		String type = (String)this.binding.getVariable("writeType");
		if(type=="csv"){
			simu.execCSV((int)this.binding.getVariable("time"));
		} else if(type=="influxDB") {
			simu.execDB((int)this.binding.getVariable("time"));
		} else {
			simu.exec((int)this.binding.getVariable("time"));
		}
		

	}

	public void aggregateZone(String zone,String type){
		if(this.binding.hasVariable(zone)){
			aggregateZones.add((Zone)this.binding.getVariable(zone));
			((Zone)this.binding.getVariable(zone)).setAggregateType(type);
		}
	}

	public  void Reapplicate(String sensor,String in,String out) throws IOException, InterruptedException{
		SimpleCSVParser par = new  SimpleCSVParser(sensor,in,out);
						par.run();
	}


	public void createLawFunction(String name,Closure c){
		Behavior law = new LawFunction(c);
		law.setName(name);
		this.binding.setVariable(name,law);
	}



    
}
