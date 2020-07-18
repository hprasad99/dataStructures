public class IntStack{
    private int arr[];
    private int pos=0;
    public IntStack(int maxSize){
        arr = new int[maxSize];
    }
    //Returns the number of elements insize the stack
    public int size(){
        return pos;
    }
    //Returns true/false on whether the stack is empty
    public boolean isEmpty(){
        return pos==0;
    }
    //Returns the element at the top of the stack
    public int peek(){
        return arr[pos-1];
    }

    //Add an element to the top of the stack
    public void push(int value){
        arr[pos++]=value;
    }

    //Make sure you check that the stack is not empty before calling pop
    public int pop(){
        return arr[--pos];
    }
    //Example usage
    public static void main(String[] args){
        IntStack s = new IntStack(5);
        s.push(5);
        s.push(4);
        s.push(3);
        s.push(1);
        s.push(10);

        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.pop());

        s.push(3);
        s.push(4);
        s.push(5);
        while(!s.isEmpty()) System.out.println(s.pop());

    }
}