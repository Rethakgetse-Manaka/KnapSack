import java.util.Random;

public class ILS {
    Solution bestSolution;
    Solution currentSolution;
    Random rand;

    public ILS(){
        bestSolution = null;
        currentSolution = null;
        rand = new Random(12345);
    }

    public Solution iteratedLocalSearch(Solution initialSolution, int maxIterations){
        currentSolution = initialSolution.clone(); // Clone the initial solution
        bestSolution = currentSolution.clone(); // Initialize the best solution

        for (int i = 0; i < maxIterations; i++) {
            Solution perturbedSolution = perturb(currentSolution); 
            Solution localOptimum = localSearch(perturbedSolution); 

            if (localOptimum.getFitness() > bestSolution.getFitness()) {
                bestSolution = localOptimum.clone();
            }
        }
        bestSolution.evaluateFitness();
        return bestSolution;
    }
    private Solution perturb(Solution solution){
        Solution perturbedSolution = solution.clone(); // Clone the current solution

        int numItems = perturbedSolution.getItemsSelected().length;
        int index1 = rand.nextInt(numItems); // Randomly select index of the first item
        int index2;
        
        do {
            index2 = rand.nextInt(numItems); // Randomly select index of the second item
        } while (index2 == index1); // Ensure that index2 is different from index1
    
        Item temp = perturbedSolution.getItemsSelected()[index1];
        perturbedSolution.getItemsSelected()[index1] = perturbedSolution.getItemsSelected()[index2];
        perturbedSolution.getItemsSelected()[index2] = temp;
        perturbedSolution.evaluateFitness(); // Evaluate the fitness of the perturbed solution

        return perturbedSolution;
    }
    private Solution localSearch(Solution solution){
        // Implement local search here
        Solution currentSolution = solution.clone();
        boolean improved = true;
        while(improved){
            improved = false;
            for(int i = 0; i < currentSolution.getItemsSelected().length; i++){
                if(currentSolution.itemsSelected[i] != null){
                    continue;
                }
                Solution newSolution = currentSolution.clone();
                newSolution.getItemsSelected()[i] = new Item(newSolution.weights[i], newSolution.values[i], i);
                newSolution.evaluateFitness();
                if(newSolution.getFitness() > currentSolution.getFitness()){
                    currentSolution = newSolution;
                    improved = true;
                }
            }
        }
        currentSolution.evaluateFitness();
        return currentSolution;
    }
}
