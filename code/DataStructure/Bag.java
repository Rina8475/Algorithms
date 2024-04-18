package DataStructure;

import java.util.Iterator;

/** Bag - 一个可迭代的集合
 * 使用链表实现 */
public class Bag<Item> implements Iterable<Item> {
    private Node head = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;

        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    public void add(Item item) {
        head = new Node(item, head);
        size += 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node first = head;

        public boolean hasNext() {
            return first != null;
        }

        public Item next() {
            Node oldFirst = first;
            first = first.next;
            return oldFirst.item;
        }
    }
}
