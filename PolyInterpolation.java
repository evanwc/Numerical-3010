import java.util.Scanner;
import java.util.Scanner.*;
import java.io.File;
import java.lang.Math;

//"C:\Users\topev\Documents\GitHub\Numerical-3010\Files\data-1.txt"

public class PolyInterpolation {
    public static void main(String args[]) {
        double[] xval = new double[50];
        double[] fxval = new double[50];
        int size = 0;

        Scanner in = new Scanner(System.in);
        //in.useDelimiter("\\R");

        System.out.println("Please type the file name and extension (ex: data.txt): ");
        String fileName = in.nextLine();
        File file = new File("resources/data-1.txt");

        Scanner fileReader = new Scanner(file);
        String line1 = fileReader.next();
        String line2 = fileReader.next();

        Scanner lineScanner = new Scanner(line1).useDelimiter("\\s");
        for (int i = 0; lineScanner.hasNext(); i++) {
            xval[i] = lineScanner.nextDouble();
            size++;
        }

        Scanner lineScanner2 = new Scanner(line2).useDelimiter("\\s");
        for (int i = 0; lineScanner.hasNext(); i++) {
            fxval[i] = lineScanner.nextDouble();
        }

        lineScanner.close();
        lineScanner2.close();
        fileReader.close();
        in.close();
    }

    public void interpolate(double[] xval, double[] fxval, int size) {
        double[] temp = new double[size];

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                temp[j] = (fxval[j + 1] - fxval[j]) / (fxval[j + i + 1] - fxval[j]);
                System.out.println(temp[i] + "\t");
            }
        }
        
        

    }

}
