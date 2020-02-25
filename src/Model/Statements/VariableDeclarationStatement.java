package Model.Statements;


import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class VariableDeclarationStatement implements Statement {

    private String name;
    private Type type;

    public VariableDeclarationStatement(String initName, Type initType){
        this.name = initName;
        this.type = initType;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, Value> symbolsTable = state.getSymbolsTable();
        if(symbolsTable.isDefined(this.name))
            throw new MyException("Variable is already declared");
        else
            symbolsTable.add(this.name, this.type.defaultValue());
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        typeEnvironment.add(this.name, this.type);
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return this.type.toString() + " " + this.name.toString();
    }

}
