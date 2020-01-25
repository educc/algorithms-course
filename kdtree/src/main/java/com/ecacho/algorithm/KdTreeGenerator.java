package com.ecacho.algorithm; /******************************************************************************
 *  Compilation:  javac KdTreeGenerator.java
 *  Execution:    java KdTreeGenerator n
 *  Dependencies: 
 *
 *  Creates n random points in the unit square and print to standard output.
 *
 *  % java KdTreeGenerator 5
 *  0.195080 0.938777
 *  0.351415 0.017802
 *  0.556719 0.841373
 *  0.183384 0.636701
 *  0.649952 0.237188
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KdTreeGenerator {

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            sb.append(String.format("%8.6f %8.6f\n", x, y));
            //StdOut.printf("%8.6f %8.6f\n", x, y);
        }
        createFileWithContent("input100.txt", sb.toString());
    }

    private static void createFileWithContent(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.append(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
