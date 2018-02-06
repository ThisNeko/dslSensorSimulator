package model;


import java.util.ArrayList;
import java.util.List;
import utils.NamedElement;

public class Zone implements NamedElement{

	private String name;	
	List<Sensor> sensors;

	public Zone(String name){
		this.name=name;
		sensors=new ArrayList<Sensor>();
	}	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> capteurs) {
		this.sensors = sensors;
	}

	public void add(Sensor s){
		this.sensors.add(s);
	}

	
}
