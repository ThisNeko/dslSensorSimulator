package model.law;

import java.lang.Math;

public class LawRandom extends Law{


    @Override
	public String generateData(int value){		
		return Math.random()+"";
	}
	
}
