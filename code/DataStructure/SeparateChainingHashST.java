package DataStructure;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SeparateChainingHashST<Key, Value> extends SymbolTable<Key, Value> {
    private SequentialSearchST<Key, Value>[] st;
    private int size;       /* 键值对总数 */
    private int M;          /* 哈希表大小 */
    private final static int INIT_CAPACITY = 997;

    public SeparateChainingHashST() 
    { this(INIT_CAPACITY); }

    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i += 1)
            st[i] = new SequentialSearchST<Key, Value>();
    }

    private int hash(Key key) 
    { return (key.hashCode() & 0x7fffffff) % M; }

    private void resize(int cap) {
        SeparateChainingHashST<Key, Value> t;
        t = new SeparateChainingHashST<Key, Value>(cap);
        for (int i = 0; i < M; i += 1)
            for (Key key : st[i].keys())
                t.put(key, st[i].get(key));
        st = t.st;
        M  = t.M;
    }

    public void put(Key key, Value val) {
        if (val == null)
            delete(key);

        if (size >= 8 * M)
            resize(2 * M);
        int i = hash(key);
        if (!st[i].contains(key))   
            size += 1;
        st[hash(key)].put(key, val);
    }

    public Value get(Key key) {
        return st[hash(key)].get(key);
    }

    public void delete(Key key) {
        int i = hash(key);
        if (st[i].contains(key))
            size -= 1;
        st[i].delete(key);
        if (M > INIT_CAPACITY && size <= 2 * M)
            resize(M / 2);
    }

    @Override
    public int size() {
        return size;
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i += 1)
            for (Key key : st[i].keys())
                queue.enqueue(key);
        return queue;
    }

    public static void main(String[] args) {
        int distinct = 0, words = 0;
        int minlen = Integer.parseInt(args[0]);
        BST<String, Integer> st = new BST<String, Integer>();

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
