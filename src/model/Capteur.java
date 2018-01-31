package model;


import java.util.ArrayList;
import java.util.List;

public abstract class Capteur {

	private String name;	
	private String type;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getType(){
		return type;
	}

	public void setType(String type){
		this.type=type;
	}



	public abstract double law(int i);

	
}
