# TP2 FPAA - Tango Solver

Projeto em Java para o Trabalho Pratico 2 de FPAA.

## Como organizar o trabalho

O desenvolvimento foi dividido em duas frentes:

1. Codificacao: implementacao do leitor de entrada, modelo do tabuleiro, validadores, forca bruta, backtracking e impressao no terminal.
2. Documentacao: explicacao da modelagem, estrategias de resolucao, exemplos de execucao e analise de complexidade.

Use os roteiros em `docs/` como checklist. Eles foram escritos para orientar a sua propria implementacao e redacao.

Veja tambem `docs/divisao-tarefas.md` para a divisao sugerida entre os integrantes do grupo.

Para entender o fluxo de execucao e a funcao de cada arquivo dentro de `src/`, leia `docs/estrutura-codigo-src.md`.

## Estrutura sugerida

```text
src/
  main/
    java/
      tango/
        Main.java
        model/
        io/
        solver/
        validation/
docs/
  plano-codificacao.md
  roteiro-documentacao.md
examples/
```

## Compilacao manual

Para compilar todas as classes do projeto:

```powershell
javac -d out src/main/java/tango/*.java src/main/java/tango/app/*.java src/main/java/tango/io/*.java src/main/java/tango/model/*.java src/main/java/tango/validation/*.java src/main/java/tango/solver/*.java
java -cp out tango.Main
```

Exemplo de execucao futura com arquivo de entrada:

```powershell
java -cp out tango.Main examples/tabuleiro-6x6.txt backtracking
```
