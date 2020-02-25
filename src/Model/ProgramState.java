package Model;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.ADTs.IList;
import Model.ADTs.IStack;
import Model.Exceptions.MyException;
import Model.Statements.Statement;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;

public class ProgramState {

    private int id;
    public static int lastUnusedId;
    private IStack<Statement> executionStack;
    private IDictionary<String, Value> symbolsTable;
    private IList<Value> output;
    private IDictionary<StringValue, BufferedReader> filesTable;
    private IHeap<Value> heap;

    public ProgramState(IStack<Statement> exeStack, IDictionary<String, Value> symTable, IList<Value> out,
                        IDictionary<StringValue, BufferedReader> fTable, IHeap<Value> heap, Statement program){
        this.executionStack = exeStack;
        this.symbolsTable = symTable;
        this.output = out;
        this.filesTable = fTable;
        this.heap = heap;
        this.executionStack.push(program);
        this.id = ProgramState.receiveId();
    }

    public static synchronized int receiveId(){
        lastUnusedId++;
        return lastUnusedId;
    }

    public IStack<Statement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(IStack<Statement> executionStack) {
        this.executionStack = executionStack;
    }

    public IDictionary<String, Value> getSymbolsTable() {
        return symbolsTable;
    }

    public void setSymbolsTable(IDictionary<String, Value> symbolsTable) {
        this.symbolsTable = symbolsTable;
    }

    public IList<Value> getOutput() {
        return output;
    }

    public void setOutput(IList<Value> output) {
        this.output = output;
    }

    public IDictionary<StringValue, BufferedReader> getFilesTable() {
        return filesTable;
    }

    public void setFilesTable(IDictionary<StringValue, BufferedReader> filesTable) {
        this.filesTable = filesTable;
    }

    public IHeap<Value> getHeap() {
        return heap;
    }

    public void setHeap(IHeap<Value> heap) {
        this.heap = heap;
    }

    public int getId() {
        return id;
    }

    public boolean isNotCompleted(){
        return !this.executionStack.isEmpty();
    }

    public ProgramState oneStep() throws MyException {
        IStack<Statement> stack = this.getExecutionStack();
        if (stack.isEmpty())
            throw new MyException("ProgramState execution stack is empty");
        Statement crtStatement = stack.pop();
        return crtStatement.execute(this);
    }

    @Override
    public String toString(){
        String executionStackData = this.executionStack.toString();
        String symbolsTableData = this.symbolsTable.toString();
        String outputData = this.output.toString();
        String filesTableData = this.filesTable.keysToString();
        String heapData = this.heap.toString();
        return "ID -- " + this.id + "\n" +
                "Execution stack:" +  executionStackData +
                "Symbols table:" + symbolsTableData +
                "Output:" + outputData +
                "Files table:" + filesTableData + "\n" +
                "Heap:" + heapData;
    }

}
