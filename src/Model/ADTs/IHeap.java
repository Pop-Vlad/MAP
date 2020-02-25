package Model.ADTs;

import java.util.Map;

public interface IHeap<V> {

    public int add(V value);
    public V remove(int key);
    public V get(int key);
    public boolean isDefined(int key);
    public void update(int key, V value);
    public int getFreeAddress();
    public void setContent(Map<Integer, V> newContent);
    public Map<Integer, V> getContent();

}
