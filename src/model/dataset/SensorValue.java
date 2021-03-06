package model.dataset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class SensorValue{

    private String name;
    private Long time;
    private Map<String, Object> measures;
    private static Logger LOGGER = Logger.getLogger(SensorValue.class.getName());

    public SensorValue(String name, Long time){
        this.name = name;
        this.time = time;
        this.measures = new HashMap<>();
    }

    public Long time(){return this.time;}
    public String sensorName(){return this.name;}
    public Optional<Integer> getInt(String key) {return getType(key, Integer.class);}
    public Optional<Double> getDouble(String key) {return getType(key, Double.class);}
    public Optional<Boolean> getBoolean(String key) {return getType(key, Boolean.class);}
    public Optional<String> getString(String key) {return getType(key, String.class);}

    public <T> Optional<T> getType(String key, Class<T> cls) {
        try {
            return Optional.of(cls.cast( this.measures.get(key)));
        } catch (ClassCastException e) {
            LOGGER.warning(e.getMessage());
            return Optional.empty();
        }
    }

    protected void putValue(String key, Object o){
        this.measures.putIfAbsent(key, o);
    }
}