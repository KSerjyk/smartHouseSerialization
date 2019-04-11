package smartHouse.Instrumnets;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CoffeMaker extends Instrument {

    private double milkLevel;
    private double waterLevel;
    private double coffeeLevel;

    public CoffeMaker() {
        setInstrumentName("Coffee machine");
    }

    public double getMilkLevel() {
        return milkLevel;
    }

    public void setMilkLevel(double milkLevel) {
        this.milkLevel = milkLevel;
    }

    public double getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(double waterLevel) {
        this.waterLevel = waterLevel;
    }

    public double getCoffeeLevel() {
        return coffeeLevel;
    }

    public void setCoffeeLevel(double coffeeLevel) {
        this.coffeeLevel = coffeeLevel;
    }

    public void makeEspresso(){
        if(this.coffeeLevel >= 0.1 && this.waterLevel >= 0.3){
            this.coffeeLevel -= 0.1;
            this.waterLevel -= 0.3;
        }
    }

    public void makeDoppio(){
        if(this.coffeeLevel >= 0.2 && this.waterLevel >= 0.3){
            this.coffeeLevel -= 0.2;
            this.waterLevel -= 0.3;
        }
    }

    public void makeAmerykano(){
        if(this.coffeeLevel >= 0.1 && this.waterLevel >= 0.5){
            this.coffeeLevel -= 0.1;
            this.waterLevel -= 0.5;
        }
    }

    public void makeLatte(){
        if(this.coffeeLevel >= 0.1 && this.waterLevel >= 0.2 && this.milkLevel >= 0.3) {
            this.coffeeLevel -= 0.1;
            this.waterLevel -= 0.2;
            this.milkLevel -= 0.3;
        }
    }

    public void makeAmerykanoWithMilk(){
        if(this.coffeeLevel >= 0.1 && this.waterLevel >= 0.5 && this.milkLevel >= 0.2) {
            this.coffeeLevel -= 0.1;
            this.waterLevel -= 0.5;
            this.milkLevel -= 0.2;
        }
    }

    public void makeEspressoWithMilk(){
        if(this.coffeeLevel >= 0.1 && this.waterLevel >= 0.3 && this.milkLevel >= 0.1) {
            this.coffeeLevel -= 0.1;
            this.waterLevel -= 0.3;
            this.milkLevel -= 0.1;
        }
    }

    @Override
    public void setWorking(boolean working) {
        if(working)
            setElectricityLevel(0.05);
        super.setWorking(working);
    }

    @Override
    public VBox buildPane() {
        VBox vBox = new VBox();
        Spinner<Double> milkLevel = new Spinner<>();
        milkLevel.setEditable(true);
        milkLevel.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, getMilkLevel()));
        Spinner<Double> waterLevel = new Spinner<>();
        waterLevel.setEditable(true);
        waterLevel.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, getWaterLevel()));
        Spinner<Double> coffeeLevel = new Spinner<>();
        coffeeLevel.setEditable(true);
        coffeeLevel.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, getCoffeeLevel()));
        Label milk = new Label("Рівень молока");
        Label water = new Label("Рівень води");
        Label coffee = new Label("Рівень кави");
        Label coffeeToMake = new Label("Яку каву зробити?");
        JFXComboBox comboBox = new JFXComboBox();
        comboBox.getItems().addAll("-","Еспресо", "Допіо", "Американо", "Латте", "Еспрессо з молоком", "Американо з молоком");
        comboBox.getSelectionModel().selectFirst();
        JFXButton commit = new JFXButton("Підтвердити");
        commit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY){
                    try {
                        setCoffeeLevel(coffeeLevel.getValue());
                        setMilkLevel(milkLevel.getValue());
                        setCoffeeLevel(coffeeLevel.getValue());
                        switch(comboBox.getSelectionModel().getSelectedIndex()){
                            case 1:
                                makeEspresso();
                                break;
                            case 2:
                                makeDoppio();
                                break;
                            case 3:
                                makeAmerykano();
                                break;
                            case 4:
                                makeLatte();
                                break;
                            case 5:
                                makeEspressoWithMilk();
                                break;
                            case 6:
                                makeAmerykanoWithMilk();
                                break;
                        }
                        Stage stage = (Stage) commit.getScene().getWindow();
                        stage.close();
                    }catch (Exception e){

                    }
                }
            }
        });
        vBox.getChildren().addAll(water, waterLevel, milk, milkLevel, coffee, coffeeLevel, coffeeToMake, comboBox, commit);
        return vBox;
    }
}
