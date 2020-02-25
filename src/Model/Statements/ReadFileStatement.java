package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement {

    Expression expression;
    String variableName;

    public ReadFileStatement(Expression initExpression, String initVariableName){
        this.expression = initExpression;
        this.variableName = initVariableName;
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
            String line = file.readLine();
            int number;
            if(line == "")
                number = 0;
            else
                number = Integer.parseInt(line);
            symbolsTable.add(this.variableName, new IntValue(number));
        } catch (IOException e) {
            throw new MyException("A read from file error occurred");
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "read(" + this.expression.toString() + ", " + this.variableName + ")";
    }

}