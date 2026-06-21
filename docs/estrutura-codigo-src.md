# Estrutura do Codigo em `src`

Este documento explica apenas os arquivos de codificacao localizados dentro da pasta `src/`. O objetivo e mostrar o papel de cada classe, como elas se conectam e qual a relevancia delas para o TP2.

## Visao Geral do Fluxo

O fluxo pensado para o codigo Java e:

1. `Main` inicia o programa.
2. `Main` chama `TangoApplication`.
3. `TangoApplication` abre o menu ou le os argumentos do terminal.
4. O menu lista, gera, cria ou importa tabuleiros pelo `BoardCatalog`.
5. `InputReader` carrega o tabuleiro e `BoardInputValidator` verifica a entrada.
6. `BoardPrinter` imprime o tabuleiro inicial.
7. `SolverFactory` escolhe entre forca bruta e backtracking.
8. O solver escolhido executa a resolucao.
9. `SolverResult` guarda o resultado e `BoardPrinter` imprime o tabuleiro final.

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

## `src/main/java/tango/Main.java`

Classe principal do programa.

O que faz:

- contem o metodo `main`;
- recebe os argumentos passados pelo terminal;
- cria uma instancia de `TangoApplication`;
- chama o fluxo principal da aplicacao;
- mostra instrucoes de uso quando o usuario digita `--help`;
- trata erros simples de uso, como algoritmo invalido.


## `src/main/java/tango/app/TangoApplication.java`

Classe que coordena o fluxo geral do programa.

O que faz:

- interpreta os argumentos recebidos pelo `Main`;
- apresenta uma interface para listar, gerar, criar e importar tabuleiros;
- usa o catalogo `examples/tabuleiros` no fluxo interativo;
- define qual algoritmo sera usado;
- chama `InputReader` para ler o tabuleiro;
- valida a entrada antes de salvar ou resolver;
- chama `BoardPrinter` para imprimir o tabuleiro inicial;
- chama `SolverFactory` para criar o algoritmo escolhido;
- executa o solver;
- imprime o tabuleiro resolvido ou uma mensagem de falha;
- exibe a quantidade de estados visitados.

Relevancia para o TP2:

- faz a integracao entre os modulos do projeto;
- evita misturar leitura, impressao e algoritmo dentro do `Main`;
- deixa o programa mais modular, como o enunciado solicita.

## `src/main/java/tango/io/InputReader.java`

Classe responsavel pela leitura do arquivo de entrada.

O que faz:

- abrir o arquivo informado no terminal;
- ler o tamanho do tabuleiro;
- ler as celulas iniciais;
- ler as restricoes de igualdade `=`;
- ler as restricoes de oposicao `x`;
- montar e retornar um objeto `Board`.

Relevancia para o TP2:

- permite que o programa receba diferentes tabuleiros sem alterar o codigo;
- atende a exigencia de leitura da configuracao inicial;
- separa a entrada de dados da logica de resolucao.

## `src/main/java/tango/io/BoardPrinter.java`

Classe responsavel pela impressao do tabuleiro no terminal.

O que faz:

- imprimir o tabuleiro inicial;
- imprimir o tabuleiro final resolvido;
- representar celulas vazias, Sol e Lua;
- mostrar as restricoes de forma legivel, se essa for a escolha do grupo.

Relevancia para o TP2:

- atende a saida esperada pelo enunciado;
- facilita a visualizacao dos resultados;
- gera material para os exemplos de execucao da documentacao.

## `src/main/java/tango/io/InputGenerator.java`

Classe responsavel por gerar tabuleiros aleatorios solucionaveis.

O que faz:

- recebe tamanho e quantidades de dicas e restricoes;
- constroi uma solucao completa com escolhas aleatorias;
- seleciona dicas em posicoes embaralhadas;
- deriva restricoes validas entre celulas vizinhas.

Relevancia para o TP2:

- produz exemplos variados sem depender de edicao externa;
- garante que os exemplos automaticos possuem pelo menos uma solucao.

## `src/main/java/tango/io/BoardCatalog.java`

Classe responsavel pelo catalogo `examples/tabuleiros`.

O que faz:

- lista os arquivos disponiveis;
- serializa tabuleiros no formato oficial;
- cria nomes sequenciais para origens automatica, manual e importada;
- inclui data e hora em cada nome.

Relevancia para o TP2:

- centraliza e organiza os exemplos usados na demonstracao;
- permite selecionar entradas pela interface sem digitar caminhos.

## `src/main/java/tango/io/BoardInputValidator.java`

Classe responsavel por validar entradas antes da busca.

O que faz:

- rejeita trios iguais ja formados;
- rejeita excesso de Sol ou Lua em linhas e colunas;
- verifica restricoes decididas;
- exige que restricoes conectem celulas vizinhas.

Relevancia para o TP2:

- impede que arquivos defeituosos prossigam para os algoritmos;
- fornece mensagens que indicam a categoria da regra quebrada.

## `src/main/java/tango/model/Board.java`

Classe que representa o tabuleiro do Tango.

O que faz atualmente:

- guarda o tamanho do tabuleiro;
- verifica se o tamanho informado e positivo e par;
- armazena uma matriz de celulas;
- inicia todas as celulas como vazias;
- permite consultar e alterar celulas;
- permite guardar restricoes;
- permite criar uma copia do tabuleiro.

Relevancia para o TP2:

- e a estrutura central do problema;
- sera usada por entrada, impressao, validacao e algoritmos;
- serve como base para explicar a modelagem no relatorio.

## `src/main/java/tango/model/CellValue.java`

Enum que representa os valores possiveis de uma celula: vazia, Sol ou Lua.

