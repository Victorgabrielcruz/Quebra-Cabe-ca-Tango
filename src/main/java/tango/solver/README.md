# Pacote `tango.solver`

Este pacote contem os arquivos relacionados aos algoritmos de resolucao.

As classes deste pacote implementam uma interface comum, a selecao do algoritmo, o resultado da busca, a forca bruta e o backtracking.

## Arquivo `Solver.java`

`Solver.java` e uma interface comum para os algoritmos.

### O que o arquivo faz

- Define o metodo `solve(Board initialBoard)`.
- Obriga os algoritmos a receberem um `Board`.
- Obriga os algoritmos a retornarem um `SolverResult`.

### Relevancia para o trabalho

Este arquivo permite que forca bruta e backtracking sejam usados do mesmo jeito pela aplicacao. Isso facilita a comparacao entre as duas tecnicas exigidas pelo enunciado.

### Decisao logica

Foi definida uma interface unica porque a aplicacao deve trocar o algoritmo sem mudar o fluxo de leitura, impressao e exibicao do resultado. O contrato recebe o estado inicial e devolve o mesmo tipo de resultado para qualquer estrategia.

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

### Decisao logica

Os nomes aceitos no terminal ficam associados ao enum para separar texto de interface dos tipos usados pelo codigo. A comparacao ignora maiusculas e minusculas, mas valores desconhecidos sao rejeitados para evitar a execucao silenciosa de um algoritmo diferente do solicitado.

## Arquivo `SolverFactory.java`

`SolverFactory.java` cria o solver correto com base no tipo escolhido.

### O que o arquivo faz

- Recebe um `SolverType`.
- Retorna `BruteForceSolver` quando o tipo e `BRUTE_FORCE`.
- Retorna `BacktrackingSolver` quando o tipo e `BACKTRACKING`.

### Relevancia para o trabalho

Este arquivo centraliza a criacao dos algoritmos. Assim, a aplicacao principal nao precisa saber os detalhes de instanciacao de cada solver.

### Decisao logica

A factory concentra o `switch` que relaciona tipo e implementacao. Isso mantem `TangoApplication` dependente do contrato `Solver` e cria um unico ponto de alteracao caso novos algoritmos sejam adicionados.

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

### Decisoes logicas do resultado

- **Ausencia de solucao e representada por tabuleiro `null`:** `hasSolution` encapsula essa verificacao para que a aplicacao nao precise conhecer o detalhe interno.
- **Estados visitados usam `long`:** buscas exponenciais podem ultrapassar rapidamente o limite de um `int`.
- **Solucao e estatistica viajam juntas:** os dois algoritmos produzem dados no mesmo formato, permitindo comparacao direta.

## Arquivo `BruteForceSolver.java`

`BruteForceSolver.java` implementa o algoritmo de forca bruta.

### O que o arquivo faz

- Implementa a interface `Solver`.
- Copia o tabuleiro inicial para preservar as dicas recebidas.
- Identifica somente as posicoes que estavam vazias no inicio.
- Tenta `SOL` e `LUA` recursivamente em cada uma dessas posicoes.
- Gera as combinacoes sem aplicar podas durante o preenchimento.
- Chama `isFinalValid` quando uma combinacao fica completa.
- Retorna a primeira solucao encontrada ou um resultado sem solucao.
- Conta cada atribuicao candidata realizada.

### Decisoes logicas

- A forca bruta gera o espaco de combinacoes sem usar validacao parcial como poda, pois sua finalidade e representar a busca exaustiva.
- A validacao completa ocorre quando todas as celulas originalmente vazias receberam um valor.
- As dicas iniciais nao devem ser sobrescritas; somente celulas vazias fazem parte das combinacoes.
- O tabuleiro e copiado para impedir que tentativas modifiquem o objeto fornecido pelo usuario.
- O contador incrementa a cada atribuicao candidata testada, usando o mesmo criterio do backtracking para permitir uma comparacao justa.
- Ao esgotar `SOL` e `LUA` em uma posicao, ela volta a `VAZIO`, restaurando o estado anterior da recursao.

### Relevancia para o trabalho

Este arquivo atende a parte de busca exaustiva exigida no enunciado. Ele tambem e usado como referencia para comparar o efeito das podas do backtracking.

## Arquivo `BacktrackingSolver.java`

`BacktrackingSolver.java` implementa o algoritmo de backtracking.

### O que o arquivo faz

- Implementa a interface `Solver`.
- Copia o tabuleiro inicial e lista suas posicoes vazias.
- Rejeita imediatamente uma configuracao inicial que ja viola alguma regra parcial.
- Tenta `SOL` e `LUA` em cada posicao vazia.
- Chama `isPartialValid` depois de cada tentativa.
- Avanca recursivamente somente quando o estado ainda pode produzir uma solucao.
- Desfaz as tentativas quando precisa retroceder.
- Confirma o tabuleiro completo com `isFinalValid`.
- Conta cada atribuicao candidata realizada.

### Decisoes logicas

- O algoritmo constroi a solucao incrementalmente e chama `BoardValidator.isPartialValid` apos cada tentativa.
- Uma tentativa e desfeita para `VAZIO` quando falha, restaurando exatamente o estado anterior antes de retornar na recursao.
- A validacao parcial poda somente estados que ja nao podem levar a uma solucao; celulas ainda vazias nao representam, sozinhas, uma violacao.
- Ao preencher todas as celulas, a solucao passa por `isFinalValid`, que tambem confirma preenchimento e equilibrio exato.
- A ordem de escolha das celulas e dos simbolos altera o desempenho, mas nao deve alterar a corretude nem o conjunto de solucoes possiveis.
- As posicoes sao percorridas por linha e coluna e `SOL` e tentado antes de `LUA`. Essa ordem deterministica facilita a repeticao dos testes.

### Relevancia para o trabalho

Este arquivo atende a parte de backtracking exigida pelo enunciado. Ele sera onde os criterios de poda aparecerao na pratica, reduzindo o espaco de busca em comparacao com a forca bruta.

## Criterio de contagem e comparacao

Nos dois algoritmos, um estado visitado corresponde a uma atribuicao candidata: colocar `SOL` ou `LUA` em uma posicao originalmente vazia. A criacao da copia, a leitura das dicas e a validacao final nao incrementam o contador.

Usar o mesmo criterio permite comparar os valores exibidos pela aplicacao. Em uma execucao com o tabuleiro vazio 4x4 de exemplo, o backtracking visitou 24 estados e a forca bruta visitou 26.528 antes de encontrar a primeira solucao. Os numeros dependem da entrada e da ordem das tentativas, mas mostram o efeito das podas.
