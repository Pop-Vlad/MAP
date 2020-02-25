package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.Value;

public interface Expression {

    Value evaluate(IDictionary<String, Value> symbolsTable, IHeap<Value> heap) throws MyException;
    Type typeCheck(IDictionary<String, Type> TypeEnvironment) throws MyException;

}
