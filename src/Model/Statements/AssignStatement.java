package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStatement implements Statement {

    private String id;
    private Expression expression;

    public AssignStatement(String initId, Expression initExpression){
        this.id = initId;
        this.expression = initExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, Value> symbolsTable = state.getSymbolsTable();
        IHeap<Value> heap = state.getHeap();
        if(symbolsTable.isDefined(this.id)){
            Value value = this.expression.evaluate(symbolsTable, heap);
            if(value.getType().equals(symbolsTable.get(this.id).getType()))
                symbolsTable.update(this.id, value);
            else
                throw new MyException("Type of expression and type of variable do not match");
        }
        else
            throw new MyException("The used variable " + id + " was not declared before");
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Type typeVariable = typeEnvironment.get(this.id);
        Type typeExpression = this.expression.typeCheck(typeEnvironment);
        if(typeVariable.equals(typeExpression))
            return typeEnvironment;
        else
            throw new MyException("Assignment: left hand side and right hand side have different types");
    }

    @Override
    public String toString(){
        return id + "=" + expression.toString();
    }

}
