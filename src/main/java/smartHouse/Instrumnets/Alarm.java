package smartHouse.Instrumnets;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Calendar;

public class Alarm extends Instrument {
    private Calendar curTime = Calendar.getInstance();
    private String alarmTime;
    private int countRepeats;

    public Alarm(){
        setInstrumentName("Alarm");
        setWorking(true);
    }

    @Override
    public void setWorking(boolean working) {
        if(working)
            setElectricityLevel(0.03);
        super.setWorking(working);
    }

    public Calendar getCurTime() {
        return curTime;
    }

    public void setCurTime(Calendar curTime) {
        this.curTime = curTime;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public int getCountRepeats() {
        return countRepeats;
    }

    public void setCountRepeats(int countRepeats) {
        this.countRepeats = countRepeats;
    }

    @Override
    public VBox buildPane() {
        VBox vBox = new VBox();
        JFXTimePicker timePicker = new JFXTimePicker();
        JFXButton commit = new JFXButton("Підтвердити");
        Label countRep = new Label("Кількість повторів");
        Label alarm = new Label("Будильник");
        Spinner<Integer> countRepSpinner = new Spinner<>();
        countRepSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10, getCountRepeats()));
        commit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCountRepeats(countRepSpinner.getValue());
                setAlarmTime(timePicker.toString());
                Stage stage = (Stage) commit.getScene().getWindow();
                stage.close();
            }
        });
        vBox.getChildren().addAll(alarm, timePicker, countRep, countRepSpinner, commit);
        return vBox;
    }
}
