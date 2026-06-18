# Pacote `tango.validation`

Este pacote contem a estrutura para validacao das regras do Tango.

## Arquivo `BoardValidator.java`

`BoardValidator.java` concentra os metodos que verificam se um tabuleiro respeita as regras do problema.

Atualmente, o arquivo esta preparado com assinaturas de metodos e mensagens de pendencia. A logica interna deve ser implementada pelo integrante responsavel pelas regras e podas.

## Metodos Principais

### `isFinalValid(Board board)`

Deve ser usado quando o tabuleiro estiver completo.

Regras esperadas:

- todas as celulas preenchidas;
- nenhuma sequencia de tres simbolos iguais na horizontal ou vertical;
- cada linha com metade Sol e metade Lua;
- cada coluna com metade Sol e metade Lua;
- todas as restricoes `=` e `x` respeitadas.

### `isPartialValid(Board board)`

Deve ser usado durante o backtracking.

Regras esperadas:

- nao pode haver tres simbolos iguais consecutivos ja formados;
- uma linha nao pode ter mais Sois ou Luas que o limite permitido;
- uma coluna nao pode ter mais Sois ou Luas que o limite permitido;
- restricoes entre celulas ja preenchidas precisam ser respeitadas;
- restricoes com alguma celula vazia ainda nao devem reprovar automaticamente.

## Metodos Auxiliares

### `hasNoEmptyCells(Board board)`

Verifica a regra de preenchimento completo.

### `hasNoThreeEqualAdjacentCells(Board board)`

Verifica se nao existem tres simbolos iguais consecutivos em linhas ou colunas.

### `hasValidRowAndColumnBalance(Board board)`

Verifica o equilibrio final de linhas e colunas.

### `hasValidPartialRowAndColumnBalance(Board board)`

Verifica a poda parcial de equilibrio.

### `hasValidConstraints(Board board)`

Verifica restricoes de igualdade e oposicao.

## Relevancia para o TP2

Este pacote e importante porque separa as regras do jogo da mecanica dos algoritmos. A forca bruta podera usar `isFinalValid`, enquanto o backtracking podera usar `isPartialValid` para podar estados invalidos antes de completar o tabuleiro.

