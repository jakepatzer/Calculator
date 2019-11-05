import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;

public class Calculator {

    private static final char[] VALID_CHARS_ARRAY = {'(', ')', '*', '+', '-', '/', '.', '^'};

    private Set validChars;
    private DecimalFormat df;

    public Calculator() {
        df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340);
        validChars = new HashSet();
        for (char c : VALID_CHARS_ARRAY) {
            validChars.add(c);
        }
    }

    public Double calculate(String expr) {

        if (expr.length() == 0) return null;

        expr.replaceAll("\\s", "");
        if (expr.charAt(expr.length() - 1) == '=') {
            expr = expr.substring(0, expr.length() - 1);
        }

        if (!isValid(expr)) return null;

        for (int i = 0; i < expr.length() - 1; i++) {
            if (Character.isDigit(expr.charAt(i)) && expr.charAt(i + 1) == '(') {
                expr = expr.substring(0, i + 1) + "*" + expr.substring(i + 1);
                i++;
            }
            else if (expr.charAt(i) == ')' && Character.isDigit(expr.charAt(i + 1))) {
                expr = expr.substring(0, i + 1) + "*" + expr.substring(i + 1);
                i++;
            }
            else if (expr.charAt(i) == ')' && expr.charAt(i + 1) == '(') {
                expr = expr.substring(0, i + 1) + "*" + expr.substring(i + 1);
            }
        }

        Stack<Integer> openParentheses = new Stack<>();
        List<Pair> indexes = new ArrayList<>();

        for (int i = 0; i < expr.length(); i++) {

            if (expr.charAt(i) == '(') {
                openParentheses.push(i);
            }
            else if (expr.charAt(i) == ')') {
                if (openParentheses.empty()) return null;
                indexes.add(new Pair(openParentheses.pop(), i));
            }

        }

        if (!openParentheses.empty()) return null;

        while (!indexes.isEmpty()) {

            Pair index = indexes.remove(0);

            String result = evaluate(expr.substring(index.first + 1, index.second));
            if (result == null) return null;
            expr = expr.substring(0, index.first)
                    + result
                    + expr.substring(index.second + 1);

            int originalLength = index.second - index.first + 1;
            int newLength = result.length();
            int begin = index.first;

            for (Pair pair : indexes) {
                if (pair.first > begin + newLength) {
                    pair.first -= originalLength - newLength;
                }
                if (pair.second > begin + newLength) {
                    pair.second -= originalLength - newLength;
                }
            }

        }

        String result = evaluate(expr);
        if (result == null) return null;

        if (result.charAt(0) == '(' && result.charAt(result.length() - 1) == ')') {
            result = result.substring(1, result.length() - 1);
        }

        try {
            return Double.parseDouble(result);
        } catch (NumberFormatException e) {
            return null;
        }

    }

    private boolean isValid(String expr) {

        for (int i = 0; i < expr.length(); i++) {
            if (!Character.isDigit(expr.charAt(i))
                    && !validChars.contains(expr.charAt(i))) {
                return false;
            }
        }

        return true;

    }

    private String evaluate(String expr) {

        List<String> tokens = new ArrayList<>(Arrays.asList(expr.split("((?<=[-+*/^()])|(?=[-+*/^()]))")));

        if (tokens.get(0).equals("-")) {
            tokens.set(0, "-1");
            tokens.add(1, "*");
        }

        try {

            for (int i = 0; i < tokens.size() - 3; i++) {
                if (tokens.get(i).equals("(") && tokens.get(i + 1).equals("-") && tokens.get(i + 3).equals(")")) {
                    tokens.remove(i);
                    tokens.remove(i);
                    tokens.set(i, "-" + tokens.get(i));
                    tokens.remove(i + 1);
                }
            }

            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).equals("^")) {
                    double op1 = Double.parseDouble(tokens.get(i - 1));
                    double op2 = Double.parseDouble(tokens.get(i + 1));
                    double result = Math.pow(op1, op2);
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.add(i - 1, df.format(result));
                    i--;
                }
            }

            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).equals("*")) {
                    double op1 = Double.parseDouble(tokens.get(i - 1));
                    double op2 = Double.parseDouble(tokens.get(i + 1));
                    double result = op1 * op2;
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.add(i - 1, df.format(result));
                    i--;
                }
                else if (tokens.get(i).equals("/")) {
                    double op1 = Double.parseDouble(tokens.get(i - 1));
                    double op2 = Double.parseDouble(tokens.get(i + 1));
                    double result = op1 / op2;
                    if (op2 == 0) return null;
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.add(i - 1, df.format(result));
                    i--;
                }
            }

            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).equals("+")) {
                    double op1 = Double.parseDouble(tokens.get(i - 1));
                    double op2 = Double.parseDouble(tokens.get(i + 1));
                    double result = op1 + op2;
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.add(i - 1, df.format(result));
                    i--;
                }
                else if (tokens.get(i).equals("-")) {
                    double op1 = Double.parseDouble(tokens.get(i - 1));
                    double op2 = Double.parseDouble(tokens.get(i + 1));
                    double result = op1 - op2;
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.add(i - 1, df.format(result));
                    i--;
                }
            }

            if (tokens.size() != 1) return null;
            if (Double.parseDouble(tokens.get(0)) < 0) {
                return "(" + tokens.get(0) + ")";
            }
            return tokens.get(0);

        } catch (NumberFormatException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }

}
