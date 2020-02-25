package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExpression implements Expression {

    private Expression left;
    private Expression right;
    private String operation;

    public LogicExpression(Expression initLeft, Expression initRight, String initOperation){
        this.left = initLeft;
        this.right = initRight;
        this.operation = initOperation;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolsTable, IHeap<Value> heap) throws MyException {
        Value v1, v2;
        v1 = left.evaluate(symbolsTable, heap);
        if(v1.getType().equals(new BoolType())){
            v2 = right.evaluate(symbolsTable, heap);
            if(v2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;
                Boolean n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if(operation == "and")
                    return new BoolValue(n1 || n2);
                if(operation == "or")
                    return new BoolValue(n1 && n2);
            }
            else
                throw new MyException("Second operand not boolean type");
        }
        else
            throw new MyException("First operand not boolean type");
        return null;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Type type1, type2;
        type1 = this.left.typeCheck(typeEnvironment);
        type2 = this.right.typeCheck(typeEnvironment);
        if(!type1.equals(new BoolType()))
            throw new MyException("First operand is not a boolean");
        if(!type2.equals(new BoolType()))
            throw new MyException("Second operand is not a boolean");
        return new BoolType();
    }

    @Override
    public String toString(){
        return this.left.toString() + " " + this.operation + " " + this.right.toString();
    }

}
