package model;



import java.lang.Math;
import utils.Tuple3;
import utils.NamedElement;

public abstract class Behavior implements NamedElement{

	private String name;
	private double noise = 2.0;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public double getNoise(){
		return noise;
	}

	public void setNoise(double noise){
		this.noise=noise;
	}

   public abstract String generateData(int i);
	
}
