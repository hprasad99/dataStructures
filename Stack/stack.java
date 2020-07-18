public class stack<T> implements Iterable<T>{

    private java.util.LinkedList<T> list= new java.util.LinkedList<T>();

    //Create an empty stack
    public stack(){}

    //Create a stack with an Initial element
    public stack(T firstElem){
        push(firstElem);
    }

    public int size(){
        return list.size();
    }

    //Check if the stack is empty
    public boolean isEmpty(){
        return size() == 0;
    }

    //Push an element on the stack
    public void push(T elem){
        list.addLast(elem);
    }

    //Pop an element of the stack
    //Throws an error if the stack is empty
    public T pop(){
        if(isEmpty()) throw new java.util.EmptyStackException();
        return list.removeLast();
    }

    //Peek the top of the stack without removing an element
    //Throws an exception if the stack is empty
    public T peek(){
        if(isEmpty()) throw new java.util.EmptyStackException();
        return list.peekLast();
    }

    //Allows user to iterate through the stack using an iterator
    @Override
    public java.util.Iterator<T> iterator(){
        return list.iterator();
    }

}