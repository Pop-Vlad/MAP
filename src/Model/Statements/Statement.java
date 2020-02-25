package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.Type;

public interface Statement {

    ProgramState execute(ProgramState state) throws MyException;
    IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException;

}
