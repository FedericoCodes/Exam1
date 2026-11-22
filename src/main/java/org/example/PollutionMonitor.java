package org.example;

import HES.Systems.EnvironmentalSystem;

public class PollutionMonitor extends Monitor{
    private final int wardID;
    private final float idealPollution;
    private float current;
    public PollutionMonitor(String name, int wardID, float idealPollution, EnvironmentalSystem system) {
        super(name, system);          // passes name and system up to Monitor
        this.wardID = wardID;
        this.idealPollution = idealPollution;
    }
    @Override
    public void update() {
        current = system.getPollutionFeed().getPollution();
        if (current > idealPollution && !system.isAirPurifierOn()){
            system.turnAirPurifierOn(true);
        } else if ((current < idealPollution && system.isAirPurifierOn())) {
            system.turnAirPurifierOn(false);
        }
    }

    @Override
    public boolean isOutOfRange() {
        if (system.getPollutionFeed().getPollution()>idealPollution){
            return true;}
        return false;
    }

    @Override
    public String getText() {
        return "";
    }
}
