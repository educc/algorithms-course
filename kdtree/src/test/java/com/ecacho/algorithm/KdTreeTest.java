package com.ecacho.algorithm;

import edu.princeton.cs.algs4.Point2D;
import org.junit.Test;

import static org.junit.Assert.*;

public class KdTreeTest {

    @Test
    public void insertAndContains() {
        KdTree kdTree = new KdTree();

        int counter = 0;

        // insert all
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 100; j++) {
                kdTree.insert(new Point2D(i, j));
                kdTree.insert(new Point2D(i*-1, j*-1));
                counter += 2;
            }
        }

        // verify it contains

        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 100; j++) {
                assertTrue(kdTree.contains(new Point2D(i, j)));
                assertTrue(kdTree.contains(new Point2D(i*-1, j*-1)));
            }
        }

        // verify when not exists

        assertEquals(false, kdTree.contains(new Point2D(4, 1)));
        assertEquals(false, kdTree.contains(new Point2D(0, 0)));
        assertEquals(false, kdTree.contains(new Point2D(3, 100)));


        // verify size
        assertEquals(counter, kdTree.size());
    }

    @Test
    public void insertFromAssignment() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(.7,.2));
        kdTree.insert(new Point2D(.5,.4));
        kdTree.insert(new Point2D(.2,.3));
        kdTree.insert(new Point2D(.4,.7));
        kdTree.insert(new Point2D(.9,.6));

        assertEquals(5, kdTree.size());
    }

    @Test
    public void nearestTopLeft() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(.9,.1));
        kdTree.insert(new Point2D(.8,.2));
        kdTree.insert(new Point2D(.7,.3));

        Point2D actual = kdTree.nearest(new Point2D(.1, .9));
        Point2D expected = new Point2D(.7,.3);
        assertEquals(expected, actual);
    }


    @Test
    public void nearestManual() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.372,0.497));
        kdTree.insert(new Point2D(0.564,0.413));
        kdTree.insert(new Point2D(0.226,0.577));
        kdTree.insert(new Point2D(0.144,0.179));
        kdTree.insert(new Point2D(0.083,0.510));
        kdTree.insert(new Point2D(0.320,0.708));
        kdTree.insert(new Point2D(0.417,0.362));
        kdTree.insert(new Point2D(0.862,0.825));
        kdTree.insert(new Point2D(0.785,0.725));
        kdTree.insert(new Point2D(0.499,0.208));

        Point2D query = new Point2D(0.4, 0.7);
        Point2D expected = new Point2D(0.320,0.708);
        Point2D actual = kdTree.nearest(query);

        assertEquals(expected, actual);
    }

    @Test
    public void nearestManual02() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.372,0.497));
        kdTree.insert(new Point2D(0.564,0.413));
        kdTree.insert(new Point2D(0.226,0.577));
        kdTree.insert(new Point2D(0.144,0.179));
        kdTree.insert(new Point2D(0.083,0.510));
        kdTree.insert(new Point2D(0.320,0.708));
        kdTree.insert(new Point2D(0.417,0.362));
        kdTree.insert(new Point2D(0.862,0.825));
        kdTree.insert(new Point2D(0.785,0.725));
        kdTree.insert(new Point2D(0.499,0.208));

        Point2D query = new Point2D(0.2, 0.4);
        Point2D expected = new Point2D(0.083,0.510);
        Point2D actual = kdTree.nearest(query);

        assertEquals(expected, actual);
    }


    @Test
    public void nearestManual03() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.372,0.497));
        kdTree.insert(new Point2D(0.564,0.413));
        kdTree.insert(new Point2D(0.226,0.577));
        kdTree.insert(new Point2D(0.144,0.179));
        kdTree.insert(new Point2D(0.083,0.510));
        kdTree.insert(new Point2D(0.320,0.708));
        kdTree.insert(new Point2D(0.417,0.362));
        kdTree.insert(new Point2D(0.862,0.825));
        kdTree.insert(new Point2D(0.785,0.725));
        kdTree.insert(new Point2D(0.499,0.208));

        Point2D query = new Point2D(0.39, 0.12);
        Point2D expected = new Point2D(0.499,0.208);
        Point2D actual = kdTree.nearest(query);

        assertEquals(expected, actual);
    }
    @Test
    public void nearestManual04() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.372,0.497));
        kdTree.insert(new Point2D(0.564,0.413));
        kdTree.insert(new Point2D(0.226,0.577));
        kdTree.insert(new Point2D(0.144,0.179));
        kdTree.insert(new Point2D(0.083,0.510));
        kdTree.insert(new Point2D(0.320,0.708));
        kdTree.insert(new Point2D(0.417,0.362));
        kdTree.insert(new Point2D(0.862,0.825));
        kdTree.insert(new Point2D(0.785,0.725));
        kdTree.insert(new Point2D(0.499,0.208));

        Point2D query = new Point2D(0.35, 0.22);
        Point2D expected = new Point2D(0.499,0.208);
        Point2D actual = kdTree.nearest(query);

        assertEquals(expected, actual);
    }


    @Test
    public void nearestManual05() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.372,0.497));
        kdTree.insert(new Point2D(0.564,0.413));
        kdTree.insert(new Point2D(0.226,0.577));
        kdTree.insert(new Point2D(0.144,0.179));
        kdTree.insert(new Point2D(0.083,0.510));
        kdTree.insert(new Point2D(0.320,0.708));
        kdTree.insert(new Point2D(0.417,0.362));
        kdTree.insert(new Point2D(0.862,0.825));
        kdTree.insert(new Point2D(0.785,0.725));
        kdTree.insert(new Point2D(0.499,0.208));

        Point2D query = new Point2D(0.34, 0.19);
        Point2D expected = new Point2D(0.499,0.208);
        Point2D actual = kdTree.nearest(query);

        assertEquals(expected, actual);
    }

}