Relevancia para o TP2:

- padroniza os valores das celulas;
- evita usar caracteres soltos pelos algoritmos.

## `src/main/java/tango/model/Position.java`

Classe que representa uma coordenada do tabuleiro, formada por linha e coluna.

Relevancia para o TP2:

- facilita identificar uma celula;
- facilita criar restricoes entre duas celulas.

## `src/main/java/tango/model/ConstraintType.java`

Enum que representa o tipo de restricao entre celulas.

Valores:

- `EQUAL`, para restricao `=`;
- `OPPOSITE`, para restricao `x`.

Relevancia para o TP2:

- representa diretamente as restricoes do enunciado.

## `src/main/java/tango/model/Constraint.java`

Classe que liga duas posicoes a um tipo de restricao.

Relevancia para o TP2:

- permite armazenar relacoes `=` e `x`;
- sera usada pelos validadores para conferir as regras do jogo.

## `src/main/java/tango/validation/BoardValidator.java`

Classe preparada para centralizar a validacao das regras do Tango.

O que deve validar:

- preenchimento completo;
- limite de adjacencia;
- equilibrio de linhas e colunas;
- restricoes de igualdade;
- restricoes de oposicao;
- validacao parcial para podas do backtracking;
- validacao final para a forca bruta.

Relevancia para o TP2:

- separa as regras do jogo dos algoritmos;
- fornece os criterios de poda usados pelo backtracking;
- fornece a validacao final usada pela forca bruta.

## `src/main/java/tango/solver/Solver.java`

Interface comum dos algoritmos de resolucao.

O que faz:

- define o metodo `solve(Board initialBoard)`;
- estabelece um formato comum para qualquer algoritmo.

Relevancia para o TP2:

- permite usar forca bruta e backtracking de forma padronizada;
- facilita comparar as duas abordagens;
- melhora a modularizacao do projeto.

## `src/main/java/tango/solver/SolverType.java`

Enum que representa os algoritmos disponiveis.

O que faz:

- define os tipos `BRUTE_FORCE` e `BACKTRACKING`;
- associa cada tipo ao nome usado no terminal:
  - `forca-bruta`;
  - `backtracking`;
- converte o texto digitado pelo usuario para o tipo correto;
- rejeita nomes invalidos.

Relevancia para o TP2:

- permite escolher a estrategia de resolucao na execucao;
- deixa claro que o projeto possui as duas tecnicas pedidas no enunciado.

## `src/main/java/tango/solver/SolverFactory.java`

Classe responsavel por criar o solver correto.

O que faz:

- recebe um `SolverType`;
- cria `BruteForceSolver` quando o tipo e `BRUTE_FORCE`;
- cria `BacktrackingSolver` quando o tipo e `BACKTRACKING`.

Relevancia para o TP2:

- centraliza a criacao dos algoritmos;
- evita espalhar decisoes de instanciacao pelo codigo;
- facilita manutencao e organizacao.

## `src/main/java/tango/solver/SolverResult.java`

Classe que representa o resultado da execucao de um algoritmo.

O que faz:

- guarda o tabuleiro resolvido;
- guarda a quantidade de estados visitados;
- informa se uma solucao foi encontrada.

Relevancia para o TP2:

- permite imprimir o resultado final;
- permite comparar a quantidade de estados visitados por cada algoritmo;
- ajuda na analise de complexidade e nos exemplos de execucao.

## `src/main/java/tango/solver/BruteForceSolver.java`

Classe que implementa a forca bruta.

O que faz:

- gerar combinacoes para as celulas vazias;
- testar os tabuleiros completos;
- verificar se alguma combinacao respeita todas as regras;
- contar quantos estados foram testados.
- preservar as dicas trabalhando sobre uma copia;
- validar apenas combinacoes completas, sem poda parcial.

Relevancia para o TP2:

- atende a exigencia da estrategia de forca bruta;
- mostra o custo da busca exaustiva;
- serve como base de comparacao com o backtracking.

## `src/main/java/tango/solver/BacktrackingSolver.java`

Classe que implementa o backtracking.

O que faz:

- escolher uma celula vazia;
- tentar preencher com Sol ou Lua;
- verificar se a escolha ainda respeita as regras parcialmente;
- desfazer escolhas invalidas;
- continuar recursivamente ate encontrar uma solucao ou esgotar as possibilidades.
- contar atribuicoes candidatas com o mesmo criterio da forca bruta.

Relevancia para o TP2:

- atende a exigencia da estrategia de backtracking;
- demonstra o uso de podas;
- tende a visitar menos estados que a forca bruta pura.

## Resumo das Responsabilidades

```text
Main.java              -> entrada inicial do programa
TangoApplication.java  -> fluxo principal e integracao
InputReader.java       -> leitura do arquivo de entrada
BoardPrinter.java      -> impressao no terminal
InputGenerator.java    -> geracao aleatoria de tabuleiros solucionaveis
BoardCatalog.java      -> catalogo, nomes e persistencia dos arquivos
BoardInputValidator.java -> validacao das entradas antes da busca
Board.java             -> representacao do tabuleiro
CellValue.java         -> valores possiveis das celulas
Position.java          -> coordenadas do tabuleiro
ConstraintType.java    -> tipos de restricao
Constraint.java        -> restricao entre duas posicoes
BoardValidator.java    -> validacao das regras e podas
Solver.java            -> contrato dos algoritmos
SolverType.java        -> tipos de algoritmo aceitos
SolverFactory.java     -> criacao do solver escolhido
SolverResult.java      -> resultado e estatisticas
BruteForceSolver.java  -> algoritmo de forca bruta
BacktrackingSolver.java -> algoritmo de backtracking
```
