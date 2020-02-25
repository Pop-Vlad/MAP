package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class RelationalExpression implements Expression {

    private Expression left;
    private Expression right;
    private String operator;

    public RelationalExpression(Expression initLeft, Expression initRight, String initOperator){
        this.left = initLeft;
        this.right = initRight;
        this.operator = initOperator;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolsTable, IHeap<Value> heap) throws MyException {
        Value v1, v2;
        v1 = left.evaluate(symbolsTable, heap);
        if(v1.getType().equals(new IntType())){
            v2 = right.evaluate(symbolsTable, heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if(this.operator == "<") return new BoolValue(n1 < n2);
                if(this.operator == "<=") return new BoolValue(n1 <= n2);
                if(this.operator == "==") return new BoolValue(n1 == n2);
                if(this.operator == "!=") return new BoolValue(n1 != n2);
                if(this.operator == ">=") return new BoolValue(n1 >= n2);
                if(this.operator == ">") return new BoolValue(n1 > n2);
                throw new MyException("Unrecognized operator");
            }
            else throw new MyException("Second operand not an integer");
        }
        else
            throw new MyException("First operand not an integer");
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Type type1, type2;
        type1 = this.left.typeCheck(typeEnvironment);
        type2 = this.right.typeCheck(typeEnvironment);
        if(!type1.equals(new IntType()))
            throw new MyException("First operand is not an integer");
        if(!type2.equals(new IntType()))
            throw new MyException("Second operand is not an integer");
        return new BoolType();
    }

    @Override
    public String toString(){
        return this.left.toString() + " " + this.operator + " " + this.right.toString();
    }

}
