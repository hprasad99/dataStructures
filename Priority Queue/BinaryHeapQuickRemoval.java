import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class BinaryHeapQuickRemoval<T extends Comparable<T>> {
    private List<T> heap = null;
    private Map<T, TreeSet<Integer>> map = new HashMap<>();

    public BinaryHeapQuickRemoval(){
        this(1);
    }
    public BinaryHeapQuickRemoval(int sz){
        heap = new ArrayList<>(sz);
    }
    
    public BinaryHeapQuickRemoval(T[] elems){
        int heapSize = elems.length;
        heap = new ArrayList<T>(heapSize);

        for(int i=0;i<heapSize;i++){
            mapAdd(elems[i],i);
            heap.add(elems[i]);
        }

        //Heapify process, O(n)
        for(int i=Math.max(0,(heapSize/2)-1);i>=0;i--)sink(i);
    }

    //Priority Queue construction, O(nlog(n))
    public BinaryHeapQuickRemoval(Collection<T> elems){
        this(elems.size());
        for(T elem: elems) add(elem);
    }

    //Returns true/false depending on if the priority queue is empty
    public boolean isEmpty(){
        return size()==0;
    }

    //Clears everything inside the heap, O(n)
    public void clear(){
        heap.clear();
        map.clear();
    }

    //Returns the size of heap
    public int size(){
        return heap.size();
    }
    
    //Returns the value of the element with the lowest
    //priority in this priority queue. If the priority
    //queue is empty null is returned
    public T peek(){
        if(isEmpty()) return null;
        return heap.get(0);
    }

    //Removes the root of the heap, O(log(n))
    public T poll(){
        return removeAt(0);
    }

    //Test if an element is in heap, O(1)
    public boolean contains(T elem){
        //Map lookup to check containment, O(1)
        if(elem==null) return false;
        return map.containsKey(elem);

        //Linear scan to check containment, O(n)
        //for(int i=0;i<heapSize;i++){
        //  if(heap.get(i).equals(elem))
        //      return true;
        //return false;    
        //}
    }

    public void add(T elem){
        if(elem == null) throw new IllegalArgumentException();

        heap.add(elem);
        int indexOfLastElem = size() - 1;
        mapAdd(elem,indexOfLastElem);
        swim(indexOfLastElem);
    }

    //Test if the value of node i <= node j
    //This method assumes i and j are valid indices, O(1)
    private boolean less(int i,int j){
        T node1 = heap.get(i);
        T node2 = heap.get(j);
        return node1.compareTo(node2)<=0;
    }

    //Perform bottom up node swim, O(log(n))
    public void swim(int k){
        
        //Grab the index of the next parent node WRT to k
        int parent = (k-1)/2;
        
        // keep swimming while we have not reached the root and while we're less than our parent
        while(k>0 && less(k,parent)){
            //Exchange k with the parent
            swap(parent,k);
            k = parent;
            parent = (k-1)/2;
        }
    }

    //Top down node sink, O(log(n))
    private void sink(int k){
        int heapSize = size();


        while(true){
            int left = 2 * k + 1; // left node
            int right = 2 * k + 2; // right node
            int smallest = left;
            if(right<heapSize && less(right,left))
                smallest = right;
            
            if(left>=heapSize || less(k,smallest))
                break;
            
            swap(smallest,k);
            k = smallest;
        }
    }

    private void swap(int i,int j){
        T i_elem = heap.get(i);
        T j_elem = heap.get(j);


        heap.set(i,j_elem);
        heap.set(j,i_elem);
        mapSwap(i_elem,j_elem,i,j);
    }

    //Removes a particular element in the heap, O(log(n))
    public boolean remove(T element){
        if(element==null) return false;

        //Logarithmic removal with map, O(log(n))
        Integer index = mapGet(element);
        if(index!=null) removeAt(index);
        return index!=null;
    }

    public T removeAt(int i){
        if(isEmpty()) return null;

        int indexOfLastElem = size() - 1;
        T removed_data = heap.get(i);
        swap(i,indexOfLastElem);

        //Obliterate the value
        heap.remove(indexOfLastElem);
        mapRemove(removed_data, indexOfLastElem);

        //Removed last element
        if(i == indexOfLastElem)
            return removed_data;
        
        T elem = heap.get(i);

        // Try sinking element
        sink(i);

        //If sinking did not work try swimming
        if(heap.get(i).equals(elem))
            swim(i);

        return removed_data;
    }

    // Recursively checsks if this heap is min heap
    // This method is just for testing purposes to make
    // sure the heap invariant is still being maintained
    //Called this method with k=0 to start at the root
    public boolean isMinHeap(int k){
        //If we are outside the bounds of the heap return true
        int heapSize = size();
        if(k>heapSize){
            return true;
        }
        int left = 2*k+1;
        int right = 2*k + 2;

        //Make sure that the current node k is less than
        //both of its children left, and right if they exist
        //return false otherwise to indicate an invalid heap
        if(left<heapSize && !less(k,left)) return false;
        if(right<heapSize && !less(k,right)) return false;

        // Recurse on both children to make sure they're also valid heaps
        return isMinHeap(left) && isMinHeap(right);
    }
    //Add a node value and its index to the map
    private void mapAdd(T value,int index){
        TreeSet<Integer> set=map.get(value);

        //New value being inserted in map
        if(set == null){
            set = new TreeSet<>();
            set.add(index);
            map.put(value,set);
            //Value already exist in map
        }else set.add(index);
    }

    //Removes the index at a given value, O(log(n))
    private void mapRemove(T value,int index){
        TreeSet<Integer> set=map.get(value);
        set.remove(index);
        if(set.size()==0) map.remove(value);
    }

    //Extract an index position for the given value 
    // NOTE: If a value exists multiple times in the heap the highest index is returned (this has arbitrarily been chosen)

    private Integer mapGet(T value){
        TreeSet<Integer> set = map.get(value);
        if(set!=null) return set.last();
        return null;
    }

    //Exchange the index of two nodes internally within the map
    private void mapSwap(T val1,T val2,int val1index,int val2index){
        Set<Integer> set1=map.get(val1);
        Set<Integer> set2=map.get(val2);

        set1.remove(val1index);
        set2.remove(val2index);
        set1.add(val2index);
        set2.add(val1index);
    }

    @Override
    public String toString(){
        return heap.toString();
    }
    
}