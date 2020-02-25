package Repository;

import Model.Exceptions.MyException;
import Model.ProgramState;

import java.util.List;

public interface RepositoryInterface {

    List<ProgramState> getProgramsList();
    void setProgramsList(List<ProgramState> programs);
    void logProgramState(ProgramState program) throws MyException;

}
