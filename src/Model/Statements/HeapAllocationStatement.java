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

public class HeapAllocationStatement implements Statement {

    private String variableName;
    private Expression expression;

    public HeapAllocationStatement(String variableName, Expression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, Value> symbolsTable = state.getSymbolsTable();
        IHeap<Value> heap = state.getHeap();
        if(symbolsTable.isDefined(this.variableName)){
            Value value = symbolsTable.get(this.variableName);
            if(value.getType() instanceof ReferenceType){
                Value expressionResult = this.expression.evaluate(symbolsTable, heap);
                if(expressionResult.getType().equals(((ReferenceType)value.getType()).getInner())){
                    int address = heap.add(expressionResult);
                    symbolsTable.update(this.variableName, new ReferenceValue(expressionResult.getType(), address));
                }
                else
                    throw new MyException("Variable and expression type do not match");
            }
            else
                throw new MyException("Variable is not of type reference");
        }
        else
            throw new MyException("The used variable " + this.variableName + " was not declared before");
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Type typeVariable = typeEnvironment.get(this.variableName);
        Type typeExpression = this.expression.typeCheck(typeEnvironment);
        if(typeVariable.equals(new ReferenceType(typeExpression)))
            return typeEnvironment;
        else
            throw new MyException("HeapAllocation: right hand side and left hand side have different types");
    }

    @Override
    public String toString(){
        return "new(" + this.variableName + "," + this.expression.toString() + ")";
    }

}
