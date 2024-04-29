/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private final Digraph graph;
    private final int vertex;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        graph = new Digraph(G);
        vertex = graph.V();
    }

    private void validateVertex(int v) {
        if (v < 0 || v > vertex) {
            throw new IllegalArgumentException();
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(graph, w);
        int length = Integer.MAX_VALUE;
        for (int i = 0; i < vertex; i += 1) {
            if (vPath.hasPathTo(i) && wPath.hasPathTo(i)) {
                length = Math.min(length, vPath.distTo(i) + wPath.distTo(i));
            }
        }
        return length == Integer.MAX_VALUE ? -1 : length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(graph, w);
        int length = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < vertex; i += 1) {
            if (vPath.hasPathTo(i) && wPath.hasPathTo(i)
                    && vPath.distTo(i) + wPath.distTo(i) < length) {
                length = vPath.distTo(i) + wPath.distTo(i);
                ancestor = i;
            }
        }
        return ancestor;
    }

    private boolean validateVertex(Iterable<Integer> v) {
        if (v == null) {
            throw new IllegalArgumentException();
        }
        int size = 0;
        for (Integer w : v) {
            size += 1;
            if (w == null) {
                throw new IllegalArgumentException();
            }
            validateVertex(w);
        }
        return size != 0;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!validateVertex(v) || !validateVertex(w)) {
            return -1;
        }
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(graph, w);
        int length = Integer.MAX_VALUE;
        for (int i = 0; i < vertex; i += 1) {
            if (vPath.hasPathTo(i) && wPath.hasPathTo(i)) {
                length = Math.min(length, vPath.distTo(i) + wPath.distTo(i));
            }
        }
        return length == Integer.MAX_VALUE ? -1 : length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!validateVertex(v) || !validateVertex(w)) {
            return -1;
        }
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(graph, w);
        int length = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < vertex; i += 1) {
            if (vPath.hasPathTo(i) && wPath.hasPathTo(i)
                    && vPath.distTo(i) + wPath.distTo(i) < length) {
                length = vPath.distTo(i) + wPath.distTo(i);
                ancestor = i;
            }
        }
        return ancestor;
    }
}
