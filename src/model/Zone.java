package model;


import java.util.ArrayList;
import java.util.List;

public class Zone {

	private String name;	
	List<Capteur> capteurs;	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Capteur> getCapteurs() {
		return capteurs;
	}

	public void setCapteurs(List<Capteur> capteurs) {
		this.capteurs = capteurs;
	}

	
}
