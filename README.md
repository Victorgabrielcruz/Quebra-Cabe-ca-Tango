# Tango Solver

Este repositorio contem uma implementacao em Java para resolver tabuleiros do jogo Tango. O programa e usado por uma interface no terminal para gerar, criar, importar, listar e resolver tabuleiros.

## Como Executar

Compile os arquivos Java:

```powershell
javac -d out src/main/java/tango/*.java src/main/java/tango/app/*.java src/main/java/tango/io/*.java src/main/java/tango/model/*.java src/main/java/tango/solver/*.java src/main/java/tango/validation/*.java
```

Abra o menu interativo:

```powershell
java -cp out tango.Main
```

Todas as operacoes ficam dentro desse menu. Por ele e possivel escolher arquivos do catalogo, gerar tabuleiros automaticos, criar tabuleiros manualmente, importar arquivos externos e selecionar o algoritmo na hora de resolver.

## Estrutura Principal

```text
tp2/
  src/
    main/
      java/
        tango/
          Main.java
          app/
          io/
          model/
          solver/
          validation/
  examples/
    tabuleiros/
  docs/
  out/
```

## Pastas

`src/main/java/tango` contem o ponto de entrada do programa. `Main.java` inicia a aplicacao e abre o menu interativo.

`src/main/java/tango/app` contem a camada de aplicacao. `TangoApplication.java` coordena o fluxo: menu, leitura, validacao, geracao, catalogo, escolha do algoritmo e exibicao do resultado.

`src/main/java/tango/io` concentra entrada e saida. Essa pasta possui o leitor de arquivos, impressor de tabuleiro, gerador aleatorio, catalogo de arquivos salvos e validador de entradas antes da busca.

`src/main/java/tango/model` contem as classes de modelagem: tabuleiro, celula, posicao e restricoes.

`src/main/java/tango/validation` contem as regras do Tango. `BoardValidator.java` faz validacao final e parcial, incluindo equilibrio, adjacencia e restricoes.

`src/main/java/tango/solver` contem os algoritmos. Ha uma interface comum, uma factory, o resultado da busca, o resolvedor por forca bruta e o resolvedor por backtracking.

`examples` guarda exemplos de entrada. A subpasta `examples/tabuleiros` e o catalogo usado pelo menu para arquivos automaticos, manuais e importados.

`docs` guarda documentacao auxiliar, incluindo a explicacao da estrutura de `src` e a documentação técnica.

`out` contem classes compiladas quando o codigo e compilado com `javac -d out`.

## Formato dos Arquivos de Entrada

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

Regras do formato:

- `size` define o tamanho do tabuleiro.
- `board:` contem exatamente `size` linhas.
- `.` representa celula vazia.
- `S` representa Sol.
- `L` representa Lua.
- `constraints:` e opcional.
- Cada restricao usa `linha1 coluna1 linha2 coluna2 =|x`.
- `=` exige valores iguais.
- `x` exige valores opostos.
- As coordenadas comecam em zero.

## Documentacao Interna

Cada pacote dentro de `src/main/java/tango` possui um `README.md` com detalhes dos arquivos daquela pasta e das decisoes logicas do codigo.

A Documentação técnica:

```text
docs/Documentação Técnica de um Solucionador do Quebra-Cabeça.pdf
```
