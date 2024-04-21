/* *****************************************************************************
 *  Name:           Rina
 *  Date:           2024/3/28
 *  Description:    Implement PointSET class
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : set)
            p.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D p : set)
            if (rect.contains(p))
                queue.enqueue(p);
        return queue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (set.isEmpty())
            return null;
        Point2D result = set.min();
        double distance = p.distanceSquaredTo(result);
        double newDistance;
        for (Point2D h : set) {
            newDistance = p.distanceSquaredTo(h);
            if (newDistance < distance) {
                result = h;
                distance = newDistance;
            }
        }

        return result;
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
