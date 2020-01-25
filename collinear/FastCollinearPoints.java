/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private LinkedStack<LineSegment> segments = new LinkedStack<>();
    private final int MIN_COLLINEAR_POINTS = 4;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        Arrays.sort(points, makeComparatorPointValidator());

        int size = points.length;
        for (int i = 0; i < size; i++) {
            Point origin = points[i];
            PointAndSlope[] pointAndSlopes =
                    makeSlopes(origin, points, i + 1, size);
            findAndAddSegments(origin, pointAndSlopes);
        }
    }

    private static class PointAndSlope implements Comparable<PointAndSlope> {
        private Point point;
        private double slope;

        public PointAndSlope(Point point, double slope) {
            this.point = point;
            this.slope = slope;
        }

        public Point getPoint() {
            return point;
        }

        public double getSlope() {
            return slope;
        }

        public int compareTo(PointAndSlope pointAndSlope) {
            if (slope < pointAndSlope.slope) {
                return -1;
            }
            if (slope == pointAndSlope.slope) {
                return 0;
            }
            return 1;
        }
    }

    private static Comparator<Point> makeComparatorPointValidator() {
        return (point, t1) -> {
            int i = point.compareTo(t1);
            if (i == 0) {
                throw new IllegalArgumentException();
            }
            return i;
        };
    }

    private static PointAndSlope[] makeSlopes(
            Point origin, Point[] points, int low, int high) {
        PointAndSlope[] result = new PointAndSlope[high - low];

        int j = 0;
        for (int i = low; i < high; i++) {
            Point to = points[i];
            result[j++] = new PointAndSlope(to, origin.slopeTo(to));
        }
        Arrays.sort(result);
        return result;
    }

    private void findAndAddSegments(Point origin,
                                    PointAndSlope[] pointAndSlope) {
        int min = MIN_COLLINEAR_POINTS - 1;
        int size = pointAndSlope.length;
        if (size < min) {
            return;
        }

        Point higher = origin;

        // comparison and counter
        int countEquals = 0;
        double lastSlope = pointAndSlope[0].getSlope();

        // loop
        for (int i = 1; i < size; i++) {
            PointAndSlope current = pointAndSlope[i];
            if (lastSlope == current.getSlope()) {
                countEquals++;
            } else {
                countEquals = 0;
                higher = origin;
            }

            if (current.getPoint().compareTo(higher) > 0) {
                higher = current.getPoint();
            }

            if (countEquals == min-1) { // add segment
                segments.push(new LineSegment(origin, higher));
            }
            lastSlope = current.getSlope();
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] r = new LineSegment[segments.size()];
        int i = 0;
        for (LineSegment item : segments) {
            r[i++] = item;
        }
        return r;
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
