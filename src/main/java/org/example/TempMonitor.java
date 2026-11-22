package org.example;
import HES.Feeds.TemperatureFeed;
import HES.Systems.EnvironmentalSystem;

public class TempMonitor extends Monitor{
    private final int wardID;
    private final float idealTemp;
    private float current;

    public TempMonitor(String name, int wardID, float idealTemp, EnvironmentalSystem system) {
        super(name, system);          // passes name and system up to Monitor
        this.wardID = wardID;
        this.idealTemp = idealTemp;
    }
    
    @Override
    public void update() {
        current = system.getTempFeed().getTemperature(wardID);
        if (current > idealTemp && system.isHeatingOn(wardID)){
            system.turnHeatingOn(wardID,false);
        } else if (current < idealTemp && !system.isHeatingOn(wardID)) {
            system.turnHeatingOn(wardID, true);
        }

    }

    @Override
    public boolean isOutOfRange() {
        if ((Math.abs(system.getTempFeed().getTemperature(wardID) - idealTemp)>0.5)){
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "";
    }
}
