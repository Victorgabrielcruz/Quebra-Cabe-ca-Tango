# Pacote `tango.solver`

Este pacote contem os arquivos relacionados aos algoritmos de resolucao.

Algumas classes ja estao prontas como estrutura de integracao. As classes dos algoritmos ainda estao como esqueletos para implementacao posterior.

## Arquivo `Solver.java`

`Solver.java` e uma interface comum para os algoritmos.

### O que o arquivo faz

- Define o metodo `solve(Board initialBoard)`.
- Obriga os algoritmos a receberem um `Board`.
- Obriga os algoritmos a retornarem um `SolverResult`.

### Relevancia para o trabalho

Este arquivo permite que forca bruta e backtracking sejam usados do mesmo jeito pela aplicacao. Isso facilita a comparacao entre as duas tecnicas exigidas pelo enunciado.

## Arquivo `SolverType.java`

`SolverType.java` e um enum com os algoritmos aceitos pelo programa.

### O que o arquivo faz

- Define `BRUTE_FORCE`, usado para forca bruta.
- Define `BACKTRACKING`, usado para backtracking.
- Associa cada tipo a um nome digitavel no terminal:
  - `forca-bruta`;
  - `backtracking`.
- Possui o metodo `fromArgument(String value)`, que converte texto em tipo de algoritmo.
- Lanca erro quando o usuario informa um algoritmo desconhecido.

### Relevancia para o trabalho

Este arquivo permite selecionar qual tecnica sera usada durante a execucao. Ele deixa explicito que o projeto suporta as duas abordagens pedidas: forca bruta e backtracking.

## Arquivo `SolverFactory.java`

`SolverFactory.java` cria o solver correto com base no tipo escolhido.

### O que o arquivo faz

- Recebe um `SolverType`.
- Retorna `BruteForceSolver` quando o tipo e `BRUTE_FORCE`.
- Retorna `BacktrackingSolver` quando o tipo e `BACKTRACKING`.

### Relevancia para o trabalho

Este arquivo centraliza a criacao dos algoritmos. Assim, a aplicacao principal nao precisa saber os detalhes de instanciacao de cada solver.

## Arquivo `SolverResult.java`

`SolverResult.java` representa o resultado de uma execucao.

### O que o arquivo faz

- Guarda o tabuleiro resolvido.
- Guarda a quantidade de estados visitados.
- Informa se uma solucao foi encontrada com `hasSolution()`.
- Permite acessar o tabuleiro final com `getSolvedBoard()`.
- Permite acessar a estatistica com `getVisitedStates()`.

### Relevancia para o trabalho

Este arquivo e importante porque o trabalho precisa mostrar o resultado final e tambem pode comparar a quantidade de estados visitados por forca bruta e backtracking. Essa estatistica ajuda na analise de complexidade.

## Arquivo `BruteForceSolver.java`

`BruteForceSolver.java` sera a classe do algoritmo de forca bruta.

### O que o arquivo faz atualmente

- Implementa a interface `Solver`.
- Possui o metodo `solve(Board initialBoard)`.
- Atualmente lanca `UnsupportedOperationException`, pois o algoritmo ainda nao foi implementado.

### O que devera fazer depois

- Gerar todas as combinacoes possiveis para as celulas vazias.
- Validar apenas tabuleiros completos.
- Retornar uma solucao valida, se existir.
- Contar a quantidade de estados testados.

### Relevancia para o trabalho

Este arquivo atende a parte de busca exaustiva exigida no enunciado. Ele tambem sera usado como comparacao contra o backtracking.

## Arquivo `BacktrackingSolver.java`

`BacktrackingSolver.java` sera a classe do algoritmo de backtracking.

### O que o arquivo faz atualmente

- Implementa a interface `Solver`.
- Possui o metodo `solve(Board initialBoard)`.
- Atualmente lanca `UnsupportedOperationException`, pois o algoritmo ainda nao foi implementado.

### O que devera fazer depois

- Escolher uma celula vazia.
- Tentar preencher com Sol ou Lua.
- Aplicar validacoes parciais.
- Desfazer tentativas invalidas.
- Continuar recursivamente ate encontrar uma solucao ou concluir que nao existe solucao.
- Contar a quantidade de estados visitados.

### Relevancia para o trabalho

Este arquivo atende a parte de backtracking exigida pelo enunciado. Ele sera onde os criterios de poda aparecerao na pratica, reduzindo o espaco de busca em comparacao com a forca bruta.

