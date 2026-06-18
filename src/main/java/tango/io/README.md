# Pacote `tango.io`

Este pacote contem os arquivos relacionados a entrada e saida do programa.

## Arquivo `InputReader.java`

`InputReader.java` le um arquivo de entrada e monta um objeto `Board`.

### O que o arquivo faz

- Le todas as linhas do arquivo informado.
- Procura a linha `size=<numero>`.
- Cria um `Board` com o tamanho informado.
- Procura a secao `board:`.
- Le exatamente `size` linhas do tabuleiro.
- Converte os simbolos das celulas:
  - `.` para vazio;
  - `S` para Sol;
  - `L` para Lua.
- Procura a secao opcional `constraints:`.
- Le restricoes no formato `linha1 coluna1 linha2 coluna2 =|x`.
- Ignora linhas vazias.
- Ignora comentarios iniciados por `#`.

### Formato esperado

```text
size=4
board:
....
.S..
..L.
....
constraints:
0 0 0 1 =
1 2 2 2 x
```

### Relevancia para o trabalho

Este arquivo atende a exigencia de leitura da configuracao inicial do tabuleiro. Ele permite testar tabuleiros diferentes sem alterar o codigo-fonte.

## Arquivo `BoardPrinter.java`

`BoardPrinter.java` imprime um objeto `Board` no terminal.

### O que o arquivo faz

- Imprime os indices das colunas.
- Imprime os indices das linhas.
- Imprime cada celula usando o simbolo definido em `CellValue`.
- Imprime a lista de restricoes.
- Informa quando nao ha restricoes cadastradas.

### Exemplo de saida

```text
   0 1 2 3
0  . . . .
1  . S . .
2  . . L .
3  . . . .
Restricoes:
  (0, 0) = (0, 1)
  (1, 2) x (2, 2)
```

### Relevancia para o trabalho

Este arquivo atende a saida esperada pelo enunciado, pois mostra o tabuleiro inicial e o tabuleiro final no console. A saida tambem pode ser usada na secao de exemplos de execucao da documentacao.

## Arquivo `InputGenerator.java`

`InputGenerator.java` gera um arquivo-base de entrada para o Tango.

### O que o arquivo faz

- Recebe um caminho de destino.
- Recebe o tamanho do tabuleiro.
- Valida se o tamanho e positivo e par.
- Cria a pasta de destino, se ela ainda nao existir.
- Escreve um tabuleiro vazio usando `.`.
- Inclui uma secao `constraints:` com exemplos comentados.

### Formato gerado

```text
size=6
board:
......
......
......
......
......
......
constraints:
# Exemplo: 0 0 0 1 =
# Exemplo: 1 2 2 2 x
```

### Relevancia para o trabalho

Este arquivo facilita criar exemplos de teste. Ele tambem padroniza o formato lido pelo `InputReader`.

