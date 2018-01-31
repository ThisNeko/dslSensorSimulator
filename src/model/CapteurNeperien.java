package model;

import java.lang.Math;

public class CapteurNeperien extends Capteur {	

    @Override
	public double law(int i){
		if(i==0)
			return 0.0;
		else
			return Math.log(i);
	}

	
}
