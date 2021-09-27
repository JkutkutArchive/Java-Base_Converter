import java.util.ArrayList;

public class Converter {

    public static final int BINARY = 2;
    public static final int OCTAL = 8;
    public static final int DECIMAL = 10;
    public static final int HEXADECIMAL = 16;

    /**
     * Number converter.
     * @param number - String with the number in any base.
     * @param baseFrom - Base of the number.
     * @param baseTo - Desired base.
     * @return The equivalent number on the given base.
     * @throws Exception
     */
    public static String converter(String number, int baseFrom, int baseTo) throws Exception {
        System.out.printf("\nConverter:\n- Converting %s from base %d to base %d:\n\n", number, baseFrom, baseTo);
        
        if (baseFrom == baseTo) { // If same base.
            System.out.println("Same base!");
            return number;
        }

        if (baseFrom == Converter.DECIMAL) {
            int decimalNumber = Integer.parseInt(number);
            return Converter.decimal2base(decimalNumber, baseTo);
        }
        return "";
    }

    /**
     * Converter from decimal to any base
     * @param number - Desired number (Decimal)
     * @param baseTo - Desired base
     * @return String with the look of number on the desired base.
     * @throws Exception
     */
    private static String decimal2base(int number, int baseTo) throws Exception {
        final int STEPS_PER_LINE = 5;
        ArrayList<String[]> steps = new ArrayList<String[]>();
        
        String solution = "";
        int nDigitsDivisor = Converter.lengthNumber(baseTo);

        while (number >= baseTo) {
            int quotient = (int) number / baseTo;
            int remainder = number % baseTo;

            // Get number of digits of Divisor and remainder
            int nDigitsNumber = Converter.lengthNumber(number);
            int nDigitsQuotient = Converter.lengthNumber(quotient);
            int nDigitsRemainder = Converter.lengthNumber(remainder);
            
            // Make step (3 lines)
            String[] s = {"", "", ""};
            
            // Start line
            s[0] = String.format("%d │%d", number, baseTo);
            for (int i = 0; i < nDigitsQuotient - nDigitsDivisor + 1; i++) {
                s[0] += " ";
            }

            // Middle line
            for (int i = 0; i < nDigitsNumber; i++) {
                s[1] += " ";
            }
            s[1] += " └";
            for (int i = 0; i < Math.max(nDigitsDivisor, nDigitsQuotient) + 1; i++) {
                s[1] += "─";
            }

            // last line
            for (int i = 0; i < nDigitsNumber - nDigitsRemainder; i++) {
                s[2] += " ";
            }
            s[2] += String.format("%d  %d ", remainder, quotient);
            
            steps.add(s);

            number = quotient;
            solution = Converter.symbolEquivalent(remainder) + solution;
        }
        solution = Converter.symbolEquivalent(number) + solution; // Add the final number


        String[] divider = {
            "      ",
            "  =>  ",
            "      "
        };

        for (int k = 0; k < steps.size(); k += STEPS_PER_LINE) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < STEPS_PER_LINE && j + k < steps.size(); j++) {
                    System.out.print(steps.get(k + j)[i]);
                    System.out.print(divider[i]);
                }
                System.out.println();
            }
            System.out.println("\n");
        }
        

        // Add the final Message
        System.out.println(" Done\n\n");
        

        return solution;
    }

    private static String base2decimal(String number, int baseFrom) throws Exception {
        int solution = 0;
        String[] calc = new String[number.length()];

        for (int i = number.length() - 1, j = 0; i >= 0; i--, j++) {
            int value = Converter.numberEquivalent(String.valueOf(number.charAt(i)));
            
            calc[i] = String.format("%d * %d^(%d)", value, baseFrom, j);
            solution += (int) (value * Math.pow(baseFrom, j));
        }

        System.out.println(String.join(" + ", calc));
        return String.valueOf(solution);
    }

    // Help methods

    /**
     * @param n - Number to use.
     * @return The number of digits of the number (3 -> 1; 123 -> 3; -234324 -> 7)
     */
    private static int lengthNumber(int n) {
        int extra = 0;
        if (n == 0) {
            return 1;
        }
        if (n < 0) {
            extra = 1;
            n *= -1;
        }

        return (int) (Math.log10(n) + 1) + extra;
    }

    private static String symbolEquivalent(int number) throws Exception {
        if (number < 0) {
            throw new Exception("The number can not be less than 0");
        }
        if (number < 10) {
            return String.valueOf(number);
        }
        return String.valueOf((char) (65 + number - 10));
    }

    public static void main(String[] args) {

        String numero = "15";

        int from = Converter.DECIMAL;
        int to = Converter.HEXADECIMAL;

        try {
            String output = converter(numero, from, to);    
            System.out.printf("The number '%s' in base %d is '%s' in base %d.\n\n", numero, from, output, to);
        }
        catch (Exception e) {
            System.out.printf("Not able to convert '%s' in base %d to base %d.\n", numero, from, to);
            System.out.println(e);
        }
    }
}