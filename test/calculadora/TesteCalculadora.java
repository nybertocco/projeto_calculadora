package calculadora;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Testes unitários (JUnit 5) da classe {@link Calculadora}.
 *
 * <p>Cada cenário é executado pelo método {@link #verificar} que imprime a
 * <b>entrada utilizada</b>, o <b>resultado esperado</b> e o <b>resultado
 * obtido</b> antes de compará-los. A ordem de execução segue o enunciado da
 * atividade (soma, subtração, multiplicação, divisão, divisão por zero e
 * operação inválida), seguida de testes complementares de robustez.</p>
 *
 * @author Nycolas Ribeiro Almeida Bertocco
 * @version 2.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesteCalculadora {

    /** Mensagem esperada quando há divisão por zero (texto literal, independente da classe testada). */
    private static final String ERRO_DIVISAO_POR_ZERO = "Erro: divisão por zero não é permitida.";
    /** Mensagem esperada quando o operador é inválido ou nulo. */
    private static final String ERRO_OPERACAO_INVALIDA = "Erro: operação inválida. Use +, -, * ou /.";

    /** Instância da classe testada, recriada antes de cada teste. */
    private Calculadora calculadora;

    /** Cria uma nova {@link Calculadora} antes de cada teste, garantindo testes independentes. */
    @BeforeEach
    void criarCalculadora() {
        calculadora = new Calculadora();
    }

    /**
     * Executa a operação, mostra entrada / esperado / obtido e compara os resultados.
     *
     * @param cenario nome do cenário, usado apenas na mensagem impressa
     * @param a       primeiro operando
     * @param b       segundo operando
     * @param op      operador (pode ser {@code null})
     * @param esperado resultado esperado da chamada a {@link Calculadora#calc}
     */
    private void verificar(String cenario, int a, int b, String op, String esperado) {
        String obtido = calculadora.calc(a, b, op);
        String situacao = esperado.equals(obtido) ? "PASSOU" : "FALHOU";
        System.out.printf("[%s] entrada: a=%d, b=%d, op=\"%s\" | esperado: %s | obtido: %s | %s%n",
                cenario, a, b, op, esperado, obtido, situacao);
        assertEquals(esperado, obtido);
    }

    // ---------- Testes obrigatórios (enunciado) ----------

    /** Soma de inteiros positivos, com número negativo e com zeros. */
    @Test
    @DisplayName("Soma")
    @Order(1)
    void testeSoma() {
        verificar("SOMA", 8, 5, "+", "13");
        verificar("SOMA", -4, 10, "+", "6");
        verificar("SOMA", 0, 0, "+", "0");
    }

    /** Subtração com resultado positivo, negativo e nulo. */
    @Test
    @DisplayName("Subtração")
    @Order(2)
    void testeSubtracao() {
        verificar("SUBTRAÇÃO", 10, 4, "-", "6");
        verificar("SUBTRAÇÃO", 4, 10, "-", "-6");
        verificar("SUBTRAÇÃO", 7, 7, "-", "0");
    }

    /** Multiplicação de inteiros positivos, com número negativo e com zero. */
    @Test
    @DisplayName("Multiplicação")
    @Order(3)
    void testeMultiplicacao() {
        verificar("MULTIPLICAÇÃO", 6, 7, "*", "42");
        verificar("MULTIPLICAÇÃO", -3, 5, "*", "-15");
        verificar("MULTIPLICAÇÃO", 9, 0, "*", "0");
    }

    /** Divisão exata, com casas decimais, com arredondamento e com dividendo zero. */
    @Test
    @DisplayName("Divisão")
    @Order(4)
    void testeDivisao() {
        verificar("DIVISÃO", 20, 4, "/", "5");
        verificar("DIVISÃO", 7, 2, "/", "3.5");
        verificar("DIVISÃO", 10, 3, "/", "3.33");
        verificar("DIVISÃO", 0, 5, "/", "0");
    }

    /** Divisão por zero deve devolver mensagem de erro, sem lançar exceção. */
    @Test
    @DisplayName("Divisão por zero")
    @Order(5)
    void testeDivisaoPorZero() {
        verificar("DIVISÃO POR ZERO", 10, 0, "/", ERRO_DIVISAO_POR_ZERO);
        verificar("DIVISÃO POR ZERO", 0, 0, "/", ERRO_DIVISAO_POR_ZERO);
    }

    /** Operadores desconhecidos ou vazios devem devolver mensagem de erro. */
    @Test
    @DisplayName("Operação inválida")
    @Order(6)
    void testeOperacaoInvalida() {
        verificar("OPERAÇÃO INVÁLIDA", 5, 3, "%", ERRO_OPERACAO_INVALIDA);
        verificar("OPERAÇÃO INVÁLIDA", 5, 3, "abc", ERRO_OPERACAO_INVALIDA);
        verificar("OPERAÇÃO INVÁLIDA", 5, 3, "", ERRO_OPERACAO_INVALIDA);
    }

    // ---------- Testes complementares (robustez) ----------

    /** Operador nulo não deve causar NullPointerException. */
    @Test
    @DisplayName("Operador nulo não interrompe a execução")
    @Order(7)
    void testeOperadorNulo() {
        verificar("OPERADOR NULO", 5, 3, null, ERRO_OPERACAO_INVALIDA);
    }

    /** Espaços ao redor do operador devem ser ignorados. */
    @Test
    @DisplayName("Operador com espaços ao redor é aceito")
    @Order(8)
    void testeOperadorComEspacos() {
        verificar("OPERADOR COM ESPAÇOS", 8, 5, " + ", "13");
    }

    /** Resultados acima de Integer.MAX_VALUE (ou abaixo de MIN_VALUE) não devem estourar. */
    @Test
    @DisplayName("Resultados fora do intervalo de int não estouram")
    @Order(9)
    void testeSemEstouroDeInteiro() {
        verificar("SEM ESTOURO", Integer.MAX_VALUE, 1, "+", "2147483648");
        verificar("SEM ESTOURO", Integer.MIN_VALUE, 1, "-", "-2147483649");
        verificar("SEM ESTOURO", Integer.MAX_VALUE, Integer.MAX_VALUE, "*", "4611686014132420609");
    }
}
