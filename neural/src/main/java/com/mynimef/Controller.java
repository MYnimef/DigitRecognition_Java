package com.mynimef;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button neuroButton;

    @FXML
    private Canvas digitCanvas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void neuroStartClick(MouseEvent mouseEvent) {
        int inputNum = 784, outputNum = 10; //Number of input and output values.
        int hiddenLayersAmount = 2;  //Number of hidden layers.
        int[] neuronNum = { inputNum, 200, 50, outputNum };    //Number of neurons in each layer.

        if (Weights.checkWeights()) {
            System.out.println("Weights file already exist");
        }
        else {
            int trainSet = 1000;    //Number of train sets (for learning).
            double[][] input = new double[trainSet][inputNum];
            double[][] output = new double[trainSet][outputNum];

            String fileName = "A:\\train\\000000-num0.png";
            String num = "00000";
            for (int i = 0; i < trainSet; i++) {
                String oldNum = num + i;

                String digit = "num";
                int digitItr = 0;
                String oldDigit = digit + digitItr;

                while (!Image.toArray(input[i], fileName)) {
                    digitItr++;
                    String newDigit = digit + digitItr;
                    fileName = fileName.replace(oldDigit, newDigit);
                    oldDigit = newDigit;
                }
                output[i][digitItr] = 1;

                if (i >= 9 && i < 99) {
                    num = "0000";
                }
                else if (i >= 99 && i < 999) {
                    num = "000";
                }
                else if (i >= 1000 && i < 10000) {
                    num = "00";
                }
                else {
                    num = "0";
                }
                fileName = fileName.replace(oldNum, num + (i + 1));
                fileName = fileName.replace(oldDigit, digit + 0);
            }
            System.out.println("Got train values!");

            Neural.neuralLearning(trainSet, neuronNum, hiddenLayersAmount, input, output);    //learning
            System.out.println("Learning process completed");
        }

        String fN1 = "try\\try", fN2 = ".png";
        for (int i = 0; i <= 9; i++)
        {

            double[] in = new double[inputNum];
            if (!Image.toArray(in, fN1 + i + fN2)) {
                System.out.println("No matching picture");
            }
            else {
                Image.print(784, in);
                double[] result = new double[outputNum];
                Neural.neuralNetwork(neuronNum, hiddenLayersAmount, in, result);

                for (int j = 0; j < outputNum; j++) {
                    if (result[j] > 0.8) {
                        System.out.println("Result is " + j + ".");
                        break;
                    }
                }
            }
        }
    }
}
