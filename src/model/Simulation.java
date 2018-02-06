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

public class Simulation implements NamedElement{

	private String name;	
	List<Zone> zones;

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
	    }
	    writer.close();
    }

    public void execDB(int totaltime) throws IOException{
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
	    }
	    writer.close();
    }
	

	
}
