package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFileStatement implements Statement {

    Expression expression;

    public CloseReadFileStatement(Expression initExpression){
        this.expression = initExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, Value> symbolsTable = state.getSymbolsTable();
        IDictionary<StringValue, BufferedReader> filesTable = state.getFilesTable();
        IHeap<Value> heap = state.getHeap();
        Value value = this.expression.evaluate(symbolsTable, heap);
        if(!value.getType().equals(new StringType()))
            throw new MyException("Expression is not of type string");
        StringValue fileName = (StringValue) value;
        if(!filesTable.isDefined(fileName))
            throw new MyException("File was not opened for reading");
        BufferedReader file = filesTable.get(fileName);
        try {
            file.close();
            filesTable.remove((StringValue) value);
        } catch (IOException e) {
            throw new MyException("An error occurred trying to close the file");
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "close(" + this.expression.toString() + ")";
    }

}