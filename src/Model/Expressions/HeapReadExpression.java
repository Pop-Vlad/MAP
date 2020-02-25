package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class HeapReadExpression implements Expression {

    private Expression expression;

    public HeapReadExpression(Expression expression){
        this.expression = expression;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolsTable, IHeap<Value> heap) throws MyException {
        Value result = this.expression.evaluate(symbolsTable, heap);
        if(result.getType() instanceof ReferenceType){
            int address = ((ReferenceValue)result).getAddress();
            if(heap.isDefined(address)){
                return heap.get(address);
            }
            else
                throw new MyException("The expression references an unallocated space");
        }
        else
            throw new MyException("The given expression is not a reference");
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Type type = this.expression.typeCheck(typeEnvironment);
        if(type instanceof ReferenceType){
            return ((ReferenceType) type).getInner();
        }
        else
            throw new MyException("ReadHeap argument is not a ReferenceType");
    }

    @Override
    public String toString(){
        return "ReadHeap(" + this.expression.toString() + ")";
    }

}
