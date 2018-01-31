package model;


import java.util.ArrayList;
import java.util.List;

public class Simulation {

	private String name;	
	List<Zone> zones;	
	
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
	

	
}
