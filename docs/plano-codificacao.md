# Plano de Codificacao

Este arquivo separa a implementacao em etapas pequenas. Marque cada item quando voce implementar e testar.

## 1. Modelo do Problema

- [ ] Definir como representar celulas vazias, Sol e Lua.
- [ ] Definir como representar a grade `n x n`.
- [ ] Definir como armazenar restricoes entre celulas:
  - igualdade: duas celulas devem ter o mesmo simbolo;
  - oposicao: duas celulas devem ter simbolos opostos.
- [ ] Decidir se o tamanho permitido sera fixo ou variavel.

## 2. Entrada

- [x] Definir o formato do arquivo de entrada.
- [x] Implementar leitura do tamanho do tabuleiro.
- [x] Implementar leitura das celulas iniciais.
- [x] Implementar leitura das restricoes `=` e `x`.
- [x] Validar erros de entrada com mensagens claras.

## 3. Saida

- [x] Imprimir tabuleiro inicial.
- [x] Imprimir restricoes de forma legivel.
- [ ] Imprimir tabuleiro resolvido.
- [ ] Informar quando nao houver solucao.
- [ ] Opcional: imprimir estatisticas, como quantidade de tentativas.

## 4. Validacao das Regras

- [x] Criar pacote e classe de validacao.
- [ ] Regra 1: toda celula final deve estar preenchida.
- [ ] Regra 2: nao permitir tres simbolos iguais consecutivos em linha ou coluna.
- [ ] Regra 3: cada linha e coluna deve ter a mesma quantidade de Sol e Lua.
- [ ] Regra 4: restricoes de igualdade devem ser respeitadas.
- [ ] Regra 5: restricoes de oposicao devem ser respeitadas.
- [ ] Separar validacao parcial de validacao final.

## 5. Forca Bruta

- [ ] Gerar todas as combinacoes possiveis para celulas vazias.
- [ ] Validar o tabuleiro somente quando estiver completo.
- [ ] Retornar a primeira solucao valida ou listar todas, conforme sua decisao.
- [ ] Registrar a quantidade de estados testados.

## 6. Backtracking

- [ ] Escolher a proxima celula vazia.
- [ ] Tentar Sol e Lua.
- [ ] Aplicar validacao parcial logo apos cada tentativa.
- [ ] Desfazer a tentativa quando ela nao levar a uma solucao.
- [ ] Definir a condicao de parada: tabuleiro completo e valido.
- [ ] Registrar a quantidade de estados visitados.

## 7. Testes Manuais

- [ ] Criar um exemplo pequeno.
- [ ] Criar um exemplo 6x6.
- [ ] Testar caso sem solucao.
- [ ] Comparar quantidade de tentativas entre forca bruta e backtracking.
