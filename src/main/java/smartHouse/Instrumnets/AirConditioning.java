package smartHouse.Instrumnets;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AirConditioning extends Instrument {

    private int speed;
    private int temperature;

    public AirConditioning() {
        setInstrumentName("Air conditioning");
    }

    @Override
    public void setWorking(boolean working) {
        if(working)
            setElectricityLevel(0.3);
        super.setWorking(working);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public VBox buildPane(){
        VBox vBox = new VBox();
        Label speed = new Label("Швидкість");
        Label temp = new Label("Температура");
        Spinner<Integer> spinnerSpeed = new Spinner<>();
        spinnerSpeed.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,getSpeed()));
        spinnerSpeed.getValueFactory().setValue(this.speed);
        Spinner<Integer> spinnerTemperature = new Spinner<>();
        spinnerTemperature.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10,30,getTemperature()));
        spinnerTemperature.getValueFactory().setValue(this.temperature);
        JFXButton okBtn = new JFXButton("Підтвердити");
        okBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY){
                    try {
                        setSpeed(spinnerSpeed.getValue());
                        setTemperature(spinnerTemperature.getValue());
                        Stage st = (Stage) okBtn.getScene().getWindow();
                        st.close();
                    } catch (Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                        alert.show();
                    }
                }
            }
        });
        vBox.getChildren().addAll(speed, spinnerSpeed, temp, spinnerTemperature, okBtn);
        return vBox;
    }
}
