package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class HeapWriteStatement implements Statement {

    private String variableName;
    private Expression expression;

    public HeapWriteStatement(String varName, Expression expression){
        this.variableName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, Value> symbolsTable = state.getSymbolsTable();
        IHeap<Value> heap = state.getHeap();
        if(!symbolsTable.isDefined(this.variableName))
            throw new MyException("The used variable " + this.variableName + " was not declared before");
        if(!(symbolsTable.get(this.variableName).getType() instanceof ReferenceType))
            throw new MyException("The used variable " + this.variableName + " is not a reference");
        int address = ((ReferenceValue)symbolsTable.get(this.variableName)).getAddress();
        if(!heap.isDefined(address))
            throw new MyException("The reference of " + this.variableName + " is invalid");
        Value result = this.expression.evaluate(symbolsTable, heap);
        if(!result.getType().equals(heap.get(address).getType()))
            throw new MyException("The variable " + this.variableName + " and expression " + this.expression.toString() + " have different types");
        heap.update(address, result);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Type typeVariable = typeEnvironment.get(this.variableName);
        Type typeExpression = this.expression.typeCheck(typeEnvironment);
        if(typeVariable.equals(new ReferenceType(typeExpression)))
            return typeEnvironment;
        else
            throw new MyException("HeapWrite: right hand side and left hand side have different types");
    }

    @Override
    public String toString(){
        return "WriteHeap(" + this.variableName + "," + this.expression.toString() + ")";
    }

}
