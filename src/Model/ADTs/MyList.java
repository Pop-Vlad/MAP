package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {

    private ArrayList<T> list;

    public MyList(){
        list = new ArrayList<T>();
    }

    @Override
    public boolean add(T newElement){
        return list.add(newElement);
    }

    @Override
    public T get(int index) throws MyException {
        if(index >= this.list.size())
            throw new MyException("List index out of range");
        return list.get(index);
    }

    @Override
    public T remove(int index) throws MyException{
        if(index >= this.list.size())
            throw new MyException("List index out of range");
        return list.remove(index);
    }

    @Override
    public int size(){
        return list.size();
    }

    @Override
    public List<T> getContent(){
        return this.list;
    }

    @Override
    public String toString(){
        return this.list.stream()
                .map(elem -> elem.toString() + "\n")
                .reduce("\n", (accumulator, item) -> accumulator+item);
    }

}
