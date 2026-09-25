import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates a random 0/1 knapsack instance (5-10 items, random profits and
 * weights) and writes it to knapsack01.txt in the format the three solvers
 * expect, so their outputs can be compared on the same instance.
 */
public class KnapsackProblemGenerator {

    public static void main(String[] args) throws FileNotFoundException {
        Random random = new Random();
        int itemCount = 5 + random.nextInt(6); // 5 to 10 items

        List<KnapsackItem> items = new ArrayList<>();
        int totalWeight = 0;
        for (int i = 1; i <= itemCount; i++) {
            int profit = 10 + random.nextInt(21); // 10 to 30
            int weight = 5 + random.nextInt(16);  // 5 to 20
            items.add(new KnapsackItem("Item" + i, profit, weight));
            totalWeight += weight;
        }

        int capacity = (int) Math.floor(0.6 * totalWeight);

        try (PrintWriter writer = new PrintWriter("knapsack01.txt")) {
            writer.println(itemCount + " " + capacity);
            for (KnapsackItem item : items) {
                writer.println(item.name + " " + item.profit + " " + item.weight);
            }
        }

        System.out.println("Generated " + itemCount + " items with capacity " + capacity + " -> knapsack01.txt");
    }
}
