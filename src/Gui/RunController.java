package Gui;

import Controller.Controller;
import Model.ProgramState;
import Model.Statements.Statement;
import Model.Values.Value;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RunController {

    @FXML
    private TextField noThreadsField;
    @FXML
    private Button oneStepButton;
    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTable;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapAddressColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapValueColumn;
    @FXML
    private ListView<Value> outList;
    @FXML
    private ListView fileList;
    @FXML
    private ListView<String> programStatesList;
    @FXML
    private TableView<Map.Entry<String, Value>> symbolsTable;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symbolsTableNameColumn;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symbolsTableValueColumn;
    @FXML
    private ListView<Statement> executionStackList;

    private Controller controller;

    public void initialize(Controller c){
        this.controller = c;
        heapAddressColumn.setCellValueFactory(elem -> new SimpleStringProperty(elem.getValue().getKey().toString()));
        heapValueColumn.setCellValueFactory(elem -> new SimpleStringProperty(elem.getValue().getValue().toString()));
        symbolsTableNameColumn.setCellValueFactory(elem -> new SimpleStringProperty(elem.getValue().getKey()));
        symbolsTableValueColumn.setCellValueFactory(elem -> new SimpleStringProperty(elem.getValue().getValue().toString()));
        RunController runControllerReference = this;
        this.programStatesList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                runControllerReference.programSelectionChanged();
            }
        });
        this.update();
    }

    @FXML
    public void buttonHandler(ActionEvent actionEvent){
        this.oneStepForAllPrograms();
        this.update();
    }

    public void programSelectionChanged(){
        this.refreshSymbolTable();
        this.refreshExecutionStack();
    }

    private void oneStepForAllPrograms() {
        List<ProgramState> programsList = Controller.removeCompletedPrograms(this.controller.getRepository().getProgramsList());
        if(programsList.size() > 0) {
            this.controller.oneStepForAllPrograms(programsList);
            this.controller.garbageCollector(programsList);
        }
        else{
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText("Done");
            errorAlert.setContentText("Program execution is complete");
            errorAlert.showAndWait();
        }
    }

    private void update(){
        this.noThreadsField.setText(Integer.toString(this.controller.getRepository().getProgramsList().size()));
        this.refreshHeap();
        this.refreshOut();
        this.refreshFileTable();
        this.refreshProgramStates();
        this.refreshSymbolTable();
        this.refreshExecutionStack();
    }

    private void refreshHeap(){
        this.heapTable.refresh();
        List<Map.Entry<Integer, Value>> list = new ArrayList<>(controller.getRepository().getProgramsList().get(0).getHeap().getContent().entrySet());
        heapTable.getItems().clear();
        heapTable.getItems().addAll(list);
    }

    private void refreshOut(){
        this.outList.refresh();
        List<Value> list = new ArrayList<Value>(controller.getRepository().getProgramsList().get(0).getOutput().getContent());
        this.outList.setItems(FXCollections.observableArrayList(list));
    }

    private void refreshFileTable(){
        this.fileList.refresh();
        List<Value> list = new ArrayList<>(controller.getRepository().getProgramsList().get(0).getFilesTable().getContent().keySet());
        this.fileList.setItems(FXCollections.observableArrayList(list));
    }

    private void refreshProgramStates(){
        this.programStatesList.refresh();
        List<String> list = controller.getRepository().getProgramsList().stream()
                .map(state -> Integer.toString(state.getId()))
                .collect(Collectors.toList());
        this.programStatesList.setItems(FXCollections.observableArrayList(list));
    }

    private void refreshSymbolTable(){
        int idx = programStatesList.getSelectionModel().getSelectedIndex();
        if(idx == -1)
            idx = 0;
        if(idx >= 0) {
            this.symbolsTable.refresh();
            List<Map.Entry<String, Value>> list = new ArrayList<Map.Entry<String, Value>>(controller.getRepository().getProgramsList().get(idx).getSymbolsTable().getContent().entrySet());
            this.symbolsTable.setItems(FXCollections.observableArrayList(list));
        }
    }

    private void refreshExecutionStack(){
        int idx = programStatesList.getSelectionModel().getSelectedIndex();
        if(idx == -1)
            idx = 0;
        if(idx >= 0) {
            this.executionStackList.refresh();
            List<Statement> exStackList = controller.getRepository().getProgramsList().get(idx).getExecutionStack().getContent();
            Collections.reverse(exStackList);
            this.executionStackList.setItems(FXCollections.observableArrayList(exStackList));
        }
    }

}
