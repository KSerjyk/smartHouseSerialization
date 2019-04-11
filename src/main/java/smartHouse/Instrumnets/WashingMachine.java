package smartHouse.Instrumnets;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WashingMachine extends Instrument {

    private int spinSpeed;
    private double timeToWash;
    private double startDelay;
    private double powderQuantityGrams;

    public WashingMachine() {
        setInstrumentName("Washing machine");
        setWorking(false);
    }

    @Override
    public VBox buildPane() {
        VBox vBox = new VBox();
        Label speed = new Label("Швидкість");
        Label temp = new Label("Час");
        Label startDelay = new Label("Відкладення старту");
        Label powderQuantity = new Label("Кількість порошку");
        Spinner<Integer> spinnerSpeed = new Spinner<>();
        spinnerSpeed.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(800,2600,getSpinSpeed()));
        Spinner<Double> timeToWash = new Spinner<>();
        timeToWash.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(10,80,getTimeToWash()));
        Spinner<Double> startDelaySpinner = new Spinner<>();
        startDelaySpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(10,80,getStartDelay()));
        Spinner<Double> powderQuantitySpinner = new Spinner<>();
        powderQuantitySpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(10,80,getPowderQuantityGrams()));
        JFXButton okBtn = new JFXButton("Підтвердити");
        okBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY){
                    try {
                        setSpinSpeed(spinnerSpeed.getValue());
                        setTimeToWash(timeToWash.getValue());
                        setStartDelay(startDelaySpinner.getValue());
                        setPowderQuantityGrams(powderQuantitySpinner.getValue());
                        Stage st = (Stage) okBtn.getScene().getWindow();
                        st.close();
                    } catch (Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                        alert.show();
                    }
                }
            }
        });
        vBox.getChildren().addAll(speed, spinnerSpeed, temp, timeToWash, startDelay, startDelaySpinner, powderQuantity, powderQuantitySpinner, okBtn);
        return vBox;
    }

    @Override
    public void setWorking(boolean working) {
        if(working)
            setElectricityLevel(0.2);
        super.setWorking(working);
    }

    public int getSpinSpeed() {
        return spinSpeed;
    }

    public void setSpinSpeed(int spinSpeed) {
        this.spinSpeed = spinSpeed;
    }

    public double getTimeToWash() {
        return timeToWash;
    }

    public void setTimeToWash(double timeToWash) {
        this.timeToWash = timeToWash;
    }

    public double getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(double startDelay) {
        this.startDelay = startDelay;
    }

    public double getPowderQuantityGrams() {
        return powderQuantityGrams;
    }

    public void setPowderQuantityGrams(double powderQuantityGrams) {
        this.powderQuantityGrams = powderQuantityGrams;
    }
}
