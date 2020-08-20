public class Tester {
    public static void main(String[] args){
        final ListI<Integer> list = new SinglyLL<Integer>();
        int n = 10;
        for (int i = 0; i < n; i++) {
            list.addFirst(i);
        }
        // for (int i = n - 1; i >= 0; i--) {
        // }
        // for (int i = 0; i < n; i++) {
        // }
    }
}