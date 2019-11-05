import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("Please enter an expression to evaluate, \"h\" for help, or \"q\" to quit\n");

        while (true) {

            String input = scanner.nextLine();

            if (input.equals("q")) {
                break;
            }

            if (input.equals("h")) {
                System.out.println(" - Valid operators are: + - * / ^\n"
                        + " - Decimals, negatives, and parentheses are all supported.\n"
                        + " - Please enclose negatives in parentheses, e.g. \"2*(-1)\"\n");
                continue;
            }

            Double result = calculator.calculate(input);

            if (result == null) {
                System.out.println("Please enter a valid expression, or enter \"h\" for help\n");
            }
            else {
                System.out.println(" = " + result + "\n");
            }

        }

    }

}
