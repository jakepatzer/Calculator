import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("Please enter an expression to evaluate, or \"q\" to quit\n");

        while (true) {

            String input = scanner.nextLine();

            if (input.equals("q")) {
                break;
            }

            Double result = calculator.calculate(input);

            if (result == null) {
                System.out.println("Please enter a valid expression\n");
            }
            else {
                System.out.println(" = " + result + "\n");
            }

        }

    }

}
