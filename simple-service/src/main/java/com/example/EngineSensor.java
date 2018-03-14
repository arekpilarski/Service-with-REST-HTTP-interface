package com.example;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * A class that imitates the state of an engine's sensor.
 */
@XmlRootElement
public class EngineSensor {

    private String id;
    private String engine;
    private String type;
    private String name;
    private String master_sensor_id;
    private int value;
    private int min_value;
    private int max_value;

    public String getId() {
        return id;
    }

    public String getEngine() {
        return engine;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMin_value() {
        return min_value;
    }

    public int getMax_value() {
        return max_value;
    }

    public String getMaster_sensor_id() {
        return master_sensor_id;
    }


}
