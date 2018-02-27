package model;


import java.util.ArrayList;
import java.util.List;
import utils.NamedElement;

public class Zone implements NamedElement{

	private String name;	
	List<Sensor> sensors;
	String type = "";

	public Zone(String name){
		this.name=name;
		sensors=new ArrayList<Sensor>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setAggregateType(String truc){
		this.type=truc;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> capteurs) {
		this.sensors = sensors;
	}

	public void add(Sensor s){
		this.sensors.add(s);
	}

	public String runAsAggregate(int t){
		switch(this.type) {
			case "max":
				double max = 0;
				for(int i=0;i<sensors.size();i++){
					max=Math.max(Double.parseDouble(sensors.get(i).getValue(t)),max);
		        }
		        return max+"";
            case "min":
                double min = 0;
				for(int i=0;i<sensors.size();i++){
					min=Math.min(Double.parseDouble(sensors.get(i).getValue(t)),min);
		        }
		        return min+"";
		    case "mean":
                double mean = 0;
                int count = 0;
				for(int i=0;i<sensors.size();i++){
					mean+=Double.parseDouble(sensors.get(i).getValue(t));
					count++;
		        }
		        return (mean/(count*1.0))+"";
            default:
                double sum = 0;
		        for(int i=0;i<sensors.size();i++){
			        sum+=Double.parseDouble(sensors.get(i).getValue(t));
		        }
		        return sum+"";       
        }
    }

	
}
