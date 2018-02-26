package model.law;

import java.lang.Math;

public class LawRandom extends Law{


	int n;
	int m;

	public LawRandom(int n,int m){
		this.n=n;
		this.m=m;
	}


    @Override
	public String generateData(int value){		
		return (n+(m-n)*Math.random())+"";
	}
	
}
