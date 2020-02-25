package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.List;

public interface IList<T> {

    public boolean add(T newElement);
    public T get(int index) throws MyException;
    public T remove(int index) throws MyException;
    public int size();
    public List<T> getContent();

}
