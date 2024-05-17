package DataStructure;

public class FlowNetwork {
    private final int V;
    private int E;
    private Bag<FlowEdge>[] adj;
    
    public FlowNetwork(int n) {
        V = n;
        E = 0;
        adj = (Bag<FlowEdge>[]) new Bag[n];
        for (int i = 0; i < n; i += 1) {
            adj[i] = new Bag<FlowEdge>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        adj[w].add(e);
        E += 1;
    }

    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> b = new Bag<FlowEdge>();
        for (int v = 0; v < V; v += 1) {
            for (FlowEdge e : adj[v]) {
                b.add(e);
            }
        }
        return b;
    }
}
