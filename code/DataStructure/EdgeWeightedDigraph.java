package DataStructure;

public class EdgeWeightedDigraph {
    private final int V;
    private Bag<DirectedEdge>[] adj;
    private int E;

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v += 1) {
            adj[v] = new Bag<DirectedEdge>();
        }
    }

    public int V() { return V; }

    public int E() { return E; }

    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E += 1;
    }

    public Iterable<DirectedEdge> adj(int v) 
    { return adj[v]; }

    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> edges = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v += 1) {
            for (DirectedEdge e : adj[v]) {
                edges.add(e);
            }
        }
        return edges;
    }
}
