package calculadora;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {

    // Realiza a operação "op" entre os números "a" e "b"
    public String calc(int a, int b, String op) {
        String r;
        if (op == null) {
            r = "Erro: operação inválida. Use +, -, * ou /.";
        } else {
            op = op.trim();
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
                if (b == 0) {
                    r = "Erro: divisão por zero não é permitida.";
                } else {
                    BigDecimal x = new BigDecimal(a).divide(new BigDecimal(b), 2, RoundingMode.HALF_UP);
                    r = x.stripTrailingZeros().toPlainString();
                }
            } else {
                r = "Erro: operação inválida. Use +, -, * ou /.";
            }
        }
        return r;
    }
}
