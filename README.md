# Pagamento
Trabalho desenvolvido para o primeiro bimestre da matéria de Programação Web. Essa API permite a gestão de jogadores e pagamentos, com a possibilidade de realizar operações CRUD (Criar, Ler, Atualizar, Deletar) sobre ambos os recursos.
## Alunos:
- Lucas Luiz Fernandes RA 21003723
- Matheus Henrique Matos RA 22027723
- Thais Fabiana Haus RA 21014623

## Tecnologias Utilizadas
- Apache Maven para gerenciamento de dependências
- SpringBoot 3.3.4 (starter-parent)
- Java 22
- Visual Studio Code
- Talend API Tester

### Banco de Dados
- PostgreSQL 16
- PgAdmin 4
  
### Dependencias
- Spring Web: Para construção de APIs RESTful e desenvolvimento web.
- Spring Data JPA: Para acesso simplificado a dados e integração com o banco de dados relacional.
- PostgreSQL Driver: Driver de conexão com o banco de dados PostgreSQL.
- Spring Boot DevTools: Ferramenta que facilita o desenvolvimento, permitindo recarregamento automático das mudanças de código em tempo de execução.
- Spring Boot Starter Test: Inclui dependências necessárias para realizar testes no projeto (com escopo de teste).

## Estrutura Geral
A API é dividida em dois principais controladores (PagamentoController e JogadorController), cada um responsável por gerenciar os recursos relacionados a pagamentos e jogadores, respectivamente.

### Controladores
#### PagamentoController:

#### - Base URL: /api/pagamentos
#### - Métodos:
 - GET /pagamentos: Retorna uma lista de pagamentos. Permite filtros por ano e valor. Se não houver filtros, retorna todos os pagamentos.
 - POST /pagamentos/add/{cod_jogador}: Cria um novo pagamento associado a um jogador específico, onde cod_jogador é passado como um parâmetro na URL.
 - GET /pagamentos/{cod_pagamento}: Retorna os detalhes de um pagamento específico identificado pelo seu código (cod_pagamento).
 - PUT /pagamentos/{cod_pagamento}: Atualiza os detalhes de um pagamento específico identificado pelo seu código.
 - DELETE /pagamentos/{cod_pagamento}: Deleta um pagamento específico identificado pelo seu código.
 - DELETE /pagamentos: Deleta todos os pagamentos.

#### JogadorController:

#### - Base URL: /api/jogadores
#### - Métodos:
 - GET /jogadores: Retorna uma lista de jogadores. Permite filtros por nome e email. Se não houver filtros, retorna todos os jogadores.
 - POST /jogadores: Cria um novo jogador com os dados fornecidos no corpo da requisição.
 - GET /jogadores/{cod_jogador}: Retorna os detalhes de um jogador específico identificado pelo seu código (cod_jogador).
 - PUT /jogadores/{cod_jogador}: Atualiza os detalhes de um jogador específico identificado pelo seu código.
 - DELETE /jogadores/{cod_jogador}: Deleta um jogador específico identificado pelo seu código.
 - DELETE /jogadores: Deleta todos os jogadores.

### Modelo de Dados
#### Modelo Jogador:

#### - Campos:
- cod_jogador: Identificador único do jogador (chave primária).
- nome: Nome do jogador.
- email: Email do jogador.
- datanasc: Data de nascimento do jogador.
#### Relacionamento:
- pagamentos: Lista de pagamentos associados ao jogador (relação um-para-muitos).

#### Modelo Pagamento:

#### - Campos:
- cod_pagamento: Identificador único do pagamento (chave primária).
- ano: Ano do pagamento.
- mes: Mês do pagamento.
- valor: Valor do pagamento.
#### Relacionamento:
- cod_jogador: Referência ao jogador que fez o pagamento (relação muitos-para-um).

### Considerações de Implementação
- Exceções:
  Cada método captura exceções e retorna um status HTTP apropriado (ex. HttpStatus.NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR, etc.).
- Anotações:
  A API utiliza várias anotações do Spring, como @RestController, @Autowired, @GetMapping, @PostMapping, etc., para facilitar o desenvolvimento e a configuração do endpoint.
