package Repository;

import Model.Exceptions.MyException;
import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface {

    private List<ProgramState> states;
    private String logFilePath;

    public Repository(ProgramState initProgram, String initLogFile){
        this.states = new ArrayList<>();
        this.states.add(initProgram);
        this.logFilePath = initLogFile;
        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, false)))){
            logFile.print("");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProgramState> getProgramsList(){
        return this.states;
    }

    @Override
    public void setProgramsList(List<ProgramState> programs){
        this.states = programs;
    }

    @Override
    public void logProgramState(ProgramState program) throws MyException{
        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))){
            logFile.println(program.toString());
        }
        catch (IOException e) {
            throw new MyException("An error occurred while writing log file");
        }
    }

}
