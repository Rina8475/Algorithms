package DataStructure;

import java.security.Key;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LinearProbingHashST<Key, Value> extends SymbolTable<Key, Value> {
    private int size;
    private int M;
    private Key[] keys;
    private Value[] vals;

    public LinearProbingHashST() 
    { this(16); }

    public LinearProbingHashST(int N) {
        keys = (Key[]) new Object[N];
        vals = (Value[]) new Object[N];
        M = N;
    }

    private int hash(Key key) 
    { return (key.hashCode() & 0x7fffffff) % M; }

    private void resize(int N) {
        LinearProbingHashST<Key, Value> t;
        t = new LinearProbingHashST<Key, Value>(N);
        for (int i = 0; i < M; i += 1) 
            if (keys[i] != null) 
                t.put(keys[i], vals[i]);
        keys = t.keys;
        vals = t.vals;
        M    = t.M;
    }

    public void put(Key key, Value val) {
        if (size >= M / 2) 
            resize(M * 2);

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (key.equals(keys[i]))
            { vals[i] = val; return; }
        keys[i] = key;
        vals[i] = val;
        size += 1;
    }

    public Value get(Key key) {
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (key.equals(keys[i]))
                return vals[i];
        return null;
    }

    public void delete(Key key) {
        if (!contains(key)) return;
        int i;
        for (i = hash(key); !key.equals(keys[i]); i = (i + 1) % M);
        keys[i] = null;
        vals[i] = null;
        for (i = (i + 1) % M; keys[i] != null; i = (i + 1) % M) {
            Key   keyToRedo = keys[i];
            Value valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            size -= 1;
            put(keyToRedo, valToRedo);
        }
        size -= 1;
        if (size > 0 && size == M / 8)      // P.S. Avoid M becoming 0
            resize(M / 2);
    }

    public int size() {
        return size;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i += 1)
            if (keys[i] != null)
                queue.enqueue(keys[i]);
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
