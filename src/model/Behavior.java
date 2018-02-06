package model;



import java.lang.Math;
import utils.Tuple3;
import utils.NamedElement;

public abstract class Behavior implements NamedElement{

	private String name;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

   public abstract String generateData(int i);
	
}
