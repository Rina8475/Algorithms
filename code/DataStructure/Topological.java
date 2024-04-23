package DataStructure;

/** Topological - Topological Sorting, Implements by Stack */
public class Topological {
    private Iterable<Integer> order = null;
    
    public Topological(Digraph G) {
        DirectedCycle cyclefinder = new DirectedCycle(G);
        if (!cyclefinder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePostorder();
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    /* isDAG - if G is a directed acyclic graph, return true */
    public boolean isDAG() {
        return order != null;
    }
}
