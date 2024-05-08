package DataStructure;

import java.util.Arrays;

public class PrimMST {
    private Edge[] edgeTo;          // the edge that connects the MST to each vertex
    private double[] distTo;        // the distance from the source to each vertex
    private boolean[] marked;       // the vertices that have been included in the MST
    private IndexMinPQ<Double> pq;    // the priority queue of edges
    
    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);

        distTo[0] = 0.0;
        pq.insert(0, 0.0);
        while (!pq.isEmpty()) {
            visit(G, pq.delMin());
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) {
                continue;
            }
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w)) {
                    pq.change(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }

    public Iterable<Edge> edges() {
        Bag<Edge> mst = new Bag<Edge>();
        for (Edge e : edgeTo) {
            mst.add(e);
        }
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edgeTo) {
            weight += e.weight();
        }
        return weight;
    }
}
