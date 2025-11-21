import java.util.ArrayList;

public class knapsack{

    // class to store value / weight
    static class Item{
        int value;
        int weight;
        int index;

        Item(int value, int weight, int index){
            this.value = value;
            this.weight = weight;
            this.index = index;

        }
    }

    static int maxValue = 0;
    static boolean[] bestTaken;

    public static double recalculatePotential(int[] values, int[] weights, int capacity, boolean[] taken) {
        ArrayList<Item> items = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            if (!taken[i]) items.add(new Item(values[i], weights[i], i));
        }

        for (int i = 0; i < items.size(); i++) {
            int max = i;

            for (int j = i + 1; j < items.size(); j++) {
                double ratio1 = (double) items.get(j).value / items.get(j).weight;
                double ratio2 = (double) items.get(max).value / items.get(max).weight;
                if (ratio1 > ratio2 || (ratio1 == ratio2 && items.get(j).weight < items.get(max).weight)) {
                    max = j;
                }
            }

            // swap
            Item temp = items.get(i);
            items.set(i, items.get(max));
            items.set(max, temp);
        }

        double result = 0.0;
        int remaining = capacity;

        // GREEDY
        for (int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            if (item.weight <= remaining){
                result += item.value;
                remaining -= item.weight;
            } else {
                result += (double) item.value / item.weight * remaining;
                break;
            }
        }

        return result;
    }

    public static void branchAndBound(int[] values, int[] weights, int capacity, int index, int currentValue, boolean[] taken) {
        if (index == values.length) {
            if (currentValue > maxValue) {
                maxValue = currentValue;
                bestTaken = taken.clone(); // save best solution
            }
            return;
        }

        // Compute upper bound potential if we skip or take this item
        double potential = currentValue + recalculatePotential(values, weights, capacity, taken);
        if (potential <= maxValue) return; // prune branch

        // Try taking current item if it fits
        if (weights[index] <= capacity) {
            taken[index] = true;
            branchAndBound(values, weights, capacity - weights[index], index + 1, currentValue + values[index], taken);
            taken[index] = false;
        }

        // Try skipping current item
        branchAndBound(values, weights, capacity, index + 1, currentValue, taken);
    }



    private static void runTest(String name, int[] values, int[] weights, int capacity, int expected) {
        knapsack.maxValue = 0;
        knapsack.bestTaken = new boolean[values.length];
        boolean[] taken = new boolean[values.length];

        knapsack.branchAndBound(values, weights, capacity, 0, 0, taken);

        System.out.println("===== " + name + " =====");
        System.out.println("Expected max value: " + expected);
        System.out.println("Computed max value: " + knapsack.maxValue);

        if (knapsack.maxValue == expected) {
            System.out.println("Result: PASS ✔");
        } else {
            System.out.println("Result: FAIL ✘");
        }

        System.out.print("Items taken: ");
        for (int i = 0; i < values.length; i++) {
            if (knapsack.bestTaken[i]) System.out.print(i + " ");
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {

        // Test 1: Provided example
        runTest(
                "Basic Case",
                new int[]{45, 48, 35, 54},
                new int[]{5, 8, 3, 9},
                10,
                80 // 35 + 45
        );

        // Test 2: Zero capacity
        runTest(
                "Zero Capacity",
                new int[]{10, 20, 30},
                new int[]{1, 2, 3},
                0,
                0
        );

        // Test 3: Single item fits
        runTest(
                "Single Item Fits",
                new int[]{100},
                new int[]{5},
                5,
                100
        );

        // Test 4: Single item does not fit
        runTest(
                "Single Item Does Not Fit",
                new int[]{100},
                new int[]{10},
                5,
                0
        );

        // Test 5: Everything fits
        runTest(
                "All Items Fit",
                new int[]{10, 20, 30},
                new int[]{1, 1, 1},
                10,
                60
        );
    }
}