/* *****************************************************************************
 *  Name:           Rina
 *  Date:           2024/4/2
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root = null;

    private class Node {
        Point2D p;
        Node left, right;
        int size;
        RectHV rect;

        public Node(Point2D p, int size, RectHV rect) {
            this.p = p;
            this.size = size;
            this.rect = rect;
        }

        public RectHV leftRect() {
            if (xAsKey)
                return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
            else
                return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
        }

        public RectHV rightRect() {
            if (xAsKey)
                return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            else
                return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
        }
    }

    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        return x == null ? 0 : x.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        root = insert(root, p, new RectHV(0, 0, 1, 1), true);
    }

    private int compare(Node x, Point2D p) {
        if (x.p.compareTo(p) == 0)
            return 0;
        double pCoordinate = x.xAsKey ? p.x() : p.y();
        return pCoordinate < x.coordinate() ? -1 : 1;
    }

    private Node insert(Node x, Point2D p, RectHV rect, boolean xAsKey) {
        if (x == null)
            return new Node(p, 1, rect);

        int cmp = compare(x, p);
        if (cmp < 0) x.left = insert(x.left, p, x.leftRect(), !xAsKey);
        else if (cmp > 0) x.right = insert(x.right, p, x.rightRect(), !xAsKey);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null)
            return false;

        int cmp = compare(x, p);
        if (cmp < 0) return contains(x.left, p);
        else if (cmp > 0) return contains(x.right, p);
        return true;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null)
            return;
        draw(x.left);
        x.p.draw();
        draw(x.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<Point2D>();
        range(root, rect, queue);
        return queue;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> queue) {
        if (x == null)
            return;
        if (rect.contains(x.p))
            queue.enqueue(x.p);
        if (rect.intersects(x.leftRect()))
            range(x.left, rect, queue);
        if (rect.intersects(x.rightRect()))
            range(x.right, rect, queue);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return nearest(root, p, root.p, p.distanceSquaredTo(root.p));
    }   // Assume root != null

    // 返回以 x 为根节点的离 p 最近的点
    private Point2D nearest(Node x, Point2D p, Point2D buf, double minDis) {
        if (x == null)
            return buf;
        if (p.distanceSquaredTo(x.p) < minDis) {
            buf = x.p;
            minDis = p.distanceSquaredTo(buf);
        }
        if (x.leftRect().distanceSquaredTo(p) <= minDis) {
            buf = nearest(x.left, p, buf, minDis);
            minDis = p.distanceSquaredTo(buf);
        }
        if (x.rightRect().distanceSquaredTo(p) <= minDis) {
            buf = nearest(x.right, p, buf, minDis);
        }
        return buf;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        // the location (x, y) of the mouse
        Point2D query = new Point2D(0.75, 0.75);

        // draw all of the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();

        // draw in red the nearest neighbor (using brute-force algorithm)
        StdDraw.setPenRadius(0.03);
        StdDraw.setPenColor(StdDraw.RED);
        brute.nearest(query).draw();
        StdDraw.setPenRadius(0.02);

        StdDraw.setPenColor(StdDraw.BLUE);
        query.draw();
    }
}
