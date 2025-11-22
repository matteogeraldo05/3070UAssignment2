import java.util.*;

public class knapsack {

    // Class to represent an item with value and weight
    static class Item {
        int value;
        int weight;

        Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }

    // Comparator to sort items by value-to-weight ratio in descending order
    static class RatioComparator implements Comparator<Item> {
        public int compare(Item a, Item b) {
            double r1 = (double) a.value / a.weight;
            double r2 = (double) b.value / b.weight;
            if (r1 < r2) return 1;
            else if (r1 > r2) return -1;
            else return 0;
        }
    }

    // Function to calculate maximum value for fractional knapsack
    public static double fractionalKnapsack(int capacity, Item[] items) {
        Arrays.sort(items, new RatioComparator()); // sort items by ratio

        double totalValue = 0.0;
        int remaining = capacity;

        for (Item item : items) {
            if (item.weight <= remaining) {  // take whole item
                totalValue += item.value;
                remaining -= item.weight;
            } else {  // take fraction of item
                totalValue += ((double) item.value / item.weight) * remaining;
                break;
            }
        }

        return totalValue;
    }

    public static void main(String[] args) {
        // Test Case
        Item[] items = new Item[]{
                new Item(45, 5),
                new Item(48, 8),
                new Item(35, 3)
        };
        int capacity = 10;
        double maxValue = fractionalKnapsack(capacity, items);
        System.out.println("Maximum value (fractional knapsack) = " + maxValue);
    }
}
