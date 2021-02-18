public class Neural {
    public static void neuralLearning(int trainSet, int[] neuronNum, int hiddenLayersAmount, double[][] input, double[][] output) {
        double k = 0.3;  //Learning ratio
        Perceptron[] perceptron = new Perceptron[hiddenLayersAmount + 1];
        for (int i = 0; i <= hiddenLayersAmount; i++)
        {
            perceptron[i] = new Perceptron(neuronNum[i], neuronNum[i + 1]);
            perceptron[i].setRandomWeights();
        }

        int maxEpoch = 1000; //Epoch amount
        int delta_load = maxEpoch / 10, loading = delta_load;   //Loading variables just for fun

        for (int epoch = 0; epoch < maxEpoch; epoch++)   //Start of learning process
        {
            if ((epoch + 1) == loading) //Loading
            {
                System.out.print("*");
                loading += delta_load;
            }

            for (int train = 0; train < trainSet; train++)
            {
                //Forward propagation.
                perceptron[0].forwardPropagation(input[train]);
                for (int i = 1; i <= hiddenLayersAmount; i++)
                {
                    perceptron[i].forwardPropagation(perceptron[i - 1].layer);
                }

                //Backward propagation and Change of weights.
                perceptron[hiddenLayersAmount].mistake(output[train]);
                for (int i = hiddenLayersAmount; i > 0; i--)
                {
                    perceptron[i - 1].backwardPropagation(perceptron[i].columnsNum, perceptron[i].weights, perceptron[i].sigma, k);
                }
                perceptron[0].changeWeights(k, input[train]);
            }
        }
        System.out.println();

        Weights.writeToFile(hiddenLayersAmount + 1, perceptron);
    }

    public static void neuralNetwork(int[] neuronNum, int hiddenLayersAmount, double[] input, double[] result) {
        Perceptron[] perceptron = new Perceptron[hiddenLayersAmount + 1];
        Weights.getFromFile(hiddenLayersAmount + 1, perceptron, neuronNum);

        perceptron[0].forwardPropagation(input);
        for (int i = 1; i <= hiddenLayersAmount; i++) {
            perceptron[i].forwardPropagation(perceptron[i - 1].layer);
        }
        if (perceptron[hiddenLayersAmount].columnsNum >= 0) {
            System.arraycopy(perceptron[hiddenLayersAmount].layer, 0, result, 0, perceptron[hiddenLayersAmount].columnsNum);
        }
    }
}