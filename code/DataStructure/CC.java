package DataStructure;

/* CC.java - finds all the Connected Components of a graph */
public class CC {

    private boolean[] marked; 
    private int[] id; // component identifier for each vertex
    private int count; // number of connected components

    public CC(Graph graph) {
        marked = new boolean[graph.V()];
        id = new int[graph.V()];
        for (int i = 0; i < graph.V(); i += 1) {
            if (!marked[i]) {
                dfs(graph, i);
                count += 1;
            }
        }
    }

    private void dfs(Graph graph, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                dfs(graph, w);
            }
        }
    }

    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    public int count() {
        return count;
    }

    /* component identifier for v (between 0 and count() - 1) */
    public int id(int v) {
        return id[v];
    }
}
