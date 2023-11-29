import java.util.Scanner;
import java.util.Scanner.*;
import java.io.File;
import java.lang.Math;

public class PolyInterpolation {
    public static void main(String args[]) {
        double[] xval, fxval;
        Scanner in = new Scanner(System.in);
        in.useDelimiter("\\R");

        System.out.println("Please type the file name and extension (ex: data.txt): ");
        String fileName = in.next();
        File file = new File(fileName);

        Scanner fileReader = new Scanner(file);
        String line1 = fileReader.next();
        String line2 = fileReader.next();

        Scanner lineScanner = new Scanner(line1).useDelimiter("\\s");
        for (int i = 0; lineScanner.hasNext(); i++) {
            xval[i] = lineScanner.nextDouble;
        }

        lineScanner = new Scanner(line2).useDelimiter("\\s");
        for (int i = 0; lineScanner.hasNext(); i++) {
            fxval[i] = lineScanner.nextDouble;
        }

        lineScanner.close();
        fileReader.close();
        in.close();
    }

    public void interpolate(double[] xval, double[] fxval) {
        final int size = xval.length;
        double[] temp = new double[size];

        // print first row table headers
        // for (int i = 0; i < size; i++) {
        //     if (i == 0) System.out.println("x\t");
        //     else {
        //         System.out.println("f[");
        //         int j = i - 1;
        //         while (okj > 0) {
        //             System.out.println(",");
        //         }
        //         System.out.println("]\t");
        //     }
        // }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                temp[j] = (fxval[j + 1] - fxval[j]) / (fxval[j + 1] - fxval[j]);
                System.out.println(temp[i] + "\t");
            }
        }
        
        

    }

}
