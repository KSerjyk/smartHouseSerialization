package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import smartHouse.SerializationClass;
import smartHouse.SmartHouse;
import smartHouse.Instrumnets.Instrument;

public class MainController {

    private Stage secondaryStage;
    private SmartHouse house = new SmartHouse();
    private ObservableList<Instrument> observableList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXCheckBox electricityCheckBox;

    @FXML
    private JFXButton statisticBtn;

    @FXML
    void showStatistic(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY)
            showTable();
    }

    @FXML
    void initialize() {
        electricityCheckBox.setSelected(true);
        electricityCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!electricityCheckBox.isSelected()) {
                    statisticBtn.setDisable(true);
                    saveChanges();
                    SerializationClass.serialize(house);
                    try {
                        secondaryStage.close();
                    }catch (Exception e){}
                }
                else {
                    house = SerializationClass.deSerialize();
                    statisticBtn.setDisable(false);
                }            }
        });
    }

    private void showTable(){
        final TableView<Instrument> table = new TableView<>();
        table.setEditable(true);

        TableColumn<Instrument, String> instrumentName = new TableColumn<>("Назва приладу");
        instrumentName.setCellValueFactory(new PropertyValueFactory<>("instrumentName"));
        //region instrumentName setTextField
        instrumentName.setCellFactory(TextFieldTableCell.forTableColumn());
        instrumentName.setOnEditCommit((TableColumn.CellEditEvent<Instrument, String> event) -> {
            TablePosition<Instrument, String> pos = event.getTablePosition();

            String newVal = event.getNewValue();

            Instrument instrument = event.getTableView().getItems().get(pos.getRow());
            instrument.setInstrumentName(newVal);
        });
        //endregion

        TableColumn<Instrument, Boolean> instrumentState = new TableColumn<>("Працює?");
        //region instrumentState setParamChange + checkbox
        instrumentState.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Instrument, Boolean>, ObservableValue<Boolean>>() {
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Instrument, Boolean> param) {
                final Instrument instrument = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(instrument.isWorking());
                booleanProp.addListener(new ChangeListener<Boolean>() {


                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        instrument.setWorking(newValue);
                        table.refresh();
                        //System.out.println(instrument.getInstrumentName() + " - " + instrument.isWorking());
                    }
                });
                return booleanProp;
            }
        });
        instrumentState.setCellFactory(new Callback<TableColumn<Instrument, Boolean>, TableCell<Instrument, Boolean>>() {
            public TableCell<Instrument, Boolean> call(TableColumn<Instrument, Boolean> param) {
                CheckBoxTableCell<Instrument,Boolean> cell = new CheckBoxTableCell<Instrument, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        //endregion

        TableColumn<Instrument, Double> instrumentElectricityConsumption = new TableColumn<>("Споживання кВт/год");
        instrumentElectricityConsumption.setCellValueFactory(new PropertyValueFactory<>("electricityLevel"));

        TableColumn instrumentEdit = new TableColumn("Налаштування приладу");
        instrumentEdit.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                TableCell tableCell = new TableCell();

                tableCell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                            try {
                                Instrument ins = (Instrument)(param.getTableView().getItems().get(((TableCell) event.getSource()).getIndex()));
                                VBox root = ins.buildPane();
                                Scene scene = new Scene(root);
                                Stage secondStage = new Stage();
                                Stage mainStage = (Stage) tableCell.getScene().getWindow();
                                secondStage.setScene(scene);
                                secondStage.initOwner(mainStage);
                                secondStage.initModality(Modality.WINDOW_MODAL);
                                secondStage.setWidth(400);
                                secondStage.setHeight(500);
                                secondStage.setTitle("Change "+ins.getInstrumentName()+" params");
                                secondStage.show();
                            }catch (Exception e){

                            }
                        }
                    }
                });

                return tableCell;
            }


        });

        table.getColumns().addAll(instrumentName, instrumentState, instrumentElectricityConsumption, instrumentEdit);
        observableList.clear();
        observableList.addAll(house.instruments);
        table.setItems(observableList);

        //region adding table to new stage and oppening it
        StackPane root = new StackPane();
        root.getChildren().add(table);

        Stage mainStage = (Stage) statisticBtn.getScene().getWindow();
        secondaryStage = new Stage();
        secondaryStage.initOwner(mainStage);
        secondaryStage.setTitle("Devices");
        Scene scene = new Scene(root, 450, 300);
        secondaryStage.setScene(scene);
        secondaryStage.show();
        //endregion
    }

    private void saveChanges(){
        house.instruments.clear();
        house.instruments.addAll(observableList);
    }
}
