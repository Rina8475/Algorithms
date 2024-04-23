package DataStructure;

/** TopologicalX - Topological Sorting, Implements by Queue */
public class TopologicalX {
    private Queue<Integer> order;

    public TopologicalX(Digraph G) {
        int[] indegrees = new int[G.V()];
        Queue<Integer> queue = new Queue<Integer>();
        int count = 0;  // record the number of vertices in the order
        order = new Queue<Integer>();

        for (int v = 0; v < G.V(); v += 1) {
            for (int w : G.adj(v)) {
                indegrees[w] += 1;
            }
        }
        for (int v = 0; v < G.V(); v += 1) {
            if (indegrees[v] == 0) {
                queue.enqueue(v);
            }
        }

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            order.enqueue(v);
            count += 1;
            for (int w : G.adj(v)) {
                indegrees[w] -= 1;
                if (indegrees[w] == 0) {
                    queue.enqueue(w);
                }
            }
        }

        if (count!= G.V()) {
            order = null;
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean isDAG() {
        return order == null;
    }
}
