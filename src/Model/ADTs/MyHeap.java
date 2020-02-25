package Model.ADTs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyHeap<V> implements IHeap<V> {

    private Map<Integer, V> map;
    private int nextFree;

    public MyHeap(){
        this.map = new HashMap<Integer, V>();
        this.nextFree = 1;
    }

    @Override
    public int add(V value) {
        this.map.put(this.nextFree, value);
        return this.nextFree++;
    }

    @Override
    public V remove(int key) {
        return this.map.remove(key);
    }

    @Override
    public V get(int key) { return this.map.get(key); }

    @Override
    public boolean isDefined(int key){
        return map.containsKey(key);
    }

    @Override
    public void update(int key, V value){
        this.map.replace(key, value);
    }

    @Override
    public int getFreeAddress() {
        return this.nextFree;
    }

    @Override
    public void setContent(Map<Integer, V> newContent){
        Map<Integer, V> newMap = new HashMap<>(newContent);
        this.map = newMap;
    }

    @Override
    public Map<Integer, V> getContent(){
        return this.map;
    }

    @Override
    public String toString(){
        return this.map.entrySet().stream()
                .map(entry -> "(" + entry.getKey().toString() + "," + entry.getValue().toString() + ")\n")
                .reduce("\n", (accumulator, item) -> accumulator+item);
    }

}
