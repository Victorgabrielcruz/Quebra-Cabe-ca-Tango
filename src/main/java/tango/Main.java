package tango;

import tango.app.TangoApplication;
import tango.solver.SolverType;

public class Main {
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

    public static void printUsage() {
        System.out.println("TP2 FPAA - Tango Solver");
        System.out.println();
        System.out.println("Uso:");
        System.out.println("  java -cp out tango.Main <arquivo-entrada> [algoritmo]");
        System.out.println();
        System.out.println("Algoritmos disponiveis:");
        for (SolverType type : SolverType.values()) {
            System.out.println("  " + type.getArgumentName());
        }
        System.out.println();
        System.out.println("Exemplo:");
        System.out.println("  java -cp out tango.Main examples/tabuleiro-6x6.txt backtracking");
        System.out.println();
        System.out.println("Se executar sem argumentos, o programa abre um menu interativo.");
    }
}
