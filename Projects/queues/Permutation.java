/* *****************************************************************************
 *  Name:           Rina
 *  Date:           2023/9/23
 *  Description:    Implement class Permutation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> que = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            que.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i += 1)
            StdOut.println(que.dequeue());
    }
}
