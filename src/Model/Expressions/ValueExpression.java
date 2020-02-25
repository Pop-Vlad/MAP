package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExpression implements Expression {

    private Value value;

    public ValueExpression(Value initValue){
        this.value = initValue;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolsTable, IHeap<Value> heap) throws MyException {
        return value;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        return this.value.getType();
    }

    @Override
    public String toString(){
        return this.value.toString();
    }

}
