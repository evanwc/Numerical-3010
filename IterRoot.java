import java.util.Scanner;
import java.lang.Math;

public class IterRoot {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.println("Which function 'a' f(x) = 2x^3 - 11.7x^2 + 17.7x - 5 or 'b' f(x) = x + 10 - xcosh(50/x)");
        String funct = in.nextLine();

        Bisection(in, funct);
        NewtonRaph(in, funct);
        FalsePos(in, funct);
        Secant(in, funct);

    }

    public static void Bisection(Scanner in, String funct) {
        System.out.println("Bisection\nWhat is a? ");
        double a = (double) in.nextInt();
        System.out.println("What is b? ");
        double b = (double) in.nextInt();

        double c = a, fa, fb, fc, error = 1d, prevC = 1d;
        
        for (int i = 0; error > 0.01 && i < 100; i++) {
            c = (a + b) / 2d;

            //function call for a and b
            if (funct.equals("a")) {
                fa = funct1(a);
                fb = funct1(b);
            }
            else {
                fa = funct2(a);
                fb = funct2(b);
            }

            //function call
            if (funct.equals("a")) fc = funct1(c);
            else fc = funct2(c);

            //decide which is next a and b
            if (fa * fc < 0) b = c;
            else if (fb * fc <= 0) a = c;
            
            error = Math.abs((c - prevC) / c);
            System.out.println(error);
            prevC = c;
        }

        if (error < 0.01) System.out.println("Root found: " + c);
        else System.out.println("Diverges at these points.");
    }

    public static void NewtonRaph(Scanner in, String funct) {
        System.out.println("Newton-Raphson\nWhat is x subscript n? ");
        double xn = (double) in.nextInt();

        double xn1 = Double.MAX_VALUE;
        double fxn, fpxn, error = 1d;

        for (int i = 0; error > 0.01 && i < 100; i++) {
            //function call
            if (funct.equals("a")) fxn = funct1(xn);
            else fxn = funct2(xn);

            //function derivative call
            if (funct.equals("a")) fpxn = derivFunct1(xn);
            else fpxn = derivFunct2(xn);

            xn1 = xn - fxn / fpxn;
            error = Math.abs( (xn1 - xn) / xn1 ); //approx rel error
            System.out.println(error);

            if (error < 0.01) System.out.println("Root found: " + xn1); //found root
            else { //not found so set up next iter
                xn = xn1;
                xn1 = Double.MAX_VALUE;
            }
        }

        if (error >= 0.01) System.out.println("Root is not near n.");
    }

    public static void FalsePos(Scanner in, String funct) {
        System.out.println("False Position\nWhat is a? ");
        double a = (double) in.nextInt();
        System.out.println("What is b? ");
        double b = (double) in.nextInt();

        double fa, fb, fc, c, prevC = 1d, error = 1d;
        
        for (int i = 0; error > 0.01 && i < 100; i++) {
            //function call for a and b
            if (funct.equals("a")) {
                fa = funct1(a);
                fb = funct1(b);
            }
            else {
                fa = funct2(a);
                fb = funct2(b);
            }

            c = ( (a * fb) - (b * fa) ) / (fb - fa);

            //function call for c
            if (funct.equals("a")) fc = funct1(c);
            else fc = funct2(c);

            //approximate relative error
            error = Math.abs((c - prevC) / c);
            System.out.println(error);

            if (error < 0.01) System.out.println("Root found: " + c);
            else { //set up next iter
                if (fa * fc < 0) b = c;
                else a = c;
                prevC = c;
            }
        }

        if (error >= 0.01) System.out.println("Diverges at these points.");
    }

    public static void Secant(Scanner in, String funct) {
        System.out.println("Secant\nWhat is x subscript n - 1? ");
        double xnn = (double) in.nextInt();
        System.out.println("What is x subscript n? ");
        double xn = (double) in.nextInt();

        double fxnn, fxn, xn1, error = 1d;
        
        for (int i = 0; error > 0.01 && i < 100; i++) {
            //function call x sub n - 1
            if (funct.equals("a")) fxnn = funct1(xnn);
            else fxnn = funct2(xnn);

            //function call x sub n
            if (funct.equals("a")) fxn = funct1(xn);
            else fxn = funct2(xn);

            //calc x sub n + 1
            xn1 = xn - ( (fxn * (xn - xnn)) / (fxn - fxnn) );
            
            error = Math.abs( (xn1 - xn) / xn1 ) ;
            System.out.println(error);

            //set up next iter
            xnn = xn;
            xn = xn1;
        }

        if (error < 0.01) System.out.println("Root found: " + xn);
        else System.out.println("Diverges at these points.");
    }

    public static double funct1(double x) {
        //f(x) = 2x^3 - 11.7x^2 + 17.7x - 5
        x = (2 * Math.pow(x, 3)) - (11.7 * Math.pow(x, 2)) + (17.7 * x) - 5;
        return x;
    }

    public static double funct2(double x) {
        //f(x) = x + 10 - xcosh(50/x)
        x = x + 10 - x * 0.5 * (Math.pow(Math.E, (50 / x)) + Math.pow(Math.E, (50 /-x)));
        return x;
    }

    public static double derivFunct1(double x) {
        //derivative of function1
        x = (6 * Math.pow(x, 2)) - (23.4 * x) + 17.7;
        return x;
    }

    public static double derivFunct2(double x) {
        //derivate of function2, product rule and chain rule 
        x = 1 - ( 0.5 * (Math.pow(Math.E, (50 / x)) + Math.pow(Math.E, (50 /-x)))
            + x * 0.5 * (-50 * Math.pow(Math.E, (50 / x)) * Math.pow(x, -2) + 50 * Math.pow(Math.E, (50 /-x)) * Math.pow(x, -2)) );
        return x;
    }
}
