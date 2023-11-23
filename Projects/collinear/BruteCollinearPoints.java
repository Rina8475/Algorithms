/* *****************************************************************************
 *  Name:           Rina
 *  Date:           2023/10/08
 *  Description:    Implement class BruteCollinearPoints
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> lineSet;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        /* 暴力枚举所有的4个点的可能的集合 */
        for (int i = 0; i < points.length; i += 1)
            for (int j = i + 1; j < points.length; j += 1)
                for (int k = j + 1; k < points.length; k += 1)
                    for (int n = k + 1; n < points.length; n += 1)
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])
                                && points[j].slopeTo(points[k]) == points[k].slopeTo(points[n])) {
                            lineSet.add(new LineSegment(points[i], points[n]));
                            // StdOut.printf("%s -> %s -> %s -> %s\n", points[i].toString(),
                            //               points[j].toString(), points[k].toString(),
                            //               points[n].toString());
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
