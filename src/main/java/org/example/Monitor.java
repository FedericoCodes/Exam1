package org.example;
import HES.Systems.EnvironmentalSystem;
public abstract class Monitor {
    private final String name;
    protected final EnvironmentalSystem system;

    public Monitor(String name, EnvironmentalSystem system) {
        this.name = name;
        this.system = system;
    }

    public String getName() {
        return name;
    }

    public abstract void update();            // read the sensor, switch the control
    public abstract boolean isOutOfRange();   // true means the cell turns red
    public abstract String getText();         // the text shown in the cell
}