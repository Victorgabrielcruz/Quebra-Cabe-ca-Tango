# Pacote `tango.app`

Este pacote contem a camada que coordena o fluxo principal da aplicacao.

## Arquivo `TangoApplication.java`

`TangoApplication.java` e responsavel por conectar os modulos do programa.

### O que o arquivo faz

- Recebe os argumentos vindos de `Main`.
- Abre um menu interativo quando o programa roda sem argumentos.
- Valida se a quantidade de argumentos esta correta.
- Lista os arquivos existentes no catalogo interno.
- Gera tabuleiros automaticos aleatorios.
- Permite montar tabuleiros manualmente pela interface.
- Importa arquivos externos depois de valida-los.
- Define qual algoritmo sera usado:
  - se o usuario informar um algoritmo, usa o algoritmo informado;
  - se nao informar, usa `backtracking` como padrao.
- Chama `InputReader` para carregar o tabuleiro.
- Chama `BoardInputValidator` antes de salvar ou resolver.
- Chama `BoardCatalog` para salvar e listar os exemplos.
- Chama `BoardPrinter` para imprimir o tabuleiro inicial.
- Chama `SolverFactory` para criar o solver correto.
- Executa o solver escolhido.
- Imprime o tabuleiro resolvido ou informa que nao encontrou solucao.
- Exibe a quantidade de estados visitados.
- Salva todos os arquivos criados pela interface em `examples/tabuleiros`.

### Fluxo dentro do `TangoApplication`

1. `runInteractive()` exibe o menu quando nao ha argumentos.
2. No menu, o usuario pode selecionar um arquivo listado, gerar automaticamente, criar manualmente, importar, ver ajuda ou sair.
3. `run(args)` recebe os argumentos quando o modo por comando e usado.
4. Se for ajuda, chama `Main.printUsage()`.
5. Se houver argumentos demais, lanca erro.
6. Converte o primeiro argumento em `Path`.
7. Converte o segundo argumento em `SolverType`, quando existir.
8. Le o tabuleiro inicial com `InputReader`.
9. Valida estrutura, regras parciais e restricoes da entrada.
10. Cria o algoritmo com `SolverFactory`.
11. Imprime o tabuleiro inicial.
12. Executa o algoritmo e imprime o resultado.

### Decisoes logicas do codigo

- **A aplicacao coordena, mas nao implementa as regras:** leitura, impressao, geracao e resolucao sao delegadas a classes especializadas. Isso reduz o acoplamento e permite alterar um modulo sem reescrever o fluxo inteiro.
- **O catalogo substitui a digitacao de caminhos no fluxo normal:** ao resolver pelo menu, o usuario escolhe um numero associado a um arquivo existente.
- **A importacao externa continua disponivel:** flexibilidade foi mantida, mas o arquivo somente entra no catalogo depois de passar pelo leitor e pelo validador.
- **Criacao manual ocorre dentro da interface:** linhas e restricoes sao solicitadas pelo programa; o usuario nao precisa conhecer pastas nem criar o arquivo em um editor.
- **Geracao automatica e configuravel:** tamanho, quantidade de dicas e quantidade de restricoes sao informados no menu.
- **Backtracking e o algoritmo padrao:** quando o segundo argumento nao e informado, escolhe-se a estrategia que devera aplicar podas e normalmente visitar menos estados que a forca bruta.
- **A criacao do solver passa pela factory:** `TangoApplication` depende da interface `Solver` e nao precisa de condicionais para instanciar cada algoritmo.
- **O tabuleiro inicial e impresso antes da busca:** isso permite conferir dicas e restricoes carregadas e comparar visualmente a entrada com a solucao.
- **O resultado informa estados visitados mesmo sem solucao:** a estatistica continua util para comparar o custo da busca e discutir complexidade.
- **Erros de cada interacao sao capturados dentro do menu:** uma opcao ou arquivo invalido nao encerra todo o programa; o usuario pode corrigir a entrada e tentar novamente.
- **Validacao acontece antes da persistencia e da busca:** exemplos contraditorios nao recebem nome no catalogo e nao chegam aos solvers.
