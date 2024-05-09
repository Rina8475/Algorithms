package DataStructure;

import java.util.PriorityQueue;

public class KruskalMST {
    private Queue<Edge> mst;

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<Edge>();
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
        UnionFind uf = new UnionFind(G.V());
        for (Edge e : G.edges()) {
            pq.add(e);
        }

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.poll();
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) {
                continue;
            }
            uf.union(v, w);
            mst.enqueue(e);
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : mst) {
            weight += e.weight();
        }
        return weight;
    }
}
