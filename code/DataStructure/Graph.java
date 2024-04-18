package DataStructure;

/** Graph - 无向图的实现
 * 使用邻接表实现 */
public class Graph {
    private final int V;
    private int E = 0;
    private Bag<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i += 1) {
            adj[i] = new Bag<Integer>();
        }
    }

    /* 返回图中的顶点数 */
    public int V() { return V; }

    /* 返回图中的边数 */
    public int E() { return E; }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E += 1;
    }

    public Iterable<Integer> adj(int v) 
    { return adj[v]; }

    public String toString() {
        String s = V + "vertices, " + E + " edges\n";
        for (int v = 0; v < V; v += 1) {
            s += v + ": ";
            for (int w : this.adj(v)) {
                s += w + "";
            }
            s += "\n";
        }
        return s;
    }
}
