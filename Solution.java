import java.util.Random;

public class Solution {
    public Item[] itemsSelected;
    private double totalValue;
    private double totalWeight;
    double[] weights;
    double[] values;
    double fitness;
    long seed = 12345;
    Random rand;

    public Solution(int numItems, double[]weights, double[] values) {
        itemsSelected = new Item[numItems];
        rand = new Random(seed);
        this.weights = weights;
        this.values = values;
        fitness = 0;
    }

    public void evaluateFitness(double maxWeight) {
        // Calculate the total value and weight of the selected items
        this.totalValue = 0;
        this.totalWeight = 0;
        for (int i = 0; i < itemsSelected.length; i++) {
            if (itemsSelected[i] != null) {
                this.totalValue += itemsSelected[i].value;
                this.totalWeight += itemsSelected[i].weight;
            }
        }
        // If the total weight exceeds the maximum weight, set the fitness to 0
        if (totalWeight > maxWeight) {
            fitness = 0;
        } else {
            // Otherwise, set the fitness to the total value
            fitness = totalValue;
        }
    }
    public double getFitness() {
        return fitness;
    }
    public void mutate(double maxWeight) {
        
    }

    public double getValue() {
        return totalValue;
    }

    public double getWeight() {
        return totalWeight;
    }

    public Item[] getItemsSelected() {
        return itemsSelected;
    }
    public void setItemsSelected(Item[] itemsSelected) {
        this.itemsSelected = itemsSelected;
    }
    public Solution clone() {
        Solution clone = new Solution(itemsSelected.length, weights, values);
        clone.setItemsSelected(itemsSelected.clone());
        return clone;
    }
}