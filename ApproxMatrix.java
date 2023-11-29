import java.text.DecimalFormat;
import java.util.Scanner;
import java.lang.Math;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ApproxMatrix {
    static String pattern = "0.0000";
    static DecimalFormat dformat = new DecimalFormat(pattern);

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please type 'i' for console input or 'f' for file input.");
        String ans = in.nextLine();

        System.out.println("Please type 'j' or 'g' for Jacobi or Gauss Seidel.");
        String type = in.nextLine();
        System.out.println("How many rows/columns in matrix: ");
        int size = in.nextInt();
    
        double[][] matrix = new double[size][size + 1];
    
        if (ans.equals("i")) {
            for (int i = 0; i < size; i++) {
                System.out.println("Please enter row " + (i + 1) + " with spaces between each number:");
                int j = 0;
                while (j < (size + 1) && in.hasNext()) {
                    if (in.hasNextInt()) matrix[i][j] = (double)in.nextInt();
                    else in.next();
                    j++;
                }
            }
        }
        else if (ans.equals("f")) {
            System.out.println("Please type the file name and extension: ");
            String fileName = in.nextLine();
            File file = new File("Matrix.txt" + fileName);
            System.out.println(file.exists());
            System.out.println(file.isDirectory());
            System.out.println(file.canRead());
            System.out.println("Current Directory = " + System.getProperty("user.dir"));
            //System.out.println(file.getCanonicalPath());
        }
        //     try {
        //         Scanner fileReader    = new Scanner(file);
        //         for (int i = 0; i < size; i++) {
        //             int j = 0;
        //             while (j < (size + 1) && fileReader.hasNext()) {
        //                 if (fileReader.hasNextInt()) matrix[i][j] = (double)in.nextInt();
        //                 else in.next();
        //                 j++;
        //             }
        //         }
        //         fileReader.close();
        //     }
        //     catch (FileNotFoundException ff) {
        //         System.out.println("Exception " + ff.toString());
        //     }
        // }

        if (type.equals("j")) Jacobi(matrix);
        else Gauss(matrix, in);
        
    }

    public static void checkDiagonal(double[][] matrix) {
        int diag = 0;

        for (int i = 0; i < matrix.length; i++) {
            double max = -Double.MAX_VALUE;
            int maxIndex = -1;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    maxIndex = j;
                }
            }
            if (i != maxIndex) diag++;
        }

        if (diag > 0) System.out.println("Matrix is not diagonally dominant; answer may be inaccurate.");
        else System.out.println("Matrix is diagonally dominant");
    }

    public static void Jacobi(double[][] matrix) {
        double[] iter = new double[matrix.length];
        double[] prev = new double[matrix.length];
        int size = matrix.length;    

        for (int index = 0; index < size; index++) {
            System.out.print("x superscript " + (index + 1) + " = [");
            for (int i = 0; i < size; i++) {
                prev[i] = iter[i];
                double temp = matrix[i][size];
                for (int j = 0; j < size; j++) {
                    if (i != j) {
                        temp -= (prev[j] * matrix[i][j]);
                    }
                }
                iter[i] = temp / matrix[i][i];
                if (i != size - 1) System.out.print(dformat.format(iter[i]) + ", ");
                else System.out.print(dformat.format(iter[i]));
            }
            System.out.println("]");
        }
    }

    public static void Gauss(double[][] matrix, Scanner in) {
        System.out.println("What is the desired error: ");
        double goal = Double.parseDouble(in.nextLine());

        double[] iter = new double[matrix.length];
        double[] prev = new double[matrix.length];
        int size = matrix.length;    

        double error = Double.MAX_VALUE;

        for (int index = 0; error > goal && index < 50; index++) {
            System.out.print("x superscript " + (index + 1) + " = [");
            for (int i = 0; i < size; i++) {
                prev[i] = iter[i];
                double temp = matrix[i][size];
                for (int j = 0; j < size; j++) {
                    if (i != j) {
                        temp -= (iter[j] * matrix[i][j]);
                    }
                }
                iter[i] = temp / matrix[i][i];
                if (i != size - 1) System.out.print(dformat.format(iter[i]) + ", ");
                else System.out.print(dformat.format(iter[i]));
            }
            System.out.println("]");

            double numer = 0d;
            double denom = 0d;
            for (int i = 0; i < iter.length; i++) {
                numer += (iter[i] - prev[i]) * (iter[i] - prev[i]);
                denom += iter[i] * iter[i];
            }
            error = Math.sqrt(numer) / Math.sqrt(denom);
            System.out.println("     error:  " + error);
        }

    }
}
