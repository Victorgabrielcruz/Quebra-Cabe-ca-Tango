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
  - modulos que ainda nao foram implementados.
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
6. Se alguma parte ainda estiver pendente, exibe uma mensagem informando que aquele modulo ainda precisa ser implementado.

### Relevancia para o trabalho

Este arquivo e relevante porque organiza a execucao pelo console, que e exatamente a interface pedida no enunciado. O menu interativo facilita o uso do programa durante testes e apresentacao.
