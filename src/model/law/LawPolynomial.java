package model.law;



import java.lang.Math;
import utils.Tuple3;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class LawPolynomial extends Law{

	private ArrayList<Double> xs = new ArrayList<Double>();



	public LawPolynomial( ArrayList<Double> list){
		this.xs=list;		
	}

	ArrayList<Double> getPoly(){
		return xs;
	}

	void setPoly(ArrayList<Double> xs){
		this.xs=xs;
	}

    @Override
	public String generateData(int value){
		Double sum = this.xs.get(0);
		


		for(int i= 1;i<this.xs.size();i++){
			sum+=this.xs.get(i)*Math.pow(value,i);
		}

		return sum+"";
		//return "";
	}
	
}
