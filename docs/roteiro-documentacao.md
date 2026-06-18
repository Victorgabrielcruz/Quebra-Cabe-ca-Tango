# Roteiro da Documentacao

Limite do enunciado: no maximo 10 paginas.

Importante: escreva o texto final com suas palavras e com base na sua propria implementacao.

## 1. Modelagem do Problema

Explique:

- como o tabuleiro foi representado;
- como cada celula guarda vazio, Sol ou Lua;
- como as restricoes `=` e `x` foram armazenadas;
- por que essa modelagem ajuda na validacao das regras.

## 2. Estrategia de Resolucao - Forca Bruta

Explique:

- como o algoritmo percorre as celulas vazias;
- como ele gera todas as combinacoes possiveis;
- em qual momento as regras sao verificadas;
- qual e o custo dessa abordagem quando o numero de celulas vazias cresce.

## 3. Estrategia de Resolucao - Backtracking

Explique:

- qual e a funcao recursiva principal;
- qual e o caso base;
- como uma tentativa e feita;
- como o algoritmo desfaz uma tentativa invalida;
- quais regras foram usadas como poda parcial.

## 4. Exemplos de Execucao

Inclua:

- tabuleiro inicial;
- comando usado para executar;
- saida final do terminal;
- comparacao breve entre forca bruta e backtracking, se voce coletar estatisticas.

## 5. Analise de Complexidade

Pontos para discutir:

- se houver `k` celulas vazias, a forca bruta pode gerar ate `2^k` combinacoes;
- o backtracking ainda tem pior caso exponencial;
- as podas reduzem o numero de estados explorados na pratica;
- restricoes de linha, coluna, adjacencia, igualdade e oposicao reduzem o espaco de busca.

## 6. Conclusao

Feche com:

- o que funcionou bem;
- diferenca observada entre forca bruta e backtracking;
- limitacoes da implementacao;
- possiveis melhorias.

