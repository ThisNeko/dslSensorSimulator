package model.dataset;
import java.util.*;

public class TimeMeasure {

    private Map<Long, SensorValue> data;

    public TimeMeasure(){
        this.data = new HashMap<>();
    }


    public List<SensorValue> getMeasures(long start, long end){
        List<SensorValue> measures = new ArrayList<>();
        Iterator<Long> iterator = data.keySet().iterator();
        while(iterator.hasNext()) {
            long time = iterator.next();
            if (time >= start && time <= end) {
                measures.add(data.get(time));
            }
        }
        return measures;
    }

    public Collection<SensorValue> getMeasures(){
        return this.data.values();
    }


    public void putMeasure(SensorValue measure){
        this.data.putIfAbsent(measure.time(), measure);
    }
}