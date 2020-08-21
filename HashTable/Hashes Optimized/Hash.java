import java.util.Iterator;
import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class Hash<K, V> implements HashI<K, V> {
    
	
    class IteratorHelper<T> implements Iterator<T>{
		T[] keys;
    	int position;
    	public IteratorHelper(T[][] Object) {
			keys = (T[]) Object[numElements];
    		int p = 0;
    		for(int i=0;i<tableSize;i++) {
    			LinkedList<HashElement<K,V>> list=hash_array[i];
    			for(HashElement<K,V> h: list)
    				keys[p++]=(T) h.key;
    		}
    		position = 0;
    	}
    	
    	
		@Override
		public boolean hasNext() {
			return position<keys.length;
		}

		@Override
		public T next() {
			if(!hasNext())
				return null;
			return keys[position++];
		}
    	
    }
    @SuppressWarnings("hiding")
	class HashElement<K, V> implements Comparable<HashElement<K, V>> {
        K key;
        V value;

        public HashElement(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public int compareTo(HashElement<K, V> objElement) {
            return (((Comparable<K>) objElement.key).compareTo(this.key));
        }
    }

    int numElements , tableSize;
    double maxLoadFactor;
    LinkedList<HashElement<K, V>>[] hash_array;

    public Hash(int tableSize) {
        this.tableSize = tableSize;
        
        hash_array = (LinkedList<HashElement<K, V>>[]) new LinkedList[tableSize];
        for (int i = 0; i < tableSize; i++) {
            hash_array[i] = new LinkedList<HashElement<K, V>>();
        }
        maxLoadFactor=0.75;
        numElements=0;
    }
    
    public boolean add(K key,V value) {
    	if(loadFactor()>maxLoadFactor) {
    		resize(tableSize*2);
    	}
    	HashElement<K,V> he = new HashElement(key, value);
    	int hashval = key.hashCode();
    	hashval = hashval & 0x7FFFFFFF;
    	hashval = hashval % tableSize;
		hash_array[hashval].add(he);
		numElements++;
		return true;
    }

    public boolean remove(K key) {
    	HashElement<K,V> he = (HashElement<K, V>) key;
    	int hashval = key.hashCode();
    	hashval = hashval & 0x7FFFFFFF;
    	hashval = hashval % tableSize;
		hash_array[hashval].remove(he);
		numElements++;
		return true;
    	
    }
    
    public V getValue(K key) {
    	int hashval = key.hashCode() & 0x7FFFFFFF % tableSize;
    	for(HashElement<K,V> he: hash_array[hashval]) {
    		if(((Comparable<K>)key).compareTo(he.key)==0)
    			return he.value;
    	}
		return null;
    	
    }
	private void resize(int newSize) {
		LinkedList<HashElement<K,V>>[] new_array = (LinkedList<HashElement<K,V>>[]) new LinkedList[newSize];
		for(int i=0;i<newSize;i++) {
			new_array[i] = new LinkedList<HashElement<K,V>>();
		}
		for(K key: this) {
			V val = getValue(key);
			HashElement<K,V> he = new HashElement(key, val);
			int hashval= (key.hashCode() & 0x7FFFFFFF)%newSize;
			new_array[hashval].add(he);
		}
		hash_array = new_array;
		tableSize = newSize;
	}

	private double loadFactor() {
		// TODO Auto-generated method stub
		return 0;
	}

}