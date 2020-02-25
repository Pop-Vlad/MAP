package Model.ADTs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {

    private Map<K, V> table;

    public MyDictionary(){
        this.table = new HashMap<>();
    }

    public MyDictionary(Map map){
        this.table = map;
    }

    @Override
    public void add(K key, V value) {
        this.table.put(key, value);
    }

    @Override
    public V remove(K key) {
        return this.table.remove(key);
    }

    @Override
    public V get(K key) { return this.table.get(key); }

    @Override
    public int size() {
        return this.table.size();
    }

    @Override
    public void update(K key, V value){
        this.table.replace(key, value);
    }

    @Override
    public boolean isDefined(K key){
        return table.containsKey(key);
    }

    @Override
    public Map<K, V> getContent(){
        return this.table;
    }

    @Override
    public String keysToString(){
        String collector = "";
        Iterator it = this.table.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            collector += pair.getKey().toString()  + "\n";
        }
        return collector.trim();
    }

    @Override
    public String toString(){
        return this.table.entrySet().stream()
                .map(entry -> "(" + entry.getKey().toString() + "," + entry.getValue().toString() + ")\n")
                .reduce("\n", (accumulator, item) -> accumulator+item);
    }

}
