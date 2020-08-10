import java.math.BigInteger;

public class HashTableDoubleHashing<K extends SecondaryHash, V> extends HashTableOpenAddressingBase<K,V>{
    private int hash;

    public HashTableDoubleHashing(){
        super();
    }
    public HashTableDoubleHashing(int capacity){
        super(capacity);
    }

    //Designated Constructor
    public HashTableDoubleHashing(int capacity,double loadFactor){
        super(capacity,loadFactor);
    }


    @Override
    protected void setupProbing(K key) {
        //Cache second hash value.
        hash = normalizeIndex(key.hashCode2());

        //Fail safe to avoid infinite loop
        if(hash==0) hash=1;
    }

    @Override
    protected int probe(int x) {
        return x * hash;
    }

    @Override
    protected void adjustCapacity() {
        while(!(new BigInteger(String.valueOf(capacity)).isProbablePrime(20))){
            capacity++;
        }
    }
}