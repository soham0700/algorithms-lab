import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exact but exponential (O(2^n)) solution to 0/1 knapsack: recursively tries
 * including and excluding each item and keeps whichever choice yields more
 * profit. Useful as a correctness baseline for the DP and greedy solvers.
 */
public class BruteForceKnapsackSolver {

    public static void main(String[] args) throws IOException {
        List<KnapsackItem> items = KnapsackIO.readInstance("knapsack01.txt");
        List<KnapsackItem> selected = solve(items, KnapsackIO.capacity, 0);
        KnapsackIO.writeSolution(selected, "Output01.txt");
    }

    private static List<KnapsackItem> solve(List<KnapsackItem> items, int remainingCapacity, int currentIndex) {
        if (currentIndex >= items.size() || remainingCapacity == 0) {
            return new ArrayList<>();
        }

        KnapsackItem currentItem = items.get(currentIndex);
        List<KnapsackItem> withoutItem = solve(items, remainingCapacity, currentIndex + 1);
        List<KnapsackItem> withItem = new ArrayList<>();

        if (currentItem.weight <= remainingCapacity) {
            withItem = solve(items, remainingCapacity - currentItem.weight, currentIndex + 1);
            withItem.add(currentItem);
        }

        return totalProfit(withItem) > totalProfit(withoutItem) ? withItem : withoutItem;
    }

    private static int totalProfit(List<KnapsackItem> items) {
        return items.stream().mapToInt(item -> item.profit).sum();
    }
}
