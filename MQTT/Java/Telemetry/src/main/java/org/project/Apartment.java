package org.project;

import java.util.HashMap;
import java.util.Map;

public class Apartment {

    private final String apt_No;
    private Map <String ,Double> telemetry_stats;

    public Apartment(String apt_No) {
        this.apt_No = apt_No;
        this.telemetry_stats = new HashMap<>();
    }

    public String getApt_No() {
        return apt_No;
    }

    public double get_stat(String sensor_name){
        return this.telemetry_stats.get(sensor_name);
    }

    public void update_stat(String sensor_name,double value){
        this.telemetry_stats.put(sensor_name,value);
    }
}
