package View;

import Controller.Controller;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyHeap;
import Model.ADTs.MyList;
import Model.ADTs.MyStack;
import Model.Expressions.*;
import Model.ProgramState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.Repository;
import Repository.RepositoryInterface;
import View.Commands.ExitCommand;
import View.Commands.RunProgramCommand;

import java.io.BufferedReader;

public class Interpreter {

    public static void main(String[] args) {

        /*
        ProgramState.lastUnusedId = 1;
        //  Example1: int v; v=2;Print(v)
        Statement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(
                new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        ProgramState program1 = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex1);
        RepositoryInterface repository1 = new Repository(program1, "log1.txt");
        Controller controller1 = new Controller(repository1, true);

        // Example2: int a;int b; a=2+3*5;b=a+1;Print(b)
        Statement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()), new CompoundStatement(new VariableDeclarationStatement(
                "b",new IntType()), new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),
                new ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))), new CompoundStatement(
                        new AssignStatement("b", new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                new PrintStatement(new VariableExpression("b"))))));
        ProgramState program2 = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex2);
        RepositoryInterface repository2 = new Repository(program2, "log2.txt");
        Controller controller2 = new Controller(repository2, true);

        // Example3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()), new CompoundStatement(new VariableDeclarationStatement("v",
                new IntType()), new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))), new CompoundStatement(new IfStatement(
                        new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(
                                new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        ProgramState program3 = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex3);
        RepositoryInterface repository3 = new Repository(program3, "log3.txt");
        Controller controller3 = new Controller(repository3, true);

        // Example4: string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
        Statement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf",new StringType()),new CompoundStatement(new AssignStatement("varf",new ValueExpression(
                new StringValue("test.in"))),new CompoundStatement(new OpenReadFileStatement(new VariableExpression("varf")),new CompoundStatement(new
                VariableDeclarationStatement("varc",new IntType()),new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),new
                CompoundStatement(new PrintStatement(new VariableExpression("varc")),new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),
                "varc") ,new CompoundStatement(new PrintStatement(new VariableExpression("varc")),new CloseReadFileStatement(new VariableExpression("varf"))))))))));
        ProgramState program4 = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex4);
        RepositoryInterface repository4 = new Repository(program4, "log4.txt");
        Controller controller4 = new Controller(repository4, true);

        // Example5: int a; int b; a=2; b=3;(If a>b Then Print(a) Else Print(b))
        Statement ex5 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new VariableDeclarationStatement("b",
                new IntType()), new CompoundStatement(new AssignStatement("a", new ValueExpression(new IntValue(2))), new CompoundStatement(new AssignStatement("b",
                new ValueExpression(new IntValue(3))), new IfStatement(new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"),
                ">"), new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b")))))));
        ProgramState program5 = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex5);
        RepositoryInterface repository5 = new Repository(program5, "log5.txt");
        Controller controller5 = new Controller(repository5, true);

        // Example6: int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        Statement ex6 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignStatement("v", new ValueExpression(new
                IntValue(4))), new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)),
                ">"), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression('-',
                new VariableExpression("v"), new ValueExpression(new IntValue(1)))))), new PrintStatement(new VariableExpression("v")))));
        ProgramState program6 = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex6);
        RepositoryInterface repository6 = new Repository(program6, "log6.txt");
        Controller controller6 = new Controller(repository6, true);

        // Example7: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        Statement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(new HeapAllocationStatement(
                "v", new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(
                        new IntType()))), new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")), new CompoundStatement(new PrintStatement(
                                new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));
        ProgramState program7 = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex7);
        RepositoryInterface repository7 = new Repository(program7, "log7.txt");
        Controller controller7 = new Controller(repository7, true);

        // Example8: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        Statement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(new HeapAllocationStatement(
                "v", new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(
                        new IntType()))), new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")), new CompoundStatement(new PrintStatement(
                                new HeapReadExpression(new VariableExpression("v"))), new PrintStatement(new ArithmeticExpression('+', new HeapReadExpression(
                                        new HeapReadExpression(new VariableExpression("a"))) , new ValueExpression(new IntValue(5)))))))));
        ProgramState program8 = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex8);
        RepositoryInterface repository8 = new Repository(program8, "log8.txt");
        Controller controller8 = new Controller(repository8, true);

        // Example9: Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        Statement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(new HeapAllocationStatement(
                "v", new ValueExpression(new IntValue(20))), new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                new CompoundStatement(new HeapWriteStatement("v", new ValueExpression(new IntValue(30))), new PrintStatement(new ArithmeticExpression('+',
                        new HeapReadExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));
        ProgramState program9 = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex9);
        RepositoryInterface repository9 = new Repository(program9, "log9.txt");
        Controller controller9 = new Controller(repository9, true);

        // Example10: Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)));
        Statement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(new HeapAllocationStatement(
                "v", new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(
                        new IntType()))), new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")), new CompoundStatement(
                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))), new PrintStatement(new HeapReadExpression(
                                        new HeapReadExpression(new VariableExpression("a")))))))));
        ProgramState program10 = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex10);
        RepositoryInterface repository10 = new Repository(program10, "log10.txt");
        Controller controller10 = new Controller(repository10, true);

        // Example11: int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
        Statement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new VariableDeclarationStatement("a",
                new ReferenceType(new IntType())), new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))), new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))), new CompoundStatement(
                        new ForkStatement(new CompoundStatement(new HeapWriteStatement("a", new ValueExpression(new IntValue(30))), new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(32))), new CompoundStatement(new PrintStatement(new HeapReadExpression(
                                        new VariableExpression("a"))), new PrintStatement(new VariableExpression("v")))
                        ))), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadExpression(
                                new VariableExpression("a")))))))));
        ProgramState program11 = new ProgramState(new MyStack<Statement>(), new MyDictionary<String, Value>(), new MyList<Value>(),
                new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), ex11);
        RepositoryInterface repository11 = new Repository(program11, "log11.txt");
        Controller controller11 = new Controller(repository11, true);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunProgramCommand("1", ex1.toString(), controller1));
        menu.addCommand(new RunProgramCommand("2", ex2.toString(), controller2));
        menu.addCommand(new RunProgramCommand("3", ex3.toString(), controller3));
        menu.addCommand(new RunProgramCommand("4", ex4.toString(), controller4));
        menu.addCommand(new RunProgramCommand("5", ex5.toString(), controller5));
        menu.addCommand(new RunProgramCommand("6", ex6.toString(), controller6));
        menu.addCommand(new RunProgramCommand("7", ex7.toString(), controller7));
        menu.addCommand(new RunProgramCommand("8", ex8.toString(), controller8));
        menu.addCommand(new RunProgramCommand("9", ex9.toString(), controller9));
        menu.addCommand(new RunProgramCommand("10", ex10.toString(), controller10));
        menu.addCommand(new RunProgramCommand("11", ex11.toString(), controller11));

        menu.run();
        */
    }

}
