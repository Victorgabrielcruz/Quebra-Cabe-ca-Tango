# Pacote `tango.app`

Este pacote contem a camada que coordena o fluxo principal da aplicacao.

## Arquivo `TangoApplication.java`

`TangoApplication.java` e responsavel por conectar os modulos do programa.

### O que o arquivo faz

- Recebe os argumentos vindos de `Main`.
- Abre um menu interativo quando o programa roda sem argumentos.
- Verifica se o usuario pediu ajuda com `--help` ou `-h`.
- Valida se a quantidade de argumentos esta correta.
- Identifica o arquivo de entrada informado pelo usuario.
- Define qual algoritmo sera usado:
  - se o usuario informar um algoritmo, usa o algoritmo informado;
  - se nao informar, usa `backtracking` como padrao.
- Chama `InputReader` para carregar o tabuleiro.
- Chama `BoardPrinter` para imprimir o tabuleiro inicial.
- Chama `SolverFactory` para criar o solver correto.
- Executa o solver escolhido.
- Imprime o tabuleiro resolvido ou informa que nao encontrou solucao.
- Exibe a quantidade de estados visitados.
- Gera arquivos de entrada vazios para testes.

### Fluxo dentro do `TangoApplication`

1. `runInteractive()` exibe o menu quando nao ha argumentos.
2. No menu, o usuario pode resolver um tabuleiro, gerar entrada, ver ajuda ou sair.
3. `run(args)` recebe os argumentos quando o modo por comando e usado.
4. Se for ajuda, chama `Main.printUsage()`.
5. Se houver argumentos demais, lanca erro.
6. Converte o primeiro argumento em `Path`.
7. Converte o segundo argumento em `SolverType`, quando existir.
8. Le o tabuleiro inicial com `InputReader`.
9. Cria o algoritmo com `SolverFactory`.
10. Imprime o tabuleiro inicial.
11. Executa o algoritmo.
12. Imprime o resultado.

### Relevancia para o trabalho

Este arquivo e importante porque concentra a integracao do sistema. Ele evita que a classe `Main` fique misturando leitura, impressao, escolha de algoritmo e execucao. Isso melhora a modularizacao, um dos pontos pedidos no enunciado.
