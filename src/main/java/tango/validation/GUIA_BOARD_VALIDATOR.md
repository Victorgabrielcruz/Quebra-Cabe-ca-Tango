# Guia de Melhoria do `BoardValidator`

Este guia explica o que ainda precisa ser corrigido e implementado em `BoardValidator.java`.

O objetivo e orientar a implementacao das regras do Tango sem substituir a autoria do integrante responsavel.

## Arquivo Alvo

```text
src/main/java/tango/validation/BoardValidator.java
```

## Objetivo do `BoardValidator`

O `BoardValidator` deve concentrar as regras do Tango:

- preenchimento completo;
- limite de adjacencia;
- equilibrio de linhas;
- equilibrio de colunas;
- restricoes de igualdade `=`;
- restricoes de oposicao `x`;
- validacao parcial para backtracking;
- validacao final para forca bruta.

## Estado Atual

Alguns metodos ja foram iniciados, mas ainda precisam de ajustes importantes:

- `hasNoEmptyCells` esta correto em ideia geral.
- `hasNoThreeEqualAdjacentCells` precisa ignorar celulas vazias.
- `hasValidRowAndColumnBalance` mistura contagem de linha e coluna nos mesmos contadores.
- `hasValidPartialRowAndColumnBalance` tambem mistura contagem de linha e coluna.
- `hasValidConstraints` trata toda restricao como oposicao, mas tambem existe igualdade.
- `isFinalValid` ainda precisa combinar as regras finais.
- `isPartialValid` ainda precisa combinar as regras parciais.

## 1. Corrigir `isFinalValid`

Este metodo deve ser usado quando o tabuleiro estiver completo.

Ele deve retornar verdadeiro somente se todas as regras finais forem verdadeiras.

Regras que deve chamar:

```text
hasNoEmptyCells
hasNoThreeEqualAdjacentCells
hasValidRowAndColumnBalance
hasValidConstraints
```

Orientacao:

- use `&&` para combinar as regras;
- se uma regra falhar, o resultado final deve ser `false`.

## 2. Corrigir `isPartialValid`

Este metodo deve ser usado durante o backtracking.

Ele nao deve exigir que o tabuleiro esteja completo.

Regras que deve chamar:

```text
hasNoThreeEqualAdjacentCells
hasValidPartialRowAndColumnBalance
hasValidConstraints
```

Orientacao:

- nao chame `hasNoEmptyCells`;
- nao chame a validacao final de equilibrio;
- use apenas regras que podem podar uma tentativa ainda incompleta.

## 3. Revisar `hasNoEmptyCells`

Este metodo verifica se ainda existe alguma celula vazia.

Comportamento esperado:

- se encontrar `VAZIO`, retorna `false`;
- se terminar a matriz inteira sem encontrar `VAZIO`, retorna `true`.

Orientacao:

- percorrer todas as linhas;
- percorrer todas as colunas;
- criar `Position`;
- consultar `board.getCell(position)`;
- usar `isVazio()` ou `isEmpty()`.

## 4. Corrigir `hasNoThreeEqualAdjacentCells`

Este metodo verifica se existem tres valores iguais consecutivos.

Problema atual:

- tres celulas vazias tambem sao consideradas iguais.

Exemplo que nao deve reprovar:

```text
. . .
```

Exemplos que devem reprovar:

```text
S S S
L L L
```

Orientacao:

- depois de obter `currentValue`, verifique se ele e vazio;
- se for vazio, use `continue`;
- depois verifique horizontalmente;
- depois verifique verticalmente.

Importante:

- a verificacao deve andar de 1 em 1;
- nao pule de 3 em 3;
- as sequencias invalidas podem comecar em qualquer coluna ou linha.

## 5. Corrigir `hasValidRowAndColumnBalance`

Este metodo e para validacao final.

Problema atual:

- a contagem de linha e coluna esta misturada nos mesmos contadores.

Orientacao:

Use contadores separados:

```text
rowSol
rowLua
columnSol
columnLua
```

Para cada indice `i`:

- conte a linha `i`;
- conte a coluna `i`;
- compare os dois separadamente.

Regra final:

- cada linha deve ter exatamente metade Sol e metade Lua;
- cada coluna deve ter exatamente metade Sol e metade Lua.

