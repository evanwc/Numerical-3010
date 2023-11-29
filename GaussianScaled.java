import java.util.Scanner;
import java.io.File;
import java.lang.Math;
import java.text.DecimalFormat;

//Gaussian elimination using scaled partial pivoting method; console input or file input
public class GaussianScaled {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please type 'i' for console input or 'f' for file input.");
        String ans = in.nextLine();

        if (ans.equals("i")) {
            consoleInput(in);
        }
        else if (ans.equals("f")) {
            fileInput(in);
        }
    }

    private static void consoleInput(Scanner in) {
        System.out.println("How many equations (up to 10): ");
        int size = in.nextInt();

        float[][] matrix = new float[size][size + 1];
        float[] max = new float[size];

        for (int i = 0; i < size; i++) {
            System.out.println("Please enter row " + (i + 1) + " with spaces between each number:");
            int j = 0;
            while (j < (size + 1) && in.hasNext()) {
                if (in.hasNextInt()) matrix[i][j] = (float)in.nextInt();
                else in.next();
                if (j < size && Math.abs(matrix[i][j]) > max[i]) max[i] = Math.abs(matrix[i][j]);
                j++;
            }
        }

        solve(matrix, max, size);
    }

    private static void fileInput(Scanner in) {
        //not currently working in my directory, maybe files and Java class are not in the same location
        System.out.println("How many equations (up to 10): ");
        int size = in.nextInt();
        in.nextLine();

        System.out.print("Enter the file to read from: ");
        String readFile = in.nextLine();

        File text = new File(readFile);

        //System.out.println(text.exists()); /test

        Scanner fileReader = new Scanner(text);

        float[][] matrix = new float[size][size + 1];
        float[] max = new float[size];

        for (int i = 0; i < size; i++) {
            int j = 0;
            while (j < (size + 1) && fileReader.hasNext()) {
                if (fileReader.hasNextInt()) matrix[i][j] = (float)in.nextInt();
                else fileReader.next();
                if (j < size && Math.abs(matrix[i][j]) > max[i]) max[i] = Math.abs(matrix[i][j]);
                j++;
            }
        }

        fileReader.close();
        
        solve(matrix, max, size);
    }

    private static void solve(float[][] matrix, float[] max, int size) {
        float[] scaleRatio = new float[size];
        float scaleMax = -Float.MAX_VALUE;
        int maxIndex = -1;
        float[] multipliers = new float[size];
        int[] order = new int[size];
        float[] answer = new float[size];
        DecimalFormat df = new DecimalFormat("0.00");

        //big nested loop for going through each column of the matrix until enough zeroes are created
        for (int j = 0; j < size - 1; j++) {
            //loop for finding all scale ratios
            for (int i = 0; i < size; i++) {
                if (scaleRatio[i] == -Float.MAX_VALUE) i++; //skip row if it has been pivot before
                if (i < size) scaleRatio[i] = (Math.abs(matrix[i][j])) / max[i];
                if (i < size) System.out.println("Scale ratios for row " + (i + 1) + ": " + (Math.abs(matrix[i][j])) / max[i]);
            }

            //loop for finding max ratio and pivot row
            for (int k = 0; k < size; k++) {
                if (scaleRatio[k] > scaleMax) {
                    scaleMax = scaleRatio[k];
                    maxIndex = k;
                }
            }
            
            scaleRatio[maxIndex] = -Float.MAX_VALUE; //set ratio to smallest value when it becomes pivot
            scaleMax = -Float.MAX_VALUE; //set max to smallest value for next iteration
            order[j] = maxIndex; //stores order of pivot rows
            System.out.println("Pivot row is: " + (maxIndex + 1));

            //loop for finding multipliers using max
            for (int l = 0; l < size; l++) {
                if (Math.abs(matrix[l][j]) < 0.00001) l++; //skip row if first number is already 0 with error
                else if (scaleRatio[l] == -Float.MAX_VALUE) l++; //skip row if it is pivot or has been pivot
                if (l < size) multipliers[l] = (matrix[l][j]) / (matrix[maxIndex][j]); //first element in this iteration/first element of pivot row
            }

            //loop for solving this iteration
            for (int m = 0; m < size; m++) {
                if (scaleRatio[m] == -Float.MAX_VALUE); //skip row if it is pivot or has been pivot
                else {
                    for (int n = j; n <= size; n++) {
                        if (m < size && Math.abs(matrix[m][n]) < 0.00001) m++; //skip row if it is 0 with error
                        if (m < size) matrix[m][n] = matrix[m][n] - (multipliers[m] * matrix[maxIndex][n]);
                    }
                }
            }

            //loop for displaying this iteration
            for (int m = 0; m < size; m++) {
                for (int n = 0; n <= size; n++) {
                    System.out.print("   " + df.format(matrix[m][n]));
                }
                System.out.println();
            }
        }

        for (int k = 0; k < size; k++) {
            if (scaleRatio[k] > scaleMax) {
                scaleMax = scaleRatio[k];
                maxIndex = k;
            }
        }
        order[size - 1] = maxIndex; //fills in the last order
        int count = size - 1; //used to find index of coefficient in each iteration

        //nested loop for backward substitution
        for (int i = size - 1; i >= 0; i--) {
            float coeff = -Float.MAX_VALUE;
            coeff = matrix[order[i]][count]; //setting coefficitn
            for (int j = size - 1; j >= 0; j--) {
                if (j > count) {
                    matrix[order[i]][size] = matrix[order[i]][size] - (matrix[order[i]][j] * answer[j]);
                }
            }
            count--; //decrementing coefficient
            answer[i] = matrix[order[i]][size] / coeff;
        }

        //print out the answer
        System.out.println();
        for (int k = 0; k < size; k++) {
            System.out.println("x (subscript " + (k + 1) + ") is approximately: " + df.format(answer[k]));
        }
    }    
}