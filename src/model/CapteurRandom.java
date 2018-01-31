package model;



import java.lang.Math;

public class CapteurRandom extends Capteur {	

    @Override
	public double law(int i){
		return Math.random();
	}

	
}
