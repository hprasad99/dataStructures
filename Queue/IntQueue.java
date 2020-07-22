public class IntQueue{
    private int[] ar;
    private int front,end,sz;

    public IntQueue(int maxSize){
        front = end = 0;
        sz = maxSize +1;
        ar = new int[sz];
    }

    public boolean isEmpty(){
        return front == end;
    }
    public int size(){
        if(front>end) return (end + sz - front);
        return end - front;
    }

    public int peek(){
        return ar[front];
    }

    public void enqueue(int value){
        ar[end] = value;
        if(++end==sz)end=0;
        if(end == front) throw new RuntimeException("Queue too small");
    }

    public int dequeue(){
        int ret_val = ar[front];
        if(++front == sz) front = 0;
        return ret_val;
    }

    public static void main(String[] args){
        IntQueue q = new IntQueue(5);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(51);

        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());

        System.out.println(q.isEmpty());

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        System.out.println(q.isEmpty());
        System.out.println(q.size());
        System.out.println(q.peek());

    }
}