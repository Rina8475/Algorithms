package DataStructure;

import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int N;

    private class Node {
        Item item;
        Node next;

        Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }
    
    public boolean isEmpty() { return N == 0; }
    public int size() { return N; }

    public void enqueue(Item item) {
        Node oldHead = head;
        head = new Node(item, null);
        if (isEmpty()) {
            tail = head;
        } else {
            oldHead.next = head;
        }
        N += 1;
    }
    
    public Item dequeue() {
        Node node = tail;
        N -= 1;
        if (isEmpty()) {
            head = tail = null;
        } else {
            tail = tail.next;
        }
        return node.item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node first = tail;

        @Override
        public boolean hasNext() {
            return first != null;
        }
        @Override
        public Item next() {
            Node node = first;
            first = first.next;
            return node.item;
        }
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<String>();
        String[] str_set = new String[] {"to", "be", "or", "not", "to", "-", "be", 
            "-", "-", "that", "-", "-", "-", "is"};
        for (String s: str_set) {
            if (s.equals("-")) 
                System.out.println(queue.dequeue());
            else queue.enqueue(s);
        }
        System.out.println(queue.size());
        for (String s: queue)
            System.out.println(s);
    }
}
