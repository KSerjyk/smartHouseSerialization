package smartHouse;

import smartHouse.Instrumnets.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SmartHouse implements Serializable {
    public List<Instrument> instruments = new ArrayList<>();

    public SmartHouse() {
        AirConditioning airConditioning = new AirConditioning();
        airConditioning.setSpeed(5);
        airConditioning.setTemperature(5);
        instruments.add(airConditioning);
        instruments.add(new Alarm());
        instruments.add(new WashingMachine());
        instruments.add(new CoffeMaker());
    }
}
