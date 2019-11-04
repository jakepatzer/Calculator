import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

public class CalculatorTest {

    private static final double ERROR = 0.0001;

    private static final String[][] tests = {
            {"2+2", "4"},
            {"(3)", "3"},
            {"3+2*7", "17"},
            {"4.5*3.2", "14.4"},
            {"(4.5)3.2", "14.4"},
            {"3.2(4.5)", "14.4"},
            {"3+", null},
            {"(2))", null}};

    @Test
    public void calculate() {

        Calculator calc = new Calculator();

        for (String[] test : tests) {

            Double result = calc.calculate(test[0]);

            if (test[1] == null) {
                assertNull(result);
            }

            else {

                Double expected = Double.parseDouble(test[1]);

                Double low = expected - ERROR;
                Double high = expected + ERROR;

                assertTrue(result >= low);
                assertTrue(result <= high);

            }

        }

    }

}
