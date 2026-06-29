# Pacote `tango`

Este pacote contem o ponto de entrada do programa.

## Arquivo `Main.java`

`Main.java` e a primeira classe executada quando o programa roda pelo terminal.

### O que o arquivo faz

- Define o metodo `public static void main(String[] args)`.
- Cria uma instancia de `TangoApplication`.
- Abre o menu interativo.
- Trata erros simples de uso, como:
  - algoritmo digitado incorretamente;
  - entradas invalidas;

### Fluxo dentro do `Main`

1. O programa inicia no metodo `main`.
2. `Main` cria `TangoApplication`.
3. Chama `application.runInteractive()`.
4. Se ocorrer erro esperado, exibe uma mensagem simples.

### Decisoes logicas do codigo

- **`Main` possui pouca logica:** a classe apenas instancia a aplicacao e abre o menu. A coordenacao das funcionalidades fica em `TangoApplication`, evitando concentrar responsabilidades no ponto de entrada.
- **O menu e a unica forma de uso:** todas as operacoes passam pelo mesmo fluxo, incluindo escolha de arquivo, geracao, importacao e selecao de algoritmo.
- **Erros esperados sao tratados no limite da aplicacao:** `IllegalArgumentException` representa entradas invalidas e `UnsupportedOperationException` identifica modulos pendentes. Assim, o usuario recebe uma mensagem compreensivel em vez do rastreamento completo da excecao.

