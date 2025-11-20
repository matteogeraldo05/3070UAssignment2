import java.util.ArrayList;

public class knapsack{

    // class to store value / weight
    static class Item{
        int value;
        int weight;

        Item(int value, int weight){
            this.value = value;
            this.weight = weight;
        }
    }

    public static double fractionalKnapsack(int[] values, int[] weights, int capacity) {
        ArrayList<Item> items = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            items.add(new Item(values[i], weights[i]));
        }

        for (int i = 0; i < items.size(); i++) {
            int max = i;

            for (int j = i + 1; j < items.size(); j++) {
                double ratio1 = (double) items.get(j).value / items.get(j).weight;
                double ratio2 = (double) items.get(max).value / items.get(max).weight;
                if (ratio1 > ratio2) {
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


    public static double recalculatePotential(){
        return -1.0;
    }


    public static void main(String[] args){
        int[] values = {45,48,35};
        int[] weights = {5,8,3};
        int capacity = 10;
        System.out.println(fractionalKnapsack(values, weights, capacity));
    }
}