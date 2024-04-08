package DataStructure;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SequentialSearchST<Key, Value> extends SymbolTable<Key, Value> {
    private Node head = null;
    private int size = 0;

    private class Node {
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    /* put - 将新节点放于链表首部 */
    public void put(Key key, Value val) {
        Node node;
        for (node = head; node != null; node = node.next)
            if (key.equals(node.key)) 
                break;
        if (node == null) {
            head = new Node(key, val, head);        
            size += 1;
        } else 
            node.val = val;
    }

    /* get - 根据一个键从表中查找对应的值，查找不到则返回 null */
    public Value get(Key key) {
        Node node;
        for (node = head; node != null; node = node.next)
            if (key.equals(node.key)) 
                return node.val;
        return null;
    }

    public int size() {
        return size;
    }

    public void delete(Key key) {
        Node first = new Node(null, null, head);
        Node node = first;
        while (node.next != null && !key.equals(node.next.key)) {
            node = node.next;
        }
        if (node.next != null) {
            node.next = node.next.next;
            head = first.next;
        }
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        Node node = head;
        while (node != null) {
            queue.enqueue(node.key);
            node = node.next;
        }
        return queue;
    }

    public static void main(String[] args) {
        int distinct = 0, words = 0;
        int minlen = Integer.parseInt(args[0]);
        SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();

        // compute frequency counts
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            if (key.length() < minlen) continue;
            words++;
            if (st.contains(key)) {
                st.put(key, st.get(key) + 1);
            }
            else {
                st.put(key, 1);
                distinct++;
            }
        }

        // find a key with the highest frequency count
        String max = "";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max))
                max = word;
        }

        StdOut.println(max + " " + st.get(max));
        StdOut.println("distinct = " + distinct);
        StdOut.println("words    = " + words);
    }
}