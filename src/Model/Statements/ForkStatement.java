package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyStack;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

import java.util.Map;
import java.util.stream.Collectors;

public class ForkStatement implements Statement {

    private Statement statement;

    public ForkStatement(Statement s){
        this.statement = s;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyStack<Statement> newThreadStack = new MyStack<>();
        Map copy = state.getSymbolsTable().getContent().entrySet().stream().
                collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        MyDictionary<String, Value> newThreadSymbolsTable = new MyDictionary<>(copy);
        ProgramState newThread = new ProgramState(newThreadStack, newThreadSymbolsTable, state.getOutput(), state.getFilesTable(), state.getHeap(), this.statement);
        return newThread;
    }

    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Map copy = typeEnvironment.getContent().entrySet().stream().
                collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        MyDictionary<String, Type> typeEnvironmentCopy = new MyDictionary<String, Type>(copy);

        this.statement.typeCheck(typeEnvironmentCopy);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "Fork(" + this.statement.toString() + ")";
    }
}
