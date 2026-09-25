import java.util.Arrays;
import java.util.Random;

/**
 * Strassen's divide-and-conquer matrix multiplication algorithm (~O(n^2.81)
 * vs. O(n^3) for the standard method), implemented recursively for square
 * matrices whose size is a power of two.
 *
 * Generates two random n x n matrices, multiplies them with both Strassen's
 * algorithm and standard triple-loop multiplication, and confirms the
 * results agree.
 *
 * Usage:
 *   javac StrassenMatrixMultiplication.java
 *   java StrassenMatrixMultiplication <n>   // n must be a power of 2, up to 1024
 */
public class StrassenMatrixMultiplication {

    public static long[][] randomMatrix(int n, int maxValue) {
        Random random = new Random();
        long[][] matrix = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = random.nextInt(maxValue + 1);
            }
        }
        return matrix;
    }

    public static long[][] subtract(long[][] a, long[][] b) {
        int n = a.length;
        long[][] result = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    public static long[][] add(long[][] a, long[][] b) {
        int n = a.length;
        long[][] result = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    /** Strassen's algorithm: multiplies two n x n matrices using 7 recursive multiplications instead of 8. */
    public static long[][] strassenMultiply(long[][] a, long[][] b) {
        int n = a.length;
        long[][] result = new long[n][n];

        if (n == 1) {
            result[0][0] = a[0][0] * b[0][0];
            return result;
        }

        int mid = n / 2;
        long[][] a11 = new long[mid][mid], a12 = new long[mid][mid];
        long[][] a21 = new long[mid][mid], a22 = new long[mid][mid];
        long[][] b11 = new long[mid][mid], b12 = new long[mid][mid];
        long[][] b21 = new long[mid][mid], b22 = new long[mid][mid];

        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < mid; j++) {
                a11[i][j] = a[i][j];
                a12[i][j] = a[i][j + mid];
                a21[i][j] = a[i + mid][j];
                a22[i][j] = a[i + mid][j + mid];

                b11[i][j] = b[i][j];
                b12[i][j] = b[i][j + mid];
                b21[i][j] = b[i + mid][j];
                b22[i][j] = b[i + mid][j + mid];
            }
        }

        long[][] p1 = strassenMultiply(add(a11, a22), add(b11, b22));
        long[][] p2 = strassenMultiply(add(a21, a22), b11);
        long[][] p3 = strassenMultiply(a11, subtract(b12, b22));
        long[][] p4 = strassenMultiply(a22, subtract(b21, b11));
        long[][] p5 = strassenMultiply(add(a11, a12), b22);
        long[][] p6 = strassenMultiply(subtract(a21, a11), add(b11, b12));
        long[][] p7 = strassenMultiply(subtract(a12, a22), add(b21, b22));

        long[][] c11 = add(subtract(add(p1, p4), p5), p7);
        long[][] c12 = add(p3, p5);
        long[][] c21 = add(p2, p4);
        long[][] c22 = add(subtract(add(p1, p3), p2), p6);

        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < mid; j++) {
                result[i][j] = c11[i][j];
                result[i][j + mid] = c12[i][j];
                result[i + mid][j] = c21[i][j];
                result[i + mid][j + mid] = c22[i][j];
            }
        }
        return result;
    }

    public static void printMatrix(long[][] matrix) {
        for (long[] row : matrix) {
            for (long element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java StrassenMatrixMultiplication <n>");
            return;
        }

        int n = Integer.parseInt(args[0]);
        if (n <= 0 || (n & (n - 1)) != 0 || n > 1024) {
            System.out.println("Provide n as a power of two between 1 and 1024.");
            return;
        }

        int maxIntegerValue = (int) Math.floor(Math.sqrt(Integer.MAX_VALUE) / n);
        long[][] a = randomMatrix(n, maxIntegerValue);
        long[][] b = randomMatrix(n, maxIntegerValue);

        System.out.println("A:");
        printMatrix(a);
        System.out.println("\nB:");
        printMatrix(b);

        System.out.println("\nStrassen multiplication result:");
        long[][] strassenResult = strassenMultiply(a, b);
        printMatrix(strassenResult);

        System.out.println("\nStandard multiplication result:");
        long[][] standardResult = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    standardResult[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        printMatrix(standardResult);

        System.out.println("--------------------------");
        System.out.println(Arrays.deepEquals(standardResult, strassenResult)
                ? "Results match."
                : "Results differ!");
    }
}
