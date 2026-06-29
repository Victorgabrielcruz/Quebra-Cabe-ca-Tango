# Pacote `tango.validation`

Este pacote contem a estrutura para validacao das regras do Tango.

## Arquivo `BoardValidator.java`

`BoardValidator.java` concentra os metodos que verificam se um tabuleiro respeita as regras do problema.

Atualmente, o arquivo possui a validacao final e a validacao parcial implementadas. Os metodos auxiliares separam cada regra para facilitar testes, manutencao e uso pelos algoritmos de solucao.

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

## Decisoes logicas da validacao

### Separacao entre validacao final e parcial

Uma solucao final precisa cumprir simultaneamente todas as cinco regras, inclusive nao possuir celulas vazias e ter equilibrio exato. Durante o backtracking, entretanto, o tabuleiro e construido aos poucos. Por isso, `isPartialValid` permite vazios e rejeita somente uma violacao que ja nao pode ser corrigida pelas proximas escolhas.

Essa decisao nao transforma `VAZIO` em um terceiro simbolo. Ele representa apenas uma posicao ainda nao decidida. Se qualquer vazio fosse reprovado na validacao parcial, o backtracking seria interrompido antes de conseguir completar a primeira solucao.

### Celulas vazias na adjacencia

O limite de adjacencia fala sobre tres Sois ou tres Luas consecutivos. Tres valores `VAZIO` nao representam tres simbolos iguais do jogo. Por isso, `hasNoThreeEqualAdjacentCells` pula a posicao atual quando ela esta vazia e somente compara sequencias iniciadas por `SOL` ou `LUA`.

### Equilibrio final e equilibrio parcial

No estado final, cada linha e coluna deve possuir exatamente `size / 2` Sois e `size / 2` Luas. No estado parcial, exigir igualdade exata reprovaria linhas ainda incompletas. A poda parcial verifica apenas se alguma quantidade ultrapassou `size / 2`, pois esse excesso nao pode ser corrigido preenchendo as celulas restantes.

As contagens de linha e coluna sao independentes. Mistura-las no mesmo contador permitiria que o resultado de uma direcao escondesse ou produzisse incorretamente uma violacao na outra.

### Restricoes ainda indecididas

Uma restricao `=` exige valores iguais e uma restricao `x` exige valores diferentes. A comparacao somente e conclusiva quando as duas celulas estao preenchidas. Se uma delas estiver vazia, ainda existe pelo menos uma escolha capaz de satisfazer a relacao, portanto o estado parcial nao deve ser podado por essa restricao.

Na validacao final isso nao cria permissividade, porque `isFinalValid` chama primeiro `hasNoEmptyCells`. Assim, quando `hasValidConstraints` for avaliado em uma solucao final, todas as relacoes obrigatoriamente terao dois valores definidos.

### Comparacao de enums e retorno antecipado

Os valores sao enums, portanto o codigo usa `==` e `!=` para comparar `CellValue` e `ConstraintType`. Cada regra retorna `false` assim que encontra a primeira violacao, pois depois disso nao e necessario continuar percorrendo o tabuleiro. Se nenhuma violacao for encontrada, retorna `true`.

### Validacao de argumento

Todos os metodos publicos chamam `requireBoard`. Essa verificacao falha imediatamente quando o tabuleiro e nulo e evita que um erro de integracao apareca mais tarde como uma excecao menos clara dentro de um laco.
