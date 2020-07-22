public class Queue<T> implements Iterable<T>{
    private java.util.LinkedList<T> list = new java.util.LinkedList<T>();

    public Queue(){}
    public Queue(T firstElement){
        offer(firstElement);
    }

    public int size(){
        return list.size();
    }

    public boolean isEmpty(){
        return size()==0;
    }

    public T peek(){
        if(isEmpty()) throw new RuntimeException("Queue Empty");
        return list.peekFirst();
    }

    public T poll(){
        if(isEmpty()) throw new RuntimeException("Queue Empty");
        return list.removeFirst();
    }

    public void offer(T ele){
        list.addLast(ele);
    }

    @Override
    public java.util.Iterator<T> iterator(){
        return list.iterator();
    }
    
    
    public static void main(String[] args){
        Queue q = new Queue(5);
        q.offer(1);
        q.offer(-2);
        q.offer(3);
        q.offer(4);
    

        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());

        System.out.println(q.isEmpty());

        q.offer(1);
        q.offer(2);
        q.offer(3);

        System.out.println(q.isEmpty());
        System.out.println(q.size());
        System.out.println(q.peek());

    }
}