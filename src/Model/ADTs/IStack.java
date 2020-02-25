package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.List;

public interface IStack<T> {

    public void push(T newElement);
    public T pop() throws MyException;
    public T top() throws MyException;
    public boolean isEmpty();
    public List<T> getContent();

}
