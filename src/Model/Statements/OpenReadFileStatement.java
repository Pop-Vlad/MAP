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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFileStatement implements Statement {

    private Expression expression;

    public OpenReadFileStatement(Expression initExpression){
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
        if(filesTable.isDefined(fileName))
            throw new MyException("File is already open for reading");
        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName.getValue()));
            filesTable.add(fileName, file);
        } catch (FileNotFoundException e) {
            throw new MyException("File not found");
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "R-open(" + this.expression.toString() + ")";
    }

}