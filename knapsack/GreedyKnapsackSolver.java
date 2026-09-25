import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Fast O(n log n) approximation to 0/1 knapsack: greedily takes items in
 * descending order of profit-to-weight ratio. This is optimal for the
 * fractional knapsack problem, but only a heuristic here — compare its
 * output against DynamicProgrammingKnapsackSolver's exact answer on the same
 * instance to see how close (or not) the greedy choice gets.
 */
public class GreedyKnapsackSolver {

    public static void main(String[] args) throws IOException {
        List<KnapsackItem> items = KnapsackIO.readInstance("knapsack01.txt");
        List<KnapsackItem> selected = solve(items, KnapsackIO.capacity);
        KnapsackIO.writeSolution(selected, "Output03.txt");
    }

    private static List<KnapsackItem> solve(List<KnapsackItem> items, int capacity) {
        List<KnapsackItem> sortedItems = new ArrayList<>(items);
        sortedItems.sort(Comparator.comparingDouble(KnapsackItem::valueToWeightRatio).reversed());

        int currentWeight = 0;
        List<KnapsackItem> selected = new ArrayList<>();
        for (KnapsackItem item : sortedItems) {
            if (currentWeight + item.weight <= capacity) {
                currentWeight += item.weight;
                selected.add(item);
            }
        }

        // Restore original item order for the output file.
        selected.sort(Comparator.comparingInt(items::indexOf));
        return selected;
    }
}
