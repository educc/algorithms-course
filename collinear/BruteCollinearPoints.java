/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LinkedStack<LineSegment> lineSegments = new LinkedStack<>();

    /**
     * finds all line segments containing 4 points
     * @param points to create segments
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) { throw new IllegalArgumentException(); }

        int size = points.length;

        for (int i = 0; i < size; i++) {
            Point p = points[i];

            int counterEquals = 0;
            for (int j = 0; j < size; j++) {
                Point q = points[j];
                double slopePQ = p.slopeTo(q);

                int c1 = p.compareTo(q);
                if (c1 == 0) counterEquals++;

                if (counterEquals > 1) throw new IllegalArgumentException();
                if (c1 != -1) continue;

                for (int k = 0; k < size; k++) {
                    Point r = points[k];
                    double slopePR = p.slopeTo(r);

                    int c2 = q.compareTo(r);
                    if (c2 != -1) continue;
                    for (int l = 0; l < size; l++) {
                        Point s = points[l];
                        double slopePS = p.slopeTo(s);

                        int c3 = r.compareTo(s);
                        if (c3 != -1) continue;
                        if (slopePQ == slopePR && slopePQ == slopePS) {
                            lineSegments.push(new LineSegment(p, s));
                        }

                    }
                }
            }

        }
    }


    /**
     * the number of line segments
     * @return total line segments
     */
    public int numberOfSegments() {
        return this.lineSegments.size();
    }

    /**
     * the line segments
     * @return all line segments found
     */
    public LineSegment[] segments() {
        LineSegment[] r = new LineSegment[lineSegments.size()];
        int i = 0;
        for (LineSegment item: lineSegments) {
            r[i++] = item;
        }
        return r;
    }
    /**
     * just for testing
     * @param args none
     */
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
        /*int expected = Integer.parseInt(args[0]);

        int size = StdIn.readInt();
        Point[] points = new Point[size];
        int i = 0;
        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();

            points[i++] = new Point(x, y);
        }

        BruteCollinearPoints collinearPoints =
                new BruteCollinearPoints(points);

        StdOut.println("line segments expected: " + expected);
        StdOut.println("line segments found: " +
                               collinearPoints.numberOfSegments());
        assert expected == collinearPoints.numberOfSegments();*/
    }
}
