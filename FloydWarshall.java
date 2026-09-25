import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Floyd-Warshall all-pairs shortest path algorithm (O(V^3)), including
 * reconstruction of the actual shortest path (not just its length) between
 * every pair of vertices via a predecessor matrix.
 *
 * Reads one or more weighted-graph problems from an input file (see
 * sample-graph.txt for the format) and writes the distance/path results to
 * output.txt.
 *
 * Usage:
 *   javac FloydWarshall.java
 *   java FloydWarshall sample-graph.txt
 */
public class FloydWarshall {

    public static void floydAlgorithm(int[][] dist, int[][] pred, int n, PrintWriter output) {
        for (int i = 0; i < n; i++) {
            Arrays.fill(pred[i], 0);
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        pred[i][j] = k + 1; // 1-based vertex numbering
                    }
                }
            }
        }

        output.println("Predecessor matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                output.print(pred[i][j] + " ");
            }
            output.println();
        }
        output.println();

        for (int i = 0; i < n; i++) {
            output.println("V" + (i + 1) + "-Vj: shortest path and length");
            for (int j = 0; j < n; j++) {
                printPath(i, j, pred, output);
                output.println(": " + dist[i][j]);
            }
            output.println();
        }
    }

    private static void printPath(int start, int end, int[][] pred, PrintWriter output) {
        output.print("V" + (start + 1) + " ");
        printIntermediatePath(start, end, pred, output);
        if (start != end) {
            output.print("V" + (end + 1) + " ");
        }
    }

    private static void printIntermediatePath(int start, int end, int[][] pred, PrintWriter output) {
        if (pred[start][end] != 0) {
            printIntermediatePath(start, pred[start][end] - 1, pred, output);
            output.print("V" + pred[start][end] + " ");
            printIntermediatePath(pred[start][end] - 1, end, pred, output);
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java FloydWarshall <graph-file>");
            System.exit(1);
        }

        File inputFile = new File(args[0]);
        if (!inputFile.exists()) {
            System.err.println("Error: the graph file " + args[0] + " does not exist.");
            System.exit(1);
        }

        try (Scanner scanner = new Scanner(inputFile);
             PrintWriter output = new PrintWriter("output.txt")) {

            while (scanner.hasNextLine()) {
                String problemLine = scanner.nextLine().trim();
                if (!problemLine.startsWith("Problem")) {
                    continue;
                }
                output.println(problemLine);

                String[] parts = problemLine.split("n = ");
                if (parts.length < 2) {
                    System.err.println("Error: 'n' is not specified in: " + problemLine);
                    System.exit(1);
                }
                int n = Integer.parseInt(parts[1].trim());

                int[][] dist = new int[n][n];
                int[][] pred = new int[n][n];

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (scanner.hasNextInt()) {
                            dist[i][j] = scanner.nextInt();
                        } else {
                            System.err.println("Error: adjacency matrix is missing entries at (" + i + ", " + j + ")");
                            System.exit(1);
                        }
                    }
                }
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                floydAlgorithm(dist, pred, n, output);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not read input file: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Could not parse 'n' from problem header: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Done. See output.txt for results.");
    }
}
