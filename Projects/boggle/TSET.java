/* *****************************************************************************
 *  Name:           rina
 *  Date:           2024/6/3
 *  Description:    This class is used to implements a TrieSET, based on only
 *               uppercase letters
 **************************************************************************** */

public class TSET {
    private static final char start = 'A';  // the start char
    private static final int R = 26;    // only contains the uppercase letters
    private Node root;

    private static class Node {
        boolean val;
        Node[] next = new Node[R];
    }

    public void add(String key) {
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            x.val = true;
            return x;
        }
        char c = (char) (key.charAt(d) - start);
        x.next[c] = add(x.next[c], key, d + 1);
        return x;
    }

    public boolean contains(String key) {
        Node x = get(root, key, 0);
        return x != null && x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        char c = (char) (key.charAt(d) - start);
        return get(x.next[c], key, d + 1);
    }

    public Node get(String key) {
        return get(root, key, 0);
    }
}
