package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IList;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStatement implements Statement {

    private Expression expression;

    public PrintStatement(Expression initExpression) {
        this.expression = initExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IList<Value> output = state.getOutput();
        output.add(expression.evaluate(state.getSymbolsTable(), state.getHeap()));
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        this.expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "Print(" + this.expression.toString() + ")";
    }

}
