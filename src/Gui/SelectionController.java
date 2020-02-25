package Gui;

import Controller.Controller;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyHeap;
import Model.ADTs.MyList;
import Model.ADTs.MyStack;
import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.ProgramState;
import Model.Statements.*;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.Repository;
import Repository.RepositoryInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectionController {

    @FXML
    private Button runButton;
    @FXML
    private ListView<String> programsList;
    private List<Statement> programs;
    private RunController runController;
    private boolean doTypeChecking = true;

    public SelectionController(){

        //  Example1: int v; v=2;Print(v)
        Statement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(
                new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));

        // Example2: int a;int b; a=2+3*5;b=a+1;Print(b)
        Statement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()), new CompoundStatement(new VariableDeclarationStatement(
                "b",new IntType()), new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),
                new ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))), new CompoundStatement(
                new AssignStatement("b", new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                new PrintStatement(new VariableExpression("b"))))));

        // Example3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()), new CompoundStatement(new VariableDeclarationStatement("v",
                new IntType()), new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))), new CompoundStatement(new IfStatement(
                new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(
                new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        // Example4: string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
        Statement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf",new StringType()),new CompoundStatement(new AssignStatement("varf",new ValueExpression(
                new StringValue("test.in"))),new CompoundStatement(new OpenReadFileStatement(new VariableExpression("varf")),new CompoundStatement(new
                VariableDeclarationStatement("varc",new IntType()),new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),new
                CompoundStatement(new PrintStatement(new VariableExpression("varc")),new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),
                "varc") ,new CompoundStatement(new PrintStatement(new VariableExpression("varc")),new CloseReadFileStatement(new VariableExpression("varf"))))))))));

        // Example5: int a; int b; a=2; b=3;(If a>b Then Print(a) Else Print(b))
        Statement ex5 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new VariableDeclarationStatement("b",
                new IntType()), new CompoundStatement(new AssignStatement("a", new ValueExpression(new IntValue(2))), new CompoundStatement(new AssignStatement("b",
                new ValueExpression(new IntValue(3))), new IfStatement(new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"),
                ">"), new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b")))))));

        // Example6: int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        Statement ex6 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new
                IntValue(4))), new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)),
                ">"), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression('-',
                new VariableExpression("v"), new ValueExpression(new IntValue(1)))))), new PrintStatement(new VariableExpression("v")))));

        // Example7: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        Statement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(new HeapAllocationStatement(
                "v", new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(
                new IntType()))), new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")), new CompoundStatement(new PrintStatement(
                new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));

        // Example8: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        Statement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(new HeapAllocationStatement(
                "v", new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(
                new IntType()))), new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")), new CompoundStatement(new PrintStatement(
                new HeapReadExpression(new VariableExpression("v"))), new PrintStatement(new ArithmeticExpression('+', new HeapReadExpression(
                new HeapReadExpression(new VariableExpression("a"))) , new ValueExpression(new IntValue(5)))))))));

        // Example9: Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        Statement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(new HeapAllocationStatement(
                "v", new ValueExpression(new IntValue(20))), new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                new CompoundStatement(new HeapWriteStatement("v", new ValueExpression(new IntValue(30))), new PrintStatement(new ArithmeticExpression('+',
                        new HeapReadExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));

        // Example10: Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)));
        Statement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(new HeapAllocationStatement(
                "v", new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(
                new IntType()))), new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")), new CompoundStatement(
                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))), new PrintStatement(new HeapReadExpression(
                new HeapReadExpression(new VariableExpression("a")))))))));

        // Example11: int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
        Statement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new VariableDeclarationStatement("a",
                new ReferenceType(new IntType())), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))), new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))), new CompoundStatement(
                new ForkStatement(new CompoundStatement(new HeapWriteStatement("a", new ValueExpression(new IntValue(30))), new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(32))), new CompoundStatement(new PrintStatement(new HeapReadExpression(
                        new VariableExpression("a"))), new PrintStatement(new VariableExpression("v")))
                ))), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadExpression(
                new VariableExpression("a")))))))));

        // Example12 (type check error): int a; a=true
        Statement ex12 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new AssignStatement("a", new ValueExpression(new BoolValue(true))));

        programs = new ArrayList<Statement>();
        programs.add(ex1);
        programs.add(ex2);
        programs.add(ex3);
        programs.add(ex4);
        programs.add(ex5);
        programs.add(ex6);
        programs.add(ex7);
        programs.add(ex8);
        programs.add(ex9);
        programs.add(ex10);
        programs.add(ex11);
        programs.add(ex12);
    }

    @FXML
    public void initialize(){
        List<String> programsL = programs.stream()
                .map(statement -> statement.toString())
                .collect(Collectors.toList());
        ObservableList<String> items = FXCollections.observableArrayList(programsL);
        this.programsList.getItems().addAll(items);
    }

    public void setRunController(RunController rc){
        this.runController = rc;
    }

    public Controller createProgram(String name, Statement statement){
        ProgramState programState = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), statement);
        RepositoryInterface repository = new Repository(programState, name + ".txt");
        return new Controller(repository, true);
    }

    public void runButtonHandler(ActionEvent actionEvent) {
        int idx = programsList.getSelectionModel().getSelectedIndex();
        if(idx >= 0) {
            String statementName = "ex" + Integer.toString(idx+1);
            if(doTypeChecking) {
                try {
                    this.programs.get(idx).typeCheck(new MyDictionary<String, Type>());
                } catch (MyException e) {
                    System.out.println(e.getMessage());
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("TypeCheck error");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                    return;
                }
            }

            Controller controller = this.createProgram(statementName, this.programs.get(idx));
            this.runController.initialize(controller);
        }
    }
}
