import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KnapSack{
    double maxWeight;
    int numItems;
    double weights[];
    double values[];
    int populationSize;
    int generationSize;
    double mutationRate;
    double crossoverRate;
    String fileName;
    public KnapSack(String fileName,int populationSize,int generationSize, double mutationRate, double crossoverRate){
        this.fileName = fileName;
        this.populationSize = populationSize;
        this. generationSize= generationSize;
        this.mutationRate = mutationRate;
        readFromFile(fileName);
        this.crossoverRate = crossoverRate;
    }
    private void readFromFile(String fileName) {
        try {
            File file = new File(fileName);

            Scanner scanner = new Scanner(file);

            // Read the first line to get max weight and number of items
            if (scanner.hasNextLine()) {
                String[] firstLine = scanner.nextLine().split(" ");
                numItems = Integer.parseInt(firstLine[0]);
                maxWeight = Double.parseDouble(firstLine[1]);
            }

            // Initialize arrays to store weights and values
            weights = new double[numItems];
            values = new double[numItems];

            int i = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.isEmpty()){
                    continue;
                }
                String[] parts = line.split(" ");
                weights[i] = Double.parseDouble(parts[1]);
                values[i] = Double.parseDouble(parts[0]);
                i++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
    public void solve(){
        Population population = new Population(populationSize, numItems, weights, values, maxWeight);
        population.initialSolution();
        int generation = 0;
        while(generation < generationSize){
            population.evaluateFitness();
            Population selectedParents = population.selection();
            Population offspring = selectedParents.crossover(crossoverRate);
            offspring.mutate(mutationRate);
            population = offspring;
            generation++;
        }
        Solution bestSolution = population.getBestSolution();
        if(bestSolution == null){
            System.out.println("No solution found");
            return;
        }
        System.out.println("Best Solution:");
        System.out.println("Value: " + bestSolution.getValue());
        System.out.println("Weight: " + bestSolution.getWeight());
        System.out.println("Items Selected:");
        Item[] itemsSelected = bestSolution.getItemsSelected();
        for (int i = 0; i < itemsSelected.length; i++) {
            if (itemsSelected[i] != null) {
                System.out.println("Item " + (i + 1) + ": Weight=" + weights[i] + ", Value=" + values[i]);
            }
        }

    }
    
}