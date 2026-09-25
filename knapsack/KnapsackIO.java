import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/** Shared file I/O for the knapsack solvers: a simple text format shared by all of them. */
public class KnapsackIO {

    public static int capacity;

    /**
     * Reads a knapsack instance from a file in the format:
     *   <itemCount> <capacity>
     *   <name> <profit> <weight>   (one line per item)
     */
    public static List<KnapsackItem> readInstance(String fileName) throws IOException {
        List<KnapsackItem> items = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String[] header = reader.readLine().split(" ");
            int itemCount = Integer.parseInt(header[0]);
            capacity = Integer.parseInt(header[1]);

            for (int i = 0; i < itemCount; i++) {
                String[] values = reader.readLine().split(" ");
                items.add(new KnapsackItem(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2])));
            }
        }
        return items;
    }

    public static void writeSolution(List<KnapsackItem> selectedItems, String fileName) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            int totalProfit = selectedItems.stream().mapToInt(item -> item.profit).sum();
            int totalWeight = selectedItems.stream().mapToInt(item -> item.weight).sum();

            writer.println(selectedItems.size() + " " + totalProfit + " " + totalWeight);
            for (KnapsackItem item : selectedItems) {
                writer.println(item.name + " " + item.profit + " " + item.weight);
            }
        }
    }
}
