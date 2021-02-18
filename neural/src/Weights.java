import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Weights {
    public static boolean checkWeights() {
        try(FileReader fread = new FileReader("weights.txt")) {
            return true;
        }
        catch(IOException ex) {
            return false;
        }
    }

    public static void getFromFile(int size, Perceptron[] obj, int[] neuronNum) {
        try(FileReader fread = new FileReader("weights.txt")) {
            BufferedReader buff = new BufferedReader(fread);
            for (int s = 0; s < size; s++) {
                obj[s] = new Perceptron(neuronNum[s], neuronNum[s + 1]);
                for (int i = 0; i < neuronNum[s]; i++) {
                    for (int j = 0; j < neuronNum[s + 1]; j++) {
                        String line = buff.readLine();
                        obj[s].weights[i][j] = Double.parseDouble(line);
                    }
                }
            }
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void writeToFile(int size, Perceptron[] obj) {
        try(FileWriter writer = new FileWriter("weights.txt", false)) {
            for (int s = 0; s < size; s++) {
                for (int i = 0; i < obj[s].linesNum; i++) {
                    for (int j = 0; j < obj[s].columnsNum; j++) {
                        String text = Double.toString(obj[s].weights[i][j]);
                        writer.write(text + "\n");
                        writer.flush();
                    }
                }
            }
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}