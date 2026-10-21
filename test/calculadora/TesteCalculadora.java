package calculadora;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesteCalculadora {

    private static final String ERRO_DIVISAO_POR_ZERO = "Erro: divisão por zero não é permitida.";
    private static final String ERRO_OPERACAO_INVALIDA = "Erro: operação inválida. Use +, -, * ou /.";

    private Calculadora calculadora;

    @BeforeEach
    void criarCalculadora() {
        calculadora = new Calculadora();
    }

    // Executa a operação, mostra entrada / esperado / obtido e compara os resultados
    private void verificar(String cenario, int a, int b, String op, String esperado) {
        String obtido = calculadora.calc(a, b, op);
        String situacao = esperado.equals(obtido) ? "PASSOU" : "FALHOU";
        System.out.printf("[%s] entrada: a=%d, b=%d, op=\"%s\" | esperado: %s | obtido: %s | %s%n",
                cenario, a, b, op, esperado, obtido, situacao);
        assertEquals(esperado, obtido);
    }

    // ---------- Testes obrigatórios ----------

    @Test
    @DisplayName("Soma")
    @Order(1)
    void testeSoma() {
        verificar("SOMA", 8, 5, "+", "13");
        verificar("SOMA", -4, 10, "+", "6");
        verificar("SOMA", 0, 0, "+", "0");
    }

    @Test
    @DisplayName("Subtração")
    @Order(2)
    void testeSubtracao() {
        verificar("SUBTRAÇÃO", 10, 4, "-", "6");
        verificar("SUBTRAÇÃO", 4, 10, "-", "-6");
        verificar("SUBTRAÇÃO", 7, 7, "-", "0");
    }

    @Test
    @DisplayName("Multiplicação")
    @Order(3)
    void testeMultiplicacao() {
        verificar("MULTIPLICAÇÃO", 6, 7, "*", "42");
        verificar("MULTIPLICAÇÃO", -3, 5, "*", "-15");
        verificar("MULTIPLICAÇÃO", 9, 0, "*", "0");
    }

    @Test
    @DisplayName("Divisão")
    @Order(4)
    void testeDivisao() {
        verificar("DIVISÃO", 20, 4, "/", "5");
        verificar("DIVISÃO", 7, 2, "/", "3.5");
        verificar("DIVISÃO", 10, 3, "/", "3.33");
        verificar("DIVISÃO", 0, 5, "/", "0");
    }

    @Test
    @DisplayName("Divisão por zero")
    @Order(5)
    void testeDivisaoPorZero() {
        verificar("DIVISÃO POR ZERO", 10, 0, "/", ERRO_DIVISAO_POR_ZERO);
        verificar("DIVISÃO POR ZERO", 0, 0, "/", ERRO_DIVISAO_POR_ZERO);
    }

    @Test
    @DisplayName("Operação inválida")
    @Order(6)
    void testeOperacaoInvalida() {
        verificar("OPERAÇÃO INVÁLIDA", 5, 3, "%", ERRO_OPERACAO_INVALIDA);
        verificar("OPERAÇÃO INVÁLIDA", 5, 3, "abc", ERRO_OPERACAO_INVALIDA);
        verificar("OPERAÇÃO INVÁLIDA", 5, 3, "", ERRO_OPERACAO_INVALIDA);
    }

    // ---------- Testes complementares (robustez) ----------

    @Test
    @DisplayName("Operador nulo não interrompe a execução")
    @Order(7)
    void testeOperadorNulo() {
        verificar("OPERADOR NULO", 5, 3, null, ERRO_OPERACAO_INVALIDA);
    }

    @Test
    @DisplayName("Operador com espaços ao redor é aceito")
    @Order(8)
    void testeOperadorComEspacos() {
        verificar("OPERADOR COM ESPAÇOS", 8, 5, " + ", "13");
    }

    @Test
    @DisplayName("Resultados fora do intervalo de int não estouram")
    @Order(9)
    void testeSemEstouroDeInteiro() {
        verificar("SEM ESTOURO", Integer.MAX_VALUE, 1, "+", "2147483648");
        verificar("SEM ESTOURO", Integer.MIN_VALUE, 1, "-", "-2147483649");
        verificar("SEM ESTOURO", Integer.MAX_VALUE, Integer.MAX_VALUE, "*", "4611686014132420609");
    }
}
