package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {

    private Stack<T> stack;

    public MyStack(){
        this.stack = new Stack<T>();
    }

    @Override
    public void push(T newElement) {
        stack.push(newElement);
    }

    @Override
    public T pop() throws MyException{
        if(stack.empty())
            throw new MyException("Stack is empty");
        return stack.pop();
    }

    @Override
    public  T top() throws MyException{
        if(stack.empty())
            throw new MyException("Stack is empty");
        return stack.lastElement();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List<T> getContent() {
        return new ArrayList<T>(stack);
    }

    @Override
    public String toString(){
        List<T> reversedStack = this.getContent();
        Collections.reverse(reversedStack);
        return reversedStack.stream()
                .map(elem -> elem.toString() + "\n")
                .reduce("\n", (accumulator, item) -> accumulator+item);
    }

}
