import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> {
    
    private int heapSize=0;

    private int heapCapacity=0;

    private List<T> heap = null;

    public BinaryHeap(){
        this(1);
    }

    public BinaryHeap(int sz) {
        heap = new ArrayList<>(sz);
    }

    public BinaryHeap(T[] elems){
        heapSize = heapCapacity = elems.length;
        heap = new ArrayList<T>(heapCapacity);

        //Place all element in heap
        for(int i=0;i<heapSize;i++){
            heap.add(elems[i]);
        }

        //Heapify process, O(n)
        for(int i=Math.max(0,(heapSize/2)-1);i>=0;i--) sink(i);
    }

    //...Priority Queue construction, O(nlog(n))
    public BinaryHeap(Collection<T> elems){
        this(elems.size());
        for(T elem: elems) add(elem);
    }

    public boolean isEmpty(){
        return heapSize==0;
    }

    public void clear(){
        for(int i=0;i<heapCapacity;i++) heap.set(i, null);
        heapSize=0;
    }

    public int size(){
        return heapSize;
    }

    public T peek(){
        if(isEmpty()) return null;
        return heap.get(0);
    }

    public T poll(){
        return removeAt(0);
    }

    public boolean contains(T elem){
        for(int i=0;i<heapSize;i++) if(heap.get(i).equals(elem)) return true;
        return false;
    }

    public void add(T elem){
        if(elem == null) throw new IllegalArgumentException();

        if(heapSize<heapCapacity){
            heap.set(heapSize, elem);
        }else{
            heap.add(elem);
            heapCapacity++;
        }

        swim(heapSize);
        heapSize++;
    }

    private boolean less(int i,int j){
        T node1=heap.get(i);
        T node2=heap.get(j);
        return node1.compareTo(node2)<=0;
    }

    private void swim(int k){
        int parent = (k-1)/2;

        while(k>0 && less(k,parent)){
            swap(parent,k);
            k = parent;
        
            parent = (k-1)/2;
        }
    }

    private void sink(int k){
        while(true){
            int left = 2*k+1;
            int right = 2*k+2;
            int smallest = left; //Assume left is the smallest node of the two children

            //find which is smaller left or right
            //If right is smaller set smallest to be right
            if(right <heapSize && less(right,left)) smallest = right;

            //Stop if we are outside the bounds of the tree or stop early if we cannot sink k anymore
            if(left>=heapSize || less(k,smallest)) break;

            swap(smallest,k);
            k = smallest;
        }
    }

    private void swap(int i,int j){
        T elem_i = heap.get(i);
        T elem_j= heap.get(j);

        heap.set(i,elem_j);
        heap.set(j,elem_i);
    }

    public boolean remove(T element){
        if(element == null) return false;
        //Linear removal via search, O(n)

        for(int i=0;i<heapSize;i++){
            if(element.equals(heap.get(i))){
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    private T removeAt(int i){
        if(isEmpty()) return null;

        heapSize--;
        T removed_data = heap.get(i);
        swap(i,heapSize);

        //Obliterate the value
        heap.set(heapSize,null);

        if(i==heapSize) return removed_data;
        T elem = heap.get(i);

        sink(i);
        return removed_data;
    }

    //Recursively checks if this heap is a min heap
    // This method is just for testing purposes to make 
    // sure the heap invariant is still being maintained 
    //Called this method with k=0 to start at the root
    public boolean isMinHeap(int k){
        //If we are outside the bounds of the heap return true
        if(k>=heapSize) return true;
        int left = 2*k+1;
        int right = 2*k+2;

        //Make sure that the current node k is less than
        //both of its children left, and right if they exist 
        //return false otherwise to indicate an invalid heap
        if(left<heapSize && !less(k,left))return false;
        if(right<heapSize && !less(k,right))return false;

        return isMinHeap(left) && isMinHeap(right);
    }

    @Override
    public String toString(){
        return heap.toString();
    }
}