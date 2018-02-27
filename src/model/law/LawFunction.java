package model.law;

import java.lang.Math;
import groovy.lang.Closure;

public class LawFunction extends Law{


	Closure c;

	public LawFunction(Closure c){
		this.c=c;
	}


    @Override
	public String generateData(int value){		
		return (c.call(value))+"";
	}
	
}
