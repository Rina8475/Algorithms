package DataStructure;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 *  Data files:   https://algs4.cs.princeton.edu/31elementary/tinyTale.txt
 *                https://algs4.cs.princeton.edu/31elementary/tale.txt
 *                https://algs4.cs.princeton.edu/31elementary/leipzig100K.txt
 *                https://algs4.cs.princeton.edu/31elementary/leipzig300K.txt
 *                https://algs4.cs.princeton.edu/31elementary/leipzig1M.txt
 * 
 * % java BST 1 < tinyTale.txt
 * it 10
 *
 * % java BST 8 < tale.txt
 * business 122
 *
 * % java BST 10 < leipzig1m.txt
 * government 24763
 */
public class BST<Key extends Comparable<Key>, Value> extends OrderedSymbolTable<Key, Value> {
    private Node root;

    private class Node {
        public Key key;
        public Value val;
        public Node left, right;
        public int N;       /* 以该结点为根的树的总结点数 */

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N   = N;
        }
    }

    @Override
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node node, Key key, Value val) {
        if (node == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(node.key);
        if      (cmp < 0)   node.left  = put(node.left, key, val);
        else if (cmp > 0)   node.right = put(node.right, key, val);
        else                node.val   = val;
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null)
            return null;
        int cmp = key.compareTo(node.key);
        if      (cmp < 0)   return get(node.left, key);
        else if (cmp > 0)   return get(node.right, key);
        else                return node.val;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        return node == null ? 0 : node.N;
    }

    @Override
    public Key min() {
        return min(root).key;
    }

    /* min - 返回子树中的最小的结点 */
    private Node min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    @Override
    public Key max() {
        return max(root).key;
    }

    /* max - 返回子树中的最大的结点 */
    private Node max(Node x) {
        if (x.right == null) 
            return x;
        return max(x.right);
    }

    @Override
    public Key floor(Key key) {
        Node node = floor(root, key);
        return node == null ? null : node.key;
    }

    private Node floor(Node node, Key key) {
        if (node == null)
            return null;
        int cmp = key.compareTo(node.key);
        if      (cmp == 0)  return node;
        else if (cmp < 0)   return floor(node.left, key);
        Node tmp = floor(node.right, key);
        return tmp == null ? node : tmp;    
    }

    @Override
    public Key ceiling(Key key) {
        Node tmp = ceiling(root, key);
        return tmp == null ? null : tmp.key;
    }

    private Node ceiling(Node node, Key key) {
        if (node == null) 
            return null;
        int cmp = key.compareTo(node.key);
        if      (cmp == 0)  return node;
        else if (cmp > 0)   return ceiling(node.right, key);   
        Node tmp = ceiling(node.left, key);
        return tmp == null ? node : tmp;
    }

    @Override
    public Key select(int k) {
        Node tmp = select(root, k);
        return tmp == null ? null : tmp.key;
    }

    private Node select(Node x, int k) {
        if (x == null)
            return null;
        int idx = size(x.left);
        if      (k < idx)   return select(x.left, k);
        else if (k > idx)   return select(x.right, k - idx - 1);
        return x;
    }

    @Override
    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null)
            return 0;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)   return rank(x.left, key);
        else if (cmp > 0)   return size(x.left) + 1 + rank(x.right, key);    
        else                return size(x.left);
    }

    @Override
    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null)
            return x.left;
        x.right = deleteMin(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    /* delete - 删除以 x 为根节点的树中的键值为 key 的结点
     * P.S. 当要删除的结点有两个子节点时，默认使用这个节点的
     * 右子树的最小节点来代替这个节点，然后删除这个结点 */
    private Node delete(Node x, Key key) {
        if (x == null)      /* base case 1. 树中查找不到要删除的结点 */
            return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)   x.left = delete(x.left, key);
        else if (cmp > 0)   x.right = delete(x.right, key);
        else {              /* base case 2. 查找到了需要删除的结点 */
            if (x.left == null)     return x.right;     // 当要删除的结点不足两个子节点时
            if (x.right == null)    return x.left;      // 直接返回另一个节点
            Node t = x;
            x = min(x.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null || lo.compareTo(x.key) > 0 || hi.compareTo(x.key) < 0)
            return;
        keys(x.left, queue, lo, hi);
        queue.enqueue(x.key);
        keys(x.right, queue, lo, hi);
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

        st.delete("business");

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
