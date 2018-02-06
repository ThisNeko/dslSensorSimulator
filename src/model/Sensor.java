package model;


import java.util.ArrayList;
import java.util.List;
import utils.NamedElement;

public class Sensor implements NamedElement{

	private String name;	
	private Behavior b;

	public Sensor(String name, Behavior b){
		this.name=name;
		this.b=b;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getValue(int t){
		return b.generateData(t);
	}

}	

	