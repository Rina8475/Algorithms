/* *****************************************************************************
 *  Name:           Rina
 *  Date:           2023/10/09
 *  Description:    Implement
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> lineSet;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        lineSet = new ArrayList<LineSegment>();

        /* 检查参数是否为 null */
        if (points == null)
            throw new IllegalArgumentException();
        /* 检查数组元素是否为 null */
        for (int i = 0; i < points.length; i += 1)
            if (points[i] == null)
                throw new IllegalArgumentException();
        /* 复制数组，避免更改作为参数传入的数组 */
        points = Arrays.copyOf(points, points.length);
        
        /* 检查是否存在重复的点 */
        Arrays.sort(points);
        for (int i = 1; i < points.length; i += 1)
            if (points[i].compareTo(points[i - 1]) == 0)
                throw new IllegalArgumentException();

        /* 对于每个点，搜索其可能构成的线段，然后加入线段集 */
        for (int i = 0; i < points.length; i += 1) {
            /* 复制数组 */
            searchSegments(points[i], Arrays.copyOf(points, points.length));
        }
    }


    private void searchSegments(Point p, Point[] points) {
        ArrayList<Point> pointSet = new ArrayList<Point>();
        int i = 0, j;
        /* 将所有的点按照和点 point[i] 之间的斜率排序 */
        Arrays.sort(points, p.slopeOrder());
        while (i < points.length) {
            /* 找出共线的点 (根据斜率) */
            pointSet.add(points[i]);
            for (j = i + 1; j < points.length && p.slopeTo(points[i]) == p.slopeTo(points[j]);
                 j += 1) {
                pointSet.add(points[j]);
            }
            i = j;
            if (pointSet.size() >= 3) {     /* 找到了 4 个点及以上共线的点集 */
                /* 这个集合不包含点 p 所以 >= 3 即可 */
                /* 对这些点进行排序 (按照 p.CompareTo) */
                pointSet.sort(null);
                /* 如果点 p 不是这些点中最小的，则这个线段就重复了 (a[0] != p) */
                if (p.compareTo(pointSet.get(0)) < 0) {
                    lineSet.add(new LineSegment(p, pointSet.get(pointSet.size() - 1)));
                }
            }
            pointSet.clear();
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSet.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSet.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