Exemplo em tabuleiro 4x4:

```text
2 SOL e 2 LUA em cada linha
2 SOL e 2 LUA em cada coluna
```

Exemplo em tabuleiro 6x6:

```text
3 SOL e 3 LUA em cada linha
3 SOL e 3 LUA em cada coluna
```

Condicao esperada:

```text
se rowSol != expected ou rowLua != expected, retorna false
se columnSol != expected ou columnLua != expected, retorna false
```

## 6. Corrigir `hasValidPartialRowAndColumnBalance`

Este metodo e para poda durante o backtracking.

Problema atual:

- tambem mistura linha e coluna nos mesmos contadores.

Orientacao:

Use contadores separados:

```text
rowSol
rowLua
columnSol
columnLua
```

Regra parcial:

- uma linha ainda pode ter vazios;
- uma coluna ainda pode ter vazios;
- mas nenhuma linha ou coluna pode passar da metade de Sol;
- nenhuma linha ou coluna pode passar da metade de Lua.

Exemplo em tabuleiro 6x6:

```text
S S S S . .
```

Deve reprovar, porque ha 4 Sois e o limite e 3.

Condicao esperada:

```text
se rowSol > limit ou rowLua > limit, retorna false
se columnSol > limit ou columnLua > limit, retorna false
```

## 7. Corrigir `hasValidConstraints`

Este metodo verifica as restricoes cadastradas no tabuleiro.

Problema atual:

- trata tudo como oposicao;
- nao diferencia `=` de `x`;
- pode reprovar restricoes que ainda possuem celulas vazias.

Orientacao:

Percorra diretamente:

```text
para cada constraint em board.getConstraints()
```

Para cada restricao:

1. pegue a primeira celula;
2. pegue a segunda celula;
3. se alguma for vazia, ignore por enquanto;
4. se o tipo for igualdade, os valores devem ser iguais;
5. se o tipo for oposicao, os valores devem ser diferentes.

Regras:

```text
EQUAL:
  primeira celula == segunda celula

OPPOSITE:
  primeira celula != segunda celula
```

Importante:

- restricao com celula vazia nao deve reprovar na validacao parcial;
- na validacao final, `hasNoEmptyCells` ja garante que nao existem vazios.

## Ordem Recomendada de Implementacao

1. Ajustar `hasNoThreeEqualAdjacentCells`.
2. Corrigir `hasValidPartialRowAndColumnBalance`.
3. Corrigir `hasValidRowAndColumnBalance`.
4. Corrigir `hasValidConstraints`.
5. Implementar `isPartialValid`.
6. Implementar `isFinalValid`.

## Testes Manuais Sugeridos

Crie arquivos em `examples/` para verificar cada regra.

### Teste de celula vazia

Um tabuleiro com pelo menos um `.` deve fazer `hasNoEmptyCells` retornar `false`.

### Teste de tres iguais

Linha invalida:

```text
SSS.
....
....
....
```

Coluna invalida:

```text
S...
S...
S...
....
```

### Teste de equilibrio parcial

Em 4x4, esta linha deve reprovar:

```text
SSS.
```

Porque o limite e 2 Sois por linha.

### Teste de equilibrio final

Em 4x4, cada linha e coluna deve ter:

```text
2 SOL
2 LUA
```

### Teste de igualdade

Restricao:

```text
0 0 0 1 =
```

Deve aprovar:

```text
S S
```

Deve reprovar:

```text
S L
```

### Teste de oposicao

Restricao:

```text
0 0 0 1 x
```

Deve aprovar:

```text
S L
```

Deve reprovar:

```text
S S
```

## Checklist de Conclusao

- [ ] `isFinalValid` nao lanca mais excecao.
- [ ] `isPartialValid` nao lanca mais excecao.
- [ ] Tres vazios consecutivos nao reprovam.
- [ ] Tres Sois consecutivos reprovam.
- [ ] Tres Luas consecutivas reprovam.
- [ ] Contagem de linha e coluna e separada.
- [ ] Equilibrio final usa comparacao exata.
- [ ] Equilibrio parcial usa limite maximo.
- [ ] Restricao `=` exige valores iguais.
- [ ] Restricao `x` exige valores diferentes.
- [ ] Restricao com celula vazia nao reprova parcialmente.

