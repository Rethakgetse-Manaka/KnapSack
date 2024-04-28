public class Main {
    public static void main(String[] args) {
        KnapSack knapSack = new KnapSack("knapPI_1_100_1000_1", 5000, 500, 0.6, 0.6);

        System.out.println("Max Weight: " + knapSack.maxWeight);
        System.out.println("Number of Items: " + knapSack.numItems);
        System.out.println("Item weights and values:");
        for (int i = 0; i < knapSack.numItems; i++) {
            System.out.println("Item " + (i + 1) + ": Weight=" + knapSack.weights[i] + ", Value=" + knapSack.values[i]);
        }
        System.out.println();
        System.out.println("Solving knapsack problem...");
        System.out.println();
        knapSack.solve();
    }
}
