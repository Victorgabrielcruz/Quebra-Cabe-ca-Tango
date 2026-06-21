# Pacote `tango`

Este pacote contem o ponto de entrada do programa.

## Arquivo `Main.java`

`Main.java` e a primeira classe executada quando o programa roda pelo terminal.

### O que o arquivo faz

- Define o metodo `public static void main(String[] args)`.
- Cria uma instancia de `TangoApplication`.
- Abre o menu interativo quando o programa e executado sem argumentos.
- Repassa os argumentos do terminal para a aplicacao principal quando eles forem informados.
- Trata erros simples de uso, como:
  - algoritmo digitado incorretamente;
  - argumentos invalidos;
- Mostra os modos interativo e por comando.
- Informa a pasta oficial `examples/tabuleiros`.
- Resume o formato aceito para arquivos externos.
- Mostra a tela de ajuda quando o usuario executa:

```powershell
java -cp out tango.Main --help
```

### Fluxo dentro do `Main`

1. O programa inicia no metodo `main`.
2. `Main` cria `TangoApplication`.
3. Se nenhum argumento for informado, chama `application.runInteractive()`.
4. Se houver argumentos, chama `application.run(args)`.
5. Se ocorrer erro de argumento, exibe mensagem de erro e instrucoes de uso.
6. A ajuda mostra o catalogo, o formato externo e os algoritmos disponiveis.

### Decisoes logicas do codigo

- **`Main` possui pouca logica:** a classe apenas decide entre o modo interativo e o modo por argumentos. A coordenacao das funcionalidades fica em `TangoApplication`, evitando concentrar responsabilidades no ponto de entrada.
- **Ausencia de argumentos abre o menu:** essa escolha torna a execucao mais amigavel para testes manuais. Quando existem argumentos, o mesmo programa continua adequado para scripts e execucoes repetidas.
- **Erros esperados sao tratados no limite da aplicacao:** `IllegalArgumentException` representa entradas invalidas e `UnsupportedOperationException` identifica modulos pendentes. Assim, o usuario recebe uma mensagem compreensivel em vez do rastreamento completo da excecao.
- **A ajuda consulta `SolverType.values()`:** os algoritmos exibidos nao ficam duplicados manualmente. Se um novo tipo for adicionado ao enum, ele passa a aparecer automaticamente na ajuda.
- **`printUsage` e publico e estatico:** tanto `Main` quanto o menu de `TangoApplication` podem mostrar exatamente as mesmas instrucoes, mantendo uma unica fonte para o texto de uso.

### Relevancia para o trabalho

Este arquivo e relevante porque organiza a execucao pelo console, que e exatamente a interface pedida no enunciado. O menu interativo facilita o uso do programa durante testes e apresentacao.

## JavaDoc do codigo

Todas as classes de producao possuem JavaDoc e cada pacote contem um arquivo `package-info.java`. A documentacao HTML gerada esta em `docs/javadoc/index.html`.

Para gerar novamente:

```powershell
javadoc -quiet -encoding UTF-8 -docencoding UTF-8 -charset UTF-8 -d docs/javadoc src/main/java/tango/*.java src/main/java/tango/app/*.java src/main/java/tango/io/*.java src/main/java/tango/model/*.java src/main/java/tango/solver/*.java src/main/java/tango/validation/*.java
```
