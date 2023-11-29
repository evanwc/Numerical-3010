import java.util.Scanner;
import java.util.Scanner.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.text.DecimalFormat;

//data-1.txt

public class PolyInterpolation {
    public static void main(String args[]) {
        double[] xval = new double[50];
        double[] fxval = new double[50];
        int size = 0;

        Scanner in = new Scanner(System.in);
        //in.useDelimiter("\\R");

        System.out.println("Please type the file name and extension: ");
            String fileName = in.nextLine();
            File file = new File(fileName);
            try {
                Scanner fileReader = new Scanner(file);
                String line1 = fileReader.nextLine();
                String line2 = fileReader.nextLine();

                Scanner lineScanner = new Scanner(line1);
                for (int i = 0; lineScanner.hasNext(); i++) {
                    xval[i] = lineScanner.nextDouble();
                    size++;
                }

                Scanner lineScanner2 = new Scanner(line2);
                for (int i = 0; lineScanner2.hasNext(); i++) {
                    fxval[i] = lineScanner2.nextDouble();
                }

                lineScanner.close();
                lineScanner2.close();
                fileReader.close();

            }
            catch (FileNotFoundException ff) {
                System.out.println("Exception " + ff.toString());
            }

        interpolate(xval, fxval, size);
        
        in.close();
    }

    public static void interpolate(double[] xval, double[] fxval, int size) {
        double[][] temp = new double[size][size];
        final DecimalFormat df = new DecimalFormat("+##.###;-##.###");
        final DecimalFormat df2 = new DecimalFormat("##.###");

        System.out.print("x\t\t");
        for (int i = 0; i < size; i++) {
            System.out.print(df.format(xval[i]) + "\t\t");
        }
        System.out.println();

        System.out.print("f[]\t\t");
        for (int i = 0; i < size; i++) {
            temp[0][i] = fxval[i];
            System.out.print(df.format(fxval[i]) + "\t\t");
        }
        System.out.println();

        for (int i = 0; i < size - 1; i++) {
            System.out.print("f[");
            for (int k = -1; k < i; k++) {
                System.out.print(",");
            }
            System.out.print("]\t\t");
            for (int j = 0; j < size - i - 1; j++) {
                temp[i + 1][j] = (temp[i][j + 1] - temp[i][j]) / (xval[j + i + 1] - xval[j]);
                System.out.print(df.format(temp[i + 1][j]) + "\t\t");
            }
            System.out.println();
        }
        
        String Newton = "";
        for (int i = 0; i < size; i++) {
            Newton += df2.format(temp[i][0]);
            for (int j = 0; j < i; j++) {
                Newton += "(x - " + xval[j] + ")";
            }
            if (i != size - 1) Newton += " + ";
        }
        System.out.println(Newton);

    }

}
