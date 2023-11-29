public class Main {
    public static void main(String[] args) {
        MySinglyLinkedList<Number> l = new MySinglyLinkedList<>();

        l.add(3);
        l.add(6);
        l.add(4);
        l.add(3);
        l.add(8);
        l.add(10);
        l.add(11);
        System.out.println(l); // [3, 6, 4, 3, 8, 10, 11]

        l.add(6, 100);
        l.add(0, 101);
        l.add(1, 102);
        System.out.println(l); // [101, 102, 3, 6, 4, 3, 8, 10, 11, 100]

        l.removeFirst();
        l.removeFirst();
        l.remove(1);
        System.out.println(l); // [3, 4, 3, 8, 10, 11, 100]

        l.remove(new Integer(3));
        l.remove(new Integer(3));
        System.out.println(l); // [4, 8, 10, 11, 100]

        System.out.println(l.get(4)); // 100

        l.set(4, 999);
        System.out.println(l); // [4, 8, 10, 11, 999]
    }
}
