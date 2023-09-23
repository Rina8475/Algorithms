/* *****************************************************************************
 *  Name:           Rina
 *  Date:           2023/9/23
 *  Description:    Implement class Deque
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;

    private class Node {
        Item item;
        Node next, prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldfirst;
        if (isEmpty())
            last = first;
        else
            oldfirst.prev = first;
        size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty())
            first = last;
        else
            oldlast.next = last;
        size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        size -= 1;
        if (isEmpty())
            last = null;
        else
            first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        size -= 1;
        if (isEmpty())
            first = null;
        else
            last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        Node ptr = first;

        public boolean hasNext() {
            return ptr != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = ptr.item;
            ptr = ptr.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> que = new Deque<String>();

        while (!StdIn.isEmpty()) {
            que.addFirst(StdIn.readString());
        }
        StdOut.println("Output 1: ");
        for (String s : que) {
            StdOut.printf("%s ", s);
        }
        StdOut.println();
        StdOut.println("Output 2: ");
        while (!que.isEmpty()) {
            StdOut.printf("%s %d ", que.removeLast(), que.size());
        }
    }
}
