/* *****************************************************************************
 *  Name:           Rina
 *  Date:           2023/9/23
 *  Description:    Implement class RandomizedQueue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] list;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        list = (Item[]) new Object[8];
        size = 0;
    }

    private void exchange(int i, int j) {
        Item temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < size; i += 1)
            temp[i] = list[i];
        list = temp;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        list[size] = item;
        size += 1;
        if (size == list.length)
            resize(list.length * 2);
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item;
        exchange(StdRandom.uniformInt(size), size - 1);
        item = list[size - 1];
        list[size - 1] = null;
        size -= 1;
        if (size == list.length / 4)
            resize(list.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        exchange(StdRandom.uniformInt(size), size - 1);
        return list[size - 1];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandQueIterator();
    }

    private class RandQueIterator implements Iterator<Item> {
        private Item[] s;
        private int size;

        public RandQueIterator() {
            size = size();
            s = (Item[]) new Object[size];
            for (int i = 0; i < size; i += 1)
                s[i] = list[i];
            StdRandom.shuffle(s);
        }

        public boolean hasNext() {
            return size > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return s[--size];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> que = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            que.enqueue(StdIn.readString());
        }
        StdOut.println("Output 1: ");
        for (String s : que) {
            StdOut.printf("%s ", s);
        }
        StdOut.println();

        StdOut.println("Output 2: ");
        for (String s : que) {
            StdOut.printf("%s ", s);
        }
        StdOut.println();

        StdOut.println("Output 3: ");
        while (!que.isEmpty()) {
            StdOut.printf("%s %d ", que.dequeue(), que.size());
        }
        que.enqueue("Hello1");
        que.dequeue();
        que.enqueue("Hello2");
        que.enqueue("Hello3");
        que.enqueue("Hello4");
        que.enqueue("Hello5");
    }
}
