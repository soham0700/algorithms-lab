/** A single item for the 0/1 knapsack problem: a profit and a weight. */
public class KnapsackItem {
    public final String name;
    public final int profit;
    public final int weight;

    public KnapsackItem(String name, int profit, int weight) {
        this.name = name;
        this.profit = profit;
        this.weight = weight;
    }

    public double valueToWeightRatio() {
        return (double) profit / weight;
    }
}
