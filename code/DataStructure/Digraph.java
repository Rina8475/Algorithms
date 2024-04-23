package DataStructure;

/** Digraph - Directed Graph
 * Adjacency Table representation */
public class Digraph {
    private final int V;    // number of vertices
    private int E = 0;      // number of edges
    private Bag<Integer>[] adj; // adjacency table

    public Digraph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v += 1) {
            adj[v] = new Bag<Integer>();
        }
    }

    public int V() { return V; }

    public int E() { return E; }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        E += 1;
    }

    public Iterable<Integer> adj(int v) 
    { return adj[v]; }

    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for (int v = 0; v < V; v += 1) {
            for (int w : adj[v]) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    public String toString() {
        String s = V + " vertices, " + E + " edges\n";
        for (int v = 0; v < V; v += 1) {
            s += v + ": ";
            for (int w : adj[v]) {
                s += w + " ";
            }
            s += "\n";
        }
        return s;
    }
}
