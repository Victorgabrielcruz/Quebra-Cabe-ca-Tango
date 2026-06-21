# Exemplos de Entrada

Os arquivos criados, gerados ou importados pela interface ficam no subdiretorio `tabuleiros/`.

O usuario nao precisa criar arquivos manualmente nesta pasta. Use o menu principal para:

- gerar um tabuleiro automatico aleatorio;
- criar um tabuleiro linha por linha;
- importar e validar um arquivo externo;
- listar e escolher um arquivo para resolver.

## Nomenclatura

```text
automatico-001-AAAAMMDD-HHMMSS.txt
manual-001-AAAAMMDD-HHMMSS.txt
importado-001-AAAAMMDD-HHMMSS.txt
```

O numero e sequencial para cada origem e a parte final registra data e hora.

Os arquivos antigos diretamente em `examples/` sao exemplos de desenvolvimento anteriores ao catalogo. Novos arquivos devem ser mantidos em `examples/tabuleiros/`.

Sugestao de cobertura para os exemplos:

- um tabuleiro pequeno para depuracao;
- um tabuleiro 6x6;
- um tabuleiro com restricoes `=`;
- um tabuleiro com restricoes `x`;
- um tabuleiro propositalmente invalido.
