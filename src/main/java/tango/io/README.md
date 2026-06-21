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

### Decisoes logicas do `InputReader`

- **O formato e dividido em secoes:** `size`, `board` e `constraints` deixam cada parte identificavel e permitem que as restricoes sejam opcionais.
- **As coordenadas comecam em zero:** o arquivo usa o mesmo sistema de indices das matrizes Java, evitando conversoes e erros de deslocamento.
- **Comentarios com `#` e linhas vazias sao ignorados:** os arquivos podem conter orientacoes e anotacoes sem afetar a leitura.
- **O tabuleiro exige exatamente `size` linhas e colunas:** a validacao ocorre na entrada para impedir que uma estrutura inconsistente alcance os algoritmos.
- **A conversao de simbolos e delegada aos enums:** `CellValue` e `ConstraintType` decidem quais caracteres sao validos. O leitor nao duplica as regras de representacao do modelo.
- **Erros de leitura viram `IllegalArgumentException`:** para a camada da aplicacao, arquivo inexistente e conteudo invalido sao problemas da entrada fornecida e podem ser apresentados de modo uniforme.

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

### Decisoes logicas do `BoardPrinter`

- **Linhas e colunas mostram indices:** a saida pode ser comparada diretamente com as coordenadas usadas nas restricoes do arquivo.
- **Os simbolos vem do proprio modelo:** o printer usa `getSymbol`, garantindo que leitura e impressao compartilhem a mesma representacao.
- **Grade e restricoes sao impressas juntas:** apenas a matriz nao seria suficiente para reconstruir todo o problema informado ao solver.
- **A ausencia de restricoes e explicita:** imprimir `Restricoes: nenhuma` diferencia corretamente uma lista vazia de uma falha de impressao.

### Relevancia para o trabalho

Este arquivo atende a saida esperada pelo enunciado, pois mostra o tabuleiro inicial e o tabuleiro final no console. A saida tambem pode ser usada na secao de exemplos de execucao da documentacao.

## Arquivo `InputGenerator.java`

`InputGenerator.java` cria um quebra-cabeca aleatorio a partir de uma solucao valida.

### O que o arquivo faz

- Recebe tamanho, quantidade de dicas e quantidade de restricoes.
- Constroi aleatoriamente um tabuleiro completo valido.
- Embaralha as posicoes e mantem somente a quantidade solicitada de dicas.
- Monta a lista de pares vizinhos horizontais e verticais.
- Escolhe pares aleatorios e deriva `=` ou `x` da solucao completa.
- Retorna um `Board` parcial que possui pelo menos a solucao usada na geracao.

### Fluxo da geracao

```text
solucao vazia
  -> preenchimento aleatorio com validacao parcial
  -> solucao completa valida
  -> selecao aleatoria de dicas
  -> selecao aleatoria de pares vizinhos
  -> quebra-cabeca parcial solucionavel
```

### Decisoes logicas do `InputGenerator`

- **Somente tamanhos positivos e pares sao aceitos:** o equilibrio exige metade Sol e metade Lua em cada linha e coluna, o que nao e possivel com dimensao impar.
- **A solucao e criada antes das dicas:** escolher simbolos isoladamente poderia gerar um exemplo contraditorio. Derivar tudo de uma solucao garante pelo menos um resultado possivel.
- **A ordem de `SOL` e `LUA` e embaralhada em cada celula:** execucoes diferentes nao ficam presas ao mesmo padrao de preenchimento.
- **Dicas usam posicoes embaralhadas:** a distribuicao nao favorece sempre o inicio da matriz.
- **Restricoes sao derivadas da solucao:** duas celulas iguais recebem `=` e duas diferentes recebem `x`, portanto a regra nunca contradiz o tabuleiro que originou o exemplo.
- **Somente vizinhos ortogonais entram na lista:** uma restricao do Tango representa um sinal entre duas celulas adjacentes.

### Relevancia para o trabalho

Este arquivo produz entradas mais realistas e permite comparar os algoritmos em exemplos diferentes sem editar arquivos.

## Arquivo `BoardCatalog.java`

`BoardCatalog.java` controla a pasta e a nomenclatura dos arquivos criados pela interface.

### O que o arquivo faz

- Cria `examples/tabuleiros` quando necessario.
- Lista apenas arquivos `.txt` regulares dessa pasta.
- Salva tabuleiros automaticos, manuais e importados.
- Mantem uma sequencia numerica independente para cada origem.
- Inclui data e hora no nome.
- Serializa grade e restricoes no formato de `InputReader`.

### Decisoes logicas do `BoardCatalog`

- **Existe uma unica pasta oficial:** o menu pode listar entradas sem pesquisar todo o projeto nem solicitar caminhos no uso normal.
- **A origem aparece no nome:** e possivel distinguir exemplos aleatorios, digitados e importados sem abrir o arquivo.
- **Sequencias sao independentes:** `automatico-002` significa o segundo automatico mesmo que existam varios arquivos manuais.
- **Data e hora usam `AAAAMMDD-HHMMSS`:** essa ordem e legivel e tambem ordenavel cronologicamente.
- **Arquivos importados sao reescritos:** o catalogo guarda uma versao normalizada depois da validacao, em vez de depender permanentemente do caminho externo.

## Arquivo `BoardInputValidator.java`

`BoardInputValidator.java` impede que uma entrada estruturalmente lida, mas logicamente defeituosa, chegue ao catalogo ou aos solvers.

### O que o arquivo verifica

- restricoes conectam celulas vizinhas na horizontal ou vertical;
- nao existem tres Sois ou tres Luas consecutivos;
- nenhuma linha ou coluna ultrapassa metade de um simbolo;
- restricoes entre duas celulas preenchidas estao satisfeitas.

### Decisoes logicas do `BoardInputValidator`

- **Leitura e validacao logica sao etapas diferentes:** `InputReader` cuida da sintaxe; este validador cuida das regras que ja podem ser avaliadas.
- **Mensagens identificam a categoria do erro:** o usuario sabe se deve corrigir adjacencia, equilibrio ou uma restricao.
- **Vazios continuam permitidos:** uma entrada e um problema ainda nao resolvido. Ela deve ser parcialmente valida, nao necessariamente completa.
- **A validacao ocorre antes de salvar e resolver:** um arquivo rejeitado nao recebe nomenclatura oficial e nao prossegue para o algoritmo.
