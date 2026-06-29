# Pacote `tango.model`

Este pacote contem as classes que representam o problema do Tango. Ele define como o tabuleiro, as celulas e as restricoes ficam armazenados no codigo.

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

### Decisoes logicas do `Board`

- **O tamanho e imutavel e deve ser par:** a dimensao nao muda durante a busca e uma linha impar nao poderia possuir quantidades iguais de Sol e Lua.
- **As celulas usam uma matriz bidimensional:** a estrutura corresponde diretamente a linhas e colunas e oferece acesso em tempo constante por coordenada.
- **Toda celula inicia como `VAZIO`:** isso elimina valores `null` dentro da grade e permite que validadores consultem `isEmpty` com seguranca.
- **As restricoes ficam no proprio tabuleiro:** uma copia ou solucao continua ligada as mesmas regras que definem aquela instancia do problema.
- **`getConstraints` devolve uma lista nao modificavel:** novas restricoes devem passar por `addConstraint`, que valida suas posicoes antes de alterar o estado.
- **`copy` duplica a matriz e a lista:** tentativas de busca podem alterar a copia sem modificar o tabuleiro inicial. Os objetos `Constraint` podem ser compartilhados porque seus campos sao imutaveis.
- **A validacao de posicao e centralizada:** leitura e escrita usam a mesma verificacao de limites, evitando comportamentos diferentes entre os metodos.

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

### Decisoes logicas do `CellValue`

- **Foi usado um enum em vez de `0`, `1` ou boolean:** `SOL`, `LUA` e `VAZIO` comunicam o dominio do problema diretamente e evitam a ambiguidade sobre qual valor binario representa cada simbolo.
- **`VAZIO` e um estado explicito:** o backtracking precisa representar tabuleiros incompletos, mas uma celula vazia nao deve ser confundida com ausencia acidental de objeto (`null`).
- **Cada valor conhece seu simbolo:** a representacao textual fica centralizada e e reutilizada pela entrada e pela saida.
- **A conversao aceita maiusculas e minusculas:** normalizar o caractere torna o arquivo de entrada menos sensivel a digitacao sem aceitar simbolos desconhecidos.

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

### Decisoes logicas do `Position`

- **Linha e coluna formam um objeto imutavel:** uma coordenada usada por uma restricao nao pode mudar acidentalmente durante a execucao.
- **`equals` e `hashCode` consideram as duas coordenadas:** posicoes podem ser comparadas corretamente e tambem usadas futuramente em colecoes como `HashSet` ou `HashMap`.
- **`toString` segue o formato `(linha, coluna)`:** mensagens e depuracao usam a mesma notacao exibida por `BoardPrinter`.

## Arquivo `ConstraintType.java`

`ConstraintType.java` representa o tipo da restricao entre duas celulas.

### Valores existentes

- `EQUAL`: as duas celulas devem ter o mesmo valor, representado por `=`.
- `OPPOSITE`: as duas celulas devem ter valores opostos, representado por `x`.

### O que o arquivo faz

- Guarda o simbolo textual de cada restricao.
- Permite recuperar o simbolo com `getSymbol`.
- Permite converter um caractere em `ConstraintType` com `fromSymbol`.

### Decisoes logicas do `ConstraintType`

- **Igualdade e oposicao sao tipos, nao caracteres espalhados:** os validadores comparam `EQUAL` e `OPPOSITE`, deixando a intencao mais clara.
- **O simbolo pertence ao enum:** parser e printer compartilham `=` e `x` sem duplicar constantes textuais.
- **O `x` e normalizado para minusculo:** tanto `x` quanto `X` podem representar oposicao na entrada.

## Arquivo `Constraint.java`

`Constraint.java` representa uma restricao entre duas posicoes do tabuleiro.

### O que o arquivo faz

- Guarda a primeira posicao.
- Guarda a segunda posicao.
- Guarda o tipo da restricao.
- Impede criar restricoes com posicoes nulas.
- Impede criar restricoes com tipo nulo.

### Decisoes logicas do `Constraint`

- **Uma restricao guarda duas posicoes e um tipo:** esse modelo representa tanto igualdade quanto oposicao sem criar duas classes quase identicas.
- **O objeto e imutavel depois de criado:** regras do problema nao mudam durante a busca, portanto podem ser compartilhadas com seguranca entre copias do tabuleiro.
- **O construtor valida dados nulos e `Board.addConstraint` valida limites:** cada classe verifica a parte que conhece; `Constraint` garante sua propria integridade e `Board` garante compatibilidade com a grade.

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
