package DataStructure;

/** Ternary search tries, the full version of this file can see
 * https://algs4.cs.princeton.edu/52trie/TST.java.html
 */
public class TST<Value> {
    private Node root;
    private class Node {
        char c;
        Node left, mid, right;
        Value val;
    }

    /** This method assume the length of <code>key</code> is greater than 0,
     * i.e. key.length() > 0
     */
    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) {    // base case
            x = new Node();
            x.c = key.charAt(d);
        }
        char c = key.charAt(d);
        if (c < x.c) {
            x.left = put(x.left, key, val, d);
        } else if (c > x.c) {
            x.right = put(x.right, key, val, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, val, d + 1);
        } else {
            x.val = val;
        }
        return x;
    }

    /** this method assume the length of <code>key</code> is greater than 0, 
     * i.e. key.length() > 0
     */
    public Value get(String key) {
        Node x = get(root, key, 0);
        return x == null ? null : x.val;
    }

    /** d is the number of characters which has matched in the trie */
    private Node get(Node x, String key, int d) {
        if (x == null) {    // base case
            return null;
        }
        char c = key.charAt(d);
        if (c < x.c) {
            return get(x.left, key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        }
        return x;
    }
}
