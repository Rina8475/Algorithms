package DataStructure;

public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    /* 在 g 中找出所有起点为 s 的路径 */
    public DepthFirstPaths(Graph g, int s) {
        this.s = s;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        dfs(g, s);
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        Stack<Integer> stack = new Stack<Integer>();
        while (v != s) {
            stack.push(v);
            v = edgeTo[v];
        }
        stack.push(s);
        return stack;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(2, 4);
        
        DepthFirstPaths depthFirstPaths = new DepthFirstPaths(graph, 0);
        assert (depthFirstPaths.hasPathTo(1));
        assert (depthFirstPaths.hasPathTo(2));
        assert !(depthFirstPaths.hasPathTo(3));
        
        Iterable<Integer> path = depthFirstPaths.pathTo(4);
        System.out.println("Path from 0 to 4: ");
        for (int i : path) {
            System.out.print(i + " ");
        }
    }
}
