# Estrutura do Codigo em `src`

Este documento explica os arquivos de codificacao localizados dentro da pasta `src/`. O foco e mostrar o papel de cada classe, como elas se conectam e quais responsabilidades cada pacote concentra.

## Visao Geral do Fluxo

1. `Main` inicia o programa.
2. `Main` chama `TangoApplication`.
3. `TangoApplication` abre o menu interativo.
4. O menu lista, gera, cria ou importa tabuleiros pelo `BoardCatalog`.
5. `InputReader` carrega o arquivo e `BoardInputValidator` verifica a entrada.
6. `BoardPrinter` imprime o tabuleiro inicial.
7. `SolverFactory` escolhe entre forca bruta e backtracking.
8. O solver escolhido tenta resolver o tabuleiro.
9. `SolverResult` guarda a solucao e a quantidade de estados visitados.
10. `BoardPrinter` imprime o resultado final quando existe solucao.

## Estrutura Dentro de `src`

```text
src/
  main/
    java/
      tango/
        Main.java
        app/
          TangoApplication.java
        io/
          InputReader.java
          BoardPrinter.java
          InputGenerator.java
          BoardCatalog.java
          BoardInputValidator.java
        model/
          Board.java
          CellValue.java
          Position.java
          ConstraintType.java
          Constraint.java
        validation/
          BoardValidator.java
        solver/
          Solver.java
          SolverType.java
          SolverFactory.java
          SolverResult.java
          BruteForceSolver.java
          BacktrackingSolver.java
```

## Pacote `tango`

`Main.java` e o ponto de entrada. Ele cria `TangoApplication`, abre o menu interativo e trata erros simples de uso.

## Pacote `tango.app`

`TangoApplication.java` coordena o fluxo principal. Ele mostra o menu, chama leitor, validador, catalogo, gerador, impressor e solvers. A classe nao implementa as regras do Tango; ela conecta os modulos especializados.

## Pacote `tango.io`

`InputReader.java` le arquivos no formato textual aceito pelo programa. Ele interpreta `size=`, a secao `board:` e a secao opcional `constraints:`.

`BoardPrinter.java` imprime tabuleiros e restricoes no terminal com indices de linha e coluna.

`InputGenerator.java` cria tabuleiros aleatorios solucionaveis. Primeiro monta uma solucao completa valida, depois seleciona dicas e restricoes derivadas dessa solucao.

`BoardCatalog.java` organiza os arquivos salvos em `examples/tabuleiros`. Ele cria nomes sequenciais por origem (`automatico`, `manual`, `importado`) e adiciona data e hora ao nome.

`BoardInputValidator.java` valida entradas antes de salvar ou resolver. Ele rejeita trios iguais ja formados, excesso de simbolos em linhas e colunas, restricoes invalidas e restricoes entre celulas nao adjacentes.

## Pacote `tango.model`

`Board.java` representa o tabuleiro. Guarda tamanho, matriz de celulas, restricoes, consulta e alteracao de celulas, copia do estado e verificacao de limites.

`CellValue.java` define os valores possiveis de uma celula: `VAZIO`, `SOL` e `LUA`.

`Position.java` representa uma coordenada por linha e coluna.

`ConstraintType.java` define os tipos de restricao: `EQUAL` para `=` e `OPPOSITE` para `x`.

`Constraint.java` liga duas posicoes a um tipo de restricao.

## Pacote `tango.validation`

`BoardValidator.java` centraliza as regras do Tango. Ele possui validacao final para tabuleiros completos e validacao parcial para estados intermediarios usados pelo backtracking.

## Pacote `tango.solver`

`Solver.java` define o contrato comum dos algoritmos.

`SolverType.java` enumera os algoritmos que podem ser escolhidos pelo menu: `forca-bruta` e `backtracking`.

`SolverFactory.java` cria a implementacao correta a partir do tipo escolhido.

`SolverResult.java` guarda o tabuleiro resolvido, quando existe, e a quantidade de estados visitados.

`BruteForceSolver.java` testa combinacoes para as celulas vazias e valida apenas tabuleiros completos.

`BacktrackingSolver.java` tenta preencher o tabuleiro incrementalmente e usa validacao parcial para podar estados que ja violam alguma regra.

## Resumo das Responsabilidades

```text
Main.java                -> entrada inicial do programa
TangoApplication.java    -> fluxo principal e integracao
InputReader.java         -> leitura do arquivo de entrada
BoardPrinter.java        -> impressao no terminal
InputGenerator.java      -> geracao aleatoria de tabuleiros solucionaveis
BoardCatalog.java        -> catalogo, nomes e persistencia dos arquivos
BoardInputValidator.java -> validacao das entradas antes da busca
Board.java               -> representacao do tabuleiro
CellValue.java           -> valores possiveis das celulas
Position.java            -> coordenadas do tabuleiro
ConstraintType.java      -> tipos de restricao
Constraint.java          -> restricao entre duas posicoes
BoardValidator.java      -> validacao das regras e podas
Solver.java              -> contrato dos algoritmos
SolverType.java          -> tipos de algoritmo aceitos
SolverFactory.java       -> criacao do solver escolhido
SolverResult.java        -> resultado e estatisticas
BruteForceSolver.java    -> algoritmo de forca bruta
BacktrackingSolver.java  -> algoritmo de backtracking
```
