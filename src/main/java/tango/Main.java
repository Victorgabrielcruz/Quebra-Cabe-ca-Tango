package tango;

import tango.app.TangoApplication;
import tango.solver.SolverType;

/**
 * Ponto de entrada do Tango Solver.
 *
 * Sem argumentos, abre o menu interativo. Com argumentos, executa o arquivo
 * e o algoritmo informados.
 */
public class Main {
    /** Impede a criacao de instancias da classe de entrada. */
    private Main() {
    }

    /**
     * Inicia a aplicacao e apresenta erros esperados em formato amigavel.
     *
     * @param args caminho do arquivo e, opcionalmente, nome do algoritmo
     */
    public static void main(String[] args) {
        TangoApplication application = new TangoApplication();

        try {
            if (args.length == 0) {
                application.runInteractive();
            } else {
                application.run(args);
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Erro: " + exception.getMessage());
            System.out.println();
            printUsage();
        } catch (UnsupportedOperationException exception) {
            System.out.println("Parte ainda nao implementada: " + exception.getMessage());
            System.out.println("Consulte docs/divisao-tarefas.md para ver quem esta responsavel por esse modulo.");
        }
    }

    /**
     * Imprime os modos de uso, algoritmos e formato resumido da entrada.
     */
    public static void printUsage() {
        System.out.println("TP2 FPAA - Tango Solver");
        System.out.println();
        System.out.println("Modo interativo:");
        System.out.println("  java -cp out tango.Main");
        System.out.println("  Permite listar, gerar, criar, importar e resolver tabuleiros.");
        System.out.println("  O catalogo fica em examples/tabuleiros.");
        System.out.println();
        System.out.println("Modo por comando:");
        System.out.println("  java -cp out tango.Main <arquivo-entrada> [algoritmo]");
        System.out.println();
        System.out.println("Algoritmos disponiveis:");
        for (SolverType type : SolverType.values()) {
            System.out.println("  " + type.getArgumentName());
        }
        System.out.println();
        System.out.println("Exemplo:");
        System.out.println("  java -cp out tango.Main examples/tabuleiros/manual-001-20260620-153012.txt backtracking");
        System.out.println();
        System.out.println("Formato externo: size=<par>, secao board: com . S L e secao opcional constraints:.");
        System.out.println("Arquivos externos passam por validacao antes da busca.");
    }
}
