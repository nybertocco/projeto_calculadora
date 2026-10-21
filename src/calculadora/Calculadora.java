package calculadora;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {

    // Realiza a operação "op" entre os números "a" e "b"
    public String calc(int a, int b, String op) {
        String r = "";
        if (op.equals("+")) {
            long x = (long) a + b;
            r = String.valueOf(x);
        } else if (op.equals("-")) {
            long x = (long) a - b;
            r = String.valueOf(x);
        } else if (op.equals("*")) {
            long x = (long) a * b;
            r = String.valueOf(x);
        } else if (op.equals("/")) {
            BigDecimal x = new BigDecimal(a).divide(new BigDecimal(b), 2, RoundingMode.HALF_UP);
            r = x.stripTrailingZeros().toPlainString();
        }
        return r;
    }
}
