public class SinglyLL<E> implements ListI<E> {
    class Node<E> {
        E data;
        Node<E> next;

        public Node(E obj) {
            data = obj;
            next = null;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int currentSize;

    public SinglyLL() {
        head = null;
        currentSize = 0;
    }

    public void addFirst(E obj) {
        Node<E> node = new Node<E>(obj);
        node.next = head;
        head = node;
        currentSize++;
    }

    public void addLast(E obj) {
        // Opt:1 O(n) complexity
        Node<E> tmp = head;
        Node<E> node = new Node<E>(obj);
        if (head == null) {
            head = node;
            currentSize++;
            return;
        }
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = node;
        currentSize++;

        // Opt:2 O(1) complexity (Preferred) Tail pointer is an overhead so...
        // Node<E> node = new Node<E>(obj);
        // if(head==null){
        // head=tail=node;
        // currentSize++;
        // return;
        // }
        // tail.next=node;
        // tail = node;
        // currentSize++;
    }

    public E removeFirst() {
        if (head == null)
            return null;
        E tmp = head.data;
        if (head == tail)
            head = tail = null;
        else
            head = head.next;
        currentSize--;
        return tmp;
    }

    public E removeLast() {
        Node<E> current = head, previous = null;
        if (head == null)
            return null;
        if (current == tail)
            return removeFirst();
        while (current != tail) {
            previous = current;
            current = current.next;
        }
        previous.next = null;
        tail = previous;
        currentSize--;
        return current.data;
    }

    public E remove(E obj) {
        Node<E> current = head, previous = null;
        while (current != null) {
            if (((Comparable<E>) obj).compareTo(current.data) == 0) {
                if (current == head)
                    return removeFirst();
                if (current == tail)
                    return removeLast();
                currentSize--;
                previous.next = current.next;
                return current.data;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    public boolean contains(E obj) {
        Node<E> current = head;
        while (current != null) {
            if (((Comparable<E>) obj).compareTo(current.data) == 0) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}

