package calculadora;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculadora de operações matemáticas básicas do módulo de cálculos do
 * aplicativo <b>FinançApp</b>.
 *
 * <p>Suporta soma ({@code +}), subtração ({@code -}), multiplicação
 * ({@code *}) e divisão ({@code /}) entre dois números inteiros. Todas as
 * falhas previsíveis (divisão por zero, operador inválido ou nulo) são
 * tratadas internamente e devolvidas como mensagens de texto, de forma que o
 * programa que utiliza a classe nunca é interrompido por uma exceção.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 * Calculadora calculadora = new Calculadora();
 * calculadora.calc(8, 5, "+");   // "13"
 * calculadora.calc(7, 2, "/");   // "3.5"
 * calculadora.calc(10, 0, "/");  // "Erro: divisão por zero não é permitida."
 * }</pre>
 *
 * @author Nycolas Ribeiro Almeida Bertocco
 * @version 2.0
 */
public class Calculadora {

    /** Símbolo da operação de soma. */
    public static final String SOMA = "+";

    /** Símbolo da operação de subtração. */
    public static final String SUBTRACAO = "-";

    /** Símbolo da operação de multiplicação. */
    public static final String MULTIPLICACAO = "*";

    /** Símbolo da operação de divisão. */
    public static final String DIVISAO = "/";

    /** Mensagem devolvida quando se tenta dividir um número por zero. */
    public static final String MSG_DIVISAO_POR_ZERO = "Erro: divisão por zero não é permitida.";

    /** Mensagem devolvida quando o operador informado não é suportado (ou é nulo). */
    public static final String MSG_OPERACAO_INVALIDA = "Erro: operação inválida. Use +, -, * ou /.";

    /** Quantidade de casas decimais usada no resultado da divisão. */
    private static final int CASAS_DECIMAIS_DIVISAO = 2;

    /**
     * Realiza a operação matemática indicada entre dois números inteiros.
     *
     * <p>Regras de funcionamento:</p>
     * <ul>
     *   <li>Soma, subtração e multiplicação são calculadas com {@code long},
     *       evitando estouro (overflow) quando o resultado ultrapassa o
     *       intervalo de {@code int}.</li>
     *   <li>A divisão é arredondada para 2 casas decimais
     *       ({@link RoundingMode#HALF_UP}) e os zeros à direita são removidos
     *       (por exemplo, {@code 20 / 4} resulta em {@code "5"} e
     *       {@code 10 / 3} resulta em {@code "3.33"}).</li>
     *   <li>Espaços ao redor do operador são ignorados
     *       (por exemplo, {@code " + "} equivale a {@code "+"}).</li>
     * </ul>
     *
     * <p><b>Exceções:</b> este método não propaga exceções ao chamador. As
     * situações que normalmente lançariam {@link ArithmeticException}
     * (divisão por zero) ou {@link NullPointerException} (operador nulo) são
     * verificadas antes da execução e convertidas em mensagens de erro.</p>
     *
     * @param a  primeiro operando
     * @param b  segundo operando
     * @param op operador da operação: {@code "+"}, {@code "-"}, {@code "*"}
     *           ou {@code "/"}
     * @return o resultado da operação como texto (por exemplo, {@code "13"} ou
     *         {@code "3.5"}); {@link #MSG_DIVISAO_POR_ZERO} se houver divisão
     *         por zero; ou {@link #MSG_OPERACAO_INVALIDA} se o operador for
     *         nulo ou não suportado
     */
    public String calc(int a, int b, String op) {
        // Operador nulo seria um NullPointerException em op.trim(): tratamos antes
        if (op == null) {
            return MSG_OPERACAO_INVALIDA;
        }

        // trim() permite entradas como " + " sem considerá-las inválidas
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

    /**
     * Soma dois números inteiros.
     *
     * @param primeiroNumero primeira parcela
     * @param segundoNumero  segunda parcela
     * @return a soma, como texto
     */
    private String somar(int primeiroNumero, int segundoNumero) {
        // Conversão para long antes da conta evita overflow de int
        long soma = (long) primeiroNumero + segundoNumero;
        return String.valueOf(soma);
    }

    /**
     * Subtrai dois números inteiros.
     *
     * @param minuendo   número do qual se subtrai
     * @param subtraendo número que é subtraído
     * @return a diferença, como texto
     */
    private String subtrair(int minuendo, int subtraendo) {
        long diferenca = (long) minuendo - subtraendo;
        return String.valueOf(diferenca);
    }

    /**
     * Multiplica dois números inteiros.
     *
     * @param multiplicando primeiro fator
     * @param multiplicador segundo fator
     * @return o produto, como texto
     */
    private String multiplicar(int multiplicando, int multiplicador) {
        long produto = (long) multiplicando * multiplicador;
        return String.valueOf(produto);
    }

    /**
     * Divide dois números inteiros, validando o divisor.
     *
     * @param dividendo número a ser dividido
     * @param divisor   número pelo qual se divide
     * @return o quociente com até 2 casas decimais, como texto, ou
     *         {@link #MSG_DIVISAO_POR_ZERO} se o divisor for zero
     */
    private String dividir(int dividendo, int divisor) {
        // Guarda contra ArithmeticException: nunca chegamos a dividir por zero
        if (divisor == 0) {
            return MSG_DIVISAO_POR_ZERO;
        }
        // BigDecimal garante arredondamento previsível (double teria imprecisão)
        BigDecimal quociente = BigDecimal.valueOf(dividendo)
                .divide(BigDecimal.valueOf(divisor), CASAS_DECIMAIS_DIVISAO, RoundingMode.HALF_UP);
        return formatar(quociente);
    }

    /**
     * Formata um valor decimal para exibição.
     *
     * @param valor valor a ser formatado
     * @return o valor sem zeros à direita e sem notação científica
     *         (por exemplo, {@code 100.00} vira {@code "100"})
     */
    private String formatar(BigDecimal valor) {
        // toPlainString evita formatos como "1E+2" após stripTrailingZeros
        return valor.stripTrailingZeros().toPlainString();
    }
}
