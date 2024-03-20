package DataStructure;

import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {
    private Node head;
    private int N;

    private class Node {
        Item item;
        Node next;

        Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    public boolean isEmpty() { return head == null; }
    public int size() { return N; }

    /** push - 让一个元素入栈 
     * P.S. 入栈时需要从 head 端入栈 */
    public void push(Item item) {
        head = new Node(item, head);
        N += 1;
    }

    public Item pop() {
        Node oldHead = head;
        head = head.next;
        N -= 1;
        return oldHead.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node first = head;
        
        @Override
        public boolean hasNext() {
            return first != null;
        }
        @Override
        public Item next() {
            Node oldFirst = first;
            first = first.next;
            return oldFirst.item;
        }
    }

    public static void main(String[] args) {
        ResizingArrayStack<String> stack = new ResizingArrayStack<String>();
        String[] str_set = new String[] {"to", "be", "or", "not", "to", "-", "be", 
            "-", "-", "that", "-", "-", "-", "is"};
        for (String s: str_set) {
            if (s.equals("-")) 
                System.out.println(stack.pop());
            else stack.push(s);
        }
        System.out.println(stack.size());
        for (String s: stack)
            System.out.println(s);
    }
}
