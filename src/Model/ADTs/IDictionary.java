package Model.ADTs;

import java.util.Map;

public interface IDictionary<K, V> {

    public void add(K key, V value);
    public V remove(K key);
    public void update(K key, V value);
    public V get(K key);
    public int size();
    public boolean isDefined(K key);
    public Map<K, V> getContent();
    public String keysToString();

}
