import java.util.*;

class Entry<K, V> {
    int hash;
    K key;
    V value;

    public Entry(final K key, final V value) {
        this.key = key;
        this.value = value;
        this.hash = key.hashCode();
    }

    // We are not overriding the Object equals method
    // No casting is required with this method.
    public boolean equals(final Entry<K, V> other) {
        if (hash != other.hash)
            return false;
        return key.equals(other.key);
    }

    @Override
    public String toString() {
        return key + " => " + value;
    }
}

@SuppressWarnings("unchecked")
public class HashTableSeparateChaining<K, V> implements Iterable<K> {

    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private double maxLoadFactor;
    private int threshold, capacity, size = 0;
    private LinkedList<Entry<K, V>>[] table;

    public HashTableSeparateChaining() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(final int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(final int capacity, final double maxLoadFactor) {
        if (capacity < 0)
            throw new IllegalArgumentException("Illegal capacity");
        if (maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
            throw new IllegalArgumentException("Illegal maxLoadFactor");
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        threshold = (int) (this.capacity * maxLoadFactor);
        table = new LinkedList[this.capacity];
    }

    // Returns the number of elements inside the hash-table
    public int size() {
        return size;
    }

    // Returns true/false depending on whether the hash-table is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Converts a hash value to an index. Essentially, this strips the
    // negative sign and places the hash value in the domain[0,capacity)
    private int normalizeIndex(final int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    // Clears all the contents of the hash-table
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    public boolean containsKey(final K key) {
        return hasKey(key);
    }

    // Returns true/false depending on whether a key is in the hash table
    public boolean hasKey(final K key) {
        final int bucketIndex = normalizeIndex(key.hashCode());
        return bucketSeekEntry(bucketIndex, key) != null;
    }

    public V put(final K key, final V value) {
        return insert(key, value);
    }

    public V add(final K key, final V value) {
        return insert(key, value);
    }

    public V insert(final K key, final V value) {
        if (key == null)
            throw new IllegalArgumentException("Null Key");
        final Entry<K, V> newEntry = new Entry(key, value);
        final int bucketIndex = normalizeIndex(newEntry.hash);
        return bucketInsertEntry(bucketIndex, newEntry);
    }

    // Gets a key's values from the map and returns the value.
    // NOTE: returns null if the value is null AND also returns
    // null if the key does not exists , so watch out..
    public V get(final K key) {
        if (key == null)
            return null;
        final int bucketIndex = normalizeIndex(key.hashCode());
        final Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
        if (entry != null)
            return entry.value;
        return null;
    }

    // Removes a key from the map and returns the value.
    // NOTE: returns null if the value is null AND also returns null if the key does
    // not exists.
    public V remove(final K key) {
        if (key == null)
            return null;
        final int bucketIndex = normalizeIndex(key.hashCode());
        return bucketRemoveEntry(bucketIndex, key);
    }

    // Removes an entry from a given bucket if it exists
    private V bucketRemoveEntry(final int bucketIndex, final K key) {
        final Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
        if (entry != null) {
            final LinkedList<Entry<K, V>> links = table[bucketIndex];
            links.remove(entry);
            --size;
            return entry.value;
        } else
            return null;
    }

    // Inserts an entry in a given bucket only if the entry does not already
    // exist in the given bucket, but if it does update the entry value
    private V bucketInsertEntry(final int bucketIndex, final Entry<K, V> entry) {
        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if (bucket == null) {
            table[bucketIndex] = bucket = new LinkedList<>();
        }
        final Entry<K, V> existentEntry = bucketSeekEntry(bucketIndex, entry.key);
        if (existentEntry == null) {
            bucket.add(entry);
            if (++size > threshold)
                resizeTable();
            return null; // Use null to indicate that there was no previous entry
        } else {
            final V oldval = existentEntry.value;
            existentEntry.value = entry.value;
            return oldval;
        }
    }

    // Finds and returns a particular entry in a given bucket if it exists, returns
    // null otherwise
    private Entry<K, V> bucketSeekEntry(final int bucketIndex, final K key) {
        if (key == null)
            return null;
        final LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if (bucket == null)
            return null;
        for (final Entry<K, V> entry : bucket)
            if (entry.key.equals(key))
                return entry;
        return null;
    }

    // Resizes the internal table holding buckets of entries
    private void resizeTable() {
        capacity *= 2;
        threshold = (int) (capacity * maxLoadFactor);
        final LinkedList<Entry<K, V>>[] newTable = new LinkedList[capacity];

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (final Entry<K, V> entry : table[i]) {
                    final int bucketIndex = normalizeIndex(entry.hash);
                    LinkedList<Entry<K, V>> bucket = newTable[bucketIndex];
                    if (bucket == null)
                        newTable[bucketIndex] = bucket = new LinkedList<>();
                    bucket.add(entry);
                }

                // Avoid memory leak. Help the GC
                table[i].clear();
                table[i] = null;
            }
        }
        table = newTable;
    }

    // Returns the list of keys found within the hash table
    public List<K> keys() {
        final List<K> keys = new ArrayList<>(size());
        for (final LinkedList<Entry<K, V>> bucket : table)
            if (bucket != null)
                for (final Entry<K, V> entry : bucket)
                    keys.add(entry.key);
        return keys;
    }

    // Returns the list of values found within the hash table
    public List<V> values() {
        final List<V> values = new ArrayList<>(size());
        for (final LinkedList<Entry<K, V>> bucket : table)
            if (bucket != null)
                for (final Entry<K, V> entry : bucket)
                    values.add(entry.value);
        return values;
    }

    // Return an iterator to iteratre over all the keys in the map
    @Override
    public java.util.Iterator<K> iterator(){
        final int elementCount = size();
        return new java.util.Iterator<K>(){
            int bucketIndex = 0;
            java.util.Iterator<Entry<K,V>> bucketIter = (table[0] == null) ? null:table[0].iterator();

            @Override
            public boolean hasNext(){
                //An item was added or removed while iterating
                if(elementCount!=size)throw new java.util.ConcurrentModificationException();
                //No iterator or the current iterator is found
                if(bucketIter == null || !bucketIter.hasNext()){
                    //Search next bucket until a valid iterator is found 
                    while(++bucketIndex<capacity){
                        if(table[bucketIndex]!=null){
                            //Make sure this iterator actually has elements
                            final java.util.Iterator<Entry<K,V>> nextIter = table[bucketIndex].iterator();
                            if(nextIter.hasNext()){
                                bucketIter = nextIter;
                                break;
                            }
                        }
                    }
                }
                return bucketIndex<capacity;
            }

            @Override
            public K next(){
                return bucketIter.next().key;
            }

            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
    }
    //Returns a string representation of this hash table
    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i=0;i<capacity;i++){
            if(table[i]==null)continue;
            for(final Entry<K,V> entry:table[i]) sb.append(entry + ",");
        }
        sb.append("}");
        return sb.toString();
    }
}