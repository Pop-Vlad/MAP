package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.Value;

public class VariableExpression implements Expression {

    private String id;

    public VariableExpression(String initId){
        this.id = initId;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolsTable, IHeap<Value> heap) throws MyException {
        return symbolsTable.get(id);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment.get(this.id);
    }

    @Override
    public String toString(){
        return this.id;
    }

}
