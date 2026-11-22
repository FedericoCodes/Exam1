package org.example;

import HES.Systems.EnvironmentalSystem;

public class HumidityMonitor extends Monitor {
    private final int wardID;
    private final float idealHumid;
    private float current;

    public HumidityMonitor(String name, int wardID, float idealHumid, EnvironmentalSystem system) {
        super(name, system);          // passes name and system up to Monitor
        this.wardID = wardID;
        this.idealHumid = idealHumid;
    }


    @Override
    public void update() {
        current = system.getHumidityFeed().getInsideHumidity();
        if (Math.abs((current - system.getHumidityFeed().getOutsideHumidity())/current)>0.1){
            system.turnDehumidifierOn(true);
        }

    }

    @Override
    public boolean isOutOfRange() {
        if (Math.abs((current - system.getHumidityFeed().getOutsideHumidity()) / current) > 0.1) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "";
    }
}
