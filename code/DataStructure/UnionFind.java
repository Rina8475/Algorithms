package DataStructure;

/** UnionFind.java - 实现并查集的数据结构 */
public class UnionFind {
    private int[] id;       // 存放父节点的索引 根节点内存放自己的索引 id[root] == root
    private int[] sz;       // 存放以这个节点为根的树的重量

    /* 此算法使用一棵树来表示一个集合 */
    public UnionFind(int n) {
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i += 1) { 
            id[i] = i;
            sz[i] = 1;
        }
    }

    /* union - 将两个元素所在的集合合并为同一个集合 */
    public void union(int p, int q) {
        int root_p = root(p);
        int root_q = root(q);

        if (root_p == root_q)       // 例外情况 需要注意
            return;
        if (sz[root_p] <= sz[root_q]) {
            id[p] = q;
            sz[root_q] += sz[root_p];
        } else {
            id[q] = p;
            sz[root_p] += sz[root_q];
        }
    }

    /* connected - 如果两个元素在同一个集合中 返回 true */
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    /* root - 查找元素 p 所在树的根节点 
     * 一种更快的的改进是在 while 循环中增添如下一行
     * id[p] = id[id[p]] */
    private int root(int p) {
        while (p != id[p])
            p = id[p];
        return p;
    }

    public static void main(String[] args) {
        UnionFind uf = new UnionFind(10);
        uf.union(4, 3);
        uf.union(3, 8);
        uf.union(6, 5);
        uf.union(9, 4);
        uf.union(2, 1);
        assert !uf.connected(0, 7);
        assert uf.connected(8, 9);
        uf.union(5, 0);
        uf.union(7, 2);
        uf.union(6, 1);
        uf.union(1, 0);
        assert uf.connected(0, 7);
    }
}
