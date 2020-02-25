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

public class IfStatement implements Statement {

    private Expression expression;
    private Statement thenStatement;
    private Statement elseStatement;

    public IfStatement(Expression initExpression, Statement initThen, Statement initElse){
        this.expression = initExpression;
        this.thenStatement = initThen;
        this.elseStatement = initElse;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<Statement> executionStack = state.getExecutionStack();
        IDictionary<String, Value> symbolsTable = state.getSymbolsTable();
        IHeap<Value> heap = state.getHeap();
        Value condition = this.expression.evaluate(symbolsTable, heap);
        if(condition.getType().getClass() != new BoolType().getClass())
            throw new MyException("Conditional expression is not a boolean");
        else{
            if(((BoolValue)condition).getValue())
                executionStack.push(this.thenStatement);
            else
                executionStack.push(this.elseStatement);
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Map copy1 = typeEnvironment.getContent().entrySet().stream().
                collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        MyDictionary<String, Type> typeEnvironmentCopy1 = new MyDictionary<String, Type>(copy1);
        Map copy2 = typeEnvironment.getContent().entrySet().stream().
                collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        MyDictionary<String, Type> typeEnvironmentCopy2 = new MyDictionary<String, Type>(copy2);

        Type typeExpression = this.expression.typeCheck(typeEnvironment);
        if(!typeExpression.equals(new BoolType()))
            throw new MyException("The condition in the IF does not have the type boolean");
        this.thenStatement.typeCheck(typeEnvironmentCopy1);
        this.thenStatement.typeCheck(typeEnvironmentCopy2);
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "If(" + this.expression.toString() + ") Then(" + this.thenStatement.toString() + ") Else(" + this.elseStatement.toString() + ")";
    }

}
