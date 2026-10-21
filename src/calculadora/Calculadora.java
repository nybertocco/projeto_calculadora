package calculadora;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {

    // Realiza a operação "op" entre os números "a" e "b"
    public String calc(int a, int b, String op) {
        if (op == null) {
            return "Erro: operação inválida. Use +, -, * ou /.";
        }

        String operador = op.trim();
        if (operador.equals("+")) {
            return somar(a, b);
        }
        if (operador.equals("-")) {
            return subtrair(a, b);
        }
        if (operador.equals("*")) {
            return multiplicar(a, b);
        }
        if (operador.equals("/")) {
            return dividir(a, b);
        }
        return "Erro: operação inválida. Use +, -, * ou /.";
    }

    private String somar(int a, int b) {
        long soma = (long) a + b;
        return String.valueOf(soma);
    }

    private String subtrair(int a, int b) {
        long diferenca = (long) a - b;
        return String.valueOf(diferenca);
    }

    private String multiplicar(int a, int b) {
        long produto = (long) a * b;
        return String.valueOf(produto);
    }

    private String dividir(int a, int b) {
        if (b == 0) {
            return "Erro: divisão por zero não é permitida.";
        }
        BigDecimal quociente = new BigDecimal(a).divide(new BigDecimal(b), 2, RoundingMode.HALF_UP);
        return quociente.stripTrailingZeros().toPlainString();
    }
}
