package calculadora;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {

    // Símbolos das operações aceitas
    public static final String SOMA = "+";
    public static final String SUBTRACAO = "-";
    public static final String MULTIPLICACAO = "*";
    public static final String DIVISAO = "/";

    // Mensagens de erro devolvidas ao chamador
    public static final String MSG_DIVISAO_POR_ZERO = "Erro: divisão por zero não é permitida.";
    public static final String MSG_OPERACAO_INVALIDA = "Erro: operação inválida. Use +, -, * ou /.";

    // Casas decimais usadas no resultado da divisão
    private static final int CASAS_DECIMAIS_DIVISAO = 2;

    // Realiza a operação "op" entre os números "a" e "b"
    public String calc(int a, int b, String op) {
        if (op == null) {
            return MSG_OPERACAO_INVALIDA;
        }

        switch (op.trim()) {
            case SOMA:
                return somar(a, b);
            case SUBTRACAO:
                return subtrair(a, b);
            case MULTIPLICACAO:
                return multiplicar(a, b);
            case DIVISAO:
                return dividir(a, b);
            default:
                return MSG_OPERACAO_INVALIDA;
        }
    }

    private String somar(int primeiroNumero, int segundoNumero) {
        long soma = (long) primeiroNumero + segundoNumero;
        return String.valueOf(soma);
    }

    private String subtrair(int minuendo, int subtraendo) {
        long diferenca = (long) minuendo - subtraendo;
        return String.valueOf(diferenca);
    }

    private String multiplicar(int multiplicando, int multiplicador) {
        long produto = (long) multiplicando * multiplicador;
        return String.valueOf(produto);
    }

    private String dividir(int dividendo, int divisor) {
        if (divisor == 0) {
            return MSG_DIVISAO_POR_ZERO;
        }
        BigDecimal quociente = BigDecimal.valueOf(dividendo)
                .divide(BigDecimal.valueOf(divisor), CASAS_DECIMAIS_DIVISAO, RoundingMode.HALF_UP);
        return formatar(quociente);
    }

    // Remove zeros à direita e evita notação científica (ex.: 100.00 -> 100)
    private String formatar(BigDecimal valor) {
        return valor.stripTrailingZeros().toPlainString();
    }
}
