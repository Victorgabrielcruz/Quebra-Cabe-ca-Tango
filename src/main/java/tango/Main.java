package tango;

import tango.app.TangoApplication;

/**
 * Ponto de entrada do Tango Solver.
 */
public class Main {
    /** Impede a criacao de instancias da classe de entrada. */
    private Main() {
    }

    /**
     * Inicia a aplicacao no menu interativo e apresenta erros esperados em
     * formato amigavel.
     *
     * @param args argumentos ignorados; a execucao ocorre pelo menu interativo
     */
    public static void main(String[] args) {
        TangoApplication application = new TangoApplication();

        try {
            application.runInteractive();
        } catch (IllegalArgumentException exception) {
            System.out.println("Erro: " + exception.getMessage());
        } catch (UnsupportedOperationException exception) {
            System.out.println("Parte ainda nao implementada: " + exception.getMessage());
            System.out.println("Consulte docs/divisao-tarefas.md para ver quem esta responsavel por esse modulo.");
        }
    }
}
