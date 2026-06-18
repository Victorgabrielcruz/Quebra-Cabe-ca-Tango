# Pacote `tango.model`

Este pacote contem as classes que representam o problema do Tango. Ele e a parte central da modelagem feita para o trabalho, pois define como o tabuleiro, as celulas e as restricoes ficam armazenados no codigo.

## Arquivo `Board.java`

`Board.java` representa o tabuleiro do jogo.

### O que o arquivo faz

- Guarda o tamanho do tabuleiro.
- Cria uma matriz de celulas.
- Inicializa todas as celulas como vazias.
- Guarda uma lista de restricoes entre posicoes.
- Permite consultar uma celula com `getCell`.
- Permite alterar uma celula com `setCell`.
- Permite verificar se uma celula esta vazia com `isEmpty`.
- Permite adicionar restricoes com `addConstraint`.
- Permite consultar as restricoes com `getConstraints`.
- Permite criar uma copia do tabuleiro com `copy`.
- Verifica se uma posicao esta dentro dos limites com `isInside`.

### Regras de seguranca do arquivo

- O tamanho do tabuleiro precisa ser positivo.
- O tamanho do tabuleiro precisa ser par.
- Posicoes fora do tabuleiro geram erro.
- Valores de celula nulos geram erro.
- Restricoes nulas geram erro.

### Relevancia para o trabalho

Esta classe e a estrutura principal do problema. Entrada, saida, validacao, forca bruta e backtracking dependem dela para acessar o estado atual do tabuleiro.

## Arquivo `CellValue.java`

`CellValue.java` representa os valores possiveis de uma celula.

### Valores existentes

- `VAZIO`: celula vazia, representada por `.`.
- `SOL`: Sol, representado por `S`.
- `LUA`: Lua, representada por `L`.

### O que o arquivo faz

- Guarda o simbolo textual de cada valor.
- Permite recuperar o simbolo com `getSymbol`.
- Permite verificar se o valor e vazio com `isEmpty`.
- Permite converter um caractere em `CellValue` com `fromSymbol`.

### Relevancia para o trabalho

Este enum evita usar caracteres soltos pelo codigo. Assim, os algoritmos e validadores trabalham com nomes claros, como `SOL`, `LUA` e `VAZIO`.

## Arquivo `Position.java`

`Position.java` representa uma coordenada do tabuleiro.

### O que o arquivo faz

- Guarda a linha.
- Guarda a coluna.
- Disponibiliza `getRow`.
- Disponibiliza `getColumn`.
- Implementa `equals`.
- Implementa `hashCode`.
- Implementa `toString`.

### Relevancia para o trabalho

Esta classe deixa o codigo mais organizado, pois uma posicao passa a ser um objeto com linha e coluna. Ela tambem facilita armazenar restricoes entre duas celulas.

## Arquivo `ConstraintType.java`

`ConstraintType.java` representa o tipo da restricao entre duas celulas.

### Valores existentes

- `EQUAL`: as duas celulas devem ter o mesmo valor, representado por `=`.
- `OPPOSITE`: as duas celulas devem ter valores opostos, representado por `x`.

### O que o arquivo faz

- Guarda o simbolo textual de cada restricao.
- Permite recuperar o simbolo com `getSymbol`.
- Permite converter um caractere em `ConstraintType` com `fromSymbol`.

### Relevancia para o trabalho

Este enum representa diretamente as regras de igualdade e oposicao descritas no enunciado.

## Arquivo `Constraint.java`

`Constraint.java` representa uma restricao entre duas posicoes do tabuleiro.

### O que o arquivo faz

- Guarda a primeira posicao.
- Guarda a segunda posicao.
- Guarda o tipo da restricao.
- Impede criar restricoes com posicoes nulas.
- Impede criar restricoes com tipo nulo.

### Relevancia para o trabalho

Esta classe permite armazenar as relacoes `=` e `x` do tabuleiro de forma clara. Depois, os validadores e algoritmos poderao percorrer a lista de restricoes e verificar se elas estao sendo respeitadas.

## Relacao Entre as Classes

```text
Board
  usa CellValue[][] para guardar as celulas
  usa List<Constraint> para guardar as restricoes

Constraint
  usa Position para indicar as duas celulas envolvidas
  usa ConstraintType para indicar se a regra e de igualdade ou oposicao

CellValue
  define os estados possiveis de cada celula

Position
  define linha e coluna
```

## Resumo da Modelagem

```text
Board.java          -> tabuleiro completo
CellValue.java      -> vazio, Sol ou Lua
Position.java       -> linha e coluna
ConstraintType.java -> igualdade ou oposicao
Constraint.java     -> ligacao entre duas posicoes com um tipo de restricao
```
