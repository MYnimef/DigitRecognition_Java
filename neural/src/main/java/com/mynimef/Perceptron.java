package com.mynimef;

import java.util.Random;

public class Perceptron {
    public int linesNum, columnsNum;
    public double[][] weights;
    public double[] layer;
    public double[] sigma;

    Perceptron(int linesNum, int columnsNum) {
        this.linesNum = linesNum;
        this.columnsNum = columnsNum;
        weights = new double[linesNum][columnsNum];
        layer = new double[columnsNum];
        sigma = new double[columnsNum];
    }

    public void setRandomWeights() {  //com.mynimef.Weights initialization and randomization.
        Random rand = new Random();
        for (int i = 0; i < linesNum; i++) {
            for (int j = 0; j < columnsNum; j++) {
                weights[i][j] = (5.0 - rand.nextInt(10)) * 0.1;
            }
        }
    }

    public void forwardPropagation(double[] previousLayer) { //Forward propagation needs previous layer values.
        for (int i = 0; i < columnsNum; i++) {
            double sum = 0;
            for (int j = 0; j < linesNum; j++) {
                sum += previousLayer[j] * weights[j][i];
            }
            layer[i] = sigmoidFunction(sum);
        }
    }

    public void mistake(double[] output) {   //Margion of error calculation, from this point backwards propagation starts.
        for (int i = 0; i < columnsNum; i++) {
            sigma[i] = (output[i] - layer[i]) * layer[i] * (1 - layer[i]);
        }
    }

    public void backwardPropagation(int previousLayerSize, double[][] previousWeights, double[] previousSigma, double k) { //Calculation of sigma (aka error) for each layer.
        for (int i = 0; i < columnsNum; i++) {
            double sigma_in = 0;
            for (int j = 0; j < previousLayerSize; j++) {
                sigma_in += previousSigma[j] * previousWeights[i][j];
                previousWeights[i][j] += k * previousSigma[j] * layer[i];
            }
            sigma[i] = sigma_in * layer[i] * (1 - layer[i]);
        }
    }

    public void changeWeights(double k, double[] previousLayer) { //Final change of weights, using sigma.
        for (int i = 0; i < linesNum; i++) {
            for (int j = 0; j < columnsNum; j++) {
                weights[i][j] += k * sigma[j] * previousLayer[i];
            }
        }
    }

    private double sigmoidFunction(double x) { //Function that calculates value INSIDE neuron.
        return (1 / (1 + Math.exp(-x)));
    }
}