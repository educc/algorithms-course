/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        int i = compareTo(that);
        // these are equals
        if (i == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        // it's vertical
        if (x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        return ((double) (that.y - y)) / (that.x - x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (y < that.y || (y == that.y && x < that.x)) {
            return -1;
        }
        if (x == that.x && y == that.y) {
            return 0;
        }
        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            public int compare(Point point, Point t1) {
                double slope1 = slopeTo(point);
                double slope2 = slopeTo(t1);
                if (slope1 < slope2) {
                    return -1;
                }
                if (slope1 == slope2) {
                    return 0;
                }
                return 1;
            }
        };
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {

        Point[] points = new Point[] {
                new Point(1, 1),
                new Point(3, 1),
                new Point(2, 2),
                new Point(1, 3),
                new Point(3, 3),
                };
        int left_down = 0;
        int right_down = 1;
        int center = 2;
        int left_top = 3;
        int right_top = 4;

        // compare test
        StdOut.println("Compare tests:");
        StdOut.println("**************");

        StdOut.println("less");
        assert -1 ==
                points[left_down].compareTo(points[right_top]);

        StdOut.println("higher");
        assert 1 ==
                points[left_top].compareTo(points[right_down]);

        StdOut.println("equal");
        assert 0 ==
                points[center].compareTo(points[center]);

        StdOut.println("other");
        assert -1 ==
                points[left_down].compareTo(points[right_down]);


        // slope test
        StdOut.println();
        StdOut.println("Slope tests:");
        StdOut.println("**************");

        StdOut.println("slope at same point");
        assert Double.NEGATIVE_INFINITY ==
                points[center].slopeTo(points[center]);

        StdOut.println("vertical");
        assert Double.POSITIVE_INFINITY ==
                points[left_down].slopeTo(points[left_top]);

        StdOut.println("horizontal");
        assert 0 ==
                points[left_down].slopeTo(points[right_down]);

        StdOut.println("other");
        assert ((3 - 1.0d) / (3 - 1.0d)) ==
                points[left_down].slopeTo(points[right_top]);

        // comparator test
        StdOut.println();
        StdOut.println("Comparator tests:");
        StdOut.println("**************");


        StdOut.println("center - top left - top right");
        Comparator<Point> comparator = points[center].slopeOrder();
        assert -1 == comparator.compare(
                points[left_top], points[right_top]);

        StdOut.println("center - bottom left - bottom right");
        assert 1 == comparator.compare(
                points[left_down], points[right_down]);

        StdOut.println("top right - top left - bottom right");
        comparator = points[right_top].slopeOrder();
        assert -1 == comparator.compare(
                points[left_top], points[right_down]);

        StdOut.println("bottom left - top left - bottom right");
        comparator = points[left_down].slopeOrder();
        assert 1 == comparator.compare(
                points[left_top], points[right_down]);

        StdOut.println("all same");
        comparator = points[center].slopeOrder();
        assert 0 == comparator.compare(
                points[center], points[center]);
    }
}
