package model;


import java.util.ArrayList;
import java.util.List;
import utils.NamedElement;

public class Simulation implements NamedElement{

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

	public void exec(int totaltime){
		for(int t=0;t<totaltime;t++){
			for(int i =0;i<zones.size();i++){
				int size = zones.size();
				for(int j=0;j<size;j++){
					String value = zones.get(i).getSensors().get(j).getValue(t);
					String nameSensor =  zones.get(i).getSensors().get(j).getName();
					System.out.println(t+","+nameSensor+","+value);
				}
		    }
	    }
    }
	

	
}
