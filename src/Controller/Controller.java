package Controller;

import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Values.ReferenceValue;
import Model.Values.Value;
import Repository.RepositoryInterface;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    private RepositoryInterface repository;
    private boolean debugFlag;
    private static boolean exitIfException = true;
    private ExecutorService executor;

    public Controller(RepositoryInterface initRepository, boolean debug){
        this.repository = initRepository;
        this.debugFlag = debug;
        this.executor = Executors.newFixedThreadPool(6);
    }

    public RepositoryInterface getRepository() {
        return this.repository;
    }

    public void allStep() throws MyException{
        List<ProgramState> programsList = Controller.removeCompletedPrograms(this.repository.getProgramsList());
        IHeap<Value> heap = programsList.get(0).getHeap();
        programsList.forEach(program -> {
            if(this.debugFlag)
                System.out.println(program.toString());
            try {
                this.repository.logProgramState(program);
            } catch (MyException e) {
                Controller.exceptionHandler(e);
            }
        });
        while(programsList.size() > 0){
            this.garbageCollector(programsList);
            this.oneStepForAllPrograms(programsList);
            programsList = Controller.removeCompletedPrograms(this.repository.getProgramsList());
        }
        this.executor.shutdownNow();
        this.repository.setProgramsList(programsList);
    }

    public void oneStepForAllPrograms(List<ProgramState> programsList) {
        List<Callable<ProgramState>> callList = programsList.stream()
                .map((ProgramState program) -> (Callable<ProgramState>) (() -> {
                    return program.oneStep();
                }))
                .collect(Collectors.toList());
        List<ProgramState> newProgramList = null;
        try {
            newProgramList = this.executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            Controller.exceptionHandler(e);
                        }
                        return null;
                    })
                    .filter(program -> program != null)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            Controller.exceptionHandler(e);
        }
        programsList.addAll(newProgramList);
        programsList.forEach(program -> {
            try {
                this.repository.logProgramState(program);
            } catch (MyException e) {
                Controller.exceptionHandler(e);
            }
        });
        this.repository.setProgramsList(programsList);
        programsList.forEach(program -> {
            if(this.debugFlag)
                System.out.println(program.toString());
            try {
                this.repository.logProgramState(program);
            } catch (MyException e) {
                Controller.exceptionHandler(e);
            }
        });
    }

    public void garbageCollector(List<ProgramState> programsList){
        IHeap<Value> heap = programsList.get(0).getHeap();
        List<Integer> usedAddreses = programsList.stream()
                .map(program -> program.getSymbolsTable().getContent().values())
                .map(values -> Controller.getSymbolsTableAddresses(values))
                .reduce(Stream.of(0).collect(Collectors.toList()) ,(l1, l2) -> {l1.addAll(l2); return l1;});
        List<Integer> allUsedAddreses = Controller.getAllUsedAddresses(usedAddreses, heap.getContent());

        Map<Integer, Value> newHeap = Controller.collectGarbage(allUsedAddreses, heap.getContent());
        heap.setContent(newHeap);
    }

    private static Map<Integer, Value> collectGarbage(List<Integer> usedAddresses, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(object -> usedAddresses.contains(object.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static List<Integer> getAllUsedAddresses(List<Integer> symTableAddresses, Map<Integer, Value> heap){
        List<Integer> allAddresses = new ArrayList<>();
        for( int address : symTableAddresses){
            allAddresses.add(address);
            Integer current = address;
            while(true){
                Value value= heap.get(current);
                if(!(value instanceof ReferenceValue))
                    break;
                current = ((ReferenceValue)value).getAddress();
                allAddresses.add(current);
            }
        }
        return allAddresses;
    }

    public static List<Integer> getSymbolsTableAddresses(Collection<Value> symbolsTableValues){
        return symbolsTableValues.stream()
                .filter(value -> value instanceof ReferenceValue)
                .map(value -> ((ReferenceValue)value).getAddress())
                .collect(Collectors.toList());
    }

    public static List<ProgramState> removeCompletedPrograms(List<ProgramState> states){
        return states.stream()
                .filter(program -> program.isNotCompleted())
                .collect(Collectors.toList());
    }

    public static void exceptionHandler(Exception e){
        System.out.println(e.getMessage());
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Execution Error");
        errorAlert.setContentText(e.getMessage());
        errorAlert.showAndWait();
        if(exitIfException)
            System.exit(1);
    }

}
