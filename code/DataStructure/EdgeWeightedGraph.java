package DataStructure;

public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private Bag<Edge>[] adj;
    
    public EdgeWeightedGraph(int v) {
        V = v;
        E = 0;
        adj = (Bag<Edge>[]) new Bag[v];
        for (int i = 0; i < v; i += 1) {
            adj[i] = new Bag<Edge>();
        } 
    }

    public  int V() {
        return V;
    }

    public  int E() {
        return E;
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E += 1;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> b = new Bag<Edge>();
        for (int v = 0; v < V; v += 1) {
            for (Edge e : adj[v]) {
                if (e.other(v) > v) {  // avoid duplicate edges and self-loops
                    b.add(e);
                }
            }
        }
        return b;
    }
}
