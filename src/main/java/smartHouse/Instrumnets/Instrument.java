package smartHouse.Instrumnets;

import javafx.scene.layout.VBox;

import java.io.Serializable;
public abstract class Instrument implements Serializable {

    private double electricityLevel;
    private boolean isWorking;
    private String instrumentName;

    public double getElectricityLevel() {
        return electricityLevel;
    }

    public void setElectricityLevel(double electricityLevel) {
        this.electricityLevel = electricityLevel;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        if(!working)
            setElectricityLevel(0);
        isWorking = working;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public abstract VBox buildPane();
}
