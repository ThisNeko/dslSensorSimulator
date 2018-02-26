package model;


import java.util.ArrayList;
import java.util.List;
import utils.NamedElement;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.InfluxDBIOException;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;
import org.influxdb.*;

import java.time.Duration;

public class Simulation implements NamedElement{

	private String name;	
	List<Zone> zones;
	List<Zone> aggregates;

	public Simulation(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

	public List<Zone> getAggregates() {
		return aggregates;
	}

	public void setAggregates(List<Zone> zones) {
		this.aggregates = zones;
	}



	public void exec(int totaltime){
		for(int t=0;t<totaltime;t++){
			for(int i =0;i<zones.size();i++){
				int size = zones.get(i).getSensors().size();
				for(int j=0;j<size;j++){
					String value = zones.get(i).getSensors().get(j).getValue(t);
					String nameSensor =  zones.get(i).getSensors().get(j).getName();
					System.out.println(t+","+nameSensor+","+value);
				}
		    }
		    if(this.aggregates.size()>0){
		    	for(int i=0;i<this.aggregates.size();i++){
		    		String valueZone = aggregates.get(i).runAsAggregate(t);
		    		String nameZone = aggregates.get(i).getName();
		    		System.out.println(t+","+nameZone+","+valueZone);
		    	}
		    }
	    }
    }


    public void execCSV(int totaltime) throws IOException{
    	File file = new File("result/"+this.name+".csv");
    	file.getParentFile().mkdirs();
    	PrintWriter writer = new PrintWriter(file);

		for(int t=0;t<totaltime;t++){
			for(int i =0;i<zones.size();i++){
				int size = zones.get(i).getSensors().size();
				for(int j=0;j<size;j++){
					String value = zones.get(i).getSensors().get(j).getValue(t);
					String nameSensor =  zones.get(i).getSensors().get(j).getName();
					writer.println(t+","+nameSensor+","+value);
				}
		    }
		    if(this.aggregates.size()>0){
		    	for(int i=0;i<this.aggregates.size();i++){
		    		String valueZone = aggregates.get(i).runAsAggregate(t);
		    		String nameZone = aggregates.get(i).getName();
		    		writer.println(t+","+nameZone+","+valueZone);
		    	}
		    }
	    }
	    writer.close();
    }

    public void execDB(int totaltime) throws IOException{

    	InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
    	String dbName = "database";
        influxDB.createDatabase(dbName);
        influxDB.setDatabase(dbName);
        String rpName = "aRetentionPolicy";
        //influxDB.createRetentionPolicy(rpName, dbName, "30d", "30m", 2, true);
        //influxDB.setRetentionPolicy(rpName);
        BatchPoints batchPoints = BatchPoints
        .database(dbName)
        //.retentionPolicy(rpName)
        .build();
        influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);
    	//BatchPoints batchPoints;

    	double currentTimeMillis = System.currentTimeMillis();


		for(int t=0;t<totaltime;t++){
			for(int i =0;i<zones.size();i++){
				int size = zones.get(i).getSensors().size();
				for(int j=0;j<size;j++){
					String value = zones.get(i).getSensors().get(j).getValue(t);
					String nameSensor =  zones.get(i).getSensors().get(j).getName();
					//writer.println(t+","+nameSensor+","+value);
					batchPoints.point(Point.measurement(nameSensor)
						.time(System.currentTimeMillis()+ (t)*1000*60*60, TimeUnit.MILLISECONDS)
						//.addField("time", t)
						//.addField("sensor", nameSensor)
						.addField("value", value)
						.build());
					/*influxDB.write(Point.measurement(nameSensor)
						.time(t+1, TimeUnit.MILLISECONDS)
						.addField("value", value)
						.build());*/

				}
		    }
		    if(this.aggregates.size()>0){
		    	for(int i=0;i<this.aggregates.size();i++){
		    		String valueZone = aggregates.get(i).runAsAggregate(t);
		    		String nameZone = aggregates.get(i).getName();
		    		batchPoints.point(Point.measurement(nameZone)
						.time(System.currentTimeMillis()+ (t)*1000*60*60, TimeUnit.MILLISECONDS)
						.addField("value", valueZone)
						.build());
		    	}
		    }
	    }

	    influxDB.write(batchPoints);
	    System.out.println("end");

	    
    }
	

	
}
