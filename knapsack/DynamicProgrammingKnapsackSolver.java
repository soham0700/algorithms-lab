import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exact, pseudo-polynomial O(n * capacity) solution to 0/1 knapsack using the
 * standard bottom-up DP table, with backtracking through the table to
 * recover which items were actually selected.
 */
public class DynamicProgrammingKnapsackSolver {

    public static void main(String[] args) throws IOException {
        List<KnapsackItem> items = KnapsackIO.readInstance("knapsack01.txt");
        List<KnapsackItem> selected = solve(items, KnapsackIO.capacity);
        KnapsackIO.writeSolution(selected, "Output02.txt");
    }

    private static List<KnapsackItem> solve(List<KnapsackItem> items, int capacity) {
        int n = items.size();
        int[][] table = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            KnapsackItem item = items.get(i - 1);
            for (int w = 0; w <= capacity; w++) {
                if (item.weight <= w) {
                    table[i][w] = Math.max(item.profit + table[i - 1][w - item.weight], table[i - 1][w]);
                } else {
                    table[i][w] = table[i - 1][w];
                }
            }
        }

        List<KnapsackItem> selected = new ArrayList<>();
        int remainingProfit = table[n][capacity];
        int w = capacity;
        for (int i = n; i > 0 && remainingProfit > 0; i--) {
            if (remainingProfit != table[i - 1][w]) {
                KnapsackItem item = items.get(i - 1);
                selected.add(item);
                remainingProfit -= item.profit;
                w -= item.weight;
            }
        }
        return selected;
    }
}
