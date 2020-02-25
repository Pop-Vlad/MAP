package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.ADTs.IStack;
import Model.ADTs.MyDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.util.Map;
import java.util.stream.Collectors;

public class WhileStatement implements Statement {

    private Expression expression;
    private Statement repeatStatement;

    public WhileStatement(Expression initExpression, Statement initStatement){
        this.expression = initExpression;
        this.repeatStatement = initStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<Statement> executionStack = state.getExecutionStack();
        IDictionary<String, Value> symbolsTable = state.getSymbolsTable();
        IHeap<Value> heap = state.getHeap();
        Value evaluation = this.expression.evaluate(symbolsTable, heap);
        if(evaluation.getType().equals(new BoolType())) {
            if (!((BoolValue) evaluation).getValue())
                return null;
            else {
                executionStack.push(new WhileStatement(this.expression, this.repeatStatement));
                executionStack.push(this.repeatStatement);
                return null;
            }
        }
        else
            throw new MyException("Conditional expression is not a boolean");
    }

    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Map copy = typeEnvironment.getContent().entrySet().stream().
                collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        MyDictionary<String, Type> typeEnvironmentCopy = new MyDictionary<String, Type>(copy);

        Type typeExpression = this.expression.typeCheck(typeEnvironment);
        if(!typeExpression.equals(new BoolType()))
            throw new MyException("The condition in the While does not have the type boolean");
        this.repeatStatement.typeCheck(typeEnvironmentCopy);
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "(While (" + this.expression.toString() + ") " + this.repeatStatement.toString() + ")";
    }

}
