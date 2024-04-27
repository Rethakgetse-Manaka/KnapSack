import java.util.Random ;
public class Population {
    public Solution[] solutions;
    private int populationSize;
    private int numItems;
    private int[] weights;
    private int[] values;
    double totalFitness;
    private int maxWeight;
    long seed = 12345;
    Random rand;

    public Population(int populationSize, int numItems, int[] weights, int[] values, int maxWeight) {
        this.populationSize = populationSize;
        this.numItems = numItems;
        this.weights = weights;
        this.values = values;
        this.maxWeight = maxWeight;
        rand = new Random(seed);
        totalFitness = 0;
    }
    public void initialSolution(){
        solutions = new Solution[populationSize];
        for(int i = 0; i < populationSize; i++){
            solutions[i] = new Solution(numItems, weights, values);
            for(int j = 0; j < numItems; j++){
                if(rand.nextBoolean()){
                    solutions[i].itemsSelected[j] = new Item(weights[j], values[j], j);
                }
            }
        }
    }
    public void evaluateFitness(){
        for(int i = 0; i < populationSize; i++){
            solutions[i].evaluateFitness(maxWeight);
            totalFitness += solutions[i].getFitness();
        }
    }
    public Population selection(){
        Population selectedParents = new Population(populationSize, numItems, weights, values, maxWeight);
        double totalFitness = this.CalculateFitness(); 
        selectedParents.solutions = new Solution[populationSize];
        for (int i = 0; i < populationSize; i++) {
            double randomFitness = rand.nextDouble() * totalFitness;
            double currentFitness = 0;
    
            for (int j = 0; j < populationSize; j++) {
                currentFitness += this.solutions[j].getFitness();
                if (currentFitness >= randomFitness) {
                    if(selectedParents.solutions[i] == null){
                        selectedParents.solutions[i] = solutions[j].clone(); // Assuming you have a method to clone solutions
                        break;
                    } 
                }
            }
        }
        return selectedParents;
    }
    public double getFitness() {
        return totalFitness;
    }
    public double CalculateFitness(){
        double fitness = 0;
        for(int i = 0; i < populationSize; i++){
            fitness += solutions[i].getFitness();
        }
        return fitness;
    }
    public Population crossover(double crossoverRate){
        Population offspring = new Population(populationSize, numItems, weights, values, maxWeight);
        offspring.solutions = new Solution[populationSize];
        for(int i = 0; i < populationSize; i++){
            Solution parent1 = solutions[i];
            Solution parent2 = solutions[rand.nextInt(populationSize)];
            Solution child = new Solution(numItems, weights, values);
            for(int j = 0; j < numItems; j++){
                if(rand.nextDouble() < crossoverRate){
                    child.itemsSelected[j] = parent1.itemsSelected[j];
                }else{
                    child.itemsSelected[j] = parent2.itemsSelected[j];
                }
            }
            offspring.solutions[i] = child;
        }
        return offspring;
    }
    public void mutate(double mutationRate){
        for(int i = 0; i < populationSize; i++){
            if(rand.nextDouble() < mutationRate){
                int index = rand.nextInt(numItems);
                if(solutions[i].itemsSelected[index] == null){
                    solutions[i].itemsSelected[index] = new Item(weights[index], values[index], index);
                }else{
                    solutions[i].itemsSelected[index] = null;
                }
            }
        }
    }
    public Solution getBestSolution(){
        Solution bestSolution = null;
        double bestFitness = 0;
        for(int i = 0; i < populationSize; i++){
            solutions[i].evaluateFitness(maxWeight);
            if(solutions[i].getFitness() > bestFitness){
                bestFitness = solutions[i].getFitness();
                bestSolution = solutions[i];
            }
        }
        return bestSolution;
    }
}