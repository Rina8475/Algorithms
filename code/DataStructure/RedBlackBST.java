package DataStructure;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RedBlackBST<Key extends Comparable<Key>, Value> 
        extends OrderedSymbolTable<Key, Value> {
    private final static boolean RED = true;
    private final static boolean BLACK = false;
    private Node root = null;

    private class Node {
        Key key;
        Value val;
        Node left, right;
        int N;
        boolean color;      /* 指向这个节点的链接的颜色 */

        public Node(Key key, Value val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    /* P.S. 规定空指针为黑色 */
    private boolean isRed(Node x) {
        return x == null ? false : x.color;
    }

    /* 对树进行左旋 返回左旋后的根节点 */
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    /* 对树进行右旋 返回右旋后的根节点 */
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    private void flipColors(Node x) {
        x.color = RED;
        x.left.color = BLACK;
        x.right.color = BLACK;
    }

    /* put - 将一个键值对放入表 */
    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null)
            return new Node(key, val, 1, RED);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)   x.left = put(x.left, key, val);
        else if (cmp > 0)   x.right = put(x.right, key, val);
        else                x.val = val;
        
        if (!isRed(x.left) && isRed(x.right))       x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left))    x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right))        flipColors(x);
        return x;
    }

    /* get - 根据一个键从表中查找对应的值，查找不到则返回 null */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)   return get(x.left, key);
        else if (cmp > 0)   return get(x.right, key);
        else                return x.val;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        return x == null ? 0 : x.N;
    }

    /* delete 方法过于复杂，此处跳过其实现，了解其大致思路即可 */

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

    /* keys - 返回 [lo ... hi] 区间中所有键的集合 */
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (lo.compareTo(x.key) > 0 || hi.compareTo(x.key) < 0)
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
