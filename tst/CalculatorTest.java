import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

public class CalculatorTest {

    private static final double ERROR = 0.0001;

    private static final String[][] tests = {
            {"2+2", "4"},
            {"5/2", "2.5"},
            {"(3)", "3"},
            {"3+2*7", "17"},
            {"4.5*3.2", "14.4"},
            {"(4.5)3.2", "14.4"},
            {"3.2(4.5)", "14.4"},
            {"3+", null},
            {"(2))", null},
            {"-5*(-(5+2)-1)(-1)", "-40"},
            {"", null},
            {"-", null},
            {"--1", null},
            {"-(-1+2)", "-1"},
            {"-(-5.7)(3)+(2.1-1/3(1.2^4))", "18.5088"},
            {"1/0", null},
            {"1/(-0)", null},
            {"3^(1+1)", "9"},
            {"(2+1)^2", "9"},
            {"1+1=", "2"},
            {"1=1", null},
            {"-0", "0"},
            {"(-2)^2", "4"},
            {"2^(-2)", "0.25"},
            {"2^0", "1"},
            {"-(2^2)", "-4"},
            {"-2^2", "-4"}};

    @Test
    public void calculate() {

        Calculator calc = new Calculator();

        for (String[] test : tests) {

            Double result = calc.calculate(test[0]);

            if (test[1] == null) {
                assertNull("Test failed with input \"" + test[0] + "\"\n"
                        + "Expected: null"
                        + "\nActual: " + result,
                        result);
            }

            else {

                Double expected = Double.parseDouble(test[1]);

                Double low = expected - ERROR;
                Double high = expected + ERROR;

                assertTrue("Test failed with input \"" + test[0] + "\"\n"
                        + "Expected: " + test[1]
                        + "\nActual: " + result,
                        result >= low);
                assertTrue("Test failed with input \"" + test[0] + "\"\n"
                        + "Expected: " + test[1]
                        + "\nActual: " + result,
                        result <= high);

            }

        }

    }

}
